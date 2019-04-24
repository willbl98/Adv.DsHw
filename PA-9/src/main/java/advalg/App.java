package advalg;

import advalg.controller.Controller;
import advalg.model.DFS;
import advalg.model.Node;
import advalg.model.NodeGraph;
import advalg.model.VisitStatus;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class App extends Application {

    // Run program
    public static void main(String[] args) {
        launch(args);
    }

    // Display GUI
    @Override
    public void start(Stage primaryStage) throws Exception {
        var fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(App.class.getResource("pa-gui.fxml"));
        var userInController = new Controller();
        fxmlLoader.setController(userInController);
        Parent layout = fxmlLoader.load();
        var scene = new Scene(layout);
        primaryStage.setTitle("PA-9");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Strongly Connected Components.  Included in the App class for easy visibility for the PA-Source
     */
    public static class SCC {

        // Tracks time for first/list time in DFS algorithm
        private static int time;

        /**
         * Outputs a list of SCC nodes
         *
         * @param nodeGraph the user input graph
         * @return scc
         */
        public static ArrayList<ArrayList<Node>> findSCCNodes(NodeGraph nodeGraph) {
            var sccGroups = new ArrayList<ArrayList<Node>>();
            var transposedNodes = transpose(nodeGraph);
            List<Node> originalGraph = new ArrayList<>(nodeGraph.getNodeMap().values());
            DFS.dfsSearch(originalGraph);
            sccSearch(transposedNodes.values(), sccGroups);
            return sccGroups;
        }

        // Swap direction of neighbors from the original user input graph
        private static List<String[]> swapDirection(NodeGraph nodeGraph) {
            List<String[]> swapped = new ArrayList<>();
            for (var string : nodeGraph.getStrings()) {
                var tmp = new String[2];
                tmp[1] = string[0];
                tmp[0] = string[1];
                swapped.add(tmp);
            }
            return swapped;
        }

        // Transpose NodeGraph
        private static HashMap<String, Node> transpose(NodeGraph nodeGraph) {
            // Create a copy of the original graph
            var graph = NodeGraph.createNodeList(nodeGraph.getNodeNames());

            // Call the swapDirection function to flip the edge direction of the user input graph.
            // Then, with the newly swapped neighbors, create a new graph resulting in a transposed graph.
            NodeGraph.establishNeighbors(graph, swapDirection(nodeGraph));

            return graph;
        }

        /**
         * DFS search which is called on the transposed graph.  Used to find SCC.
         *
         * @param nodes     graph nodes
         * @param sccGroups holds list of SCC.  Null when SCC are not being calculated
         */
        private static void sccSearch(Collection<Node> nodes, ArrayList<ArrayList<Node>> sccGroups) {

            // Init time
            time = 0;

            // Begin DFS search
            for (var node : nodes) {
                // If node is not visited, begin the visit
                if (node.getVisitStatus() == VisitStatus.WHITE) {
                    // Initialize a new list of scc nodes
                    var sccGroup = new ArrayList<Node>();
                    // add current node to the scc group
                    sccGroup.add(node);
                    // visit the nodes transposed neighbors
                    dsfVisit(node, sccGroup);
                    // add the result group which contains any new scc nodes ot the final sccGroups list
                    sccGroups.add(sccGroup);
                }
            }
        }

        /**
         * Called when performing a SCC search. Recursively traverses graph and saves SCC groups to the
         * sccGroup list.
         *
         * @param node     starting node
         * @param sccGroup Holds list containing SCC for the starting node
         */
        private static void dsfVisit(Node node, List<Node> sccGroup) {
            // Mark node as partially visited
            node.setVisitStatus(VisitStatus.GRAY);
            node.setFirstTime(time);
            time++;

            for (var neighbor : node.getNeighbors()) {
                // If node's neighbors is not visited, begin the visit
                if (neighbor.getVisitStatus() == VisitStatus.WHITE) {
                    // add neighbors as a SCC
                    sccGroup.add(neighbor);
                    neighbor.setPredecessor(node);
                    // visit the neighbor's neighbors
                    dsfVisit(neighbor, sccGroup);
                }
            }

            // Mark node as completely visited
            node.setVisitStatus(VisitStatus.BLACK);
            node.setLastTime(time);
            time++;
        }
    }
}
