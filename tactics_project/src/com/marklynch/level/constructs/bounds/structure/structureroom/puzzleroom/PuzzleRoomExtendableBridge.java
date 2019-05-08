package com.marklynch.level.constructs.bounds.structure.structureroom.puzzleroom;

import java.util.HashMap;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.primary.AnimationStraightLine;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Switch;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Utils.Point;

public class PuzzleRoomExtendableBridge extends StructureRoom implements SwitchListener {

	boolean bridgeExtended = false;
	int posX;
	int posY;
	final static int totalWidthInSquares = 20;
	final static int totalHeightInSquares = 2;
	int bridgeWidth = 2;
	int bridgeLength = 12;
	int bridgePosX = 4;
	int bridgePosY = 0;

	ArrayList<Square> extendedBridgeSquares = new ArrayList<Square>(Square.class);
	ArrayList<Square> unextendedBridgeSquares = new ArrayList<Square>(Square.class);
	ArrayList<Square> activeBridgeSquares;

	Square voidSquare;

	public PuzzleRoomExtendableBridge(int posX, int posY) {
		super("Bridge Room", posX, posY, false, false, new ArrayList<Actor>(Actor.class), 1, false, new Node[] {},
				new RoomPart[] {
						new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;
		this.bridgeWidth = 2;
		this.bridgeLength = totalWidthInSquares - 2;
		this.bridgePosX = posX + 1;
		this.bridgePosY = posY + 1;
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

//unextendedBridgeSquares
		for (int i = bridgeWidth - 1; i >= 0; i--) {
			for (int j = 0; j < bridgeLength; j++) {
				Square square = Level.squares[bridgePosX + j][posY + i];
				unextendedBridgeSquares.add(square);
			}
		}

		// extended points
		for (int i = bridgeWidth - 1; i >= 0; i--) {
			for (int j = 0; j < bridgeLength; j++) {
				Square square = Level.squares[bridgePosX + j][posY + i];
				extendedBridgeSquares.add(square);
			}
		}

		if (!bridgeExtended)
			activeBridgeSquares = unextendedBridgeSquares;
		else
			activeBridgeSquares = extendedBridgeSquares;

		for (Square bridgeSquare : activeBridgeSquares) {
			GameObject floor = Templates.STONE_FLOOR.makeCopy(null, null);
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
		bridgeExtended = !bridgeExtended;
		if (bridgeExtended)
			activeBridgeSquares = extendedBridgeSquares;
		else
			activeBridgeSquares = unextendedBridgeSquares;
		moveBridge();
	}

	public void moveBridge() {

		HashMap<GameObject, Square> movesToPerform = new HashMap<GameObject, Square>();
		// HashMap<GameObject, Square> midTeleportationsToPerform = new
		// HashMap<GameObject, Square>();
		ArrayList<GameObject> objectsToMoveInOrder = new ArrayList<GameObject>(GameObject.class);

		if (!bridgeExtended) {

			for (Square newSquare : unextendedBridgeSquares) {
				newSquare.inventory.removeGameObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			}

			for (int i = 0; i < extendedBridgeSquares.size(); i++) {
				Square oldSquare = extendedBridgeSquares.get(i);
				for (GameObject gameObject : (ArrayList<GameObject>) oldSquare.inventory.gameObjects.clone()) {
					if (gameObject.templateId != Templates.VOID_HOLE.templateId) {
						objectsToMoveInOrder.add(gameObject);
						// midTeleportationsToPerform.put(gameObject, midBridgeSquares.get(i));
						movesToPerform.put(gameObject, unextendedBridgeSquares.get(i));
					}
				}
			}

			for (GameObject gameObject : objectsToMoveInOrder) {
				move(gameObject, movesToPerform.get(gameObject));
			}

			for (Square oldSquare : extendedBridgeSquares) {
				if (!oldSquare.inventory.containsGameObjectWithTemplateId(Templates.VOID_HOLE.templateId)) {
					oldSquare.inventory.add(Templates.VOID_HOLE.makeCopy(null, null, voidSquare));
				}
			}
		} else {

			for (Square newSquare : extendedBridgeSquares) {
				newSquare.inventory.removeGameObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			}

			for (int i = 0; i < unextendedBridgeSquares.size(); i++) {
				Square oldSquare = unextendedBridgeSquares.get(i);
				for (GameObject gameObject : (ArrayList<GameObject>) oldSquare.inventory.gameObjects.clone()) {
					if (gameObject.templateId != Templates.VOID_HOLE.templateId) {
						objectsToMoveInOrder.add(gameObject);
						// midTeleportationsToPerform.put(gameObject, midBridgeSquares.get(i));
						movesToPerform.put(gameObject, extendedBridgeSquares.get(i));
					}
				}
			}

			for (GameObject gameObject : objectsToMoveInOrder) {
				move(gameObject, movesToPerform.get(gameObject));
			}

			for (Square oldSquare : unextendedBridgeSquares) {
				if (!oldSquare.inventory.containsGameObjectWithTemplateId(Templates.VOID_HOLE.templateId)) {
					oldSquare.inventory.add(Templates.VOID_HOLE.makeCopy(null, null, voidSquare));
				}
			}
		}
	}

	float focalPointX = posX * Game.SQUARE_WIDTH + (totalWidthInSquares / 2) * Game.SQUARE_WIDTH;
	float focalPointY = posY * Game.SQUARE_HEIGHT + (totalHeightInSquares / 2) * Game.SQUARE_HEIGHT;
	Point focalPoint = new Point(focalPointX, focalPointY);

	public void move(final GameObject gameObject, final Square... targetSquares1) {
		gameObject.setPrimaryAnimation(new AnimationStraightLine(gameObject, 2000f, true, 0f, null, targetSquares1));

	}

	public void setupBridgeConnections() {
		Square square;
		// bridge connector parts on the edges
		for (int i = 0; i < bridgeWidth; i++) {
			// left

			square = Level.squares[posX][posY + i];
			square.inventory.removeGameObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			square.setFloorImageTexture(Square.GRASS_TEXTURE);
			// if
			// (square.inventory.containsObjectWithTemplateId(Templates.VOID_HOLE.templateId))
			// {
			// square.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			// }

			// Level.squares[posX][posY + gapsWidth + i].passable = true;
			// right
			square = Level.squares[posX + totalWidthInSquares - 1][posY + i];
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
			square = Level.squares[posX + i][posY];
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
			square = Level.squares[posX + i][posY + totalHeightInSquares - 1];
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
