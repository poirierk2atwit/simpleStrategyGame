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
	
	public boolean isDestroyed() {
		return health <= 0 && isDestroyable;
	}
	
	public int getObscurity() {
		return obscurity;
	}
	
	public int getElevation() {
		return elevation;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
}
