package advalg.model;

import java.util.ArrayList;

/**
 * Elements to fill the graph
 */
@SuppressWarnings("unused")
public class Node {
    private final String name;  // Node name

    private VisitStatus visitStatus;  // Marks if a node has been traversed during BFS
    private Node predecessor;  // Previously accessed node during BFS traversal
    private ArrayList<Node> neighbors;  // Neighboring nodes in the graph
    private int firstTime;
    private int lastTime;

    /**
     * Created during initial creation of the NodeGraph using information from user input
     *
     * @param name Node name
     */
    Node(String name) {
        this.name = name;
        this.visitStatus = VisitStatus.WHITE;
        this.predecessor = null;
        this.firstTime = Integer.MIN_VALUE;
        this.lastTime = Integer.MIN_VALUE;
        this.neighbors = new ArrayList<>();
    }

    /**
     * Contains all Node information except minus its neighbors.  The Node objects are created during table generation.
     * Only the necessary information to create the tables are needed.  BNew object are created so changes are not
     * carried over when the ordinal objects from the NodeGraph are referenced.
     *
     * @param name        Name to display on the tables/To use to reference node in the neighbor HashMap
     * @param visitStatus Marks a node as WHITE (not visited), GRAY (partially) or BLACK (completely visited)
     * @param firstTime   marks when node is first encountered during DFS search, ie when it goes from WHITE to GRAY
     * @param lastTime    marks when node is last encountered during DFS search, ie when it goes from GRAY to BLACK
     * @param predecessor First previously encountered neighbor if this node
     */
    public Node(String name, VisitStatus visitStatus, int firstTime, int lastTime, Node predecessor) {
        this.name = name;
        this.visitStatus = visitStatus;
        this.firstTime = firstTime;
        this.lastTime = lastTime;
        this.predecessor = predecessor;
    }

    public VisitStatus getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(VisitStatus visitStatus) {
        this.visitStatus = visitStatus;
    }

    public void setPredecessor(Node predecessor) {
        this.predecessor = predecessor;
    }

    public ArrayList<Node> getNeighbors() {
        return neighbors;
    }

    String getName() {
        return name;
    }

    public void setLastTime(int lastTime) {
        this.lastTime = lastTime;
    }

    public void setFirstTime(int firstTime) {
        this.firstTime = firstTime;
    }

    // Add node as neighbor
    void addNeighbor(Node node) {
        neighbors.add(node);
    }

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
}
