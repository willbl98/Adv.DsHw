package edu.advalg.pa8.model;

import edu.advalg.utils.table.PACell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Tools to build the tables. Creates 2D structures of PACells which determine what to display and how to display it.
 */
class GraphData {

    /**
     * Create the rows, title, cols, and body
     *
     * @param title      table title
     * @param cols       col headers
     * @param rows       row headers
     * @param corner     corner title
     * @param iGraphData body content
     * @param nodeMap    data used to populate the body
     * @return finished table
     */
    static ArrayList<ArrayList<PACell>> buildTable(String title,
                                                   String[] cols,
                                                   String[] rows,
                                                   String corner,
                                                   IGraphData iGraphData,
                                                   HashMap<String, Node> nodeMap) {
        ArrayList<ArrayList<PACell>> table = new ArrayList<>();
        if (rows != null) initRows(rows, table);
        if (cols != null) table.add(0, initCols(cols));
        if (corner != null) table.get(0).add(0, new PACell(TableCellType.CORNER, corner));
        table.add(0, new ArrayList<>(Collections.singleton(new PACell(TableCellType.TITLE, title))));
        return iGraphData.buildTableBody(nodeMap, table);
    }

    /**
     * Create columns
     *
     * @param colName column headers
     * @return row of col headers
     */
    private static ArrayList<PACell> initCols(String[] colName) {
        ArrayList<PACell> col = new ArrayList<>();
        for (String c : colName) {
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
    private static void initRows(String[] rowNames, ArrayList<ArrayList<PACell>> table) {
        for (String c : rowNames) {
            ArrayList<PACell> row = new ArrayList<>();
            row.add(0, new PACell(TableCellType.ROW_HEADER, c));
            table.add(row);
        }
    }
}
