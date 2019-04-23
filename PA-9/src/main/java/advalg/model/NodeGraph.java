package advalg.model;

import java.util.*;

/**
 * Builds graph of Node objects
 */
public class NodeGraph {
    private final ArrayList<ArrayList<Node>> sccNodes;
    private List<String[]> strings;
    private HashMap<String, Node> nodeMap; // Collection of Nodes
    private String[] nodeNameSet;  // Set of all nodes in the graph

    /**
     * @param strings List of Node as edge pairings entered during user input
     */
    public NodeGraph(List<String[]> strings) {
        this.strings = strings;
        nodeNameSet = createNodeNameSet(strings);  // Creates a 'Set' of node names in the graph
        Arrays.sort(nodeNameSet);  // sort alphabetically
        nodeMap = createNodeList(Arrays.asList(nodeNameSet));  // add nodes to map
        establishNeighbors(nodeMap, strings);  // map neighbors to there nodes
        sccNodes = new ArrayList<>();
    }

    // Populate map with the names from the node Set
    public static HashMap<String, Node> createNodeList(List<String> stringSet) {
        HashMap<String, Node> list = new HashMap<>();
        for (String string : stringSet) list.put(string, new Node(string));
        return list;
    }

    // Map nodes to neighbors from the pairs provided by the user
    public static void establishNeighbors(HashMap<String, Node> nodeMap, List<String[]> stringArrays) {
        for (String[] strings : stringArrays) {
            nodeMap.get(strings[0]).addNeighbor(nodeMap.get(strings[1]));
        }
    }

    public List<String[]> getStrings() {
        return strings;
    }

    public HashMap<String, Node> getNodeMap() {
        return nodeMap;
    }

    public List<String> getNodeNames() {
        return Arrays.asList(nodeNameSet);
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

    public ArrayList<ArrayList<Node>> getSCCNodes() {
        return sccNodes;
    }
}
