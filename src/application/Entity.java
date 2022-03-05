package application;

public abstract class Entity {
	int x;
	int y;
	int healthPoints;
	int viewDistance;
	int moveDistance;
	int team;
	boolean[][] visionMap;
	
	public Entity(int x, int y, int team) {
		this.x = x;
		this.y = y;
		this.team = team;
	}
	
	public boolean isDead() {
		return healthPoints <= 0;
	}
	
	abstract public boolean[][] setVisionMap(GameMap m);
	
	public boolean[][] getVisionMap() {
		return visionMap;
	}
	
	public boolean canSee(GameMap m, int x, int y) {
		return false;
	}
	
	public boolean isTeam(int team) {
		return this.team == team;
	}
	
	abstract public boolean canAttack(GameMap m, int x, int y);
	
	abstract public boolean canMove(GameMap m, int x, int y);
	
	abstract public void attack(GameMap m, int x, int y);
	
	abstract public void move(GameMap m, int x, int y);
	
}
