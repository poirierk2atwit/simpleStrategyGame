package application;

public abstract class Entity {
	int x;
	int y;
	int healthPoints;
	int viewDistance;
	int moveDistance;
	int team;
	boolean[][] visionMap;
	
	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean isDead() {
		return healthPoints <= 0;
	}
	
	public boolean[][] setVisionMap(Map m) {
		return null;
	}
	
	public boolean[][] getVisionMap() {
		return visionMap;
	}
	
	public boolean canSee(Map m, int x, int y) {
		return false;
	}
	
	public boolean isTeam(int team) {
		return this.team == team;
	}
	
	abstract public boolean attack(Map m, int x, int y);
	
	abstract public boolean move(Map m, int x, int y);
	
}
