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

    private final ArrayList<ArrayList<SequencePair>> optimumSequences;

    public NodeMatrix(int match, int mismatch, int gap) {
        _match = match;
        _mismatch = mismatch;
        _gap = gap;
        optimumSequences = new ArrayList<>();
    }

    public ArrayList<ArrayList<SequencePair>> getOptimumSequences() {
        return optimumSequences;
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

    public int findMaxScore() {
        return _dpMatrix[getRowSize()-1][getColSize()-1].getValue();
    }

    public void createMatrix() {
        initMatrix();
        assignValues();
        findOptimumSolution();
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

    // Traverse Matrix and determine best score using the provided match, mismatch and gap values
    private void assignValues() {
        for (int i = 2; i < _dpMatrix.length; i++) {
            for (int j = 2; j < _dpMatrix[0].length; j++) {

                // Use either the match or mismatch depending on the sequence values
                int diagValue = _dpMatrix[i - 1][j - 1].getValue();
                diagValue += _row[i - 2].equals(_col[j - 2]) ? _match : _mismatch;

                // Create three parent Node objects and add the score modifiers to their respective values to determine
                // where to derive the score for the current node.
                // Each object references a neighboring node in the matrix that is either to the left, above or
                // diagonally adjacent to the current node.
                ParentNode diag = new ParentNode(
                        Direction.DIAG,
                        diagValue,
                        _dpMatrix[i - 1][j - 1]
                );
                ParentNode left = new ParentNode(
                        Direction.LEFT,
                        _dpMatrix[i][j - 1].getValue() + _gap,
                        _dpMatrix[i][j - 1]
                );
                ParentNode up = new ParentNode(
                        Direction.UP,
                        _dpMatrix[i - 1][j].getValue() + _gap,
                        _dpMatrix[i - 1][j]
                );

                // Find best match
                ArrayList<ParentNode> bestMatches = findMaxValue(Arrays.asList(left, up, diag));
                HashMap<Direction, Node> directionNodeHashMap = new HashMap<>();

                // Use the best values for the new node and save the Parent from whom they were derived
                for (ParentNode o : bestMatches) directionNodeHashMap.put(o.getDirection(), o.getNode());
                Node node = new Node(bestMatches.get(0).getValue(), i, j);
                node.setParentOrigin(directionNodeHashMap);
                _dpMatrix[i][j] = node;
            }
        }
    }

    // Compares node scores and their origins.  Allows ties
    private ArrayList<ParentNode> findMaxValue(List<ParentNode> parentNodes) {
        ArrayList<ParentNode> bestMatches = new ArrayList<>();
        int max = Integer.MIN_VALUE;
        for (ParentNode o : parentNodes) {
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

    // Starting backtracking process to find the best sequence alignments.  Starts at the final position in the DP
    // matrix
    private void findOptimumSolution() {
        _dpMatrix[_dpMatrix.length - 1][_dpMatrix[0].length - 1].setOptimumType();
        Node node = _dpMatrix[_dpMatrix.length - 1][_dpMatrix[0].length - 1];
        backTracking(node, new ArrayList<>());
    }

    // Backtrack through the Dynamic Programming Matrix to collect the final solution(s)
    private void backTracking(Node node, ArrayList<SequencePair> pairs) {
        if (getNode(node.getRow(), node.getCol()).getParentOrigin().containsKey(Direction.LEFT)) {
            Node parent = _dpMatrix[node.getRow()][node.getCol()].getParentOrigin().get(Direction.LEFT);
            SequencePair sequencePair = new SequencePair(Direction.LEFT, _row[node.getRow() - 2], _col[node.getCol() - 2]);
            backTracking(parent, getOptimumNodes(parent, sequencePair, pairs));
        }
        if (getNode(node.getRow(), node.getCol()).getParentOrigin().containsKey(Direction.UP)) {
            Node parent = _dpMatrix[node.getRow()][node.getCol()].getParentOrigin().get(Direction.UP);
            SequencePair sequencePair = new SequencePair(Direction.UP, _row[node.getRow() - 2], _col[node.getCol() - 2]);
            backTracking(parent, getOptimumNodes(parent, sequencePair, pairs));
        }
        if (getNode(node.getRow(), node.getCol()).getParentOrigin().containsKey(Direction.DIAG)) {
            Node parent = _dpMatrix[node.getRow()][node.getCol()].getParentOrigin().get(Direction.DIAG);
            SequencePair sequencePair = new SequencePair(Direction.DIAG, _row[node.getRow() - 2], _col[node.getCol() - 2]);
            backTracking(parent, getOptimumNodes(parent, sequencePair, pairs));
        }
        if (hasReachedEnd(node)) {
            optimumSequences.add(pairs);
        }
    }

    // Add previously backtracked nodes to the current solution
    private ArrayList<SequencePair> getOptimumNodes(Node node, SequencePair sequencePair, ArrayList<SequencePair> nodes) {
        _dpMatrix[node.getRow()][node.getCol()].setOptimumType();
        ArrayList<SequencePair> updatedSequences = new ArrayList<>(nodes);
        updatedSequences.add(0, sequencePair);
        return updatedSequences;
    }

    // Returns true when the end of the scoring matrix has been reached
    private boolean hasReachedEnd(Node node) {
        return node.getCol() == 1 && node.getRow() == 1 || node.getCol() == 2 && node.getRow() == 1 ||
                node.getCol() == 1 && node.getRow() == 2;
    }
}
