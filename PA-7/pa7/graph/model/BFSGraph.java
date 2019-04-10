package pa7.graph.model;

import pa7.table.PACell;
import pa7.table.PACellType;
import pa7.graph.Main;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Creates the BFS tracking table elements to be processed by BFSView
 */
public class BFSGraph implements IGraphData {

    private static final String[] rowHeaders = new String[]{"Node", "Color", "Distance", "Predecessor"};

    /**
     * Create list of tables, including rows, cols, and title to be processed by a View class.  Since its required
     * to output all tracking tables, a list of all tracking tables is created to be looped through during the BFSView
     * creation.
     *
     * @param ng NodeGraph
     * @return complete table
     */
    public static ArrayList<ArrayList<ArrayList<PACell>>> createBFSTable(NodeGraph ng) {
        ArrayList<ArrayList<ArrayList<PACell>>> bfsTables = new ArrayList<>();
        for (String name : ng.getNodeNames()) {
            HashMap<String, Node> nm = Main.BFS(ng.getNodeMap(), name);
            bfsTables.add(GraphData.buildTable(name, null, rowHeaders, null, new BFSGraph(), nm));
        }
        return bfsTables;
    }

    /**
     * Create columns cells depedning on current node
     *
     * @param type    Marked or Unmarked
     * @param nodeMap Graph nodes
     * @param name    Node name, ie node map key
     * @param table   the collection of cells/ table of cells
     */
    private void createColCells(PACellType type,
                                HashMap<String, Node> nodeMap,
                                String name,
                                ArrayList<ArrayList<PACell>> table) {
        // Node name
        table.get(1).add(new PACell(type, name));
        // BFS alg color status
        table.get(2).add(new PACell(type, nodeMap.get(name).getColor()));
        // Distance from current node
        table.get(3).add(new PACell(type, String.valueOf(nodeMap.get(name).getDistance())));
        // Predecessor to current node
        if (nodeMap.get(name).getPredecessor() == null) {
            table.get(4).add(new PACell(type, "^"));
        } else {
            table.get(4).add(new PACell(type, nodeMap.get(name).getPredecessor().getName()));
        }
    }

    /**
     * Traverse nodeMap and add elements to table.  Labels cells as 'CELL_MARKED' if they were not traversed, ie they
     * are not reachable from the current node.
     *
     * @param nodeMap Map of nodes in the NodeGraph
     * @param table   table holding AdjMatrix contents
     * @return table body ready for row, col and title to be added
     */
    @Override
    public ArrayList<ArrayList<PACell>> buildTableBody(HashMap<String, Node> nodeMap,
                                                       ArrayList<ArrayList<PACell>> table) {
        for (String name : nodeMap.keySet()) {
            // Nodes are not reachable from current node
            if (nodeMap.get(name).getVisitStatus() != Node.VisitStatus.BLACK) {
                createColCells(PACellType.CELL_MARKED, nodeMap, name, table);
            }
            // Nodes are reachable from current node
            else {
                createColCells(PACellType.CELL, nodeMap, name, table);
            }
        }
        return table;
    }
}
