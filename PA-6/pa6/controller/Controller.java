package pa6.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pa6.model.NodeMatrix;
import pa6.view.NodeMatrixView;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
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
    private Button _button_newWindow;  // opens a window with just the text results.  Useful if several alignments

    private NodeMatrix _nodeMatrix;


    public Controller() {
    }

    // Display output in textArea
    private void writeOutput(NodeMatrix nodeMatrix) {
        TextWindowController.writeOutput(nodeMatrix, _textArea_output);
    }

    // Grab user data and create the matrix
    private void createMatrix() {
        String[] s1 = _textField_sequence2.getText().split("");
        String[] s2 = _textField_sequence1.getText().split("");
        if (isInvalidSequence(s1) || isInvalidSequence(s2)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sequences cannot contain empty space");
            alert.setTitle("Sequence Data Error");
            alert.showAndWait();
            return;
        }
        try {
            int match = Integer.parseInt(_textField_match.getText());
            int mismatch = Integer.parseInt(_textField_mismatch.getText());
            int gap = Integer.parseInt(_textField_gap.getText());
            _nodeMatrix = new NodeMatrix(s1, s2, match, mismatch, gap);
            NodeMatrixView boardView = new NodeMatrixView(_nodeMatrix);
            _grid_output.getChildren().setAll(boardView.getGridPane());
            writeOutput(_nodeMatrix);
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Score values must be integers");
            alert.setTitle("Score Data Error");
            alert.showAndWait();
        }
    }

    private boolean isInvalidSequence(String[] s) {
        return Arrays.asList(s).contains("");
    }

    // Opens a new window and displays the max profit Schedule as well as all legitimate schedules
    private void showInNewWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Controller.class.getResource("/pa6/view/textwindow.fxml"));

        TextWindowController setupMenuController = new TextWindowController(_nodeMatrix);
        fxmlLoader.setController(setupMenuController);

        Parent layout = fxmlLoader.load();
        Scene scene = new Scene(layout);
        Stage popupStage = new Stage();

        popupStage.setTitle("PA-6 Just Text Results");
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
