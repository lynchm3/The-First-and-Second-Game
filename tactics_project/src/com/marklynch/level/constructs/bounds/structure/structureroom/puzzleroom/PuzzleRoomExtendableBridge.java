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

		System.out.println("EXEXEXEX ");

		// extended points
		for (int i = 0; i < bridgeWidth; i++) {
			for (int j = 0; j < bridgeLength; j++) {
				Square square = Level.squares[bridgePosX + j][posY + i];
				extendedBridgeSquares.add(square);
			}
		}

		for (Square bridgeSquare : extendedBridgeSquares) {
			GameObject floor = Templates.STONE_FLOOR.makeCopy(null, null);
			Game.level.squares[bridgePosX][bridgeSquare.yInGrid].inventory.add(floor);
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
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void zwitch(Switch zwitch) {

		System.out.println("zwitch - " + zwitch);
		bridgeExtended = !bridgeExtended;
		moveBridge();
	}

	public void moveBridge() {

		HashMap<GameObject, Square> movesToPerform = new HashMap<GameObject, Square>();
		ArrayList<GameObject> objectsToMoveInOrder = new ArrayList<GameObject>(GameObject.class);

		if (!bridgeExtended) {

			for (int i = 0; i < extendedBridgeSquares.size(); i++) {
				Square oldSquare = extendedBridgeSquares.get(i);
				for (GameObject gameObject : (ArrayList<GameObject>) oldSquare.inventory.gameObjects.clone()) {
					if (gameObject.templateId != Templates.VOID_HOLE.templateId) {
						objectsToMoveInOrder.add(gameObject);
						movesToPerform.put(gameObject, Game.level.squares[bridgePosX][oldSquare.yInGrid]);
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
		} else {///////////////////////////////////////////////////////////////////////////////////

			for (Square newSquare : extendedBridgeSquares) {
				newSquare.inventory.removeGameObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			}

			ArrayList<Square> unextendedBridgeSquares = new ArrayList<Square>(Square.class);
			unextendedBridgeSquares.add(Level.squares[bridgePosX][bridgePosY]);
			unextendedBridgeSquares.add(Level.squares[bridgePosX][bridgePosY + 1]);

			for (int i = 0; i < extendedBridgeSquares.size(); i++) {
				Square oldSquare = Game.level.squares[bridgePosX][extendedBridgeSquares.get(i).yInGrid];

//				if (i < oldSquare.inventory.size()) {
				GameObject gameObject = oldSquare.inventory.get(i % oldSquare.inventory.size());
				objectsToMoveInOrder.add(gameObject);
				movesToPerform.put(gameObject, extendedBridgeSquares.get(i));
//				}

//				for (GameObject gameObject : (ArrayList<GameObject>) oldSquare.inventory.gameObjects.clone()) {
//					if (gameObject.templateId != Templates.VOID_HOLE.templateId) {
//						objectsToMoveInOrder.add(gameObject);
//						movesToPerform.put(gameObject, extendedBridgeSquares.get(extendedSquaresIndex));
//					}
//				}
			}
			System.out.println("extendedBridgeSquares.size() = " + extendedBridgeSquares.size());

			for (GameObject gameObject : objectsToMoveInOrder) {
				move(gameObject, movesToPerform.get(gameObject));
			}
		}
	}

	public void move(final GameObject gameObject, final Square... targetSquares1) {

//		System.out.println("move(gameObject = " + gameObject + ", targetSquare = " + targetSquares1[0] + ")");
		gameObject.setPrimaryAnimation(new AnimationStraightLine(gameObject, 2000f, true, 0f, null, targetSquares1));
	}

}
