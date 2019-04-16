package edu.advalg.pa8.model;

import java.util.ArrayList;

/**
 * Elements to fill the graph
 */
public class Node {
    private final String name;  // Node name

    private int visitOrder;
    private VisitStatus visitStatus;  // Marks if a node has been traversed during dfs
    private Node predecessor;  // Previously accessed node during dfs traversal
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
        this.visitOrder = Integer.MIN_VALUE;
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
     * @param visitOrder  Marks order node was visited
     */
    public Node(String name, VisitStatus visitStatus, int firstTime, int lastTime, Node predecessor, int visitOrder) {
        this.name = name;
        this.visitStatus = visitStatus;
        this.firstTime = firstTime;
        this.lastTime = lastTime;
        this.predecessor = predecessor;
        this.visitOrder = visitOrder;
    }

    public VisitStatus getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(VisitStatus visitStatus) {
        this.visitStatus = visitStatus;
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

    public int getVisitOrder() {
        return visitOrder;
    }

    public void setVisitOrder(int visitOrder) {
        this.visitOrder = visitOrder;
    }


    public String getName() {
        return name;
    }

    public int getLastTime() {
        return lastTime;
    }

    public void setLastTime(int lastTime) {
        this.lastTime = lastTime;
    }

    public int getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(int firstTime) {
        this.firstTime = firstTime;
    }

    // Returning true if Node contains the queried name as a neighbor
    boolean containsNeighbor(String name) {
        return neighbors.stream().map(Node::getName).anyMatch(name::equals);
    }

    // Add node as neighbor
    void addNeighbor(Node node) {
        neighbors.add(node);
    }

    // Called during map copy action in the DFS function.  Resets copy nodes to default state
    public void resetToDefaults() {
        setVisitStatus(VisitStatus.WHITE);
        setVisitOrder(Integer.MIN_VALUE);
        setFirstTime(Integer.MIN_VALUE);
        setLastTime(Integer.MIN_VALUE);
        setPredecessor(null);
    }

    // Return colors as strings for tracking table output
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
