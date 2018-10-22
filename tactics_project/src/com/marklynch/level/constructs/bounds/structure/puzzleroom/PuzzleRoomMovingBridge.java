package com.marklynch.level.constructs.bounds.structure.puzzleroom;

import java.util.ArrayList;
import java.util.HashMap;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.primary.AnimationStraightLine;
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
	int posX;
	int posY;
	final static int totalWidthInSquares = 20;
	final static int totalHeightInSquares = 20;
	int bridgeWidth;
	int bridgeLength;
	int bridgePosX;
	int bridgePosY;
	int gapsWidth;
	int bridgeConnectorsWidth;

	ArrayList<Square> verticalBridgeSquares = new ArrayList<Square>();
	ArrayList<Square> horizontalBridgeSquares = new ArrayList<Square>();
	// ArrayList<Square> midBridgeSquares = new ArrayList<Square>();
	ArrayList<Square> activeBridgeSquares;

	Square voidSquare;

	public PuzzleRoomMovingBridge(int posX, int posY) {
		super("Bridge Room", posX, posY, false, false, new ArrayList<Actor>(), 1, false, new Node[] {}, new RoomPart[] {
				new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;
		this.bridgeWidth = 2;
		this.bridgeLength = totalWidthInSquares - 2;
		this.bridgePosX = posX + 1;
		this.bridgePosY = posY + 1;
		this.gapsWidth = (totalWidthInSquares - bridgeWidth) / 2;
		this.bridgeConnectorsWidth = (totalWidthInSquares - bridgeWidth) / 2;
		voidSquare = Level.squares[90][40];

		// Templates.ANTLERS_SWITCH.makeCopy(Game.level.squares[posX][posY - 1], null,
		// this, Switch.SWITCH_TYPE.OPEN_CLOSE,
		// new RequirementToMeet[] { new
		// StatRequirementToMeet(HIGH_LEVEL_STATS.STRENGTH, 1) });
		Templates.PRESSURE_PLATE.makeCopy(Game.level.squares[posX - 1][posY + totalHeightInSquares / 2 - 1], null,
				Switch.SWITCH_TYPE.OPEN_CLOSE, 5, this);

		Templates.CRATE.makeCopy("Crate",
				Level.squares[posX + totalWidthInSquares - 1][posY + totalHeightInSquares / 2], false, null);

		for (int i = posX; i < posX + totalWidthInSquares; i++) {
			for (int j = posY; j < posY + totalHeightInSquares; j++) {
				Game.level.squares[i][j].imageTexture = Square.VOID_SQUARE;
			}
		}

		ArrayList<Square> middleSquares = new ArrayList<Square>();
		middleSquares.add(Level.squares[posX + totalWidthInSquares / 2 - 1][posY + totalHeightInSquares / 2 - 1]);
		middleSquares.add(Level.squares[posX + totalWidthInSquares / 2 - 1][posY + totalHeightInSquares / 2]);
		middleSquares.add(Level.squares[posX + totalWidthInSquares / 2][posY + totalHeightInSquares / 2 - 1]);
		middleSquares.add(Level.squares[posX + totalWidthInSquares / 2][posY + totalHeightInSquares / 2]);
		for (Square middleSquare : middleSquares) {
			Floor floor = Templates.STONE_FLOOR.makeCopy(null, null);
			middleSquare.inventory.add(floor);
		}

		// bridge vertical points
		for (int i = 0; i < bridgeWidth; i++) {
			for (int j = 0; j < bridgeLength; j++) {
				Square square = Level.squares[posX + gapsWidth + i][bridgePosY + j];
				if (!middleSquares.contains(square)) {
					verticalBridgeSquares.add(square);
				}
			}
		}

		// bridge horizontal points
		for (int i = bridgeWidth - 1; i >= 0; i--) {
			for (int j = 0; j < bridgeLength; j++) {
				Square square = Level.squares[bridgePosX + j][posY + gapsWidth + i];
				if (!middleSquares.contains(square)) {
					horizontalBridgeSquares.add(square);
				}
			}
		}

		// int midSquareX1 = bridgePosX + bridgeLength / 2 - 1;
		// int midSquareX2 = bridgePosX + bridgeLength / 2;
		// int midSquareY1 = bridgePosY + bridgeLength / 2 - 1;
		// int midSquareY2 = bridgePosY + bridgeLength / 2;
		// midBridgeSquares.add(Level.squares[midSquareX1][midSquareY1]);
		// midBridgeSquares.add(Level.squares[midSquareX1][midSquareY1]);
		// midBridgeSquares.add(Level.squares[midSquareX1][midSquareY1]);
		// midBridgeSquares.add(Level.squares[midSquareX1][midSquareY1]);
		// midBridgeSquares.add(Level.squares[midSquareX1][midSquareY1]);
		// midBridgeSquares.add(Level.squares[midSquareX1][midSquareY1]);
		// midBridgeSquares.add(Level.squares[midSquareX1][midSquareY1]);
		// midBridgeSquares.add(Level.squares[midSquareX1][midSquareY1]);
		//
		// midBridgeSquares.add(Level.squares[midSquareX2][midSquareY2]);
		// midBridgeSquares.add(Level.squares[midSquareX2][midSquareY2]);
		// midBridgeSquares.add(Level.squares[midSquareX2][midSquareY2]);
		// midBridgeSquares.add(Level.squares[midSquareX2][midSquareY2]);
		// midBridgeSquares.add(Level.squares[midSquareX2][midSquareY2]);
		// midBridgeSquares.add(Level.squares[midSquareX2][midSquareY2]);
		// midBridgeSquares.add(Level.squares[midSquareX2][midSquareY2]);
		// midBridgeSquares.add(Level.squares[midSquareX2][midSquareY2]);

		if (bridgeVertical)
			activeBridgeSquares = verticalBridgeSquares;
		else
			activeBridgeSquares = horizontalBridgeSquares;

		for (Square bridgeSquare : activeBridgeSquares) {
			Floor floor = Templates.STONE_FLOOR.makeCopy(null, null);

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
				if (Level.squares[i][j].inventory.containsObjectWithTemplateId(Templates.STONE_FLOOR.templateId)) {
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
		// HashMap<GameObject, Square> midTeleportationsToPerform = new
		// HashMap<GameObject, Square>();
		ArrayList<GameObject> teleportationObjectsInOrder = new ArrayList<GameObject>();

		if (bridgeVertical) {

			for (Square newSquare : verticalBridgeSquares) {
				newSquare.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			}

			for (int i = 0; i < horizontalBridgeSquares.size(); i++) {
				Square oldSquare = horizontalBridgeSquares.get(i);
				for (GameObject gameObject : (ArrayList<GameObject>) oldSquare.inventory.gameObjects.clone()) {
					if (gameObject.templateId != Templates.VOID_HOLE.templateId) {
						teleportationObjectsInOrder.add(gameObject);
						// midTeleportationsToPerform.put(gameObject, midBridgeSquares.get(i));
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
		} else {

			for (Square newSquare : horizontalBridgeSquares) {
				newSquare.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			}

			for (int i = 0; i < verticalBridgeSquares.size(); i++) {
				Square oldSquare = verticalBridgeSquares.get(i);
				for (GameObject gameObject : (ArrayList<GameObject>) oldSquare.inventory.gameObjects.clone()) {
					if (gameObject.templateId != Templates.VOID_HOLE.templateId) {
						teleportationObjectsInOrder.add(gameObject);
						// midTeleportationsToPerform.put(gameObject, midBridgeSquares.get(i));
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
		}
	}

	float focalPointX = posX * Game.SQUARE_WIDTH + (totalWidthInSquares / 2) * Game.SQUARE_WIDTH;
	float focalPointY = posY * Game.SQUARE_HEIGHT + (totalHeightInSquares / 2) * Game.SQUARE_HEIGHT;
	Point focalPoint = new Point(focalPointX, focalPointY);

	public void move(final GameObject gameObject, final Square... targetSquares1) {

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
		// Square startSquare = gameObject.squareGameObjectIsOn;
		// targetSquares[targetSquares.length - 1].inventory.add(gameObject);
		// if (Level.player.inventory.groundDisplay != null)
		// Level.player.inventory.groundDisplay.refreshGameObjects();
		gameObject.setPrimaryAnimation(new AnimationStraightLine(gameObject, 1f, true, 0f, targetSquares1) {
			@Override
			public void runCompletionAlgorightm(boolean wait) {
				postRangedAnimation(gameObject, targetSquares1);
				super.runCompletionAlgorightm(wait);
			}
		});

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
