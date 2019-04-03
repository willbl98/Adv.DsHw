package pa6.controller;

import pa6.model.NodeMatrix;
import pa6.model.SequencePair;
import pa6.view.NodeMatrixView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller class for the GUI.  Processes user input.
 */
public class Controller implements Initializable {

    @FXML
    private GridPane _grid_output; // visual representation of Dynamic Programming matrix

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


    public Controller() {
    }

    // Sequence 2 is used as the row, sequence1 is used as the column sequence
    private void setSequences(NodeMatrix nodeMatrix) {
        nodeMatrix.setSequences(_textField_sequence2.getText().split(""),
                _textField_sequence1.getText().split(""));
    }

    private void writeOutput(NodeMatrix nodeMatrix) {
        _textArea_output.clear();
        _textArea_output.appendText("Optimum Score: "+ nodeMatrix.findMaxScore() + "\n");
        _textArea_output.appendText("Optimum Sequences:\n");
        for (ArrayList<SequencePair> sequencePairs : nodeMatrix.getOptimumSequenceAlignments()) {
            for (SequencePair sequencePair : sequencePairs) {
                _textArea_output.appendText(sequencePair.getColLetter());
            }
            _textArea_output.appendText("\n");
            for (SequencePair sequencePair : sequencePairs) {
                _textArea_output.appendText(sequencePair.getRowLetter());
            }
            _textArea_output.appendText("\n\n");
        }
    }

    // Grab user data and create the matrix
    private void createMatrix() {
        NodeMatrix nodeMatrix = new NodeMatrix(
                Integer.parseInt(_textField_match.getText()),
                Integer.parseInt(_textField_mismatch.getText()),
                Integer.parseInt(_textField_gap.getText()));
        setSequences(nodeMatrix);
        nodeMatrix.createMatrix();
        NodeMatrixView boardView = new NodeMatrixView(nodeMatrix);
        _grid_output.getChildren().setAll(boardView.getGridPane());
        writeOutput(nodeMatrix);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        _button_calculate.setOnAction(e -> createMatrix());
    }
}
