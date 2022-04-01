package application;

import java.io.File;
import java.util.Scanner;

public class Main {

	/**
	 * A debug function intended to explicitly show different
	 * map attributes that would be visible in the final game.
	 * 
	 * @param attribute string of the attribute to be printed
	 * @param map GameMap to be printed
	 */
	final static void printMap(String attribute, GameMap map) {
		String toPrint = "";
		for (int j = 0; j < map.height(); j++) {
			for (int i = 0; i < map.length(); i++) {
				if (attribute.equals("isVisible")) {
					toPrint = map.isVisible(i, j) ? "1" : "0";
				} else if (attribute.equals("health")) {
					toPrint = "" + map.getTile(i, j).getHealth();
				} else if (attribute.equals("isEntity")) {
					toPrint = map.isEntitiy(i, j) && map.isVisible(i, j) ? "1" : "0";
				}
				
				if (toPrint.length() == 1) {
					System.out.print(toPrint + "   ");
				} else if (toPrint.length() == 2) {
					System.out.print(toPrint + "  ");
				} else {
					System.out.print(toPrint + " ");
				}
				
			}
			System.out.print("\n\n");
		}
	}
	
	//Implement test map
	
	public static void main(String[] args) {
		//Load in textures
		
		GameMap currentMap; 
		Player[] players = {new Player(0), new Player(1)};
		
		Scanner input = new Scanner(System.in);
		
		while (true) {
			System.out.print("NewGame to start, Exit to exit. ");
			String thisInput = input.nextLine();
			if (thisInput.equals("Exit")) {
				System.out.println("Goodbye.");
				break;
				
			} else if (thisInput.equals("NewGame")) {
				File[] files = new File("src/maps/").listFiles();
				String mapList = "Avalible maps: ";
				for (int i = 0; i < files.length; i++) {
					mapList += "\n  " + files[i].getName().split("\\.", 2)[0];
				}
				
				System.out.println(mapList + "\n");
				while (true) {
					System.out.print("Write a map name: ");
					String mapName = input.next();
					currentMap = GameMap.fromFile(mapName);
					if (currentMap == null) {
						System.out.println("Map not found.");
					} else {
						System.out.println("Playing on map " + mapName + ".\n");
						break;
					}
				}
				
				input.nextLine(); //Fix for a bug where the Scanner takes a previous new line as the first user command.
				
				//initialize players
				
				while (true) {
					for (int p = 0; p < players.length; p++) {						
						players[p].newTurn();
						currentMap.fogOfWar(p);
						System.out.println("Player " + p);
						System.out.println("Input a command. Help for options. ");
						
						while (players[p].getMoves() > 0 && players[p].getStrikes() > 0) {
							System.out.print(" > ");
							String command = input.nextLine();
							String[] coms = command.split(" ");
							
							//User interface will use send commands ig
							
							if (coms[0].equals("set")) {
								if (coms[1].equals("slcPos")) {
									//don't worry about proper input; user interface will use proper values.
									int x = Integer.parseInt(coms[2]);
									int y = Integer.parseInt(coms[3]);
									if (currentMap.isEntitiy(x, y) && currentMap.getEntity(x, y).isTeam(players[p].getTeam())) {
										players[p].setSelectedPos(x, y);
									} else {
										System.out.println("Can't select tile.");
									}
								} else if (coms[1].equals("opPos")) {
									players[p].setOperationPos(Integer.parseInt(coms[2]), Integer.parseInt(coms[3]));
								}
							} else if (coms[0].equals("move")) {
								if (!currentMap.move(players[p].getSelectedPos(), players[p].getOperationPos(), players[p].getTeam()) || !players[p].useMove()) {
									System.out.println("Cannot complete move.");
								}
							} else if (coms[0].equals("attack")) {
								if (!currentMap.attack(players[p].getSelectedPos(), players[p].getOperationPos(), players[p].getTeam()) || !players[p].useMove()) {
									System.out.println("Cannot complete attack.");
								}
							} else if (coms[0].equals("strike")) {
								if (!currentMap.canStrike(players[p].getOperationPos(), players[p].getTeam()) || !players[p].useStrike()) {
									System.out.println("Cannot complete strike.");
								} else {
									currentMap.strike(players[p].getOperationPos(), players[p].getTeam());
								}
							} else if (coms[0].equals("display")) {
								printMap(coms[1], currentMap);
							} else if (coms[0].equals("end")){
								break;
							} else if (coms[0].equals("help")) {
								System.out.println("set <slcPos|opPos> x y\nmove\nattack\nstrike\ndisplay <attribute>\nend\nhelp\n");
							} else {
								System.out.println("Unknown Command.");
							}
						}
						//implement change player indicator
						System.out.print("Press ENTER when the next player is ready. ");
						input.nextLine();
					}
					//implement win detection
				}
			}
		}
		input.close();
	}
}
