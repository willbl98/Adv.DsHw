package advalg.model;

import java.util.Collection;

/**
 * Contains static method to perform a DFS on a NodeGraph
 */
public class DFS {
    private static int time;

    /**
     * DFS search that does not track SCC
     *
     * @param node starting node
     */
    private static void dsfVisit(Node node) {

        // Mark node as partially visited
        node.setVisitStatus(VisitStatus.GRAY);
        node.setFirstTime(time);
        time++;

        for (var neighbor : node.getNeighbors()) {
            // If node's neighbors is not visited, begin the visit
            if (neighbor.getVisitStatus() == VisitStatus.WHITE) {
                neighbor.setPredecessor(node);
                // visit the neighbor's neighbors
                dsfVisit(neighbor);
            }
        }

        // Mark node as completely visited
        node.setVisitStatus(VisitStatus.BLACK);
        node.setLastTime(time);
        time++;
    }

    /**
     * DFS Search
     *
     * @param nodes graph nodes
     */
    public static void dfsSearch(Collection<Node> nodes) {

        // Init time
        time = 0;

        // Begin DFS search
        for (var node : nodes) {
            // If node is not visited, begin the visit
            if (node.getVisitStatus() == VisitStatus.WHITE) {
                // continue the traversal with the node's neighbors
                dsfVisit(node);
            }
        }
    }
}
