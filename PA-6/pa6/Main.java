package pa6;

import pa6.controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pa6.model.Direction;
import pa6.model.Node;
import pa6.model.ParentHelper;
import pa6.model.SequencePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main extends Application {

    // Prepare the GUI
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("view/pa6-gui-main.fxml"));
        Controller controller = new Controller();
        fxmlLoader.setController(controller);
        Parent layout = fxmlLoader.load();
        Scene scene = new Scene(layout);
        primaryStage.setTitle("PA-6");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Display the GUI and run the program
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Included as an inner class to make finding the 'main' code portion for pa-6 easier reader
     *
     * NodeMatrix creates a sequence scoring matrix and using dynamic programming to determine the
     * optimum alignments.  The alignments are then displayed to the user using the NodeMatrixView
     * class.  Each node contains a field that allows for saving the parent node, (the node at either
     * the left, above or diagonal), the score came from.  This is then used to backtrack and find
     * each of the optimally aligned sequences.
     */
    public static class NodeMatrix {
        private final int _match;
        private final int _mismatch;
        private final int _gap;
        private final String[] _rowSequence;
        private final String[] _colSequence;

        // List of aligner's with the best scores
        private final ArrayList<ArrayList<SequencePair>> _optimumSequenceAlignments;

        private Node[][] _dpMatrix;

        /**
         * The 2D grid of Node objects
         *
         * @param rowSequence A sequence entered by the user.  Cannot be a the empty string
         * @param colSequence A sequence entered by the user.  Cannot be a the empty string
         * @param match Score for sequence matches
         * @param mismatch Score for sequence mismatches
         * @param gap Score for inserting a 'gap' into a sequence if it is aligned sequences of different lengths
         */
        public NodeMatrix(String[] rowSequence, String[] colSequence, int match, int mismatch, int gap) {
            _rowSequence = rowSequence;
            _colSequence = colSequence;
            _match = match;
            _mismatch = mismatch;
            _gap = gap;
            _optimumSequenceAlignments = new ArrayList<>();

            // Create the matrix
            initMatrix();
            populateMatrix();
            findOptimumSequenceAlignments();
            System.out.println();
        }

        public ArrayList<ArrayList<SequencePair>> getOptimumSequenceAlignments() {
            return _optimumSequenceAlignments;
        }

        public Node getNode(int i, int j) {
            return _dpMatrix[i][j];
        }

        public int getRowSize() {
            return _dpMatrix.length;
        }

        public int getColSize() {
            return _dpMatrix[0].length;
        }

        private void initMatrix() {
            _dpMatrix = new Node[_rowSequence.length + 2][_colSequence.length + 2];
            fillHeaders();
            fillStartingValues();
        }

        // Fills row and col header matrix nodes with the either a "" square or
        // the character of the corresponding sequence element
        private void fillHeaders() {
            _dpMatrix[0][0] = new Node("", 0, 0);
            _dpMatrix[1][0] = new Node("", 1, 0);
            _dpMatrix[0][1] = new Node("", 0, 1);
            for (int i = 2; i < _dpMatrix.length; i++) {
                _dpMatrix[i][0] = new Node(_rowSequence[i - 2], i, 0);
                for (int j = 2; j < _dpMatrix[0].length; j++) {
                    _dpMatrix[0][j] = new Node(_colSequence[j - 2], 0, j);
                }
            }
        }

        // Fill matrix with initial GAP score values
        private void fillStartingValues() {
            for (int i = 1; i < _dpMatrix.length; i++) {
                _dpMatrix[i][1] = new Node((i - 1) * -2, i, 1);
                for (int j = 1; j < _dpMatrix[0].length; j++) {
                    _dpMatrix[1][j] = new Node((j - 1) * -2, 1, j);
                }
            }
        }

        // Traverse the matrix and determine best score using the provided match, mismatch and gap values using the
        // dynamic programming algorithm discussed in class
        private void populateMatrix() {
            for (int i = 2; i < _dpMatrix.length; i++) {
                for (int j = 2; j < _dpMatrix[0].length; j++) {

                    // Find best match (the one with that gives the highest score). The new score and its origin are
                    // saved in the ParentHelper class
                    ArrayList<ParentHelper> bestAlignment = findBestParentValue(i, j);

                    // Map is used to track where the score originated from (LEFT, UP, DIAG) and which node its from
                    HashMap<Direction, Node> parentNodeMap = new HashMap<>();

                    // Populate the HashMap containing the Direction and the parent nodes that would provide the best score
                    for (ParentHelper o : bestAlignment) parentNodeMap.put(o.getDirection(), o.getNode());

                    // Creat a new node and use the highest score as its value.
                    Node node = new Node(bestAlignment.get(0).getValue(), i, j);

                    // Save the node's parent node(s)
                    node.setParentNodes(parentNodeMap);

                    // Add the node to the matrix
                    _dpMatrix[i][j] = node;
                }
            }
        }

        /**
         * Compares node values from each of three possible parent sources: LEFT, UP, DIAG.  Allows ties.
         *
         * @param row current row value in the DP matrix
         * @param col current row value in the DP matrix
         * @return List of parent nodes with the highest score
         */
        private ArrayList<ParentHelper> findBestParentValue(int row, int col) {
            ArrayList<ParentHelper> bestMatches = new ArrayList<>();
            int max = Integer.MIN_VALUE;

            for (ParentHelper o : gatherParentNodes(row, col)) {
                if (o.getValue() >= max) {
                    if (o.getValue() > max) {
                        bestMatches.clear();
                        max = o.getValue();
                    }
                    bestMatches.add(o);
                }
            }
            return bestMatches;
        }

        /**
         * Gathers current node's parent nodes from LEFT, UP and DIAG
         *
         * @param row current row value in the DP matrix
         * @param col current row value in the DP matrix
         * @return List of parent nodes with the highest score
         */
        private List<ParentHelper> gatherParentNodes(int row, int col) {
            // Use either the match or mismatch modifier depending on the sequence values
            int diagValue = _dpMatrix[row - 1][col - 1].getValue();
            diagValue += _rowSequence[row - 2].equals(_colSequence[col - 2]) ? _match : _mismatch;

            // Create three parent Node objects and add the score modifiers to their respective values to determine
            // where to derive the score for the current node.
            // Each object references a neighboring node in the matrix that is either to the left, above or
            // diagonally adjacent to the current node.
            ParentHelper diagParent = new ParentHelper(Direction.DIAG, diagValue, _dpMatrix[row - 1][col - 1]);
            ParentHelper leftParent = new ParentHelper(Direction.LEFT, _dpMatrix[row][col - 1].getValue() + _gap,
                    _dpMatrix[row][col - 1]);
            ParentHelper upParent = new ParentHelper(Direction.UP, _dpMatrix[row - 1][col].getValue() + _gap,
                    _dpMatrix[row - 1][col]);
            return Arrays.asList(leftParent, upParent, diagParent);
        }

        // Starting backtracking process to find the best sequence alignments.  Starts at the final position in the DP
        // matrix
        private void findOptimumSequenceAlignments() {
            _dpMatrix[_dpMatrix.length - 1][_dpMatrix[0].length - 1].setOptimumType();
            Node node = _dpMatrix[_dpMatrix.length - 1][_dpMatrix[0].length - 1];
            backTrack(node, new ArrayList<>());
        }

        // Backtrack through the dynamic programming matrix to collect the final solution(s)
        // When the end of the matrix is reached each the sequenceAlignment list is updated
        private void backTrack(Node node, ArrayList<SequencePair> pairs) {
            if (getNode(node.getRow(), node.getCol()).getParentOrigin().containsKey(Direction.LEFT)) {
                Node parent = _dpMatrix[node.getRow()][node.getCol()].getParentOrigin().get(Direction.LEFT);
                SequencePair sequencePair = new SequencePair(Direction.LEFT, _rowSequence[node.getRow() - 2], _colSequence[node.getCol() - 2]);
                backTrack(parent, determineSequencePair(parent, sequencePair, pairs));
            }
            if (getNode(node.getRow(), node.getCol()).getParentOrigin().containsKey(Direction.UP)) {
                Node parent = _dpMatrix[node.getRow()][node.getCol()].getParentOrigin().get(Direction.UP);
                SequencePair sequencePair = new SequencePair(Direction.UP, _rowSequence[node.getRow() - 2], _colSequence[node.getCol() - 2]);
                backTrack(parent, determineSequencePair(parent, sequencePair, pairs));
            }
            if (getNode(node.getRow(), node.getCol()).getParentOrigin().containsKey(Direction.DIAG)) {
                Node parent = _dpMatrix[node.getRow()][node.getCol()].getParentOrigin().get(Direction.DIAG);
                SequencePair sequencePair = new SequencePair(Direction.DIAG, _rowSequence[node.getRow() - 2], _colSequence[node.getCol() - 2]);
                backTrack(parent, determineSequencePair(parent, sequencePair, pairs));
            }
            if (hasReachedEnd(node)) {
                fillGaps(pairs);
                _optimumSequenceAlignments.add(pairs);
            }
        }

        /**
         * Fills in gaps for aligning that are not of equal size.  Allows for more freedom with GAP placement
         * <p>
         * For example, the sequences CAT and Z, Z can be positioned; Z__, _Z_, and __Z even though any one of the will
         * give the best score
         *
         * @param pairs updated best aligned sequence pairs
         */
        private void fillGaps(ArrayList<SequencePair> pairs) {
            if (pairs.size() < Math.max(_rowSequence.length, _colSequence.length)) {
                if (_rowSequence.length < _colSequence.length) {
                    for (int j = _colSequence.length - pairs.size() - 1; j >= 0; j--) {
                        pairs.add(0, new SequencePair("_", _colSequence[j]));
                    }
                } else {
                    for (int j = _rowSequence.length - pairs.size() - 1; j >= 0; j--) {
                        pairs.add(0, new SequencePair(_rowSequence[j], "_"));
                    }
                }
            }
        }

        /**
         * Add previously backtracked nodes to the current solution
         *
         * @param sequencePair  The current sequence pairs for the alignment
         * @param sequencePairs Previously determined sequences pairs for the alignment
         * @return an updated list containing all current sequence pairs to be used in the next backtracking step
         */
        private ArrayList<SequencePair> determineSequencePair(Node parent,
                                                              SequencePair sequencePair,
                                                              ArrayList<SequencePair> sequencePairs) {
            _dpMatrix[parent.getRow()][parent.getCol()].setOptimumType();
            ArrayList<SequencePair> updatedSequences = new ArrayList<>(sequencePairs);
            // since its from the bottom up, insert at beginning so the sequences aren't backwards
            updatedSequences.add(0, sequencePair);
            return updatedSequences;
        }

        /**
         * Returns true when the end of the scoring matrix has been reached
         *
         * @param node current node placement in the DP matrix
         * @return true if the end matrix boundary has been reached
         */
        private boolean hasReachedEnd(Node node) {
            return node.getParentOrigin().isEmpty();
        }

        // Get the max score from the completed matrix
        public int findMaxScore() {
            return _dpMatrix[getRowSize() - 1][getColSize() - 1].getValue();
        }
    }
}