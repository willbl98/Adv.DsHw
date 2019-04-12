package pa7.graph.model;

import java.util.ArrayList;

/**
 * Elements to fill the graph
 */
public class Node {
    private final String name;  // Node name
    private String predecessorName = "";

    private int visitOrder;
    private VisitStatus visitStatus;  // Marks if a node has been traversed during BFS
    private int distance;  // Distance from starting node during BFS
    private Node predecessor;  // Previously accessed node during BFS traversal
    private ArrayList<Node> neighbors;  // Neighboring nodes in the graph

    /**
     * @param name Node name
     */
    Node(String name) {
        this.name = name;
        this.visitStatus = VisitStatus.WHITE;
        this.distance = Integer.MIN_VALUE;
        this.predecessor = null;
        this.visitOrder = Integer.MIN_VALUE;
        this.neighbors = new ArrayList<>();
    }

    public Node(String name, VisitStatus visitStatus, int distance, Node predecessor) {
        this.name = name;
        this.visitStatus = visitStatus;
        this.distance = distance;
        this.predecessor = predecessor;
    }

    public VisitStatus getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(VisitStatus visitStatus) {
        this.visitStatus = visitStatus;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Node getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Node predecessor) {
        this.predecessor = predecessor;
    }

    public ArrayList<Node> getNeighbors() {
        return neighbors;
    }

    int getVisitOrder() {
        return visitOrder;
    }

    public void setVisitOrder(int visitOrder) {
        this.visitOrder = visitOrder;
    }


    public String getName() {
        return name;
    }

    // Returning true if Node contains the queried name as a neighbor
    boolean containsNeighbor(String name) {
        return neighbors.stream().map(Node::getName).anyMatch(name::equals);
    }

    // Add node as neighbor
    void addNeighbor(Node node) {
        neighbors.add(node);
    }

    // Called during map copy action in the BFS function.  Resets copy nodes to default state
    public void resetForCopy() {
        setVisitStatus(VisitStatus.WHITE);
        setVisitOrder(Integer.MIN_VALUE);
        setDistance(Integer.MIN_VALUE);
        setPredecessor(null);
    }

    // Return colors as strings for BFS table output
    String getColor() {
        switch (visitStatus) {
            case WHITE:
                return "White";
            case GRAY:
                return "Gray";
            case BLACK:
                return "Black";
        }
        return null;
    }

    // Compares end times when sorting tasks
    int compareVisitOrder(Node task) {
        return Integer.compare(task.getVisitOrder(), getVisitOrder());
    }

    public String getPredecessorName() {
        return predecessorName;
    }

    /**
     * Marks nodes as visited during BFS.
     * <p>
     * White = not visited
     * Gray = partially visited
     * Black = visited as well as all its neighbors
     */
    public enum VisitStatus {
        WHITE, GRAY, BLACK
    }
}
