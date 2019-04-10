package pa7.graph.model;

import java.util.ArrayList;

/**
 * Elements to fill the graph
 */
public class Node {
    private final String name;  // Node name
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
        this.neighbors = new ArrayList<>();
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

    Node getPredecessor() {
        return predecessor;
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
