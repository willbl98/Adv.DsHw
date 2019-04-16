package edu.advalg.pa8;

import edu.advalg.pa8.controller.Controller;
import edu.advalg.pa8.model.Node;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    // Run program
    public static void main(String[] args) {
        launch(args);
    }

    // Preform DSF search
    public static ArrayList<HashMap<String, Node>> dsfSearch(HashMap<String, Node> o,
                                                             String start) {
        DSF dsf = new DSF(o, start);
        return dsf.search();
    }

    // Display GUI
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("view/pa-gui.fxml"));
        Controller userInController = new Controller();
        fxmlLoader.setController(userInController);
        Parent layout = fxmlLoader.load();
        Scene scene = new Scene(layout);
        primaryStage.setTitle("PA-8");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static class DSF {

        public static HashMap<String, Node> traversed = null;
        private final HashMap<String, Node> nodeHashMap;
        private final String start;
        private int time;
        private int order;

        DSF(HashMap<String, Node> nodeHashMap, String start) {
            this.nodeHashMap = nodeHashMap;
            this.start = start;
        }

        // Makes deep copy of graph mapping
        private static HashMap<String, Node> copyMap(HashMap<String, Node> original) {
            HashMap<String, Node> copy = new HashMap<>();
            for (Map.Entry<String, Node> entry : original.entrySet()) {
                Node nodeCopy = entry.getValue();
                nodeCopy.resetToDefaults();
                copy.put(entry.getKey(), nodeCopy);
            }
            return copy;
        }

        private static HashMap<String, Node> currentNodeStates(HashMap<String, Node> nm) {
            HashMap<String, Node> current = new HashMap<>();
            for (Map.Entry<String, Node> entry : nm.entrySet()) {
                Node nodeCopy = new Node(entry.getValue().getName(), entry.getValue().getVisitStatus(),
                        entry.getValue().getFirstTime(), entry.getValue().getLastTime(), entry.getValue().getPredecessor(),
                        entry.getValue().getVisitOrder());
                current.put(entry.getKey(), nodeCopy);
            }
            return current;
        }

        // Perform the depth first search
        private void dsfVisit(Node node, HashMap<String, Node> nm, ArrayList<HashMap<String, Node>> maps) {
            node.setVisitStatus(Node.VisitStatus.GRAY);

            node.setFirstTime(getTime());
            setTime(getTime() + 1);

            node.setVisitOrder(getOrder());
            setOrder(getOrder() + 1);

            maps.add(currentNodeStates(nm));

            for (Node neighbor : node.getNeighbors()) {
                if (neighbor.getVisitStatus() == Node.VisitStatus.WHITE) {
                    neighbor.setPredecessor(node);
                    dsfVisit(neighbor, nm, maps);
                }
            }

            node.setVisitStatus(Node.VisitStatus.BLACK);
            node.setLastTime(getTime() + 1);
            maps.add(currentNodeStates(nm));
        }

        // Search graph using DFS.  Inits the DSF
        ArrayList<HashMap<String, Node>> search() {

            // Clear the traveled path of previous entries
            traversed = new HashMap<>();

            // Save steps for when Colors are changed from White to Gray to Black
            ArrayList<HashMap<String, Node>> maps = new ArrayList<>();

            // Init time
            setTime(0);

            // Marks order visited
            setOrder(0);

            // Copy map so original contents to affected
            HashMap<String, Node> nm = copyMap(nodeHashMap);

            // Initialize the nodes
            for (Node node : nm.values()) {
                node.resetToDefaults();
            }

            // Save initialized State, ie, all WHITE
            maps.add(currentNodeStates(nm));

            // Begin DFS search starting with user-selected node
            dsfVisit(nm.get(start), nm, maps);

            // Save the final traversed order
            traversed.putAll(maps.get(maps.size() - 1));

            // Return all steps taken during the dfs
            return maps;
        }

        private int getTime() {
            return time;
        }

        private void setTime(int time) {
            this.time = time;
        }

        private int getOrder() {
            return order;
        }

        private void setOrder(int order) {
            this.order = order;
        }
    }
}
