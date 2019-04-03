package pa6.view;

import pa6.model.Node;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

class NodeView extends StackPane {
    @SuppressWarnings("FieldCanBeLocal")
    private final double SQUARE_SIZE = 60.0; // rectangle size
    private final Label labelLeftArrow = new Label("▶ ");
    private final Label labelDownArrow = new Label("▾");
    private final Label labelDiagonalArrow = new Label("◢ ");
    final Rectangle _rectangle;

    NodeView(Node node) {
        _rectangle = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);

        // Container for the Labels that sit on top of the Rectangle.
        // BorderPans have 5 'containers'. Left, Right, Top, Bottom, and Center
        BorderPane _borderPane = new BorderPane();

        // Right container of the BorderPane
        labelLeftArrow.setVisible(false); // Set as invisible by default and show when needed
        labelLeftArrow.getStyleClass().add("arrow");
        VBox borderPaneRight = new VBox();
        borderPaneRight.setAlignment(Pos.CENTER_RIGHT);
        borderPaneRight.setPrefWidth(15);
        borderPaneRight.getChildren().add(labelLeftArrow);
        _borderPane.setRight(borderPaneRight);

        // BorderPane Left
        HBox borderPaneLeft = new HBox();
        borderPaneLeft.setPrefWidth(15);
        _borderPane.setLeft(borderPaneLeft);

        // Bottom Container: left half
        labelDownArrow.setVisible(false); // Set as invisible by default and show when needed
        labelDownArrow.getStyleClass().add("arrow");
        HBox bottomDown = new HBox();
        bottomDown.setAlignment(Pos.BOTTOM_RIGHT);
        bottomDown.setPrefWidth(34.0);
        bottomDown.getChildren().add(labelDownArrow);

        // Bottom right half of the the BorderPane
        labelDiagonalArrow.setVisible(false); // Set as invisible by default and show when needed
        labelDiagonalArrow.getStyleClass().add("arrow");
        HBox bottomDiag = new HBox();
        bottomDiag.setAlignment(Pos.BOTTOM_RIGHT);
        bottomDiag.setPrefWidth(26.0);
        bottomDiag.getChildren().add(labelDiagonalArrow);

        // Complete Bottom
        HBox borderPaneBottom = new HBox();
        borderPaneBottom.setPrefHeight(15);
        borderPaneBottom.getChildren().addAll(bottomDown, bottomDiag);
        _borderPane.setBottom(borderPaneBottom);

        // BorderPane Top
        HBox borderPaneTop = new HBox();
        borderPaneTop.setPrefHeight(15.0);
        _borderPane.setTop(borderPaneTop);

        // BorderPane Center
        HBox borderPaneCenter = new HBox();
        borderPaneCenter.setAlignment(Pos.CENTER);
        Label labelValue = new Label(node.getLetter());
        //Label labelValue = new Label(node.getLetter());
        borderPaneCenter.getChildren().addAll(labelValue);
        labelValue.getStyleClass().add("value-face");
        _borderPane.setCenter(borderPaneCenter);

        // Add Rectangle and BorderPane to StackPane
        getChildren().clear();
        getChildren().addAll(_rectangle, _borderPane);
    }


    void showLeftArrow() {
        labelLeftArrow.setVisible(true);
    }

    void showDownArrow() {
        labelDownArrow.setVisible(true);
    }

    void showDiagonalArrow() {
        labelDiagonalArrow.setVisible(true);
    }
}
