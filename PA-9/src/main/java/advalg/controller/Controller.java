package advalg.controller;

import advalg.App;
import advalg.model.NodeGraph;
import advalg.model.SCCTable;
import advalg.view.SCCView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class for the Main GUI.  Allows user to enter and clear task data.
 */
public class Controller implements Initializable {

    @FXML
    private GridPane _gridPane_scc;
    @FXML
    private Button _button_calculate;  // when pressed processes user-in
    @FXML
    private TextArea _textArea_userIn;  // space for user-in

    private NodeGraph ng;

    /**
     * Process user information on graph nodes and edges
     * <p>
     * Graph info can be entered using a ',' to represent an edge, e.g. A,B is nodes A & B connected
     * <p>
     * A ';' is used to separate node pairings. For example: A,B;A,E;A,F;B,C;B,E;E,D;C,D
     * <p>
     * If a semicolon is added to the last pair of each line pairs can be entered on multiple lines:
     * Using the previous example:
     * <p>
     * A,B;A,E;A,F;
     * B,C;B,E;
     * E,D;
     * C,D
     *
     * @return List of edge pairings
     */
    private List<String[]> parseUserIn() {
        var inputString = _textArea_userIn.getText().replace("\n", ";");
        var initialSplit = new ArrayList<>(Arrays.asList(inputString.split(";")));
        initialSplit.removeIf(a -> a.equals(""));

        List<String[]> parseInput = new ArrayList<>();
        for (var string : initialSplit) {
            parseInput.add(string.split(","));
        }
        return parseInput;
    }

    // Remove any previously generated tables
    private void clear() {
        _gridPane_scc.getChildren().clear();
    }

    // Initialize GUI elements
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        _button_calculate.setOnAction(e -> {

            // Return if user input is is empty
            if (_textArea_userIn.getText().isEmpty()) return;

            // Clear GUI
            clear();
            ng = new NodeGraph(parseUserIn());

            App.SCC.findSCCNodes(ng);

            var sccView = new SCCView(SCCTable.createAdjList(ng));
            _gridPane_scc.getChildren().add(sccView.getGridPane());
        });
    }
}
