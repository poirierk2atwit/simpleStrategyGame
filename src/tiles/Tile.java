package tiles;

public abstract class Tile {
	boolean isVisible = false;
	int health;
	boolean traversable = true;
	boolean destroyed = false;
	boolean isDestroyable;
	int obscurity;
	int elevation;
	int mobility;
	
	public Tile () {
		
	}
	
	public Tile (int health, boolean isDestroyable, int obscurity, int elevation, int mobility) {
		this.health = health;
		this.isDestroyable = isDestroyable;
		this.obscurity = obscurity;
		this.elevation = elevation;
		this.mobility = mobility;
	}
	
	public boolean isVisible() {
		return isVisible;
	}
	
	public void setVisible(boolean set) {
		isVisible = set;
	}
	
	/**
	 * Returns whether the tile health is below 1 and the tile can be destroyed
	 * 
	 * @return whether the tile health is below 1 and the tile can be destroyed
	 */
	public boolean isDestroyed() {
		return health <= 0 && isDestroyable;
	}
	
	/**
	 * Returns obscurity value
	 * 
	 * @return obscurity value
	 */
	public int getObscurity() {
		return obscurity;
	}
	
	/**
	 * Returns elevation value
	 * 
	 * @return elevation value
	 */
	public int getElevation() {
		return elevation;
	}
	
	/**
	 * Returns health value
	 * 
	 * @return health value
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * Sets health value to input health value
	 * 
	 * @param health new health value
	 */
	public void setHealth(int health) {
		this.health = health;
	}
}
