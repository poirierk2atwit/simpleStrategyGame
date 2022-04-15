package application;

import java.util.ArrayList;

import utility.Pair;

public class PlayerDEPRECIATED {
	public static final int NUM_MOVES = 2;
	int moves = NUM_MOVES;
	int team;
	Pair selectedPos = new Pair();
	Pair operationPos = new Pair();
	
	public PlayerDEPRECIATED (int team) {
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
		selectedPos = null;
		operationPos = null;
	}
	
	public int getTeam() {
		return team;
	}
}
