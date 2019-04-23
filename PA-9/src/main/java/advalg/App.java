package advalg;

import advalg.controller.Controller;
import advalg.model.DFS;
import advalg.model.Node;
import advalg.model.NodeGraph;
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
     * Strongly Connected Components
     */
    public static class SCC {
        private static int time = 0;

        public static void findSCCNodes(NodeGraph nodeGraph) {
            // Transpose the graph
            var transposedNodes = transpose(nodeGraph);

            // Gather HashMaps values into a list
            List<Node> originalGraph = new ArrayList<>(nodeGraph.getNodeMap().values());

            // Perform DFS on the original graph
            DFS.dfsSearch(originalGraph);

            // Perform DFS on the transposed graph and group SCC
            sccSearch(transposedNodes.values(), nodeGraph.getSCCNodes());
        }

        // Swap the graph edge direction of nodes neighbors
        private static List<String[]> swapDirection(NodeGraph nodeGraph) {
            List<String[]> swapped = new ArrayList<>();
            for (var string : nodeGraph.getStrings()) {
                var nodeName = new String[2];
                nodeName[1] = string[0];
                nodeName[0] = string[1];
                swapped.add(nodeName);
            }
            return swapped;
        }

        // Transpose NodeGraph
        private static HashMap<String, Node> transpose(NodeGraph nodeGraph) {
            var transposedGraph = NodeGraph.createNodeList(nodeGraph.getNodeNames());
            NodeGraph.establishNeighbors(transposedGraph, swapDirection(nodeGraph));
            return transposedGraph;
        }

        /**
         * Called when performing a SCC search.
         *
         * @param node     starting node
         * @param sccGroup Holds list containing SCC for the starting node
         */
        private static void visitNode(Node node, List<Node> sccGroup) {

            node.setVisitStatus(Node.VisitStatus.GRAY);
            node.setFirstTime(time);
            time++;

            for (var neighbor : node.getNeighbors()) {
                if (neighbor.getVisitStatus() == Node.VisitStatus.WHITE) {
                    sccGroup.add(neighbor);
                    neighbor.setPredecessor(node);
                    visitNode(neighbor, sccGroup);
                }
            }

            node.setVisitStatus(Node.VisitStatus.BLACK);
            node.setLastTime(time);
            time++;
        }

        static void sccSearch(Collection<Node> nodes, ArrayList<ArrayList<Node>> sccNodes) {
            // Init time
            time = 0;
            for (var node : nodes) {
                if (node.getVisitStatus() == Node.VisitStatus.WHITE) {
                    var sccGroup = new ArrayList<Node>();
                    sccGroup.add(node);
                    visitNode(node, sccGroup);
                    sccNodes.add(sccGroup);
                }
            }
        }
    }
}
