package pa7.graph;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pa7.graph.controller.Controller;
import pa7.graph.model.Node;

import java.util.*;

public class Main extends Application {

    // Updated based on order of Node traversal
    public static HashMap<String, Node> traversed = new HashMap<>();

    // Run program
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * BFS Search for the NodeGraph
     *
     * @param o     HashMap of the nodes in the graph
     * @param start Starting node to begin BFS
     * @return all steps taken during BFS search
     */
    public static ArrayList<HashMap<String, Node>> BFS(HashMap<String, Node> o, String start) {
        // Clear the traveled path of previous entries
        traversed.clear();
        int orderCtr = -1;

        // Copy map so original contents to affected
        HashMap<String, Node> nm = copyMap(o);

        // Save steps for when Colors are changed from White to Gray to Black
        ArrayList<HashMap<String, Node>> maps = new ArrayList<>();
        maps.add(currentNodeStates(nm));

        // Initialize first node
        nm.get(start).setVisitStatus(Node.VisitStatus.GRAY); // set gray status
        nm.get(start).setDistance(0); // set distance 0
        nm.get(start).setVisitOrder(++orderCtr);
        maps.add(currentNodeStates(nm));

        //nm.get(start).setVisitOrder(++orderCtr); // set distance 0

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
                    maps.add(currentNodeStates(nm));
                }
            }
            // Mark node as completely visited, ie set color to black
            currentNode.setVisitStatus(Node.VisitStatus.BLACK);
            // Remove from queue
            queue.poll();
            maps.add(currentNodeStates(nm));
        }
        traversed = nm;
        // Return all steps taken during the BFS
        return maps;
    }

    private static HashMap<String, Node> currentNodeStates(HashMap<String, Node> nm) {
        HashMap<String, Node> current = new HashMap<>();
        for (Map.Entry<String, Node> entry : nm.entrySet()) {
            Node nodeCopy = new Node(entry.getValue().getName(), entry.getValue().getVisitStatus(),
                    entry.getValue().getDistance(), entry.getValue().getPredecessor());
            current.put(entry.getKey(), nodeCopy);
        }
        return current;
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