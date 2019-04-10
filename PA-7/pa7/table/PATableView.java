package pa7.table;

import javafx.scene.CacheHint;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

/**
 * Contains a GridPane of all PACellView objects
 */
public abstract class PATableView {

    private static final int CELL_SIZE_MODIFIER = 10;  // used when determining cell width based on largest cell label
    private static final int MIN_CELL_SIZE = 45;
    protected final GridPane _gridPane;
    protected final ArrayList<ArrayList<PACell>> matrix;
    protected int cellSize;
    protected int titleCellSize;

    protected PATableView(ArrayList<ArrayList<PACell>> matrix) {
        this.matrix = matrix;
        _gridPane = new GridPane();
        _gridPane.setCache(true);
        _gridPane.setCacheHint(CacheHint.SPEED);
        _gridPane.getChildren().clear();
    }

    // Returns number of columns in table.  Determines number of cols to span
    protected static int findMaxListLength(ArrayList<ArrayList<PACell>> matrix) {
        int max = Integer.MIN_VALUE;
        for (ArrayList<PACell> strings : matrix) {
            int tmp = strings.size();
            if (max < tmp) {
                max = tmp;
            }
        }
        return max;
    }

    // Returns max size of PACell content.  Ensures labels don't overlap cells
    protected static int findMaxStringLength(ArrayList<ArrayList<PACell>> matrix, int i, int j) {
        int max = Integer.MIN_VALUE;
        for (int row = i; row < matrix.size(); row++) {
            for (int col = j; col < matrix.get(row).size(); col++) {
                int tmp = matrix.get(row).get(col).getCellContent().length();
                if (max < tmp) {
                    max = tmp;
                }
            }
        }
        max *= CELL_SIZE_MODIFIER;
        if (max < MIN_CELL_SIZE) max = MIN_CELL_SIZE;
        return max;
    }

    public GridPane getGridPane() {
        return _gridPane;
    }

    protected abstract void createCells(ArrayList<ArrayList<PACell>> matrix);

    protected abstract void cellFactory(PACell PACell, int row, int col);
}
