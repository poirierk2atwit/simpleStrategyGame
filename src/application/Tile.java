package application;

public abstract class Tile {
	boolean isVisible;
	int health;
	boolean destroyed;
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
