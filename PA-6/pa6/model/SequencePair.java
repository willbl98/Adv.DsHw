package pa6.model;

/**
 * Used when backtracking to determine the sequence element to use, or if a '_' is used.  Decides which sequence
 * character is used based on the where a node's parent lies in the matrix
 */
public class SequencePair {
    private String _colLetter;
    private String _rowLetter;

    /**
     * Used a parent node's direction, and a sequence element from each of the user provided sequences
     *
     * @param direction parent node's orientation to current node during th backtracking process
     * @param fromRow character element from the row sequence
     * @param fromCol character element from the col sequence
     */
    public SequencePair(Direction direction, String fromRow, String fromCol) {
        // If from a left, a gap is inserted into the row sequence
        if (direction == Direction.LEFT) {
            _colLetter = fromCol;
            _rowLetter = "_";
        }
        // If from a above, a gap is inserted into the col sequence
        else if (direction == Direction.UP) {
            _colLetter = "_";
            _rowLetter = fromRow;
        }
        // For matches and mismatches, each sequence character at give alignment location is used
        else {
            _colLetter = fromCol;
            _rowLetter = fromRow;
        }
    }

    public SequencePair(String fromRow, String fromCol) {
        _colLetter = fromCol;
        _rowLetter = fromRow;
    }

    public String getColLetter() {
        return _colLetter;
    }

    public String getRowLetter() {
        return _rowLetter;
    }
}
