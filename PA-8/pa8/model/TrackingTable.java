package edu.advalg.pa8.model;

import edu.advalg.pa8.Main;
import edu.advalg.utils.table.PACell;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Creates the Tracking table elements to be processed by TrackingView
 */
public class TrackingTable implements IGraphData {

    private static final String[] rowHeaders = new String[]{"Node", "Color", "Predecessor", "First Time", "Last Time"};

    /**
     * Create list of tables, including rows, cols, and title to be processed by a View class.  Since its required
     * to output all tracking tables, a list of all tracking tables is created to be looped through during the TrackingView
     * creation.
     *
     * @param ng NodeGraph
     * @return complete table
     */
    public static ArrayList<ArrayList<ArrayList<PACell>>> createTrackingTable(NodeGraph ng, String start) {
        ArrayList<ArrayList<ArrayList<PACell>>> bfsTables = new ArrayList<>();
        ArrayList<HashMap<String, Node>> maps = Main.dsfSearch(ng.getNodeMap(), start);
        for (HashMap<String, Node> map : maps) {
            bfsTables.add(GraphData.buildTable(start, null, rowHeaders, null, new TrackingTable(), map));
        }
        return bfsTables;
    }

    /**
     * Create columns cells depending on current node
     *
     * @param nodeMap Graph nodes
     * @param name    Node name, ie node map key
     * @param table   the collection of cells table of cells
     */
    private void createColCells(TableCellType type,
                                HashMap<String, Node> nodeMap,
                                String name,
                                ArrayList<ArrayList<PACell>> table) {
        // Node name
        table.get(1).add(new PACell(type, name));
        // Alg color status
        table.get(2).add(new PACell(type, nodeMap.get(name).getColor()));
        // Predecessor to current node
        if (nodeMap.get(name).getPredecessor() == null) {
            table.get(3).add(new PACell(type, "-"));
        } else {
            table.get(3).add(new PACell(type, nodeMap.get(name).getPredecessor().getName()));
        }
        // time node goes from WHITE to GRAY
        if (nodeMap.get(name).getFirstTime() >= 0) {
            table.get(4).add(new PACell(type, String.valueOf(nodeMap.get(name).getFirstTime())));
        } else {
            table.get(4).add(new PACell(type, "-"));
        }
        // time node goes from GRAY to BLACK
        if (nodeMap.get(name).getLastTime() >= 0) {
            table.get(5).add(new PACell(type, String.valueOf(nodeMap.get(name).getLastTime())));
        } else {
            table.get(5).add(new PACell(type, "-"));
        }
    }

    /**
     * Traverse nodeMap and add elements to table.  Labels cells as 'CELL_GRAY' if they were not traversed, ie they
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
            // Nodes are reachable from current node
            switch (nodeMap.get(name).getVisitStatus()) {
                case WHITE:
                    createColCells(TableCellType.CELL_WHITE, nodeMap, name, table);
                    break;
                case GRAY:
                    createColCells(TableCellType.CELL_GRAY, nodeMap, name, table);
                    break;
                case BLACK:
                    createColCells(TableCellType.CELL_BLACK, nodeMap, name, table);
                    break;
            }
        }
        return table;
    }
}
