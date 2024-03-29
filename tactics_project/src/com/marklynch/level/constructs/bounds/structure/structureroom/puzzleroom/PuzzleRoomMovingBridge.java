package com.marklynch.level.constructs.bounds.structure.structureroom.puzzleroom;

import java.util.concurrent.ConcurrentHashMap;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.primary.AnimationStraightLine;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Switch;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.utils.CopyOnWriteArrayList;

public class PuzzleRoomMovingBridge extends StructureRoom implements SwitchListener {

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

	CopyOnWriteArrayList<Square> verticalBridgeSquares = new CopyOnWriteArrayList<Square>(Square.class);
	CopyOnWriteArrayList<Square> horizontalBridgeSquares = new CopyOnWriteArrayList<Square>(Square.class);
	// CopyOnWriteArrayList<Square> midBridgeSquares = new CopyOnWriteArrayList<Square>();
	CopyOnWriteArrayList<Square> activeBridgeSquares;

	Square voidSquare;

	public PuzzleRoomMovingBridge(int posX, int posY) {
		super("Bridge Room", posX, posY, false, false, new CopyOnWriteArrayList<Actor>(Actor.class), 1, false, new RoomPart[] {
				new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;
		this.bridgeWidth = 2;
		this.bridgeLength = totalWidthInSquares - 2;
		this.bridgePosX = posX + 1;
		this.bridgePosY = posY + 1;
		this.gapsWidth = (totalWidthInSquares - bridgeWidth) / 2;
		this.bridgeConnectorsWidth = (totalWidthInSquares - bridgeWidth) / 2;
		voidSquare = Level.squares[posX][posY];

		// Templates.ANTLERS_SWITCH.makeCopy(Game.level.squares[posX][posY - 1], null,
		// this, Switch.SWITCH_TYPE.OPEN_CLOSE,
		// new RequirementToMeet[] { new
		// StatRequirementToMeet(HIGH_LEVEL_STATS.STRENGTH, 1) });
		Templates.PRESSURE_PLATE.makeCopy(Game.level.squares[posX - 1][posY + totalHeightInSquares / 2 - 1], null,
				Switch.SWITCH_TYPE.OPEN_CLOSE, 5, this);

		Templates.CRATE.makeCopy(Level.squares[posX + totalWidthInSquares - 1][posY + totalHeightInSquares / 2], false,
				null);

		for (int i = posX; i < posX + totalWidthInSquares; i++) {
			for (int j = posY; j < posY + totalHeightInSquares; j++) {
				Game.level.squares[i][j].setFloorImageTexture(Square.VOID_SQUARE);
			}
		}

		CopyOnWriteArrayList<Square> middleSquares = new CopyOnWriteArrayList<Square>(Square.class);
		middleSquares.add(Level.squares[posX + totalWidthInSquares / 2 - 1][posY + totalHeightInSquares / 2 - 1]);
		middleSquares.add(Level.squares[posX + totalWidthInSquares / 2 - 1][posY + totalHeightInSquares / 2]);
		middleSquares.add(Level.squares[posX + totalWidthInSquares / 2][posY + totalHeightInSquares / 2 - 1]);
		middleSquares.add(Level.squares[posX + totalWidthInSquares / 2][posY + totalHeightInSquares / 2]);
		for (Square middleSquare : middleSquares) {
			GameObject floor = Templates.STONE_FLOOR.makeCopy(null, null);
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
			GameObject floor = Templates.STONE_FLOOR.makeCopy(null, null);

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
				if (Level.squares[i][j].inventory.containsGameObjectWithTemplateId(Templates.STONE_FLOOR.templateId)) {
				} else if (!Level.squares[i][j].inventory
						.containsGameObjectWithTemplateId(Templates.VOID_HOLE.templateId)) {
					Level.squares[i][j].inventory.add(Templates.VOID_HOLE.makeCopy(null, null, voidSquare));
				}
			}
		}

		setupBridgeConnections();
	}

	@Override
	public Long getId() {
		return id;
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

		ConcurrentHashMap<GameObject, Square> movesToPerform = new ConcurrentHashMap<GameObject, Square>();
		// ConcurrentHashMap<GameObject, Square> midTeleportationsToPerform = new
		// ConcurrentHashMap<GameObject, Square>();
		CopyOnWriteArrayList<GameObject> objectsToMoveInOrder = new CopyOnWriteArrayList<GameObject>(GameObject.class);

		if (bridgeVertical) {

			for (Square newSquare : verticalBridgeSquares) {
				newSquare.inventory.removeGameObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			}

			for (int i = 0; i < horizontalBridgeSquares.size(); i++) {
				Square oldSquare = horizontalBridgeSquares.get(i);
				for (GameObject gameObject : (CopyOnWriteArrayList<GameObject>) oldSquare.inventory.gameObjects) {
					if (gameObject.templateId != Templates.VOID_HOLE.templateId) {
						objectsToMoveInOrder.add(gameObject);
						movesToPerform.put(gameObject, verticalBridgeSquares.get(i));
					}
				}
			}

			for (GameObject gameObject : objectsToMoveInOrder) {
				move(gameObject, movesToPerform.get(gameObject));
			}

			for (Square oldSquare : horizontalBridgeSquares) {
				if (!oldSquare.inventory.containsGameObjectWithTemplateId(Templates.VOID_HOLE.templateId)) {
					oldSquare.inventory.add(Templates.VOID_HOLE.makeCopy(null, null, voidSquare));
				}
			}
		} else { /////////////////////////////////////////////////////////////////////////////////////////////

			for (Square newSquare : horizontalBridgeSquares) {
				newSquare.inventory.removeGameObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			}

			for (int i = 0; i < verticalBridgeSquares.size(); i++) {
				Square oldSquare = verticalBridgeSquares.get(i);
				for (GameObject gameObject : (CopyOnWriteArrayList<GameObject>) oldSquare.inventory.gameObjects) {
					if (gameObject.templateId != Templates.VOID_HOLE.templateId) {
						objectsToMoveInOrder.add(gameObject);
						movesToPerform.put(gameObject, horizontalBridgeSquares.get(i));
					}
				}
			}

			for (GameObject gameObject : objectsToMoveInOrder) {
				move(gameObject, movesToPerform.get(gameObject));
			}

			for (Square oldSquare : verticalBridgeSquares) {
				if (!oldSquare.inventory.containsGameObjectWithTemplateId(Templates.VOID_HOLE.templateId)) {
					oldSquare.inventory.add(Templates.VOID_HOLE.makeCopy(null, null, voidSquare));
				}
			}
		}
	}

	public void move(final GameObject gameObject, final Square... targetSquares1) {
		gameObject.setPrimaryAnimation(new AnimationStraightLine(gameObject, 2000f, true, 0f, null, targetSquares1));

	}

	public void setupBridgeConnections() {
		Square square;
		// bridge connector parts on the edges
		for (int i = 0; i < bridgeWidth; i++) {
			// left

			square = Level.squares[posX][posY + gapsWidth + i];
			square.inventory.removeGameObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			square.setFloorImageTexture(Square.GRASS_TEXTURE);
			// if
			// (square.inventory.containsObjectWithTemplateId(Templates.VOID_HOLE.templateId))
			// {
			// square.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			// }

			// Level.squares[posX][posY + gapsWidth + i].passable = true;
			// right
			square = Level.squares[posX + totalWidthInSquares - 1][posY + gapsWidth + i];
			square.inventory.removeGameObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			square.setFloorImageTexture(Square.GRASS_TEXTURE);
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
			square.inventory.removeGameObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			square.setFloorImageTexture(Square.GRASS_TEXTURE);
			// square.imageTexture = Square.STONE_TEXTURE;
			// if
			// (square.inventory.containsObjectWithTemplateId(Templates.VOID_HOLE.templateId))
			// {
			// square.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			// }
			// Level.squares[posX + gapsWidth + i][posY].passable = true;
			// bottom
			square = Level.squares[posX + gapsWidth + i][posY + totalHeightInSquares - 1];
			square.inventory.removeGameObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			square.setFloorImageTexture(Square.GRASS_TEXTURE);
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
