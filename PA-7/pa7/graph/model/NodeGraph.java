package pa7.graph.model;

import java.util.*;

/**
 * Builds graph of Node objects
 */
public class NodeGraph {
    private boolean directed;
    private HashMap<String, Node> nodeMap; // Collection of Nodes
    private String[] nodeNameSet;  // Set of all nodes in the graph

    /**
     * @param strings  List of Node as edge pairings entered during user input
     * @param directed indicates graph is directed or undirected
     */
    public NodeGraph(List<String[]> strings, boolean directed) {
        this.directed = directed;
        nodeNameSet = createNodeNameSet(strings);  // Creates a Set of node names in the graph
        Arrays.sort(nodeNameSet);  // sort alphabetically
        nodeMap = createNodeList(nodeNameSet);  // add nodes to map
        establishNeighbors(strings);  // map neighbors to there nodes
    }

    HashMap<String, Node> getNodeMap() {
        return nodeMap;
    }

    String[] getNodeNames() {
        return nodeNameSet;
    }

    /**
     * Fills array with Node names, removing duplicate entries
     *
     * @param stringArrays Node pairings from user-in
     * @return Set of node names
     */
    private String[] createNodeNameSet(List<String[]> stringArrays) {
        List<String> list = new ArrayList<>();
        for (String[] strings : stringArrays) Collections.addAll(list, strings);
        // HashSet does not allow duplicate entries
        return new HashSet<>(list).toArray(new String[0]);
    }

    // Populate map with the names from the node Set
    private HashMap<String, Node> createNodeList(String[] stringSet) {
        HashMap<String, Node> list = new HashMap<>();
        for (String string : stringSet) list.put(string, new Node(string));
        return list;
    }

    // Map nodes to neighbors
    private void establishNeighbors(List<String[]> stringArrays) {
        for (String[] strings : stringArrays) {
            nodeMap.get(strings[0]).addNeighbor(nodeMap.get(strings[1]));
            if (!directed) nodeMap.get(strings[1]).addNeighbor(nodeMap.get(strings[0]));
        }
    }
}
