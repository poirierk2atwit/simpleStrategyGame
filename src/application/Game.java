package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import mapObjects.Entity;
import mapObjects.Tile;
import utility.Pair;

public class Game extends Application {
	static final int NUM_MOVES = 2;
	static ImageHandler selectImage = new ImageHandler("secondSelect");
	static int numPlayers = 2;
	static int player = 0;
	static int moves = NUM_MOVES;
	static Entity selectedEntity = null;
	static Pair selectedTile = null;
	static GameMap currentMap;
	static StackPane mainStack;
	static Scene mapScene;
	static StackPane blankStack = new StackPane();
	static Scene blankScene = new Scene(blankStack);
	static Pane otherElements = new Pane();
	static Pane attackMove = new Pane();
	static Button next = new Button();
	
	public static void setSelectedEntity(Entity entity) {
		selectedEntity = entity;
	}
	
	public static Pair locFromPixel(Pair pixelLoc) {
		return new Pair((int) Math.floor((double) pixelLoc.x() / (double) ImageHandler.SIZE), (int) Math.floor((double) pixelLoc.y() / (double) ImageHandler.SIZE));
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Scanner input = new Scanner(System.in);
		
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
		
		ImageHandler attack = new ImageHandler("attackIcon", 7*ImageHandler.SCALE, 7*ImageHandler.SCALE);
		attack.setLayoutX(1*ImageHandler.SCALE);
		attack.setLayoutY(4*ImageHandler.SCALE+(ImageHandler.SCALE/2));
		ImageHandler move = new ImageHandler("moveIcon", 7*ImageHandler.SCALE, 7*ImageHandler.SCALE);
		move.setLayoutX(8*ImageHandler.SCALE);
		move.setLayoutY(4*ImageHandler.SCALE+(ImageHandler.SCALE/2));
		
		attackMove.getChildren().add(attack);
		attackMove.getChildren().add(move);
		
		blankStack.getChildren().add(new ImageHandler("nextPlayer", 600, 400));
		
		next.setText("Continue");
		next.setLayoutX((((double) currentMap.length()) / 2.0) * ImageHandler.SCALE);
		next.setLayoutY(4*ImageHandler.SCALE);
		next.setOnMouseClicked(e -> {
			turnBridge1(stage);
		});
		
		currentMap.fogOfWar(0);
		mainStack = new StackPane();
		mainStack.getChildren().add(currentMap.getMapStack());
		mainStack.getChildren().add(otherElements);
		mapScene = new Scene(mainStack);
		mapScene.setOnMouseClicked(e -> {
			Pair loc = locFromPixel(new Pair((int) e.getSceneX(), (int) e.getSceneY()));
			if (selectedEntity == null) {
				if (currentMap.isEntity(loc) && currentMap.getEntity(loc).isTeam(player)) {
					currentMap.getEntity(loc).select();
				}
			} else {
				if (currentMap.isEntity(loc) && currentMap.getEntity(loc) == selectedEntity) {
					selectedEntity.deselect();
					if (selectedTile != null) {
						selectedTile = null;
						otherElements.getChildren().remove(selectImage);
						otherElements.getChildren().remove(attackMove);
					}
				} else if (selectedTile == null) {
					selectedTile = loc;
					selectImage.setLayoutX(loc.x() * ImageHandler.SIZE);
					selectImage.setLayoutY(loc.y() * ImageHandler.SIZE);
					
					attackMove.setLayoutX(loc.x() * ImageHandler.SIZE);
					attackMove.setLayoutY(loc.y() * ImageHandler.SIZE);
					otherElements.getChildren().add(selectImage);
					otherElements.getChildren().add(attackMove);
				} else if (!selectedTile.equals(loc)) {
					selectedTile = null;
					otherElements.getChildren().remove(selectImage);
					otherElements.getChildren().remove(attackMove);
				} else {
					Pair locFine = new Pair((int) e.getSceneX() % ImageHandler.SIZE, (int) e.getSceneY() % ImageHandler.SIZE);
					int attackX = 1*ImageHandler.SCALE;
					int attackY = 4*ImageHandler.SCALE+(ImageHandler.SCALE/2);
					Pair attackPos1 = new Pair(attackX, attackY);
					Pair attackPos2 = new Pair(attackX + 7*ImageHandler.SCALE, attackY + 7*ImageHandler.SCALE);
					int moveX = 8*ImageHandler.SCALE;
					int moveY = 4*ImageHandler.SCALE+(ImageHandler.SCALE/2);
					Pair movePos1 = new Pair(moveX, moveY);
					Pair movePos2 = new Pair(moveX + 7*ImageHandler.SCALE, moveY + 7*ImageHandler.SCALE);
					
					if (locFine.isBetween(attackPos1, attackPos2)) {
						if (moves > 0 && currentMap.attack(selectedEntity, loc, player)) {
							useMove(input, stage);
						} else {
							selectedTile = null;
							otherElements.getChildren().remove(selectImage);
							otherElements.getChildren().remove(attackMove);
							System.out.println("Cannot attack " + loc.toString());
						}
					} else if (locFine.isBetween(movePos1, movePos2)) {
						if (moves > 0 && currentMap.move(selectedEntity, loc, player)) {
							useMove(input, stage);
						} else {
							selectedTile = null;
							otherElements.getChildren().remove(selectImage);
							otherElements.getChildren().remove(attackMove);
							System.out.println("Cannot move to " + loc.toString());
						}
					}
				}
			}
		});
		
		stage.setScene(mapScene);
		stage.show();
	}
	
	private void useMove(Scanner input, Stage stage) {
		selectedTile = null;
		otherElements.getChildren().remove(selectImage);
		otherElements.getChildren().remove(attackMove);
		selectedEntity.deselect();
		currentMap.fogOfWar(player);
		currentMap.updateMapStack();
		if ((moves -= 1) <= 0) {
			otherElements.getChildren().add(next);
		}
	}
	
	private void turnBridge1(Stage stage) {
		next.setOnMouseClicked(e -> {
			turnBridge2(stage);
		});
		otherElements.getChildren().remove(next);
		blankStack.getChildren().add(next);
		stage.setScene(blankScene);
		if (++player >= numPlayers) {
			player = 0;
		}
	}
	
	private void turnBridge2(Stage stage) {
		next.setOnMouseClicked(e -> {
			turnBridge1(stage);
		});
		blankStack.getChildren().remove(next);
		moves = NUM_MOVES;
		currentMap.fogOfWar(player);
		currentMap.updateMapStack();
		stage.setScene(mapScene);
	}

}
