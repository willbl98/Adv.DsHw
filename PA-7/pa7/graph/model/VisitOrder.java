package pa7.graph.model;

import pa7.graph.Main;
import pa7.table.PACell;
import pa7.table.PACellType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VisitOrder implements IGraphData {

    @SuppressWarnings("FieldCanBeLocal")
    private static final String title = "Visit Order";
    @SuppressWarnings("FieldCanBeLocal")
    private static final String corner = "->";  // Corner node that is between th ecol and row headers
    @SuppressWarnings("FieldCanBeLocal")
    private static final String[] colBanner = new String[]{}; // col header

    /**
     * Create table, including rows, cols, and title to be processed by a View class
     *
     * @param ng NodeGraph
     * @return complete table
     */
    public static ArrayList<ArrayList<PACell>> createOrderList(NodeGraph ng) {
        return GraphData.buildTable(title, colBanner, null, corner, new VisitOrder(), ng.getNodeMap());
    }

    private void sortList(List<Node> order) {
        order.sort((Node n1, Node n2) -> n2.compareVisitOrder(n1));
    }

    private void removeNonVisited(List<Node> order) {
        List<Node> toRemove = new ArrayList<>();
        for (Node n : order) {
            if (n.getVisitOrder() < 0) {
                toRemove.add(n);
            }
        }
        order.removeAll(toRemove);
    }

    @Override
    public ArrayList<ArrayList<PACell>> buildTableBody(HashMap<String, Node> nodeMap, ArrayList<ArrayList<PACell>> table) {
        List<Node> order = new ArrayList<>(Main.traversed.values());
        removeNonVisited(order);
        sortList(order);
        for (Node node : order) {
            if (node.getVisitOrder() >= 0) {
                table.get(1).add(new PACell(PACellType.CELL_WHITE, node.getName()));
            }
        }
        return table;
    }
}
