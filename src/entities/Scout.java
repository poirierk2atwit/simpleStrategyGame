package entities;

import application.GameMap;
import tiles.Tile;

public class Scout extends Entity {

	public Scout() {
		
	}
	
	public Scout(int x, int y, int team) {
		super(x, y, team);
	}

	@Override
	public boolean[][] setVisionMap(GameMap m, boolean toDisplay) {
		Tile thisTile = m.getTile(x, y);
		int maxViewDist = viewDistance + thisTile.getElevation();
		int[][] viewMap = new int[maxViewDist*2+1][maxViewDist*2+1];
		int[][] obscurityMap = new int[maxViewDist*2+1][maxViewDist*2+1];
		boolean[][] output = new boolean[maxViewDist*2+1][maxViewDist*2+1];
		for (int i = 0; i <= (2 * maxViewDist); i++) {
			for (int j = 0; j <= (2 * maxViewDist); j++) {
				try {
					obscurityMap[i][j] = m.getTile(i + x - maxViewDist, j + y - maxViewDist).getObscurity();
				} catch (Exception e) {
					obscurityMap[i][j] = Integer.MIN_VALUE;
				}
			}
		}
		int mid = maxViewDist + 1;
		viewMap[mid][mid] = maxViewDist;
		for (int right = 0; right < maxViewDist; right++) { 
			for (int out = 1; out <= maxViewDist - right; out++) {
				int isHors = 1;
				int outNeg = -1;
				for (int b = 0; b < 4; b++) {
					isHors = isHors == 1 ? 0 : 1;
					outNeg = isHors == 0 ? (outNeg*-1) : outNeg;
					int rightNeg = (isHors*2 + outNeg) == 1 ? 1 : -1;
					int i = mid+(out*isHors*outNeg)+(right*((isHors*-1)+1)*rightNeg);
					int j = mid+(out*((isHors*-1)+1)*outNeg)+(right*isHors*rightNeg);
					int iIn = i+(-1*isHors*outNeg);
					int jIn = j+(-1*((isHors*-1)+1)*outNeg);
					int iLeft = i+(-1*((isHors*-1)+1)*rightNeg);
					int jLeft = j+(-1*isHors*rightNeg);
					if (right == 0) {
						viewMap[i][j] = viewMap[iIn][jIn] - obscurityMap[i][j];
					} else {
						viewMap[i][j] = (viewMap[iIn][jIn] + viewMap[iLeft][jLeft])/2 - obscurityMap[i][j];
					}
					if (viewMap[i][j] <= 0) {
						viewMap[i][j] = 0;
						output[i][j] = false;
					} else {
						output[i][j] = true;
					}
				}
			}
		}
		if (toDisplay && output.length >= 1) {
			for (int j = 0; j < output.length; j++) {
				for (int i = 0; i < output[0].length; i++) {
					if (output[i][j]) {
						System.out.print("1  ");
					} else {
						System.out.print("0  ");
					}
				}
				System.out.print("\n");
			}
		}
		return output;
	}

	@Override
	public boolean canAttack(GameMap m, int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canMove(GameMap m, int x, int y) {
		// TODO Auto-generated method stub
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
