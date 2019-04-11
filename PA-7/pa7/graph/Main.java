package pa7.graph;

import pa7.graph.controller.Controller;
import pa7.graph.model.Node;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Main extends Application {

    // Run program
    public static void main(String[] args) {
        launch(args);
    }

    // BFS Search
    public static HashMap<String, Node> BFS(HashMap<String, Node> o, String start) {
        // Copy map so original contents to affected
        HashMap<String, Node> nm = copyMap(o);
        int orderCtr = -1;
        // Initialize first node
        nm.get(start).setVisitStatus(Node.VisitStatus.GRAY); // set gray status
        nm.get(start).setDistance(0); // set distance 0
        nm.get(start).setVisitOrder(++orderCtr); // set distance 0

        // Creat the queue
        Queue<Node> queue = new LinkedList<>();
        queue.add(nm.get(start));

        // Loop until queue is empty
        while (!queue.isEmpty()) {
            // Get the queue item
            Node currentNode = queue.peek();
            // Loop through neighbors
            for (Node neighbor : currentNode.getNeighbors()) {
                // Mark node as gray if its white, ie not yet visited
                if (neighbor.getVisitStatus() == Node.VisitStatus.WHITE) {
                    neighbor.setVisitStatus(Node.VisitStatus.GRAY);
                    // Set distance
                    neighbor.setDistance(currentNode.getDistance() + 1);
                    // Save order visited
                    neighbor.setVisitOrder(++orderCtr);
                    // Set Predecessor
                    neighbor.setPredecessor(currentNode);
                    // Add to the queue
                    queue.add(neighbor);
                }
            }
            // Mark node as completely visited, ie set color to black
            currentNode.setVisitStatus(Node.VisitStatus.BLACK);
            // Remove from queue
            queue.poll();
        }
        // Return new graph mapping
        return nm;
    }

    // Makes deep copy of graph mapping
    private static HashMap<String, Node> copyMap(HashMap<String, Node> original) {
        HashMap<String, Node> copy = new HashMap<>();
        for (Map.Entry<String, Node> entry : original.entrySet()) {
            Node nodeCopy = entry.getValue();
            nodeCopy.resetForCopy();
            copy.put(entry.getKey(), nodeCopy);
        }
        return copy;
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
        primaryStage.setTitle("PA-5");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}