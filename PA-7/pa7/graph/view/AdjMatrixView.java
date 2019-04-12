package pa7.graph.view;

import pa7.table.PACell;
import pa7.table.PACellView;
import pa7.table.PATableView;

import java.util.ArrayList;

/**
 * Creatae graphic of the Adjacency Matrix Table
 */
public class AdjMatrixView extends PATableView {

    /**
     * Populates table from the 2D PACell structure
     *
     * @param matrix matrix of PACells
     */
    public AdjMatrixView(ArrayList<ArrayList<PACell>> matrix) {
        super(matrix);
        titleCellSize = findMaxListLength(matrix);
        cellSize = findMaxStringLength(matrix, 1, 0);
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
        switch (PACell.getPACellType()) {
            case CELL_WHITE:
                cell.formatCell("cell-label", "white-cell");
                _gridPane.add(cell, col, row + 1);
                break;
            case CELL_GRAY:
                cell.formatCell("cell-label", "green-cell");
                _gridPane.add(cell, col, row + 1);
                break;
            case COL_HEADER:
                cell.formatCell("cell-label", "orange-cell");
                _gridPane.add(cell, col, row + 1);
                break;
            case ROW_HEADER:
                cell.formatCell("cell-label", "light-blue-cell");
                _gridPane.add(cell, col, row + 1);
                break;
            case TITLE:
                cell.formatCell("cell-label", "title-cell");
                cell.setCellWidth(cellSize * titleCellSize);
                _gridPane.add(cell, col, row, matrix.get(2).size(), 1);
                break;
            case CORNER:
                cell.formatCell("cell-label", "blue-cell");
                _gridPane.add(cell, col, row + 1);
                break;
        }
    }
}
