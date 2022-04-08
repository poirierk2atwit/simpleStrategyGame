package entities;

import java.util.ArrayList;

import application.GameMap;
import application.Main;
import tiles.Tile;
import utility.Node;
import utility.Trigger;

public abstract class Entity {
	public static ArrayList<int[]> directPath(int[] pos1, int[] pos2) {
		ArrayList<int[]> output = new ArrayList<int[]>();
		double length = pos2[0] - pos1[0];
		double height = pos2[1] - pos1[1];
		int signL = (length > 0) ? 1 : -1;
		int signH = (height > 0) ? 1 : -1;
		int x = pos1[0];
		int y = pos1[1];
		if (!(Math.abs(height) < 0.001) && Math.abs(length / height) < 1) {
			double ratio = Math.abs(length / height);
			double progress = 0.5;
			for (int i = 0; i < Math.abs(height); i++) {
				progress += ratio;
				y += (1 * signH);
				if (Math.abs(progress - 1) < 0.001 || progress > 1) {
					x += (1 * signL);
					progress -= 1;
				}
				output.add(new int[] {x, y});
			}
		} else {
			double ratio = height / length;
			double progress = 0.5;
			for (int i = 0; i < Math.abs(length); i++) {
				progress += ratio;
				x += (1 * signL);
				if (Math.abs(progress - 1) < 0.001 || progress > 1) {
					y += (1 * signH);
					progress -= 1;
				}
				output.add(new int[] {x, y});
			}
		}
		return output;
	}
	
	public int x;
	public int y;
	public double healthPoints;
	int viewDistance;
	int moveDistance;
	int range;
	int team;
	boolean[][] visionMap;
	
	//Do not use, only for Gson
	@SuppressWarnings("unused")
	protected Entity() {
		
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
		double[][] viewMap = new double[maxViewDist*2+1][maxViewDist*2+1];
		double[][] obscurityMap = new double[maxViewDist*2+1][maxViewDist*2+1];
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
		visionMap = output;
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
		return visionMap[x][y];
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
	 * If possible, executes the entity's attack on the target tile.
	 * 
	 * @param m GameMap object
	 * @param x x of target tile
	 * @param y y of target tile
	 */
	abstract public void attack(GameMap m, int x, int y);
	
	/**
	 * Returns whether the entity can move to the tile x y
	 * 
	 * @param m GameMap object
	 * @param x x of target tile
	 * @param y y of target tile
	 * @return whether the entity can move to the tile x y
	 */
	public boolean canMove(GameMap m, int x2, int y2) {
		System.out.println(Node.moveCost(m.getPath(x, y, x2, y2)));
		return Node.moveCost(m.getPath(x, y, x2, y2)) <= moveDistance && 
				(!(m.isVisible(x2, y2) && m.isEntity(x2, y2))); 
	}
	
	public boolean canMove(GameMap m, int x2, int y2, ArrayList<int[]> path) {
		return Node.moveCost(path) <= moveDistance && 
				!(m.isVisible(x2, y2) && m.isEntity(x2, y2)); 
	}
	
	/**
	 * If possible, executes the entity's movement to the target tile.
	 * 
	 * @param m GameMap object
	 * @param x x of target tile
	 * @param y y of target tile
	 */
	abstract public void move(GameMap m, int x2, int y2);
	
	public static void main(String[] args) {
		
		/*
		//DirectPath testing
		int[] pos = new int[] {9, 9};
		int[] target = new int[] {0, 3};
		GameMap currentMap = new GameMap(10, 10);
		ArrayList<int[]> path = directPath(pos, target);
		
		String toPrint;
		for (int j = 0; j < currentMap.height(); j++) {
			for (int i = 0; i < currentMap.length(); i++) {
				if (i == pos[0] && j == pos[1]) {
					toPrint = "S";
				} else if (i == target[0] && j == target[1]) {
					toPrint = "E";
				} else {
					toPrint = ".";
					for (int[] a : path) {
						if (i == a[0] && j == a[1]) {
							toPrint = "o";
						}
					}
				}
				System.out.print(Main.space(toPrint));
			}
			System.out.print("\n\n");
		}
		*/
	}
}
