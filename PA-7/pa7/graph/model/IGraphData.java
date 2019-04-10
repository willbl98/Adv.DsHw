package pa7.graph.model;

import pa7.table.PACell;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Contains method to populate table body
 */
public interface IGraphData {
    /**
     * Creates table body
     *
     * @param nodeMap Node graph data
     * @param table   table to populate
     * @return completed table body
     */
    ArrayList<ArrayList<PACell>> buildTableBody(HashMap<String, Node> nodeMap, ArrayList<ArrayList<PACell>> table);
}
