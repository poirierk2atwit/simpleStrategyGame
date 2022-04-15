package application;

import java.io.File;
import java.util.Scanner;

import utility.Pair;
import utility.Pair.Path;

public class OldMain {

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
				Pair loc = new Pair (i, j);
				if (attribute.equals("isVisible")) {
					toPrint = map.isVisibleOnMap(loc) ? "1" : "0";
				} else if (attribute.equals("health")) {
					toPrint = "" + map.getTile(loc).getHealth();
				} else if (attribute.equals("isEntity")) {
					toPrint = map.isEntity(loc) && map.isVisibleOnMap(loc) ? "1" : "0";
				} else if (attribute.equals("type")) {
					toPrint = "";
					for (String a : map.getTile(loc).getName().split(" ")) {
						toPrint += a.substring(0, 1);
					}
				} else if (attribute.equals("mobility")) {
					toPrint = "" + map.getTile(loc).getMobility();
					if (toPrint.equals("10000")) {
						toPrint = "-";
					}
				} else if (attribute.equals("entityHealth")) {
					if (map.isEntity(loc)) {
						toPrint = "" + map.getEntity(loc).getHealth();
					} else {
						toPrint = ".";
					}
				}
				
				System.out.print(space(toPrint));
				
			}
			System.out.print("\n\n");
		}
	}
	
	public final static String space(String string) {
		if (string.length() >= 4) {
			string = string.substring(0, 3);
		}
		if (string.length() == 1) {
			string = string + "   ";
		} else if (string.length() == 2) {
			string = string + "  ";
		} else {
			string = string + " ";
		}
		return string;
	}
	
	public static void main(String[] args) {
		GameMap currentMap; 
		PlayerDEPRECIATED[] players = {new PlayerDEPRECIATED(0), new PlayerDEPRECIATED(1)};
		
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
						
						while (players[p].getMoves() > 0) {
							System.out.print(" > ");
							String command = input.nextLine();
							String[] coms = command.split(" ");
							
							//User interface will use send commands ig
							
							if (coms[0].equals("set")) {
								if (coms[1].equals("slcPos")) {
									//don't worry about proper input; user interface will use proper values.
									Pair loc = new Pair (Integer.parseInt(coms[2]), Integer.parseInt(coms[3]));
									if (currentMap.isEntity(loc) && currentMap.getEntity(loc).isTeam(players[p].getTeam())) {
										players[p].setSelectedPos(loc);
									} else {
										System.out.println("Can't select tile.");
									}
								} else if (coms[1].equals("opPos")) {
									players[p].setOperationPos(new Pair (Integer.parseInt(coms[2]), Integer.parseInt(coms[3])));
								}
							} else if (coms[0].equals("move")) {
								Pair pos = players[p].getSelectedPos();
								Pair target = players[p].getOperationPos();
								Path path = currentMap.getPath(pos, target);
								if (!currentMap.move(pos, target, players[p].getTeam()) || !players[p].useMove()) {
									System.out.println("Cannot complete move.");
								}
								if (coms.length >= 2 && coms[1].equals("disp")) {
									String toPrint;
									for (int j = 0; j < currentMap.height(); j++) {
										for (int i = 0; i < currentMap.length(); i++) {
											Pair loc = new Pair (i, j);
											if (pos.equals(loc)) {
												toPrint = "S";
											} else if (target.equals(loc)) {
												toPrint = "E";
											} else {
												toPrint = ".";
												for (Pair a : path) {
													if (loc.equals(a)) {
														toPrint = "o";
													}
												}
											}
											System.out.print(space(toPrint));
										}
										System.out.print("\n\n");
									}
								}
								currentMap.fogOfWar(p);
							} else if (coms[0].equals("attack")) {
								if (!currentMap.attack(players[p].getSelectedPos(), players[p].getOperationPos(), players[p].getTeam()) || !players[p].useMove()) {
									System.out.println("Cannot complete attack.");
								}
							} else if (coms[0].equals("strike")) {
								if (!currentMap.canStrike(players[p].getOperationPos(), players[p].getTeam()) || !players[p].useMove()) {
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
