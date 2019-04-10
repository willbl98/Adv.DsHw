package pa7.graph.model;

import pa7.table.PACell;
import pa7.table.PACellType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Creates the Adjacency Matrix table elements to be processed by AdjMatrixView
 */
public class AdjMatrix implements IGraphData {

    @SuppressWarnings("FieldCanBeLocal")
    private static final String title = "Adjacency Matrix";
    @SuppressWarnings("FieldCanBeLocal")
    private static final String corner = "Nodes";  // Corner node that is between th ecol and row headers

    /**
     * Create table, including rows, cols, and title to be processed by a View class
     *
     * @param ng NodeGraph
     * @return complete table
     */
    public static ArrayList<ArrayList<PACell>> createAdjMatrix(NodeGraph ng) {
        return GraphData.buildTable(title, ng.getNodeNames(), ng.getNodeNames(), corner, new AdjMatrix(), ng.getNodeMap());
    }

    /**
     * Traverse nodeMap and add elements to table
     *
     * @param nodeMap Map of nodes in the NodeGraph
     * @param table   table holding AdjMatrix contents
     * @return table body ready for row, col and title to be added
     */
    @Override
    public ArrayList<ArrayList<PACell>> buildTableBody(HashMap<String, Node> nodeMap,
                                                       ArrayList<ArrayList<PACell>> table) {
        String[] nodeNameSet = nodeMap.keySet().toArray(new String[0]);
        for (int row = 2; row < nodeNameSet.length + 2; row++) {
            for (int col = 1; col < nodeNameSet.length + 1; col++) {
                if (nodeMap.get(nodeNameSet[row - 2]).containsNeighbor(nodeNameSet[col - 1])) {
                    table.get(row).add(col, new PACell(PACellType.CELL_MARKED, "1"));
                } else {
                    table.get(row).add(col, new PACell(PACellType.CELL, "0"));
                }
            }
        }
        return table;
    }
}
