package tiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Tile {
	public static final int IMPASS = 10000;
	boolean isVisible = false;
	boolean destroyed = false;
	boolean isDestroyable = false;
	boolean isTop = false;
	boolean canStack = true;
	String name = "GenericTile";
	double obscurity;
	int elevation;
	int mobility;
	double health;
	
	@SuppressWarnings("serial")
	public static ArrayList<Tile> bt = new ArrayList<Tile>() {
		{
			//			  name, 		health, obscurity, elevation, mobility, canStack, isTop
			add(new Tile("Ocean", 		0, 		0.6, 		0, 		IMPASS));
			add(new Tile("Grass", 		0, 		1, 			0, 		1, 			true, 	false));
			add(new Tile("Forest", 		10, 	0.8, 		0, 		1, 			true, 	true));
			add(new Tile("Hill", 		0, 		1.5, 		1, 		2, 			true, 	false));
			add(new Tile("Fort", 		40, 	1.2, 		1, 		3, 			true, 	true));
			add(new Tile("Mountain",	0, 		IMPASS, 	0, 		IMPASS));
			add(new Tile("Tower", 		20, 	1.5, 		2, 		3, 			true, 	true));
			add(new Tile("Beach", 		0, 		0.8, 		0, 		1));
		}
	};
	
	@SuppressWarnings("serial")
	public static final HashMap<String, Tile> TILE_SET = new HashMap<String, Tile>() {
		{
			put(bt.get(0).name, bt.get(0));
			put(bt.get(1).name, bt.get(1));
			put(bt.get(2).name, bt.get(2));
			put(bt.get(3).name, bt.get(3));
			put(bt.get(4).name, bt.get(4));
			put(bt.get(5).name, bt.get(5));
			put(bt.get(6).name, bt.get(6));
			put(bt.get(7).name, bt.get(7));
			put(Tile.stack(bt.get(2), bt.get(1)).name, Tile.stack(bt.get(2), bt.get(1)));
			put(Tile.stack(bt.get(2), bt.get(3)).name, Tile.stack(bt.get(2), bt.get(3)));
			put(Tile.stack(bt.get(4), bt.get(1)).name, Tile.stack(bt.get(4), bt.get(1)));
			put(Tile.stack(bt.get(4), bt.get(3)).name, Tile.stack(bt.get(4), bt.get(3)));
			put(Tile.stack(bt.get(6), bt.get(1)).name, Tile.stack(bt.get(6), bt.get(1)));
			put(Tile.stack(bt.get(6), bt.get(3)).name, Tile.stack(bt.get(6), bt.get(3)));
		}
	};
	
	@SuppressWarnings("unused")
	private Tile () {
		
	}
	
	public Tile (String name, double health, double obscurity, int elevation, int mobility) {
		this.name = name;
		this.health = health;
		if (this.health > 0) {
			this.isDestroyable = true;
		}
		this.obscurity = obscurity;
		this.elevation = elevation;
		this.mobility = mobility;
	}
	
	public Tile (String name, double health, double obscurity, int elevation, int mobility, boolean canStack, boolean isTop) {
		this.name = name;
		this.health = health;
		if (this.health > 0) {
			this.isDestroyable = true;
		}
		this.obscurity = obscurity;
		this.elevation = elevation;
		this.mobility = mobility;
		this.isTop = isTop;
		this.canStack = canStack;
	}
	
	private Tile (Tile top, Tile bottom) {
		if (top.canStack && bottom.canStack && top.isTop && !bottom.isTop) {
			this.name = "" + bottom.name + " " + top.name;
			this.health = top.health + bottom.health;
			this.isDestroyable = top.isDestroyable || bottom.isDestroyable;
			this.isTop = false;
			this.canStack = false;
			this.obscurity = top.obscurity + bottom.obscurity;
			this.elevation = top.elevation + bottom.elevation;
			this.mobility = top.mobility + bottom.mobility - 1;
		} else {
			this.name = null;
		}
	}
	
	public static Tile stack(Tile top, Tile bottom) {
		Tile stack = new Tile (top, bottom);
		if (stack.name == null) {
			return null;
		} else {
			return stack;
		}
	}
	
	public Tile copy() {
		return new Tile(this.name, this.health, this.obscurity, this.elevation, this.mobility, this.canStack, this.isTop);
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
	public double getObscurity() {
		return obscurity;
	}
	
	public int getMobility() {
		return mobility;
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
	public double getHealth() {
		return health;
	}
	
	/**
	 * Sets health value to input health value
	 * 
	 * @param d new health value
	 */
	public void setHealth(double d) {
		this.health = d;
		if (health < 0) {
			health = 0;
		}
	}
	
	/**
	 * Returns name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * for testing only
	 * 
	 * @param args ignored
	 */
	public static void main(String[] args) {
		Iterator<Tile> testTiles = TILE_SET.values().iterator();
		
		while (testTiles.hasNext()) {
			System.out.print("" + testTiles.next().name);
			if (testTiles.hasNext()) System.out.print(", ");
		}
		System.out.print("\n");
	}
}
