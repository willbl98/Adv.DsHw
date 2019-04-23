package advalg.model;

import advalg.view.PACell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Tools to build the tables. Creates 2D structures of PACells which determine what to display and how to display it.
 */
class GraphData {

    static ArrayList<ArrayList<PACell>> buildTable(String title,
                                                   List<String> cols,
                                                   List<String> rows,
                                                   String corner,
                                                   IGraphData iGraphData,
                                                   NodeGraph nodeGraph) {
        var table = new ArrayList<ArrayList<PACell>>();
        if (rows != null) initRows(rows, table);
        if (cols != null) table.add(0, initCols(cols));
        if (corner != null) table.get(0).add(0, new PACell(TableCellType.CORNER, corner));
        table.add(0, new ArrayList<>(Collections.singleton(new PACell(TableCellType.TITLE, title))));
        return iGraphData.buildTableBody(nodeGraph, table);
    }

    /**
     * Create columns
     *
     * @param colName column headers
     * @return row of col headers
     */
    private static ArrayList<PACell> initCols(List<String> colName) {
        var col = new ArrayList<PACell>();
        for (var c : colName) {
            col.add(new PACell(TableCellType.COL_HEADER, c));
        }
        return col;
    }

    /**
     * Crete row headers
     *
     * @param rowNames headers
     * @param table    table nx1 table filled with the column headers
     */
    private static void initRows(List<String> rowNames, ArrayList<ArrayList<PACell>> table) {
        for (var c : rowNames) {
            var row = new ArrayList<PACell>();
            row.add(0, new PACell(TableCellType.ROW_HEADER, c));
            table.add(row);
        }
    }
}
