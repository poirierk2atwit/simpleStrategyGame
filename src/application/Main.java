package application;

import java.util.Scanner;

public class Main {

	//Implement text based map viewing functions for debug
	
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
				//initialize currentMap from file of proper map
				Tile[][] blank = new Tile[2][2]; //temporary
				currentMap = new GameMap(blank); //temporary
				//initialize players
				
				while (true) {
					for (int i = 0; i < players.length; i++) {
						players[i].newTurn();
						System.out.println("Player " + i);
						
						System.out.print("Input a command. Help for options. ");
						String command = input.nextLine();
						String[] coms = command.split(" ");
						
						//User interface will use send commands ig
						
						if (coms[0].equals("set")) {
							if (coms[1].equals("selectedPos")) {
								//don't worry about proper input; user interface will use proper values.
								int x = Integer.parseInt(coms[2]);
								int y = Integer.parseInt(coms[3]);
								if (currentMap.isEntitiy(x, y) && currentMap.getEntity(x, y).isTeam(players[i].getTeam())) {
									players[i].setSelectedPos(x, y);
								} else {
									System.out.println("Can't select tile.");
								}
							} else if (coms[1].equals("operationPos")) {
								players[i].setOperationPos(Integer.parseInt(coms[2]), Integer.parseInt(coms[3]));
							}
						} else if (coms[0].equals("move")) {
							if (!currentMap.move(players[i].getSelectedPos(), players[i].getOperationPos(), players[i].getTeam()) || !players[i].useMove()) {
								System.out.println("Cannot complete move.");
							}
						} else if (coms[0].equals("attack")) {
							if (!currentMap.attack(players[i].getSelectedPos(), players[i].getOperationPos(), players[i].getTeam()) || !players[i].useMove()) {
								System.out.println("Cannot complete attack.");
							}
						} else if (coms[0].equals("strike")) {
							
						} else if (coms[0].equals("display")) {
							
						} else if (coms[0].equals("end")){
							continue;
						} else {
							System.out.println("Unknown Command.");
						}
						
					}
				}
			}
		}
		
		//Run game
		//	Open map object
		//	Set entity locations
		// 	create 2 player objects in an array
		// 	For loop for all players wrapped by a while loop
		// 	Implement move, attack, strike, select, and secondary select functions
		//	Implement mediary state for switching players

	}

}
