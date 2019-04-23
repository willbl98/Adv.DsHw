package advalg.model;

import advalg.view.PACell;

import java.util.ArrayList;

public class SCCTable implements IGraphData {
    private static final String title = "Results";
    private static ArrayList<String> rowBanner;

    /**
     * Create table, including rows, cols, and title to be processed by a View class
     *
     * @param ng NodeGraph
     * @return complete table
     */
    public static ArrayList<ArrayList<PACell>> createAdjList(NodeGraph ng) {
        rowBanner = new ArrayList<>();
        createRowBanner(ng);
        return GraphData.buildTable(title, null, rowBanner, null, new SCCTable(), ng);
    }

    private static void createRowBanner(NodeGraph nodeGraph) {
        for (int i = 1; i <= nodeGraph.getSCCNodes().size(); i++) {
            rowBanner.add("Group: " + i);
        }
    }

    @Override
    public ArrayList<ArrayList<PACell>> buildTableBody(NodeGraph nodeGraph,
                                                       ArrayList<ArrayList<PACell>> table) {
        int ctr = 1;
        for (ArrayList<Node> nodes : nodeGraph.getSCCNodes()) {
            for (Node node : nodes) {
                table.get(ctr).add(new PACell(TableCellType.CELL_WHITE, node.getName()));
            }
            ctr++;
        }
        return table;
    }
}
