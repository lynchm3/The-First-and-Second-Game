package com.marklynch.level.constructs.bounds.structure.puzzleroom;

import java.util.ArrayList;
import java.util.HashMap;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.secondary.AnimationStraightLine;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Switch;
import com.marklynch.objects.SwitchListener;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;

public class PuzzleRoomMovingBridge extends StructureRoom implements SwitchListener {

	// oooxxooo
	// oooxxooo
	// oooxxooo
	// oooxxooo

	// to

	// oooooooo
	// xxxxxxxx
	// xxxxxxxx
	// oooooooo

	// When switch is hit

	// o = chasm / drop /death/ impassable
	// x = bridge

	boolean bridgeVertical = false;
	static int posX = 100;
	static int posY = 50;
	static int totalWidthInSquares = 10;
	static int totalHeightInSquares = totalWidthInSquares;
	int bridgeWidth = 2;
	int bridgeLength = totalWidthInSquares - 2;
	int bridgePosX = posX + 1;
	int bridgePosY = posY + 1;
	int gapsWidth = (totalWidthInSquares - bridgeWidth) / 2;
	int bridgeConnectorsWidth = (totalWidthInSquares - bridgeWidth) / 2;

	ArrayList<Square> verticalBridgeSquares = new ArrayList<Square>();
	ArrayList<Square> horizontalBridgeSquares = new ArrayList<Square>();
	ArrayList<Square> activeBridgeSquares;

	public PuzzleRoomMovingBridge() {
		super("Bridge Room", posX, posY, false, new ArrayList<Actor>(), 1, false, new Node[] {},
				new RoomPart[] { new RoomPart(posX, posY, posX + totalWidthInSquares, posY + totalHeightInSquares) });

		// Templates.ANTLERS_SWITCH.makeCopy(Game.level.squares[posX][posY - 1], null,
		// this, Switch.SWITCH_TYPE.OPEN_CLOSE,
		// new RequirementToMeet[] { new
		// StatRequirementToMeet(HIGH_LEVEL_STATS.STRENGTH, 1) });
		Templates.PRESSURE_PLATE.makeCopy(Game.level.squares[posX][posY - 1], null, this, Switch.SWITCH_TYPE.OPEN_CLOSE,
				5);

		for (int i = posX; i < posX + totalWidthInSquares; i++) {
			for (int j = posY; j < posY + totalHeightInSquares; j++) {
				Game.level.squares[i][j].imageTexture = Square.VOID_SQUARE;
			}
		}

		setupBridgeConnections();

		// bridge vertical points
		for (int i = 0; i < bridgeWidth; i++) {
			for (int j = 0; j < bridgeLength; j++) {
				verticalBridgeSquares.add(Level.squares[posX + gapsWidth + i][bridgePosY + j]);
			}
		}

		// bridge horizontal points
		for (int i = 0; i < bridgeWidth; i++) {
			for (int j = 0; j < bridgeLength; j++) {
				horizontalBridgeSquares.add(Level.squares[bridgePosX + j][posY + gapsWidth + i]);
			}
		}

		for (Square square : verticalBridgeSquares) {
			System.out.println("verticalBridgeSquare " + square);
		}
		for (Square square : horizontalBridgeSquares) {
			System.out.println("horizontalBridgeSquare " + square);
		}

		if (bridgeVertical)
			activeBridgeSquares = verticalBridgeSquares;
		else
			activeBridgeSquares = horizontalBridgeSquares;

		for (Square bridgeSquare : activeBridgeSquares) {
			bridgeSquare.inventory.add(Templates.FLOOR.makeCopy(null, null));
		}

		// Put void hole on all the squares
		for (int i = posX; i < posX + totalWidthInSquares; i++) {

			for (int j = posY; j < posY + totalHeightInSquares; j++) {
				if (Level.squares[i][j].inventory.containsObjectWithTemplateId(Templates.FLOOR.templateId)) {
				} else if (!Level.squares[i][j].inventory
						.containsObjectWithTemplateId(Templates.VOID_HOLE.templateId)) {
					Level.squares[i][j].inventory.add(Templates.VOID_HOLE.makeCopy(null, null));
				}
			}
		}

		// setup();
	}

	// public void fillInVoidSquares() {
	//
	// for (int i = posX; i < posX + totalWidthInSquares; i++) {
	//
	// for (int j = posY; j < posY + totalHeightInSquares; j++) {
	// Level.squares[i][j].imageTexture = null;
	// if (activeBridgeSquares.contains(Level.squares[i][j])) {
	// } else if (!Level.squares[i][j].inventory
	// .containsObjectWithTemplateId(Templates.VOID_HOLE.templateId)) {
	// Level.squares[i][j].inventory.add(Templates.VOID_HOLE.makeCopy(null, null));
	// }
	// }
	// }
	// }

	@Override
	public void zwitch(Switch zwitch) {
		bridgeVertical = !bridgeVertical;
		if (bridgeVertical)
			activeBridgeSquares = verticalBridgeSquares;
		else
			activeBridgeSquares = horizontalBridgeSquares;
		moveBridge();
	}

	public void moveBridge() {

		HashMap<GameObject, Square> teleportationsToPerform = new HashMap<GameObject, Square>();
		ArrayList<GameObject> teleportationObjectsInOrder = new ArrayList<GameObject>();

		if (bridgeVertical) {
			for (int i = 0; i < horizontalBridgeSquares.size(); i++) {
				Square oldSquare = horizontalBridgeSquares.get(i);
				for (GameObject gameObject : (ArrayList<GameObject>) oldSquare.inventory.gameObjects.clone()) {
					if (gameObject.templateId != Templates.VOID_HOLE.templateId) {
						teleportationObjectsInOrder.add(gameObject);
						teleportationsToPerform.put(gameObject, verticalBridgeSquares.get(i));
					}
				}
			}

			for (GameObject gameObject : teleportationObjectsInOrder) {
				move(gameObject, teleportationsToPerform.get(gameObject));

			}

			for (Square oldSquare : horizontalBridgeSquares) {
				if (!oldSquare.inventory.containsObjectWithTemplateId(Templates.VOID_HOLE.templateId)) {
					oldSquare.inventory.add(Templates.VOID_HOLE.makeCopy(null, null));
				}
			}

			for (Square newSquare : verticalBridgeSquares) {
				newSquare.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			}
		} else {
			for (int i = 0; i < verticalBridgeSquares.size(); i++) {
				Square oldSquare = verticalBridgeSquares.get(i);
				horizontalBridgeSquares.get(i).inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
				for (GameObject gameObject : (ArrayList<GameObject>) oldSquare.inventory.gameObjects.clone()) {
					if (gameObject.templateId != Templates.VOID_HOLE.templateId) {
						teleportationObjectsInOrder.add(gameObject);
						teleportationsToPerform.put(gameObject, horizontalBridgeSquares.get(i));
					}
				}
			}

			for (GameObject gameObject : teleportationObjectsInOrder) {
				move(gameObject, teleportationsToPerform.get(gameObject));

			}

			for (Square oldSquare : verticalBridgeSquares) {
				if (!oldSquare.inventory.containsObjectWithTemplateId(Templates.VOID_HOLE.templateId)) {
					oldSquare.inventory.add(Templates.VOID_HOLE.makeCopy(null, null));
				}
			}

			for (Square newSquare : horizontalBridgeSquares) {
				newSquare.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			}
		}
	}

	public void move(final GameObject gameObject, Square targetSquare) {

		if ((gameObject.squareGameObjectIsOn.onScreen() && gameObject.squareGameObjectIsOn.visibleToPlayer)
				|| (targetSquare.onScreen() && targetSquare.visibleToPlayer)) {
			Level.player.addSecondaryAnimation(new AnimationStraightLine(gameObject, targetSquare, 1f) {
				@Override
				public void runCompletionAlgorightm() {
					super.runCompletionAlgorightm();
					postRangedAnimation(gameObject, targetSquare);
					// postRangedAnimation(arrow);
				}
			});
		} else {

			AnimationStraightLine.postRangedAnimation(gameObject, targetSquare);
		}

		// Square startSquare = gameObject.squareGameObjectIsOn;
		//
		// gameObject.squareGameObjectIsOn.inventory.remove(gameObject);
		// gameObject.squareGameObjectIsOn = targetSquare;
		// targetSquare.inventory.add(gameObject);
		//
		// gameObject.setPrimaryAnimation(new AnimationTeleport(gameObject, startSquare,
		// targetSquare));

	}

	public void setupBridgeConnections() {
		Square square;
		// bridge connector parts on the edges
		for (int i = 0; i < bridgeWidth; i++) {
			// left

			square = Level.squares[posX][posY + gapsWidth + i];
			square.inventory.add(Templates.FLOOR.makeCopy(null, null));
			// square.imageTexture = Square.STONE_TEXTURE;
			// if
			// (square.inventory.containsObjectWithTemplateId(Templates.VOID_HOLE.templateId))
			// {
			// square.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			// }

			// Level.squares[posX][posY + gapsWidth + i].passable = true;
			// right
			square = Level.squares[posX + totalWidthInSquares - 1][posY + gapsWidth + i];
			square.inventory.add(Templates.FLOOR.makeCopy(null, null));
			// square.imageTexture = Square.STONE_TEXTURE;
			// if
			// (square.inventory.containsObjectWithTemplateId(Templates.VOID_HOLE.templateId))
			// {
			// square.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			// }
			// Level.squares[posX + totalWidthInSquares - 1][posY + gapsWidth + i].passable
			// = true;
			// top
			square = Level.squares[posX + gapsWidth + i][posY];
			square.inventory.add(Templates.FLOOR.makeCopy(null, null));
			// square.imageTexture = Square.STONE_TEXTURE;
			// if
			// (square.inventory.containsObjectWithTemplateId(Templates.VOID_HOLE.templateId))
			// {
			// square.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			// }
			// Level.squares[posX + gapsWidth + i][posY].passable = true;
			// bottom
			square = Level.squares[posX + gapsWidth + i][posY + totalHeightInSquares - 1];
			square.inventory.add(Templates.FLOOR.makeCopy(null, null));
			// square.imageTexture = Square.STONE_TEXTURE;
			// if
			// (square.inventory.containsObjectWithTemplateId(Templates.VOID_HOLE.templateId))
			// {
			// square.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			// }
			// Level.squares[posX + gapsWidth + i][posY + totalHeightInSquares - 1].passable
			// = true;
		}
	}

}
