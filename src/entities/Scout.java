package entities;

import application.GameMap;

public class Scout extends Entity {

	@SuppressWarnings("unused")
	private Scout() {
		viewDistance = 4;
	}
	
	public Scout(int x, int y, int team) {
		super(x, y, team);
		viewDistance = 4;
	}

	@Override
	public boolean canAttack(GameMap m, int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canMove(GameMap m, int x, int y) {
		return false;
	}

	@Override
	public void attack(GameMap m, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(GameMap m, int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
