package mapObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import application.ImageHandler;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class Tile {
	private static HashMap<String, ImageHandler> utility = new HashMap<String, ImageHandler>();
	private static boolean setUp = false;
	
	public static final int IMPASS = 10000;
	transient ImageHandler baseImage = null;
	transient ImageHandler topImage = null;
	transient StackPane stackPane = new StackPane();
	transient ImageHandler visionImage = null;
	boolean isVisible = false;
	boolean destroyed = false;
	boolean isDestroyable = false;
	boolean isTop = false;
	boolean canStack = true;
	Tile destroyedVersion = null;
	String name = "Generic";
	double obscurity;
	int elevation;
	int mobility;
	double health;
	
	private static class DestroyedTile extends Tile {
		public final String replacing;
		
		public DestroyedTile (String replacing, double obscurity, int elevation, int mobility) {
			super ("Destroyed-" + replacing, 0, obscurity, elevation, mobility, true, true);
			this.replacing = replacing;
		}
	}
	
	@SuppressWarnings("serial")
	private static final HashMap<String, Tile> TILE_SET = new HashMap<String, Tile>() {

		ArrayList<DestroyedTile> dt = new ArrayList<DestroyedTile>() {
			{
				add(new DestroyedTile("Forest", 	0.3, 		0,		1));
				add(new DestroyedTile("Fort", 		0.6, 		0,		2));
				add(new DestroyedTile("Tower", 		0.5, 		0,		2));
			}
		};
		ArrayList<Tile> bt = new ArrayList<Tile>() {
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
		{
			ArrayList<Integer> bottoms = new ArrayList<Integer>();
			ArrayList<Integer> tops = new ArrayList<Integer>();
			for (int i = 0; i < bt.size(); i++) {
				for (DestroyedTile d : dt) {
					if (bt.get(i).name == d.replacing) {
						bt.get(i).setDestroyedVersion(d);
					}
				}
				if (bt.get(i).canStack) {
					if (bt.get(i).isTop) {
						tops.add(i);
					} else {
						bottoms.add(i);
						put(bt.get(i).name, bt.get(i));
					}
				} else {
					put(bt.get(i).name, bt.get(i));
				}
			}
			
			for (int i : tops) {
				for (int j : bottoms) {
					Tile tile = new Tile(bt.get(i), bt.get(j));
					put(tile.name, tile);
				}
			}
		}
		//ngl longest variable declaration I've ever written.
	};
	
	public static void getImages(Tile tile) {
		String[] names = tile.getName().split(" ");
		if (names.length == 1) {
			if (tile.isTop) {
				tile.topImage = new ImageHandler(tile.name);
			} else {
				tile.baseImage = new ImageHandler(tile.name);
			}
		} else {
			tile.topImage = new ImageHandler(names[1]);
			tile.baseImage = new ImageHandler(names[0]);
		}
	}

	public Tile (String name) {
		Tile ref = TILE_SET.get(name);
		isVisible = ref.isVisible;
		destroyed = ref.destroyed;
		isDestroyable = ref.isDestroyable;
		isTop = ref.isTop;
		canStack = ref.canStack;
		destroyedVersion = ref.destroyedVersion;
		this.name = ref.name;
		obscurity = ref.obscurity;
		elevation = ref.elevation;
		mobility = ref.mobility;
		health = ref.health;
	}
	
	@SuppressWarnings("unused")
	private Tile () {
		
	}
	
	private Tile (String name, double health, double obscurity, int elevation, int mobility) {
		this(name, health, obscurity, elevation, mobility, false, false);
	}
	
	private Tile (String name, double health, double obscurity, int elevation, int mobility, boolean canStack, boolean isTop) {
		this.name = name;
		this.health = health;
		if (this.health > 0) {
			this.isDestroyable = true;
		}
		this.obscurity = obscurity;
		this.elevation = elevation;
		this.mobility = mobility;
		this.canStack = canStack;
		this.canStack = canStack;
		this.isTop = isTop;
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
			if (!(top.destroyedVersion == null)) {
				this.destroyedVersion = new Tile(top.destroyedVersion, bottom);
			}
		} else {
			this.name = null;
		}
	}
	
	public void createStack() {
		getImages(this);
		if (!stackPane.getChildren().contains(baseImage)) {
			stackPane.getChildren().add(baseImage);
		}
		if (!(topImage == null) && !stackPane.getChildren().contains(topImage)) {
			stackPane.getChildren().add(topImage);
		}
		if (!stackPane.getChildren().contains(utility.get("notVisible"))) {
			this.visionImage = new ImageHandler("notVisible");
			stackPane.getChildren().add(visionImage);
		} 
		if (isVisible) {
			visionImage.setVisible(false);
		} else {
			visionImage.setVisible(true);
		}
	}
	
	public StackPane updateStack() {
		if (isVisible) {
			visionImage.setVisible(false);
		} else {
			visionImage.setVisible(true);
		}
		return stackPane;
	}
	
	private void setDestroyedVersion(Tile tile) {
		destroyedVersion = tile;
	}
	
	public Tile getDestroyedVersion() {
		return destroyedVersion;
	}
	
	public Tile copy() {
		return new Tile(this.name, this.health, this.obscurity, this.elevation, this.mobility, this.canStack, this.isTop);
	}
	
	public boolean isVisibleOnMap() {
		return isVisible;
	}
	
	public void setVisibleOnMap(boolean set) {
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

	public boolean damage(double damage) {
		health -= damage;
		if (isDestroyed()) {
			return true;
		}
		return false;
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
