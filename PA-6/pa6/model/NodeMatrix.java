package pa6.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class NodeMatrix {
    private String[] _row;
    private String[] _col;

    private Node[][] _dpMatrix;//
    private int _match;
    private int _mismatch;
    private int _gap;

    private final ArrayList<ArrayList<SequencePair>> _optimumSequenceAlignments;

    public NodeMatrix(int match, int mismatch, int gap) {
        _match = match;
        _mismatch = mismatch;
        _gap = gap;
        _optimumSequenceAlignments = new ArrayList<>();
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

    public void setSequences(String[] row, String[] col) {
        _row = row;
        _col = col;
    }

    public void createMatrix() {
        initMatrix();
        assignMatrixValues();
        findOptimumSequenceAlignments();
    }

    private void initMatrix() {
        _dpMatrix = new Node[_row.length + 2][_col.length + 2];
        fillHeaders();
        fillStartingValues();
    }

    // Fills Row and Col header matrix nodes with the either a "" square or
    // the character of the corresponding sequence element
    private void fillHeaders() {
        _dpMatrix[0][0] = new Node("", 0, 0);
        _dpMatrix[1][0] = new Node("", 1, 0);
        _dpMatrix[0][1] = new Node("", 0, 1);
        for (int i = 2; i < _dpMatrix.length; i++) {
            _dpMatrix[i][0] = new Node(_row[i - 2], i, 0);
            for (int j = 2; j < _dpMatrix[0].length; j++) {
                _dpMatrix[0][j] = new Node(_col[j - 2], 0, j);
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

    // Traverse Matrix and determine best score using the provided match, mismatch and gap values using the
    // dynamic programming algorithm discussed in class
    private void assignMatrixValues() {
        for (int i = 2; i < _dpMatrix.length; i++) {
            for (int j = 2; j < _dpMatrix[0].length; j++) {
                // Find best match
                ArrayList<ParentHelper> bestAlignment = findBestParentValue(i, j);
                HashMap<Direction, Node> parentNodeMap = new HashMap<>();

                // Use the best values for the new node and save the Parent from whom they were derived
                for (ParentHelper o : bestAlignment) parentNodeMap.put(o.getDirection(), o.getNode());
                Node node = new Node(bestAlignment.get(0).getValue(), i, j);
                node.setParentOrigin(parentNodeMap);
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
        diagValue += _row[row - 2].equals(_col[col - 2]) ? _match : _mismatch;

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

    // Backtrack through the Dynamic Programming Matrix to collect the final solution(s)
    // When the end of the matrix is reached each the sequenceAlignment list is updated
    private void backTrack(Node node, ArrayList<SequencePair> pairs) {
        if (getNode(node.getRow(), node.getCol()).getParentOrigin().containsKey(Direction.LEFT)) {
            Node parent = _dpMatrix[node.getRow()][node.getCol()].getParentOrigin().get(Direction.LEFT);
            SequencePair sequencePair = new SequencePair(Direction.LEFT, _row[node.getRow() - 2], _col[node.getCol() - 2]);
            backTrack(parent, determineSequencePair(parent, sequencePair, pairs));
        }
        if (getNode(node.getRow(), node.getCol()).getParentOrigin().containsKey(Direction.UP)) {
            Node parent = _dpMatrix[node.getRow()][node.getCol()].getParentOrigin().get(Direction.UP);
            SequencePair sequencePair = new SequencePair(Direction.UP, _row[node.getRow() - 2], _col[node.getCol() - 2]);
            backTrack(parent, determineSequencePair(parent, sequencePair, pairs));
        }
        if (getNode(node.getRow(), node.getCol()).getParentOrigin().containsKey(Direction.DIAG)) {
            Node parent = _dpMatrix[node.getRow()][node.getCol()].getParentOrigin().get(Direction.DIAG);
            SequencePair sequencePair = new SequencePair(Direction.DIAG, _row[node.getRow() - 2], _col[node.getCol() - 2]);
            backTrack(parent, determineSequencePair(parent, sequencePair, pairs));
        }
        if (hasReachedEnd(node)) {
            _optimumSequenceAlignments.add(pairs);
        }
    }

    /**
     * Add previously backtracked nodes to the current solution
     *
     * @param parent        The current nodes parent during backtracking
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
        return node.getCol() == 1 && node.getRow() == 1 || node.getCol() == 2 && node.getRow() == 1 ||
                node.getCol() == 1 && node.getRow() == 2;
    }

    // Get the max score from the completed matrix
    public int findMaxScore() {
        return _dpMatrix[getRowSize() - 1][getColSize() - 1].getValue();
    }
}
