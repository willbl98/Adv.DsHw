package edu.advalg.pa8.view;


import edu.advalg.pa8.model.TableCellType;
import edu.advalg.utils.table.PACell;
import edu.advalg.utils.table.PACellView;
import edu.advalg.utils.table.PATableView;

import java.util.ArrayList;

/**
 * Graphic of the BFS table
 */
public class TrackingView extends PATableView {

    @SuppressWarnings("FieldCanBeLocal")
    private final int ROW_HEADER_WIDTH = 80;  // Width of row header cell

    /**
     * Populates table from the 2D PACell structure
     *
     * @param matrix matrix of PACells
     */
    public TrackingView(ArrayList<ArrayList<PACell>> matrix) {
        super(matrix);
        titleCellSize = findMaxListLength(matrix);
        cellSize = findMaxStringLength(matrix, 0, 1);
        createCells(matrix);
        _gridPane.getStylesheets().add(getClass().getResource("pa-style.css").toExternalForm());
        _gridPane.getStyleClass().add("matrixview");
    }

    /**
     * Traverse PACell matrix and populate grid
     *
     * @param matrix PACells
     */
    @Override
    protected void createCells(ArrayList<ArrayList<PACell>> matrix) {
        for (int row = 0; row < matrix.size(); row++) {
            for (int col = 0; col < matrix.get(row).size(); col++) {
                cellFactory(matrix.get(row).get(col), row, col);
            }
        }
    }

    /**
     * Create table cells and add to grid based on the cell type from the PACell matrix
     *
     * @param PACell contains cell type and content to display
     * @param row    gridPane row
     * @param col    gridPane col
     */
    @Override
    protected void cellFactory(PACell PACell, int row, int col) {
        PACellView cell = new PACellView(PACell.getCellContent());
        cell.setCellWidth(cellSize);
        switch ((TableCellType) PACell.getPACellType()) {
            case CELL_WHITE:
                cell.formatCell("cell-label", "white-cell");
                _gridPane.add(cell, col, row);
                break;
            case CELL_GRAY:
                cell.formatCell("cell-label", "gray-cell");
                _gridPane.add(cell, col, row);
                break;
            case CELL_BLACK:
                cell.formatCell("cell-label", "black-cell");
                _gridPane.add(cell, col, row);
                break;
            case ROW_HEADER:
                cell.formatCell("cell-label", "light-blue-cell");
                cell.setCellWidth(ROW_HEADER_WIDTH);
                _gridPane.add(cell, col, row);
                break;
            case TITLE:
                cell.setLabel("Tracking Table");
                cell.formatCell("cell-label", "title-cell");
                cell.setCellWidth((titleCellSize - 1) * cellSize + ROW_HEADER_WIDTH);
                _gridPane.add(cell, 0, 0, titleCellSize, 1);
                break;
        }
    }
}
