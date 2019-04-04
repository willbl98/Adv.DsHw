package pa6.model;

import java.util.HashMap;

/**
 * Represents an element from the sequence comparision in the dynamic programming matrix
 */
public class Node {
    // Row and column placement in the DP matrix
    private final int _row;
    private final int _col;

    // The calculated 'best' score from evaluating the LEFT, UP and DIAG score
    private int _value;

    // Holds the Parent node and its orientation in the matrix with respect to the current node
    private HashMap<Direction, Node> _parentOrigin;

    // Values used during visual representation.  _letter is the character or value that is seen on each square
    private String _letter;
    // Marks a node as part of the final solution or a placeholder for the visual presentation
    private NodeType _type;

    // 'Main' nodes that are used during calculation
    public Node(int value, int row, int col) {
        _value = value;
        _row = row;
        _col = col;
        _parentOrigin = new HashMap<>();
        _letter = String.valueOf(_value);
        initType(row, col);
    }

    // Header nodes that show information for the GUI
    public Node(String letter, int row, int col) {
        _row = row;
        _col = col;
        _parentOrigin = new HashMap<>();
        _letter = letter;
        initType(row, col);
    }

    public int getRow() {
        return _row;
    }

    public int getCol() {
        return _col;
    }

    public int getValue() {
        return _value;
    }

    public HashMap<Direction, Node> getParentOrigin() {
        return _parentOrigin;
    }

    public void setParentNodes(HashMap<Direction, Node> origin) {
        _parentOrigin = origin;
    }

    public String getLetter() {
        return _letter;
    }

    public NodeType getType() {
        return _type;
    }

    public void setOptimumType() {
        _type = NodeType.OPTIMUM;
    }

    // Determine Node type based on location in the matrix
    private void initType(int row, int col) {
        if (row > 0 && col > 0) {
            _type = NodeType.NORMAL;
        } else if (row == 0 && col > 1) {
            _type = NodeType.COL_LETTER;
        } else if (col == 0 && row > 1) {
            _type = NodeType.ROW_LETTER;
        } else {
            _type = NodeType.EXTRA;
        }
    }

    public enum NodeType {
        NORMAL, OPTIMUM, ROW_LETTER, COL_LETTER, EXTRA
    }
}
