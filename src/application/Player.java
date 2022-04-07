package application;

import java.util.ArrayList;

import utility.Action;

public class Player {
	public static final int NUM_MOVES = 4;
	int moves = NUM_MOVES;
	int team;
	int[] selectedPos = new int[2];
	int[] operationPos = new int[2];
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
	
	public void setSelectedPos(int x, int y) {
		selectedPos = new int[] {x, y};
	}
	
	public void setOperationPos(int x, int y) {
		operationPos = new int[] {x, y};
	}
	
	public int[] getSelectedPos() {
		return selectedPos;
	}
	
	public int[] getOperationPos() {
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
