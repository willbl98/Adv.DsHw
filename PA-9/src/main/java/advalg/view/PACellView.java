package advalg.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 * Stack-able of a Rectangle and Label to use to display PACell contents
 */
@SuppressWarnings("unused, WeakerAccess")
public class PACellView extends StackPane {

    private Label labelValue;  // Contains PACell contents
    private Rectangle _rectangle;  // Visual cell body
    private double cellWidth = 9.0;  // default rectangle width
    private double cellHeight = 24.0;  // default rectangle height

    /**
     * @param face PACell content
     */
    public PACellView(String face) {
        _rectangle = new Rectangle(cellWidth, cellHeight);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        labelValue = new Label(face);
        hBox.getChildren().addAll(labelValue);
        getChildren().clear();
        getChildren().addAll(_rectangle, hBox);
    }

    double getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(double cellWidth) {
        this.cellWidth = cellWidth;
        _rectangle.setWidth(cellWidth);
    }

    double getCellHeight() {
        return cellHeight;
    }

    void setCellHeight(double cellHeight) {
        this.cellHeight = cellHeight;
        _rectangle.setHeight(cellHeight);
    }

    // Update cell styling
    public void formatCell(String label, String square) {
        labelValue.getStyleClass().add(label);
        _rectangle.getStyleClass().add(square);
    }

    public void setLabel(String s) {
        labelValue.setText(s);
    }
}
