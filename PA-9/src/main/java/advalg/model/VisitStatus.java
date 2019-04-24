package advalg.model;

/**
 * Marks nodes as visited during DSF.
 * <p>
 * White = not visited
 * Gray = partially visited
 * Black = visited as well as all its neighbors
 */
public enum VisitStatus {
    WHITE, GRAY, BLACK
}
