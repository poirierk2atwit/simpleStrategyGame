package application;

import java.util.ArrayList;

public class Map {
	Tile[][] tiles;
	ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public Map(int r, int c) {
		tiles = new Tile[r][c];
	}
	
	public Map(Tile[][] tiles) {
		this.tiles = tiles;
	}
	
	//implement loading a map from a file
	//public Map() {
	//	
	//}
	
	public Tile getTile(int r, int c) {
		return tiles[r][c];
	}
	
	public void setTile(int r, int c, Tile tile) {
		tiles[r][c] = tile;
	}
	
	public void addEntity(ArrayList<Entity> entities) {
		
	}
	
	public void addEntity(Entity entity) {
		
	}
	
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
	public boolean removeEntity(int x, int y) {
		return false;
	}
	
	public void strike(int x, int y) {
		
	}
	
	public void fogOfWar(int team) {
		
	}
}
