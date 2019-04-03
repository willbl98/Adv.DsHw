package pa6.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import pa6.model.NodeMatrix;
import pa6.model.SequencePair;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Opens a new window with only the text results
 */
class TextWindowController implements Initializable {

    private final NodeMatrix _nodeMatrix;
    @FXML
    private TextArea _textArea_output;

    TextWindowController(NodeMatrix nodeMatrix) {
        _nodeMatrix = nodeMatrix;
    }

    static void writeOutput(NodeMatrix nodeMatrix, TextArea textArea_output) {
        textArea_output.clear();
        textArea_output.appendText("Optimum Alignment Score: " + nodeMatrix.findMaxScore() + "\n");
        textArea_output.appendText("Optimum Sequence Alignments:\n");
        for (ArrayList<SequencePair> sequencePairs : nodeMatrix.getOptimumSequenceAlignments()) {
            for (SequencePair sequencePair : sequencePairs) {
                textArea_output.appendText(sequencePair.getColLetter());
            }
            textArea_output.appendText("\n");
            for (SequencePair sequencePair : sequencePairs) {
                textArea_output.appendText(sequencePair.getRowLetter());
            }
            textArea_output.appendText("\n\n");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        writeOutput(_nodeMatrix, _textArea_output);
    }
}
