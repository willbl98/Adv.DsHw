package pa7.graph.controller;

import pa7.graph.model.AdjList;
import pa7.graph.model.AdjMatrix;
import pa7.graph.model.BFSGraph;
import pa7.graph.model.NodeGraph;
import pa7.graph.view.AdjListView;
import pa7.graph.view.AdjMatrixView;
import pa7.graph.view.BFSView;
import pa7.table.PACell;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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
    private GridPane _gridPane_matrix;  // holds adjacency matrix table

    @FXML
    private GridPane _gridPane_list;  // holds adjacency list table

    @FXML
    private VBox _vBox_bfsContainer;  // holds bfs tracking tables

    @FXML
    private Button _button_calculate;  // when pressed processes user-in

    @FXML
    private TextArea _textArea_userIn;  // space for user-in

    @FXML
    private TitledPane _titledPane_console;  // holds user-in tools

    @FXML
    private RadioButton _radioButton_directed;  // lets user select directed graph

    @FXML
    private RadioButton _radioButton_undirected;  // lets user select directed graph

    private BooleanProperty isDirected = new SimpleBooleanProperty();  // listens for changes to radio_button

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
        String inputString = _textArea_userIn.getText().replace("\n", ";");
        ArrayList<String> initialSplit = new ArrayList<>(Arrays.asList(inputString.split(";")));
        initialSplit.removeIf(a -> a.equals(""));

        List<String[]> parseInput = new ArrayList<>();
        for (String string : initialSplit) {
            parseInput.add(string.split(","));
        }
        return parseInput;
    }

    // Remove any previously generated tables
    private void clear() {
        _vBox_bfsContainer.getChildren().clear();
        _gridPane_matrix.getChildren().clear();
        _gridPane_list.getChildren().clear();
    }

    // Initialize GUI elements
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // bind lister to radio_button
        isDirected.bind(_radioButton_directed.selectedProperty());

        // Undirected is the default option
        _radioButton_undirected.setSelected(true);
        _button_calculate.setOnAction(e -> {
            // Clear GUI
            clear();

            // Build Graph
            NodeGraph ng = new NodeGraph(parseUserIn(), isDirected.get());

            // Generate Adjacency Matrix tables
            AdjMatrixView adjMatrix = new AdjMatrixView(AdjMatrix.createAdjMatrix(ng));
            _gridPane_matrix.getChildren().add(adjMatrix.getGridPane());

            // Generate Adjacency List tables
            AdjListView adjListView = new AdjListView(AdjList.createAdjList(ng));
            _gridPane_list.getChildren().add(adjListView.getGridPane());

            // Generate BFS tables
            for (ArrayList<ArrayList<PACell>> bfsTables : BFSGraph.createBFSTable(ng)) {
                BFSView bfsView = new BFSView(bfsTables);
                GridPane gridPane = new GridPane();
                gridPane.getChildren().add(bfsView.getGridPane());
                gridPane.setAlignment(Pos.CENTER);
                _vBox_bfsContainer.getChildren().add(gridPane);
            }
        });

        // Update radio buttons when either is toggled
        _radioButton_undirected.selectedProperty().addListener((observableValue, isNotToggled, isToggled) -> {
            if (isToggled) {
                _radioButton_directed.setSelected(false);
            } else {
                _radioButton_directed.setSelected(true);
            }
        });
        _radioButton_directed.selectedProperty().addListener((observableValue, isNotToggled, isToggled) -> {
            if (isToggled) {
                _radioButton_undirected.setSelected(false);
            } else {
                _radioButton_undirected.setSelected(true);
            }
        });

        // Color titledPane based on if its toggled or not
        _titledPane_console.expandedProperty().addListener((observableValue, isNotToggled, isToggled) -> {
            if (isToggled) {
                _titledPane_console.setStyle("-fx-color: #ef6c00; -fx-background-color: #ef6c00; -fx-text-fill: black");
            } else {
                _titledPane_console.setStyle("-fx-color: #a9cff1; -fx-background-color: #a9cff1;");
            }
        });
    }
}
