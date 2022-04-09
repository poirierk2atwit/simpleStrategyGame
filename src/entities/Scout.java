package entities;

import java.util.ArrayList;

import application.GameMap;
import utility.Node;
import utility.Pair;

public class Scout extends Entity {
	@SuppressWarnings("unused")
	private Scout() {
	}
	
	public Scout(Pair loc, int team) {
		super(loc, team);
		viewDistance = 4;
		moveDistance = 5;
		range = 6;
		health = 20;
	}

	@Override
	public boolean canAttack(GameMap m, Pair loc) {
		return true;
	}

	//Scout deals 10 damage (guarded) when attacking
	@Override
	public void attack(GameMap m, Pair loc) {
		Path path = directPath(new Pair(this), loc);
		int elevation = m.getTile(new Pair(this)).getElevation();
		for (int r = range; r >= 0 ; r--) {
			Pair next = path.pop();
			if (m.isEntity(next) && !m.getEntity(next).isTeam(team) ||
					(m.getTile(next).getElevation() > elevation) ||
					(path.size() == 0)) {
				m.damage(next, 10);
				break;
			}
		}
	}
	
	public void move(GameMap m, Pair loc) {
		Path path = Node.getPath(Node.aStar(new Pair(this), loc, m.getMobilityMap(true)));
		double moveDist = moveDistance;
		while (path.size() > 0) {
			Pair next = path.pop();
			if (m.isEntity(next) || moveDist - m.getTile(next).getMobility() < 0) {
				break;
			} else {
				moveDist -= m.getTile(next).getMobility();
				setX(next.x());
				setY(next.y());
			}
		}
	}
}
