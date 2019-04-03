package pa6.model;

/**
 * Direction of the Parent Node.
 * If LEFT, then a gap has been inserted in the ROW sequence
 * If UP, then a gap has been inserted in the COL sequence
 * If DIAG, then either the match or mismatch score is being used
 */
public enum Direction {
    LEFT, UP, DIAG
}
