package com.marklynch.level.constructs.bounds.structure.puzzleroom;

import java.util.ArrayList;
import java.util.HashMap;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.secondary.AnimationStraightLine;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Floor;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Switch;
import com.marklynch.objects.SwitchListener;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.Utils.Point;

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
	ArrayList<Square> midBridgeSquares = new ArrayList<Square>();
	ArrayList<Square> activeBridgeSquares;

	Square voidSquare = Level.squares[90][40];

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

		// bridge vertical points
		for (int i = 0; i < bridgeWidth; i++) {
			for (int j = 0; j < bridgeLength; j++) {
				verticalBridgeSquares.add(Level.squares[posX + gapsWidth + i][bridgePosY + j]);
			}
		}

		// bridge horizontal points
		for (int i = bridgeWidth - 1; i >= 0; i--) {
			for (int j = 0; j < bridgeLength; j++) {
				horizontalBridgeSquares.add(Level.squares[bridgePosX + j][posY + gapsWidth + i]);
			}
		}

		int midSquareX1 = bridgePosX + bridgeLength / 2 - 1;
		int midSquareX2 = bridgePosX + bridgeLength / 2;
		int midSquareY1 = bridgePosY + bridgeLength / 2 - 1;
		int midSquareY2 = bridgePosY + bridgeLength / 2;
		midBridgeSquares.add(Level.squares[midSquareX1][midSquareY1]);
		midBridgeSquares.add(Level.squares[midSquareX1][midSquareY1]);
		midBridgeSquares.add(Level.squares[midSquareX1][midSquareY1]);
		midBridgeSquares.add(Level.squares[midSquareX1][midSquareY1]);
		midBridgeSquares.add(Level.squares[midSquareX1][midSquareY1]);
		midBridgeSquares.add(Level.squares[midSquareX1][midSquareY1]);
		midBridgeSquares.add(Level.squares[midSquareX1][midSquareY1]);
		midBridgeSquares.add(Level.squares[midSquareX1][midSquareY1]);

		midBridgeSquares.add(Level.squares[midSquareX2][midSquareY2]);
		midBridgeSquares.add(Level.squares[midSquareX2][midSquareY2]);
		midBridgeSquares.add(Level.squares[midSquareX2][midSquareY2]);
		midBridgeSquares.add(Level.squares[midSquareX2][midSquareY2]);
		midBridgeSquares.add(Level.squares[midSquareX2][midSquareY2]);
		midBridgeSquares.add(Level.squares[midSquareX2][midSquareY2]);
		midBridgeSquares.add(Level.squares[midSquareX2][midSquareY2]);
		midBridgeSquares.add(Level.squares[midSquareX2][midSquareY2]);

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
			Floor floor = Templates.FLOOR.makeCopy(null, null);

			// double random = Math.random();
			//
			// if (random < 0.25) {
			// floor.imageTexture = Square.DARK_GRASS_TEXTURE;
			// } else if (random < 0.5) {
			// floor.imageTexture = Square.GRASS_TEXTURE;
			//
			// } else if (random < 0.75) {
			//
			// floor.imageTexture = Square.STONE_TEXTURE;
			// } else {
			//
			// floor.imageTexture = Square.MUD_TEXTURE;
			// }

			bridgeSquare.inventory.add(floor);
		}

		// Put void hole on all the squares
		for (int i = posX; i < posX + totalWidthInSquares; i++) {

			for (int j = posY; j < posY + totalHeightInSquares; j++) {
				if (Level.squares[i][j].inventory.containsObjectWithTemplateId(Templates.FLOOR.templateId)) {
				} else if (!Level.squares[i][j].inventory
						.containsObjectWithTemplateId(Templates.VOID_HOLE.templateId)) {
					Level.squares[i][j].inventory.add(Templates.VOID_HOLE.makeCopy(null, null, voidSquare));
				}
			}
		}

		setupBridgeConnections();
	}

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
		HashMap<GameObject, Square> midTeleportationsToPerform = new HashMap<GameObject, Square>();
		ArrayList<GameObject> teleportationObjectsInOrder = new ArrayList<GameObject>();

		if (bridgeVertical) {
			for (int i = 0; i < horizontalBridgeSquares.size(); i++) {
				Square oldSquare = horizontalBridgeSquares.get(i);
				for (GameObject gameObject : (ArrayList<GameObject>) oldSquare.inventory.gameObjects.clone()) {
					if (gameObject.templateId != Templates.VOID_HOLE.templateId) {
						teleportationObjectsInOrder.add(gameObject);
						midTeleportationsToPerform.put(gameObject, midBridgeSquares.get(i));
						teleportationsToPerform.put(gameObject, verticalBridgeSquares.get(i));
					}
				}
			}

			for (GameObject gameObject : teleportationObjectsInOrder) {
				move(gameObject, teleportationsToPerform.get(gameObject));

			}

			for (Square oldSquare : horizontalBridgeSquares) {
				if (!oldSquare.inventory.containsObjectWithTemplateId(Templates.VOID_HOLE.templateId)) {
					oldSquare.inventory.add(Templates.VOID_HOLE.makeCopy(null, null, voidSquare));
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
						midTeleportationsToPerform.put(gameObject, midBridgeSquares.get(i));
						teleportationsToPerform.put(gameObject, horizontalBridgeSquares.get(i));
					}
				}
			}

			for (GameObject gameObject : teleportationObjectsInOrder) {
				move(gameObject, teleportationsToPerform.get(gameObject));

			}

			for (Square oldSquare : verticalBridgeSquares) {
				if (!oldSquare.inventory.containsObjectWithTemplateId(Templates.VOID_HOLE.templateId)) {
					oldSquare.inventory.add(Templates.VOID_HOLE.makeCopy(null, null, voidSquare));
				}
			}

			for (Square newSquare : horizontalBridgeSquares) {
				newSquare.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			}
		}
	}

	float focalPointX = posX * Game.SQUARE_WIDTH + (totalWidthInSquares / 2) * Game.SQUARE_WIDTH;
	float focalPointY = posY * Game.SQUARE_HEIGHT + (totalHeightInSquares / 2) * Game.SQUARE_HEIGHT;
	Point focalPoint = new Point(focalPointX, focalPointY);

	public void move(final GameObject gameObject, final Square... targetSquares) {

		// Curve
		// (GameObject projectileObject, float speed, float focalPoint, float
		// targetAngle)
		// if ((gameObject.squareGameObjectIsOn.onScreen() &&
		// gameObject.squareGameObjectIsOn.visibleToPlayer)
		// || (targetSquares[0].onScreen() && targetSquares[0].visibleToPlayer)
		// || (targetSquares[targetSquares.length - 1].onScreen()
		// && targetSquares[targetSquares.length - 1].visibleToPlayer)) {
		//
		// float angle = 1.57f;
		// if (!bridgeVertical)
		// angle = -1.57f;
		//
		// Level.player.addSecondaryAnimation(new AnimationCurve(gameObject, 0.002f,
		// focalPoint, angle) {
		// @Override
		// public void runCompletionAlgorightm() {
		// super.runCompletionAlgorightm();
		// postRangedAnimation(gameObject, targetSquares);
		// }
		// });
		// } else {
		//
		// AnimationStraightLine.postRangedAnimation(gameObject, targetSquares);
		// }

		// Straight
		if ((gameObject.squareGameObjectIsOn.onScreen() && gameObject.squareGameObjectIsOn.visibleToPlayer)
				|| (targetSquares[0].onScreen() && targetSquares[0].visibleToPlayer)
				|| (targetSquares[targetSquares.length - 1].onScreen()
						&& targetSquares[targetSquares.length - 1].visibleToPlayer)) {
			Level.player.addSecondaryAnimation(new AnimationStraightLine(gameObject, 1f, targetSquares) {
				@Override
				public void runCompletionAlgorightm() {
					super.runCompletionAlgorightm();
					postRangedAnimation(gameObject, targetSquares);
					// postRangedAnimation(arrow);
				}
			});
		} else {

			AnimationStraightLine.postRangedAnimation(gameObject, targetSquares);
		}

	}

	public void setupBridgeConnections() {
		Square square;
		// bridge connector parts on the edges
		for (int i = 0; i < bridgeWidth; i++) {
			// left

			square = Level.squares[posX][posY + gapsWidth + i];
			square.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			square.imageTexture = Square.GRASS_TEXTURE;
			// if
			// (square.inventory.containsObjectWithTemplateId(Templates.VOID_HOLE.templateId))
			// {
			// square.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			// }

			// Level.squares[posX][posY + gapsWidth + i].passable = true;
			// right
			square = Level.squares[posX + totalWidthInSquares - 1][posY + gapsWidth + i];
			square.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			square.imageTexture = Square.GRASS_TEXTURE;
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
			square.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			square.imageTexture = Square.GRASS_TEXTURE;
			// square.imageTexture = Square.STONE_TEXTURE;
			// if
			// (square.inventory.containsObjectWithTemplateId(Templates.VOID_HOLE.templateId))
			// {
			// square.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			// }
			// Level.squares[posX + gapsWidth + i][posY].passable = true;
			// bottom
			square = Level.squares[posX + gapsWidth + i][posY + totalHeightInSquares - 1];
			square.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			square.imageTexture = Square.GRASS_TEXTURE;
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
