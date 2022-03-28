package entities;

import application.GameMap;
import tiles.Tile;

public abstract class Entity {
	public int x;
	public int y;
	int healthPoints;
	int viewDistance;
	int moveDistance;
	int team;
	boolean[][] visionMap;
	
	//Do not use, only for Gson
	public Entity() {
		
	}
	
	public Entity(int x, int y, int team) {
		this.x = x;
		this.y = y;
		this.team = team;
	}
	
	public String toString() {
		return "x:" + x + " y:" + y + " team:" + team;
	}
	
	/**
	 * Returns whether the entity has health points below 1
	 * 
	 * @return whether the entity has health points below 1
	 */
	public boolean isDead() {
		return healthPoints <= 0;
	}
	
	/**
	 * Creates a 2D boolean array of whether the entity can see tiles around it.
	 * 
	 * @param m GameMap object
	 * @return 2D boolean array
	 */
	public boolean[][] setVisionMap(GameMap m, boolean toDisplay) {
		Tile thisTile = m.getTile(x, y);
		int maxViewDist = viewDistance + thisTile.getElevation();
		int[][] viewMap = new int[maxViewDist*2+1][maxViewDist*2+1];
		int[][] obscurityMap = new int[maxViewDist*2+1][maxViewDist*2+1];
		boolean[][] output = new boolean[maxViewDist*2+1][maxViewDist*2+1];
		for (int i = 0; i <= (2 * maxViewDist); i++) {
			for (int j = 0; j <= (2 * maxViewDist); j++) {
				try {
					obscurityMap[i][j] = m.getTile(i + x - maxViewDist, j + y - maxViewDist).getObscurity();
				} catch (Exception e) {
					obscurityMap[i][j] = Integer.MIN_VALUE;
				}
			}
		}
		int mid = maxViewDist;
		viewMap[mid][mid] = maxViewDist;
		output[mid][mid] = true;
		for (int right = 0; right < maxViewDist; right++) { 
			for (int out = 1; out <= maxViewDist - right; out++) {
				int isHors = 1;
				int outNeg = -1;
				for (int b = 0; b < 4; b++) {
					isHors = isHors == 1 ? 0 : 1;
					outNeg = isHors == 0 ? (outNeg*-1) : outNeg;
					int rightNeg = (isHors*2 + outNeg) == 1 ? 1 : -1;
					int i = mid+(out*isHors*outNeg)+(right*((isHors*-1)+1)*rightNeg);
					int j = mid+(out*((isHors*-1)+1)*outNeg)+(right*isHors*rightNeg);
					int iIn = i+(-1*isHors*outNeg);
					int jIn = j+(-1*((isHors*-1)+1)*outNeg);
					int iLeft = i+(-1*((isHors*-1)+1)*rightNeg);
					int jLeft = j+(-1*isHors*rightNeg);
					if (right == 0) {
						viewMap[i][j] = viewMap[iIn][jIn] - obscurityMap[i][j];
					} else {
						viewMap[i][j] = (viewMap[iIn][jIn] + viewMap[iLeft][jLeft])/2 - obscurityMap[i][j];
					}
					if (viewMap[i][j] <= 0) {
						viewMap[i][j] = 0;
						output[i][j] = false;
					} else {
						output[i][j] = true;
					}
				}
			}
		}
		if (toDisplay && output.length >= 1) {
			for (int j = 0; j < output.length; j++) {
				for (int i = 0; i < output[0].length; i++) {
					if (output[i][j]) {
						System.out.print("1 ");
					} else {
						System.out.print("0 ");
					}
				}
				System.out.print("\n");
			}
		}
		return output;
	}
	
	public boolean[][] setVisionMap(GameMap m) {
		return setVisionMap(m, false);
	}
	
	/**
	 * Returns the 2D boolean array vision map
	 * 
	 * @return 2D boolean array vision map.
	 */
	public boolean[][] getVisionMap() {
		return visionMap;
	}
	
	/**
	 * Checks the 2D boolean array vision map at the point x y
	 * 
	 * @param x x of target tile
	 * @param y y of target tile
	 * @return the value of the target tile on the vision map, or false if out of range.
	 */
	public boolean canSee(int x, int y) {
		return false;
	}
	
	/**
	 * Returns whether the entity team matches the input team value
	 * 
	 * @param team input team value
	 * @return whether the input team is equal to the entity team
	 */
	public boolean isTeam(int team) {
		return this.team == team;
	}
	
	/**
	 * Returns whether the entity is capable of attacking the tile x y
	 * 
	 * @param m GameMap object
	 * @param x x of target tile
	 * @param y y of target tile
	 * @return whether the entity is capable of attacking the tile x y
	 */
	abstract public boolean canAttack(GameMap m, int x, int y);
	
	/**
	 * Returns whether the entity can move to the tile x y
	 * 
	 * @param m GameMap object
	 * @param x x of target tile
	 * @param y y of target tile
	 * @return whether the entity can move to the tile x y
	 */
	abstract public boolean canMove(GameMap m, int x, int y);
	
	/**
	 * If possible, executes the entity's attack on the target tile.
	 * 
	 * @param m GameMap object
	 * @param x x of target tile
	 * @param y y of target tile
	 */
	abstract public void attack(GameMap m, int x, int y);
	
	/**
	 * If possible, executes the entity's movement to the target tile.
	 * 
	 * @param m GameMap object
	 * @param x x of target tile
	 * @param y y of target tile
	 */
	abstract public void move(GameMap m, int x, int y);
	
}
