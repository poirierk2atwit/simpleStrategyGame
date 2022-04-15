package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import mapObjects.*;
import utility.*;
import utility.Pair.Path;

/**
 * A class that handles a 2D array of Tiles and entities which sit on those tiles.
 * 
 * @author poirierk2
 */
public class GameMap {
	public static Gson gson = new GsonBuilder().setPrettyPrinting().
			registerTypeAdapter(Entity.class, new EntityAdapter()).create();
	private Tile[][] tiles;
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private transient GridPane tilePane = null;
	private transient Pane entityPane = null;
	private transient StackPane mapStack = null;
	
	static Tile[][] rotate(Tile[][] array) {
		if (array.length == 0 || array[0].length == 0) return null;
		Tile[][] output = new Tile[array[0].length][array.length];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				int x = j;
				int y = i;
				output[x][y] = array[i][j];
			}
		}
		return output;
	}
	
	/**
	 * For use by Gson.
	 */
	@SuppressWarnings("unused")
	private GameMap() {
		
	}
	
	/**
	 * Creates a blank GameMap object with rows r and columns c
	 * 
	 * @param x columns
	 * @param y rows
	 */
	public GameMap(int x, int y) {
		tiles = new Tile[x][y];
	}
	
	/**
	 * Creates a GameMap object from a 2D array of Tile objects.
	 * 
	 * @param tiles 2D array of Tile objects.
	 */
	public GameMap(Tile[][] tiles) {
		this.tiles = rotate(tiles);
	}
	
	private GridPane createTilePane() {
		if (tilePane == null) {
			tilePane = new GridPane();
			for (int j = 0; j < this.height(); j++) {
				for (int i = 0; i < this.length(); i++) {
					Pair loc = new Pair(i, j);
					getTile(loc).createStack();
					tilePane.add(getTile(loc).updateStack(), i, j);
				}
			}
		}
		return tilePane;
	}
	
	private Pane createEntityGroup() {
		if (entityPane == null) {
			entityPane = new Pane();
			entityPane.setLayoutX(0);
			entityPane.setLayoutY(0);
			for (int i = 0; i < entities.size(); i++) {
				entities.get(i).createPane(this);
				entityPane.getChildren().add(entities.get(i).updatePane(this));
			}
		}
		return entityPane;
	}
	
	private void updateTilePane() {
		for (int j = 0; j < this.height(); j++) {
			for (int i = 0; i < this.length(); i++) {
				Pair loc = new Pair(i, j);
				getTile(loc).createStack();
			}
		}
	}
	
	private void updateEntityPane() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).updatePane(this);
		}
	}
	
	public StackPane getMapStack() {
		if (mapStack == null) {
			mapStack = new StackPane();
			mapStack.getChildren().add(createTilePane());
			mapStack.getChildren().add(createEntityGroup());
		}
		return mapStack;
	}
	
	public void updateMapStack() {
		updateTilePane();
		updateEntityPane();
	}
	
	/**
	 * Gets the Tile object at position x y.
	 * 
	 * @param x x position
	 * @param y y position
	 * @return Tile object at x y
	 */
	public Tile getTile(Pair loc) {
		return (Tile) loc.getFrom(tiles);
	}
	
	/**
	 * Returns the isVisible method for the Tile at x y.
	 * Shortcut for <GameMap>.getTile(x, y).isVisible();
	 * 
	 * @param x x position
	 * @param y y position
	 * @return the isVisible method for the Tile at x y
	 */
	public boolean isVisibleOnMap(Pair loc) {
		return getTile(loc).isVisibleOnMap();
	}
	
	/**]
	 * Sets the Tile object at position x y.
	 * 
	 * @param x x position
	 * @param y y position
	 * @param tile new Tile object
	 */
	public void setTile(Pair loc, Tile tile) {
		tiles[loc.x()][loc.y()] = tile;
	}
	
	public void destroy(Pair loc) {
		setTile(loc, getTile(loc).getDestroyedVersion());
	}
	
	public int length() {
		return tiles.length;
	}
	
	public int height() {
		if (this.length() == 0) {
			return 0;
		}
		return tiles[0].length;
	}
	
	/**
	 * Adds an arbitrary number of entity objects to the GameMap object from an ArrayList<Entitiy>
	 * 
	 * @param entities ArrayList of Entity objects.
	 */
	public void addEntity(ArrayList<Entity> entities) {
		for (int i = 0; i < entities.size(); i++) {
			addEntity(entities.get(i));
		}
	}
	
	/**
	 * Adds a single Entity objects to the GameMap object.
	 * 
	 * @param entity new Entity object
	 */
	public void addEntity(Entity entity) {
		if (!(entity.x() < 0 || entity.y() < 0 || entity.x() > this.length() || entity.y() > this.height())) {
			entities.add(entity);
		} else {
			throw new IndexOutOfBoundsException(); 
		}
	}
	
	/**
	 * Returns all Entity objects from the GameMap object.
	 * 
	 * @return all Entity objects from the GameMap object
	 */
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
	/**
	 * Returns all Entity objects from the GameMap object of the team specified, or null if there are no such entities.
	 * 
	 * @param team number of the team
	 * @return all Entity objects that match the team specified
	 */
	public ArrayList<Entity> getEntitiesTeam (int team) {
		ArrayList<Entity> output = new ArrayList<Entity>();
		for (int i = entities.size() - 1; i >= 0; i--) {
			if (entities.get(i).isTeam(team)) {
				output.add(entities.get(i));
			}
		}
		return output;
	}
	
	/**
	 * Returns whether an entity is at position x y
	 * 
	 * @param x x position
	 * @param y y position
	 * @return whether an entity is at position x y
	 */
	public boolean isEntity(Pair loc) {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).equals(loc)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns Entity object at position x y, or null if there is no such object.
	 * 
	 * @param x x position
	 * @param y y position
	 * @return Entity object at position x y
	 */
	public Entity getEntity(Pair loc) {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).equals(loc)) {
				return entities.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Removes Entity object at the position x y. Returns false if there is no entity, or true if there is.
	 * 
	 * @param x x position
	 * @param y y position
	 * @return True on success and false on failure.
	 */
	public boolean removeEntity(Pair loc) {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).equals(loc)) {
				entities.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public int[][] getMobilityMap(boolean omniscient) {
		int[][] output = new int[length()][height()];
		for (int i = 0; i < length(); i++) {
			for (int j = 0; j < height(); j++) {
				Pair loc = new Pair (i, j);
				if ((isVisibleOnMap(loc) || omniscient) && isEntity(loc)) {
					output[i][j] = Tile.IMPASS;
				} else {
					output[i][j] = tiles[i][j].getMobility();
				}
			}
		}
		return output;
	}
	
	public int[][] getMobilityMap() {
		return getMobilityMap(false);
	}
	
	public Path getPath(Pair start, Pair target) {
		return PathNode.getPath(PathNode.aStar(start, target, getMobilityMap()));
	}
	
	/**
	 * Allows strike if it does not hit an allied entity.
	 * 
	 * @param x x position
	 * @param y y position
	 * @param team team of the striker
	 * @return whether the strike does not hit an allied entity
	 */
	public boolean canStrike(Pair loc, int team) {
		return false;
	}

	/**
	 * Executes an artillery strike on the position x y, damaging the tile and any entity present.
	 * 
	 * @param x x position
	 * @param y y position
	 */
	public void strike(Pair loc, int team) {
		
	}
	
	/**
	 * If there is an entity at x1 y1 of the correct team and entity.canAttack() returns true, runs the x1 y1 Entity object attack method.
	 * 
	 * @param x1 x position of attacker
	 * @param y1 y position of attacker
	 * @param x2 x position of attacked
	 * @param y2 y position of attacked
	 * @param team attacker team confirmation
	 * @return whether the operation could be run
	 */
	public boolean attack(Pair pos1, Pair pos2, int team) {
		if (getEntity(pos1).canAttack(this, pos2)) {
			getEntity(pos1).attack(this, pos2);
			return true;
		}
		return false;
	}
	
	/**
	 * Deals damage to a point on the map
	 * 
	 * @param x x position
	 * @param y y position
	 * @param damage amount of damage
	 * @param guarded whether or not the tile can take a portion of the damage.
	 */
	public void damage(Pair loc, double damage, boolean guarded) {
		Tile tile = getTile(loc);
		double tileDamage = 0;
		if (isEntity(loc)) {
			double entityDamage;
			if (guarded) {	
				double ratio = 1/(1 + tile.getObscurity());
				double extra = 0;
				tileDamage = ratio * damage * tile.getObscurity();
				if (tile.getHealth() < tileDamage) {
					extra = tileDamage - tile.getHealth();
				}
				entityDamage = (ratio * damage) + extra;
			} else {
				entityDamage = damage;
			}
			if (getEntity(loc).damage(entityDamage)) {
				entityPane.getChildren().remove(getEntity(loc).updatePane(this));
				removeEntity(loc);
			}
		} else {
			tileDamage = damage;
		}
		if (tile.damage(tileDamage)) {
			destroy(loc);
		}
	}

	public void damage(Pair loc, double damage) {
		damage (loc, damage, true);
	}
	
	/**
	 * If there is an entity at x1 y1 of the correct team and entity.canMove() return true, runs the x1 y1 Entity object move method.
	 * 
	 * @param x1 x position of the mover
	 * @param y1 y position of the mover
	 * @param x2 x position to be moved to
	 * @param y2 y position to be moved to
	 * @param team mover team confirmation
	 * @return whether the operation could be run
	 */
	public boolean move(Pair pos1, Pair pos2, int team) {
		if (getEntity(pos1).isTeam(team) && getEntity(pos1).canMove(this, pos2)) {
			getEntity(pos1).move(this, pos2);
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the isVisible boolean of all Tile objects to match the visibility map of all Entity objects from the specified team.
	 * 
	 * @param team team entities will be selected from
	 */
	public void fogOfWar(int team) {
		ArrayList<Entity> teamEntities = this.getEntitiesTeam(team);
		this.clearVisibility();
		for (int c = 0; c < teamEntities.size(); c++) {
			Entity thisEntity = teamEntities.get(c);
			boolean[][] thisVisionMap = thisEntity.setVisionMap(this);
			int i2 = 0;
			for (int i = thisEntity.x() - (thisVisionMap.length)/2; i <= thisEntity.x() + (thisVisionMap.length)/2; i++) {
				if (i < 0) {
					i2 -= i;
					i = -1;
					continue;
				} else if (i >= tiles.length) {
					break;
				}
				int j2 = 0;
				for (int j = thisEntity.y() - (thisVisionMap.length)/2; j <= thisEntity.y() + (thisVisionMap.length)/2; j++) {
					if (j < 0) {
						j2 -= j;
						j = -1;
						continue;
					} else if (j >= tiles[i].length) {
						break;
					}
					tiles[i][j].setVisibleOnMap(tiles[i][j].isVisibleOnMap() || thisVisionMap[i2][j2]);
					j2++;
				}
				i2++;
			}
		}
	}
	
	public void clearVisibility() {
		for (int i = 0; i < length(); i++) {
			for (int j = 0; j < height(); j++) {
				Pair loc = new Pair(i, j);
				getTile(loc).setVisibleOnMap(false);
			}
		}
	}
	
	/**
	 * Creates JSON text with all the values needed for a GameMap object.
	 * Intended use is to then create a JSON file for the GameMap.
	 * 
	 * @param map A GameMap object
	 * @return JSON text with the values of a GameMap object.
	 * @throws IOException 
	 * @throws JsonIOException 
	 */
	public String toJson() {	
		return gson.toJson(this);
	}
	
	/**
	 * Creates a json file by the name name in the maps package of the GameMap
	 * 
	 * @param name name of the file
	 * @param overwrite whether this function will overwrite an existing file.
	 * @return Success
	 */
	public boolean toFile(String name, boolean overwrite) {
		String filePath = "src/maps/";
		String fileName = name + ".json";
		
		File myObj = new File(filePath + fileName);
		
		try {
	      System.out.println(myObj.getAbsolutePath() + "\n");
	      if (myObj.createNewFile()) {
	        System.out.println("File created: " + myObj.getName());
	      } else {
	    	  System.out.println("File already exists.");
	        if (!overwrite) {
	        	return false;
	        }
	      }
	    } catch (IOException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	      return false;
	    }
		
		try {
			FileWriter myWriter = new FileWriter(filePath + fileName);
	      	myWriter.write(this.toJson());
	      	myWriter.close();
	      	System.out.println("Successfully wrote to the file.");
	    } catch (IOException e) {
	    	System.out.println("An error occurred.");
	      	e.printStackTrace();
	      	return false;
	    }
		return true;
	}
	
	public boolean toFile(String name) {
		return toFile(name, false);
	}
	
	/**
	 * Generates a GameMap object from the file specified using Gson and a custom deserializer.
	 * 
	 * @param name name of the file to read
	 * @return a GameMap object from the file.
	 */
	public static GameMap fromFile(String name) {
		String filePath = "src/maps/";
		String fileName = name + ".json";
		
		File myObj = new File(filePath + fileName);
		
		try {
			Scanner gameMapFile = new Scanner(myObj);
			String json = "";
			while (gameMapFile.hasNextLine()) {
				json += gameMapFile.nextLine();
			}
			gameMapFile.close();
			return gson.fromJson(json, GameMap.class);
		} catch (Exception e) {
			System.out.println("An error occurred.");
		    e.printStackTrace();
		}
		return null;
	}
	
	private interface TileBuilder {
		Tile tile();
	}
	
	/**
	 * A main that can be run as a developer utility for creating maps. NOT THE MAIN FOR THE GAME.
	 * 
	 * @param args ignored
	 * @throws IOException 
	 */
	@SuppressWarnings("unused")
	public static void main (String[] args) throws IOException { //Leave this at the bottom of the file please
		int x;
		int y;
		Scanner input = new Scanner(System.in);
		//System.out.print("Please input x and y: ");
		//x = input.nextInt();
		//y = input.nextInt();
		
		TileBuilder o = () -> {return new Tile("Ocean");};
		TileBuilder g = () -> {return new Tile("Grass");};
		TileBuilder fg = () -> {return new Tile("Grass Forest");};
		TileBuilder b = () -> {return new Tile("Beach");};
		TileBuilder m = () -> {return new Tile("Mountain");};
		TileBuilder h = () -> {return new Tile("Hill");};
		TileBuilder fh = () -> {return new Tile("Hill Forest");};
		
		TileBuilder[][] prepMap = {
			{o, o, b, b, g,fg, g,fg, g, g, g, g, b, o, o},
			{o, b, b, g, g, g,fg,fg, g, g, g, g, g, b, o},
			{o, b, g, g, g, g, g, g,fg, g, h, g, g, b, o},
			{o, b, b, g, g, g,fg, g, g, g, g, g, h, h, o},
			{o, o, b, g, g, g, g,fg,fg,fh, g, g, h, h, o},
			{o, o, b, b, g, g,fg, g,fg, g, h, h, g, h, o},
			{o, o, o, b, b,fg, g, g, g, g, g, g, b, o, o},
			{o, o, o, o, b, b, g,fg, g, g, h, g, b, o, o},
		};
		
		Tile[][] map = new Tile[prepMap.length][prepMap[0].length];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = prepMap[i][j].tile();
			}
		}
		
		//GameMap testMap = new GameMap(x, y);
		GameMap testMap = new GameMap(map);
		x = testMap.length();
		y = testMap.height();
		
		testMap.addEntity(new Scout(new Pair (4, 1), 0));
		testMap.addEntity(new Scout(new Pair (3, 3), 0));
		testMap.addEntity(new Scout(new Pair (11, 1), 1));
		testMap.addEntity(new Scout(new Pair (10, 4), 1));
		
		//System.out.println(testMap.toJson());
		
		String name = "island";// + x + "x" + y;
		testMap.toFile(name, false);
		
		//System.out.println("\n" + GameMap.fromFile(name).toJson());
		input.close();
	}
}
