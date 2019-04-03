package pa6.model;

/**
 * Helper class used when backtracking to determine the sequence element to use, or if a '_' is used
 */
public class SequencePair {
    private String _colLetter;
    private String _rowLetter;
    SequencePair(Direction direction, String fromRow, String fromCol) {
        if (direction == Direction.LEFT) {
            _colLetter = fromCol;
            _rowLetter = "_";
        } else if (direction == Direction.UP) {
            _colLetter = "_";
            _rowLetter = fromRow;
        } else {
            _colLetter = fromCol;
            _rowLetter = fromRow;
        }
    }

    public String getColLetter() {
        return _colLetter;
    }

    public String getRowLetter() {
        return _rowLetter;
    }
}
