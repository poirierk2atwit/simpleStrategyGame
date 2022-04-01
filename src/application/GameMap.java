package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import entities.Entity;
import tiles.ExampleTile1;
import tiles.Tile;
import tiles.TileTypeAdapter;

/**
 * A class that handles a 2D array of Tiles and entities which sit on those tiles.
 * 
 * @author poirierk2
 */
public class GameMap {
	public static Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Tile.class, new TileTypeAdapter()).create();
	private Tile[][] tiles;
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public GameMap() {
		
	}
	
	/**
	 * Creates a blank GameMap object with rows r and columns c
	 * 
	 * @param r rows
	 * @param c columns
	 */
	public GameMap(int r, int c) {
		tiles = new Tile[r][c];
	}
	
	/**
	 * Creates a GameMap object from a 2D array of Tile objects.
	 * 
	 * @param tiles 2D array of Tile objects.
	 */
	public GameMap(Tile[][] tiles) {
		this.tiles = tiles;
	}
	
	//Assign new GameMap directly from the json as in GameMap map = gson.fromJson(data, GameMap.class);
	
	/**
	 * Gets the Tile object at position x y.
	 * 
	 * @param x x position
	 * @param y y position
	 * @return Tile object at x y
	 */
	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}
	
	/**]
	 * Sets the Tile object at position x y.
	 * 
	 * @param x x position
	 * @param y y position
	 * @param tile new Tile object
	 */
	public void setTile(int x, int y, Tile tile) {
		tiles[x][y] = tile;
	}
	
	/**
	 * Adds an arbitrary number of entity objects to the GameMap object from an ArrayList<Entitiy>
	 * 
	 * @param entities ArrayList of Entity objects.
	 */
	public void addEntity(ArrayList<Entity> entities) {
		int inSize = entities.size();
		for (int i = 0;i <= inSize;i++) {
			this.entities.add(entities.get(i));
		}
	}
	
	/**
	 * Adds a single Entity objects to the GameMap object.
	 * 
	 * @param entity new Entity object
	 */
	public void addEntity(Entity entity) {
		this.entities.add(entity);
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
		return null;
	}
	
	/**
	 * Returns whether an entity is at position x y
	 * 
	 * @param x x position
	 * @param y y position
	 * @return whether an entity is at position x y
	 */
	public boolean isEntitiy(int x, int y) {
		return false;
	}
	
	/**
	 * Returns Entity object at position x y, or null if there is no such object.
	 * 
	 * @param x x position
	 * @param y y position
	 * @return Entity object at position x y
	 */
	public Entity getEntity(int x, int y) {
		return null;
	}
	
	/**
	 * Removes Entity object at the position x y. Returns false if there is no entity, or true if there is.
	 * 
	 * @param x x position
	 * @param y y position
	 * @return True on success and false on failure.
	 */
	public boolean removeEntity(int x, int y) {
		return false;
	}
	
	/**
	 * Allows strike if it does not hit an allied entity.
	 * 
	 * @param x x position
	 * @param y y position
	 * @param team team of the striker
	 * @return whether the strike does not hit an allied entity
	 */
	public boolean canStrike(int x, int y, int team) {
		return false;
	}
	
	public boolean canStrike(int[] pos, int team) {
		return this.canStrike(pos[0], pos[1], team);
	}
	
	/**
	 * Executes an artillery strike on the position x y, damaging the tile and any entity present.
	 * 
	 * @param x x position
	 * @param y y position
	 */
	public void strike(int x, int y, int team) {
		
	}
	
	public void strike(int[] pos, int team) {
		this.strike(pos[0], pos[1], team);
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
	public boolean attack(int x1, int y1, int x2, int y2, int team) {
		return false;
	}
	
	public boolean attack(int[] pos1, int[] pos2, int team) {
		return this.attack(pos1[0], pos1[1], pos2[0], pos2[1], team);
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
	public boolean move(int x1, int y1, int x2, int y2, int team) {
		return false;
	}
	
	public boolean move(int[] pos1, int[] pos2, int team) {
		return this.move(pos1[0], pos1[1], pos2[0], pos2[1], team);
	}
	
	/**
	 * Sets the isVisible boolean of all Tile objects to match the visibility map of all Entity objects from the specified team.
	 * 
	 * @param team team entities will be selected from
	 */
	public void fogOfWar(int team) {
		
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
	
	/**
	 * A main that can be run as a developer utility for creating maps. NOT THE MAIN FOR THE GAME.
	 * 
	 * @param args ignored
	 * @throws IOException 
	 */
	public static void main (String[] args) throws IOException { //Leave this at the bottom of the file please
		//int x = 1;
		//int y = 1;
		Tile[][] testTile = {{new ExampleTile1()}};
		GameMap testMap = new GameMap(testTile);
		
		//write tiles and entities in whatever way seems well enough
		
		System.out.println(testMap.toJson());
		
		String name = "mapTest2";
		testMap.toFile(name, true);
		
		System.out.println("\n" + GameMap.fromFile(name).toJson());
		
	}
}
