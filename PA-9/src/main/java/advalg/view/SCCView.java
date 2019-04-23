package advalg.view;

import advalg.App;
import advalg.model.TableCellType;

import java.util.ArrayList;

/**
 * Create table graphic of the Adjacency List
 */
public class SCCView extends PATableView {

    private int rowBannerSize = findMaxStringLength(matrix, 1, 0);

    /**
     * Populates table from the 2D PACell structure
     *
     * @param matrix matrix of PACells
     */
    public SCCView(ArrayList<ArrayList<PACell>> matrix) {
        super(matrix);
        titleCellSize = findMaxListLength(matrix);
        cellSize = findMaxStringLength(matrix, 1, 1);
        createCells(matrix);
        _gridPane.getStylesheets().add(App.class.getResource("pa-style.css").toExternalForm());
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
            case ROW_HEADER:
                cell.setCellWidth(rowBannerSize);
                cell.formatCell("cell-label", "light-blue-cell");
                _gridPane.add(cell, col, row);
                break;
            case TITLE:
                cell.formatCell("title-label", "title-cell");
                cell.setCellWidth(cellSize * (titleCellSize - 1) + rowBannerSize);
                _gridPane.add(cell, col, row, titleCellSize, 1);
                break;
        }
    }
}

