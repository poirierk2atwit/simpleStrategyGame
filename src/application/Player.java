package application;

public class Player {
	public static final int NUM_MOVES = 4;
	public static final int NUM_STRIKES = 1;
	int moves = NUM_MOVES;
	int strikes = NUM_STRIKES;
	int team;
	int[] selectedPos = new int[2];
	int[] operationPos = new int[2];
	
	public Player (int team) {
		this.team = team;
	}
	
	public int getMoves() {
		return moves;
	}
	
	public int getStrikes() {
		return strikes;
	}
	
	public void resetMoves() {
		moves = NUM_MOVES;
	}
	
	public void resetStrikes() {
		strikes = NUM_STRIKES;
	}
	
	public boolean useMove() {
		return moves-- > -1;
	}
	
	public boolean useStrike() {
		return strikes-- > -1;
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
}
