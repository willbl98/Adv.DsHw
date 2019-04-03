package pa6.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pa6.model.NodeMatrix;
import pa6.view.NodeMatrixView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the GUI.  Processes user input.
 */
public class Controller implements Initializable {

    @FXML
    private GridPane _grid_output; // visual representation of dynamic programming matrix

    @FXML
    private TextField _textField_sequence1; // input for first sequence

    @FXML
    private TextField _textField_sequence2;  // input for second sequence

    @FXML
    private TextField _textField_match;  // input match score

    @FXML
    private TextField _textField_mismatch;  // input mismatch score

    @FXML
    private TextField _textField_gap;  // input gap score

    @FXML
    private TextArea _textArea_output;  // located at bottom of window

    @FXML
    private Button _button_calculate;  // when pressed, creates a matrix from user data

    @FXML
    private Button _button_newWindow;

    private NodeMatrix _nodeMatrix;


    public Controller() {
    }

    // Sequence 2 is used as the row, sequence1 is used as the column sequence
    private void setSequences(NodeMatrix nodeMatrix) {
        nodeMatrix.setSequences(_textField_sequence2.getText().split(""),
                _textField_sequence1.getText().split(""));
    }

    private void writeOutput(NodeMatrix nodeMatrix) {
        JustResultsController.writeOutput(nodeMatrix, _textArea_output);
    }

    // Grab user data and create the matrix
    private void createMatrix() {
        _nodeMatrix = new NodeMatrix(
                Integer.parseInt(_textField_match.getText()),
                Integer.parseInt(_textField_mismatch.getText()),
                Integer.parseInt(_textField_gap.getText()));
        setSequences(_nodeMatrix);
        _nodeMatrix.createMatrix();
        NodeMatrixView boardView = new NodeMatrixView(_nodeMatrix);
        _grid_output.getChildren().setAll(boardView.getGridPane());
        writeOutput(_nodeMatrix);
    }

    // Opens a new window and displays the max profit Schedule as well as all legitimate schedules
    private void showInNewWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Controller.class.getResource("/pa6/view/popup.fxml"));

        JustResultsController setupMenuController = new JustResultsController(_nodeMatrix);
        fxmlLoader.setController(setupMenuController);

        Parent layout = fxmlLoader.load();
        Scene scene = new Scene(layout);
        Stage popupStage = new Stage();

        popupStage.setTitle("PA-6 Just Results");
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        _button_calculate.setOnAction(e -> {
            createMatrix();
            _button_newWindow.setDisable(false);
        });
        _button_newWindow.setOnAction(e -> {
            try {
                showInNewWindow();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        _button_newWindow.setDisable(true);
    }
}
