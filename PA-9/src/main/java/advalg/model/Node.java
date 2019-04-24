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
    private int firstTime;  // marks first time node is visited during DFS (Turns Gray)
    private int lastTime;  // marks list time node is visited during DFS (Turns Black)

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

    public String getName() {
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
}
