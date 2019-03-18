import java.util.ArrayList;

public class stairHelper {
	private int position;
	private ArrayList<ArrayList<Integer>> possibilities = new ArrayList<ArrayList<Integer>>();
	
	public stairHelper() {
		position = 0;
	}
	
	public stairHelper (int startingPosition) {
		position = startingPosition;
	}
	
	public int getPosition() {
		return position;
	}
	public void setPosition(int newPosition) {
		position = newPosition;
	}
	
	public void addPossibility(ArrayList<Integer> newPossibility) {
		possibilities.add(newPossibility);
		return;
	}
	
	public ArrayList<Integer> returnPossibility(int posPosition) {
		return possibilities.get(posPosition);
	}
	
	public ArrayList<ArrayList<Integer>> returnAll() {
		return possibilities;
	}
}