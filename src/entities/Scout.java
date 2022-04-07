package entities;

import java.util.ArrayList;

import application.GameMap;
import utility.Node;

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
	
	public void move(GameMap m, int x2, int y2) {
		ArrayList<int[]> path = Node.getPath(Node.aStar(new int[] {x, y}, new int[] {x2, y2}, m.getMobilityMap(true)));
		double moveDist = moveDistance;
		while (path.size() > 0) {
			int[] next = path.remove(0);
			if (m.isEntity(next[0], next[1]) || moveDist - m.getTile(next[0], next[1]).getMobility() < 0) {
				break;
			} else {
				moveDist -= m.getTile(next[0], next[1]).getMobility();
				this.x = next[0];
				this.y = next[1];
			}
		}
	}
}
