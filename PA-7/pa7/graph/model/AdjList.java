package pa7.graph.model;

import pa7.table.PACell;
import pa7.table.PACellType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Creates the Adjacency List table elements to be processed by AdjListView
 */
public class AdjList implements IGraphData {

    @SuppressWarnings("FieldCanBeLocal")
    private static final String title = "Adjacency List";
    @SuppressWarnings("FieldCanBeLocal")
    private static final String corner = "Nodes";  // Corner node that is between th ecol and row headers
    @SuppressWarnings("FieldCanBeLocal")
    private static final String[] colBanner = new String[]{"Neighbors"}; // col header

    /**
     * Create table, including rows, cols, and title to be processed by a View class
     *
     * @param ng NodeGraph
     * @return complete table
     */
    public static ArrayList<ArrayList<PACell>> createAdjList(NodeGraph ng) {
        return GraphData.buildTable(title, colBanner, ng.getNodeNames(), corner, new AdjList(), ng.getNodeMap());
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
            for (Node node : nodeMap.get(nodeNameSet[row - 2]).getNeighbors()) {
                table.get(row).add(new PACell(PACellType.CELL_WHITE, node.getName()));
            }
        }
        return table;
    }
}
