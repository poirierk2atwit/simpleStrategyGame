package application;

import java.util.ArrayList;

/**
 * A class that handles a 2D array of Tiles and entities which sit on those tiles.
 * 
 * @author poirierk2
 */
public class GameMap {
	Tile[][] tiles;
	ArrayList<Entity> entities = new ArrayList<Entity>();
	
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
	
	///**
	// * Creates a GameMap object from loading a file.
	// */
	//public GameMap() {
	//	
	//}
	
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
		
	}
	
	/**
	 * Adds a single Entity objects to the GameMap object.
	 * 
	 * @param entity new Entity object
	 */
	public void addEntity(Entity entity) {
		
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
	
	/**
	 * Executes an artillery strike on the position x y, damaging the tile and any entity present.
	 * 
	 * @param x x position
	 * @param y y position
	 */
	public void strike(int x, int y, int team) {
		
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
	
	/**
	 * Sets the isVisible boolean of all Tile objects to match the visibility map of all Entity objects from the specified team.
	 * 
	 * @param team team entities will be selected from
	 */
	public void fogOfWar(int team) {
		
	}
	
	/**
	 * A main that can be run as a developer utility for creating maps. NOT THE MAIN FOR THE GAME.
	 * 
	 * @param args ignored
	 */
	public static void main (String[] args) { //Leave this at the bottom of the file please
		
	}
}
