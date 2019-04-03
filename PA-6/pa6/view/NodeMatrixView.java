package pa6.view;

import pa6.model.Direction;
import pa6.model.Node;
import pa6.model.NodeMatrix;
import javafx.scene.CacheHint;
import javafx.scene.layout.GridPane;

import java.util.Objects;

public class NodeMatrixView {

    private final GridPane gridPane;
    private final NodeMatrix _nodeMatrix;

    public NodeMatrixView(NodeMatrix nodeMatrix) {
        _nodeMatrix = nodeMatrix;
        gridPane = new GridPane();
        gridPane.setCache(true);
        gridPane.setCacheHint(CacheHint.SPEED);
        createMatrixView();
        gridPane.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        gridPane.getStyleClass().add("matrixview");
    }

    private void createMatrixView() {
        gridPane.getChildren().clear();
        for (int row = 0; row < _nodeMatrix.getRowSize(); row++) {
            for (int col = 0; col < _nodeMatrix.getColSize(); col++) {
                addNodeView(_nodeMatrix.getNode(row, col));
            }
        }
    }

    private void addNodeView(Node node) {
        gridPane.add(nodeViewFactory(node), node.getCol(), node.getRow());
    }

    private NodeView getNodeViewNode(int col, int row) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return (NodeView) node;
            }
        }
        return null;
    }


    private NodeView nodeViewFactory(Node node) {
        switch (node.getType())  {
            case NORMAL:
                NormalNodeView normalNodeView = new NormalNodeView(node);
                markAsOptimum(node);
                return normalNodeView;
            case OPTIMUM:
                OptimumNodeView optimumNodeView = new OptimumNodeView(node);
                markAsOptimum(node);
                return optimumNodeView;
            case ROW_LETTER:
                RowHeaderNodeView rowNodeView = new RowHeaderNodeView(node);
                markAsOptimum(node);
                return rowNodeView;
            case COL_LETTER:
                ColHeaderNodeView colNodeView = new ColHeaderNodeView(node);
                markAsOptimum(node);
                return colNodeView;
            case EXTRA:
                ExtraNodeView extraNodeView = new ExtraNodeView(node);
                markAsOptimum(node);
                return extraNodeView;
            default:
                throw new IllegalStateException("Unexpected value: " + node.getType());
        }
    }

    private void markAsOptimum(Node node) {
        if (node.getParentOrigin().containsKey(Direction.LEFT)) {
            Objects.requireNonNull(getNodeViewNode(node.getCol() - 1, node.getRow())).showLeftArrow();
        }
        if (node.getParentOrigin().containsKey(Direction.UP)) {
            Objects.requireNonNull(getNodeViewNode(node.getCol(), node.getRow() - 1)).showDownArrow();
        }
        if (node.getParentOrigin().containsKey(Direction.DIAG)) {
            Objects.requireNonNull(getNodeViewNode(node.getCol() - 1, node.getRow() - 1)).showDiagonalArrow();
        }
    }

    public GridPane getGridPane() {
        return gridPane;
    }
}
