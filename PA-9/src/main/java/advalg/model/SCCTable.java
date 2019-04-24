package advalg.model;

import advalg.view.PACell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Builds a table containing results of the SCC search.
 */
public class SCCTable {

    // Title of table
    private static final String title = "Results";

    // Creates numbered headers for each of the SCC groups
    private static ArrayList<String> createRowHeaders(int size) {
        var rowHeaders = new ArrayList<String>();
        for (int i = 1; i <= size; i++) {
            rowHeaders.add("Group: " + i);
        }
        return rowHeaders;
    }

    // Places the row headers in the table
    private static void buildRows(List<String> rowNames, ArrayList<ArrayList<PACell>> table) {
        for (var c : rowNames) {
            var row = new ArrayList<PACell>();
            row.add(0, new PACell(TableCellType.ROW_HEADER, c));
            table.add(row);
        }
    }

    // Puts cells displaying the name of each Node in the appropriate SCC row
    private static void buildTableBody(ArrayList<ArrayList<Node>> nodeGraph,
                                       ArrayList<ArrayList<PACell>> table) {
        int ctr = 1;
        for (ArrayList<Node> nodes : nodeGraph) {
            for (Node node : nodes) {
                table.get(ctr).add(new PACell(TableCellType.CELL_WHITE, node.getName()));
            }
            ctr++;
        }
    }

    /**
     * Create table, including rows, cols, and title to be processed by a View class
     *
     * @param sccNodes NodeGraph
     * @return complete table
     */
    public static ArrayList<ArrayList<PACell>> buildTable(ArrayList<ArrayList<Node>> sccNodes) {
        var table = new ArrayList<ArrayList<PACell>>();
        table.add(0, new ArrayList<>(Collections.singleton(new PACell(TableCellType.TITLE, title))));
        buildRows(createRowHeaders(sccNodes.size()), table);
        buildTableBody(sccNodes, table);
        return table;
    }
}
