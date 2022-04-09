package application;

import java.util.ArrayList;

import utility.Action;
import utility.Pair;

public class Player {
	public static final int NUM_MOVES = 2;
	int moves = NUM_MOVES;
	int team;
	Pair selectedPos = new Pair();
	Pair operationPos = new Pair();
	ArrayList<Action<Object, Boolean>> actionQueue = new ArrayList<Action<Object, Boolean>>();
	
	public Player (int team) {
		this.team = team;
	}
	
	public int getMoves() {
		return moves;
	}
	
	public void resetMoves() {
		moves = NUM_MOVES;
	}
	
	public boolean useMove() {
		return moves-- > -1;
	}
	
	public void addAction(Action<Object, Boolean> action) {
		actionQueue.add(action);
	}
	
	public Boolean doAction() {
		if (actionQueue.size() == 0) {
			return null;
		}
		return actionQueue.remove(0).exec();
	}
	
	public void setSelectedPos(Pair loc) {
		selectedPos = loc;
	}
	
	public void setOperationPos(Pair loc) {
		operationPos = loc;
	}
	
	public Pair getSelectedPos() {
		return selectedPos;
	}
	
	public Pair getOperationPos() {
		return operationPos;
	}
	
	public void newTurn() {
		resetMoves();
		actionQueue = new ArrayList<Action<Object, Boolean>>();
		selectedPos = null;
		operationPos = null;
	}
	
	public int getTeam() {
		return team;
	}
}
