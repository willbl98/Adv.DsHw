package advalg.model;

import advalg.view.PACell;

import java.util.ArrayList;

/**
 * Contains method to populate table body
 */
public interface IGraphData {
    ArrayList<ArrayList<PACell>> buildTableBody(NodeGraph nodeGraph, ArrayList<ArrayList<PACell>> table);
}
