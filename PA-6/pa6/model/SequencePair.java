package pa6.model;

/**
 * Helper class used when backtracking to determine the sequence element to use, or if a '_' is used
 */
public class SequencePair {
    private String colLetter;
    private String rowLetter;
    SequencePair(Direction direction, String fromRow, String fromCol) {
        if (direction == Direction.LEFT) {
            colLetter = fromCol;
            rowLetter = "_";
        } else if (direction == Direction.UP) {
            colLetter = "_";
            rowLetter = fromRow;
        } else {
            colLetter = fromCol;
            rowLetter = fromRow;
        }
    }

    public String getColLetter() {
        return colLetter;
    }

    public String getRowLetter() {
        return rowLetter;
    }
}
