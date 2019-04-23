package advalg.model;

import java.util.Collection;

public class DFS {
    private static int time;

    /**
     * DFS search that does not track SCC
     *
     * @param node starting node
     */
    private static void dsfVisit(Node node) {

        node.setVisitStatus(Node.VisitStatus.GRAY);
        node.setFirstTime(time);
        time++;

        for (var neighbor : node.getNeighbors()) {
            if (neighbor.getVisitStatus() == Node.VisitStatus.WHITE) {
                neighbor.setPredecessor(node);
                dsfVisit(neighbor);
            }
        }

        node.setVisitStatus(Node.VisitStatus.BLACK);
        node.setLastTime(time);
        time++;
    }

    // Begins DFS
    public static void dfsSearch(Collection<Node> graph) {
        // Init time
        time = 0;

        for (var node : graph) {
            if (node.getVisitStatus() == Node.VisitStatus.WHITE) {
                dsfVisit(node);
            }
        }
    }
}
