package edu.advalg;

import java.util.ArrayList;

public class StairHelper {
    private int position;
    private ArrayList<ArrayList<Integer>> possibilities = new ArrayList<>();

    public StairHelper() {
        position = 0;
    }

    StairHelper(int startingPosition) {
        position = startingPosition;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int newPosition) {
        position = newPosition;
    }

    void addPossibility(ArrayList<Integer> newPossibility) {
        possibilities.add(newPossibility);
    }

    public ArrayList<Integer> returnPossibility(int posPosition) {
        return possibilities.get(posPosition);
    }

    ArrayList<ArrayList<Integer>> returnAll() {
        return possibilities;
    }
}
