package com.marklynch.level.constructs.bounds.structure.structureroom.puzzleroom;

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

public class PuzzleRoomFallawayFloor extends StructureRoom implements SwitchListener {
	int posX;
	int posY;
	final static int totalWidthInSquares = 30;
	final static int totalHeightInSquares = 30;
	final static int xOffsetThatSquaresFallawayTo = 26;
	boolean floorFallenAway = false;
	ArrayList<Square> squaresToFallaway = new ArrayList<Square>(Square.class);
	int safeBorderWidthOnLeft = 5;
	int safeBorderWidthOnRight = 5;
	Square voidSquare;

	public PuzzleRoomFallawayFloor(int posX, int posY) {
		super("Fallaway floor", posX, posY, false, false, new ArrayList<Actor>(Actor.class), 1, false, new Node[] {},
				new RoomPart[] {
						new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;

		Templates.PRESSURE_PLATE.makeCopy(Game.level.squares[posX + 1][posY + totalHeightInSquares / 2], null,
				Switch.SWITCH_TYPE.OPEN_CLOSE, 5, this);

		for (int i = posX + safeBorderWidthOnLeft; i < posX + totalWidthInSquares - safeBorderWidthOnRight; i++) {
			for (int j = posY; j < posY + totalHeightInSquares; j++) {
				squaresToFallaway.add(Game.level.squares[i][j]);
				Templates.STONE_FLOOR.makeCopy(Game.level.squares[i][j], null);
			}
		}

		voidSquare = Game.level.squares[posX + 1][posY + totalHeightInSquares / 2 - 2];

		for (Square square : squaresToFallaway) {

		}
	}

	@Override
	public void zwitch(Switch zwitch) {
		// bridgeVertical = !bridgeVertical;
		// if (bridgeVertical)
		// activeBridgeSquares = verticalBridgeSquares;
		// else
		// activeBridgeSquares = horizontalBridgeSquares;
		fallawayTheFloor();
	}

	public void fallawayTheFloor() {

		if (floorFallenAway == true)
			return;

		for (Square square : squaresToFallaway) {
			// final GameObject floor = square.inventory.getGameObjectOfClass(Floor.class);
			final Square targetSquare = Level.squares[posX + xOffsetThatSquaresFallawayTo][square.yInGrid];
			for (final GameObject gameObject : (ArrayList<GameObject>) square.inventory.gameObjects.clone()) {
				gameObject.setPrimaryAnimation(
						new AnimationStraightLine(gameObject, 2000f, true, 0f, null, targetSquare));
			}
			square.inventory.add(Templates.VOID_HOLE.makeCopy(null, null, voidSquare));
			square.setFloorImageTexture(Square.VOID_SQUARE);
		}

		floorFallenAway = true;

	}

	@Override
	public Long getId() {
		return id;
	}

}
