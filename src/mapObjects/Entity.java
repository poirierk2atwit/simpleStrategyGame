package mapObjects;

import application.Game;
import application.GameMap;
import application.ImageHandler;
import application.OldMain;
import javafx.scene.layout.StackPane;
import utility.PathNode;
import utility.Pair;

public abstract class Entity extends Pair {
	public static ImageHandler selectedImage = new ImageHandler("selected");
	public double health;
	int viewDistance;
	int moveDistance;
	int range;
	int team;
	boolean[][] visionMap;
	transient ImageHandler texture;
	transient StackPane pane = new StackPane();
	String name;
	
	public static void getImages(Entity entity) {
		entity.texture = new ImageHandler(entity.name);
	}
	
	//Do not use, only for Gson
	@SuppressWarnings("unused")
	protected Entity() {

	}
	
	public Entity(Pair pos, int team) {
		super (pos);
		this.team = team;
	}
	
	public void createPane(GameMap m) {
		getImages(this);
		if (!pane.getChildren().contains(texture)) {
			pane.getChildren().add(texture);
		}
		if (!(m.isVisibleOnMap(this))) {
			pane.setVisible(false);
		} else {
			pane.setVisible(true);
		}
		pane.setLayoutX(this.x() * ImageHandler.SIZE);
		pane.setLayoutY(this.y() * ImageHandler.SIZE);
	}
	
	public StackPane updatePane(GameMap m) {
		pane.setLayoutX(this.x() * ImageHandler.SIZE);
		pane.setLayoutY(this.y() * ImageHandler.SIZE);
		if (!(m.isVisibleOnMap(this))) {
			pane.setVisible(false);
		} else {
			pane.setVisible(true);
		}
		return this.pane;
	}
	
	public void select() {
		Game.setSelectedEntity(this);
		pane.getChildren().add(selectedImage);
	}
	
	public void deselect() {
		Game.setSelectedEntity(null);
		pane.getChildren().remove(selectedImage);
	}
	
	public String toString() {
		return "x:" + x() + " y:" + y() + " team:" + team;
	}
	
	/**
	 * Returns whether the entity has health points below 1
	 * 
	 * @return whether the entity has health points below 1
	 */
	public boolean isDead() {
		return health <= 0;
	}
	
	/**
	 * Creates a 2D boolean array of whether the entity can see tiles around it.
	 * 
	 * @param m GameMap object
	 * @return 2D boolean array
	 */
	public boolean[][] setVisionMap(GameMap m, boolean toDisplay) {
		Tile thisTile = m.getTile(this);
		int maxViewDist = viewDistance + thisTile.getElevation();
		double[][] viewMap = new double[maxViewDist*2+1][maxViewDist*2+1];
		double[][] obscurityMap = new double[maxViewDist*2+1][maxViewDist*2+1];
		boolean[][] output = new boolean[maxViewDist*2+1][maxViewDist*2+1];
		for (int i = 0; i <= (2 * maxViewDist); i++) {
			for (int j = 0; j <= (2 * maxViewDist); j++) {
				try {
					obscurityMap[i][j] = m.getTile(new Pair (i + x() - maxViewDist, j + y() - maxViewDist)).getObscurity();
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
	public boolean canSee(Pair pair) {
		return pair.getFrom(visionMap);
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
	
	public double getHealth() {
		return health;
	}
	
	public void setHealth(double health) {
		this.health = health;
		if (health < 0) {
			health = 0;
		}
	}
	
	public boolean damage(double damage) {
		health -= damage;
		if (isDead()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns whether the entity is capable of attacking the tile x y
	 * 
	 * @param m GameMap object
	 * @param x x of target tile
	 * @param y y of target tile
	 * @return whether the entity is capable of attacking the tile x y
	 */
	abstract public boolean canAttack(GameMap m, Pair pair);
	
	/**
	 * If possible, executes the entity's attack on the target tile.
	 * 
	 * @param m GameMap object
	 * @param x x of target tile
	 * @param y y of target tile
	 */
	abstract public void attack(GameMap m, Pair pair);
	
	/**
	 * Returns whether the entity can move to the tile x y
	 * 
	 * @param m GameMap object
	 * @param x x of target tile
	 * @param y y of target tile
	 * @return whether the entity can move to the tile x y
	 */
	public boolean canMove(GameMap m, Pair pos2) {
		return PathNode.moveCost(m.getPath(this, pos2)) <= moveDistance && 
				(!(m.isVisibleOnMap(pos2) && m.isEntity(pos2))); 
	}
	
	public boolean canMove(GameMap m, Pair pos2, Path path) {
		return PathNode.moveCost(path) <= moveDistance && 
				!(m.isVisibleOnMap(pos2) && m.isEntity(pos2)); 
	}
	
	/**
	 * If possible, executes the entity's movement to the target tile.
	 * 
	 * @param m GameMap object
	 * @param x x of target tile
	 * @param y y of target tile
	 */
	abstract public void move(GameMap m, Pair pos2);
	
	public static void main(String[] args) {
		
		//DirectPath testing
		Pair pos = new Pair(9, 9);
		Pair target = new Pair(2, 8);
		GameMap currentMap = new GameMap(10, 10);
		Path path = directPath(pos, target);
		
		String toPrint;
		for (int j = 0; j < currentMap.height(); j++) {
			for (int i = 0; i < currentMap.length(); i++) {
				Pair loc = new Pair (i, j);
				if (pos.equals(loc)) {
					toPrint = "S";
				} else if (target.equals(loc)) {
					toPrint = "E";
				} else {
					toPrint = ".";
					for (Pair a : path) {
						if (loc.equals(a)) {
							toPrint = "o";
						}
					}
				}
				System.out.print(OldMain.space(toPrint));
				//System.out.print(pos + " " + target + " " + loc);
			}
			System.out.print("\n\n");
		}
	}
}
