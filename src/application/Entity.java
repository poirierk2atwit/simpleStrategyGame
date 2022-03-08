package application;

public abstract class Entity {
	int x;
	int y;
	int healthPoints;
	int viewDistance;
	int moveDistance;
	int team;
	boolean[][] visionMap;
	
	public Entity(int x, int y, int team) {
		this.x = x;
		this.y = y;
		this.team = team;
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
	abstract public boolean[][] setVisionMap(GameMap m);
	
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
