package entities;

import application.GameMap;

public class Scout extends Entity {
	@SuppressWarnings("unused")
	private Scout() {
	}
	
	public Scout(int x, int y, int team) {
		super(x, y, team);
		viewDistance = 4;
		moveDistance = 4;
	}

	@Override
	public boolean canAttack(GameMap m, int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void attack(GameMap m, int x, int y) {
		// TODO Auto-generated method stub
		
	}
}
