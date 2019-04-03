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
    private final Label _labelLeftArrow = new Label("▶ ");
    private final Label _labelDownArrow = new Label("▾");
    private final Label _labelDiagonalArrow = new Label("◢ ");
    final Rectangle _rectangle;

    NodeView(Node node) {
        // Set at size 60x60
        _rectangle = new Rectangle(60.0, 60.0);

        // Container for the Labels that sit on top of the Rectangle.
        // BorderPans have 5 'containers'. Left, Right, Top, Bottom, and Center
        BorderPane _borderPane = new BorderPane();

        // Right container of the BorderPane
        _labelLeftArrow.setVisible(false); // Set as invisible by default and show when needed
        _labelLeftArrow.getStyleClass().add("arrow");
        VBox borderPaneRight = new VBox();
        borderPaneRight.setAlignment(Pos.CENTER_RIGHT);
        borderPaneRight.setPrefWidth(15);
        borderPaneRight.getChildren().add(_labelLeftArrow);
        _borderPane.setRight(borderPaneRight);

        // BorderPane Left
        HBox borderPaneLeft = new HBox();
        borderPaneLeft.setPrefWidth(15);
        _borderPane.setLeft(borderPaneLeft);

        // Bottom Container: left half
        _labelDownArrow.setVisible(false); // Set as invisible by default and show when needed
        _labelDownArrow.getStyleClass().add("arrow");
        HBox bottomDown = new HBox();
        bottomDown.setAlignment(Pos.BOTTOM_RIGHT);
        bottomDown.setPrefWidth(34.0);
        bottomDown.getChildren().add(_labelDownArrow);

        // Bottom right half of the the BorderPane
        _labelDiagonalArrow.setVisible(false); // Set as invisible by default and show when needed
        _labelDiagonalArrow.getStyleClass().add("arrow");
        HBox bottomDiag = new HBox();
        bottomDiag.setAlignment(Pos.BOTTOM_RIGHT);
        bottomDiag.setPrefWidth(26.0);
        bottomDiag.getChildren().add(_labelDiagonalArrow);

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
        _labelLeftArrow.setVisible(true);
    }

    void showDownArrow() {
        _labelDownArrow.setVisible(true);
    }

    void showDiagonalArrow() {
        _labelDiagonalArrow.setVisible(true);
    }
}
