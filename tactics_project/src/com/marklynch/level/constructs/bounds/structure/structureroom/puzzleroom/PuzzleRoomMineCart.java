package com.marklynch.level.constructs.bounds.structure.structureroom.puzzleroom;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Actor.Direction;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Rail;
import com.marklynch.objects.inanimateobjects.Seesaw;
import com.marklynch.objects.inanimateobjects.Switch;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.ArrayList;

public class PuzzleRoomMineCart extends StructureRoom {
	int posX;
	int posY;
	final static int totalWidthInSquares = 13;
	final static int totalHeightInSquares = 20;

	public PuzzleRoomMineCart(int posX, int posY) {
		super("Minecart Room", posX, posY, false, false, new ArrayList<Actor>(Actor.class), 1, false, new Node[] {},
				new RoomPart[] {
						new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;

		Square voidSquare = Level.squares[posX][posY];
		Seesaw seesaw = Templates.SEESAW.makeCopy(Level.squares[posX + 5][posY + 10], null,
				Level.squares[posX + 3][posY + 10], Level.squares[posX + 7][posY + 10], voidSquare);

		// Top loop

		// top
		Templates.RAIL.makeCopy(Level.squares[posX + 2][posY + 2], null, Direction.DOWN, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 3][posY + 2], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 4][posY + 2], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 5][posY + 2], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 6][posY + 2], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 7][posY + 2], null, Direction.LEFT, Direction.DOWN);

		// right
		Templates.RAIL.makeCopy(Level.squares[posX + 7][posY + 3], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 7][posY + 4], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 7][posY + 5], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 7][posY + 6], null, Direction.UP, Direction.DOWN);

		// bottom
		Rail switchableRight = Templates.RAIL.makeCopy(Level.squares[posX + 7][posY + 7], null, Direction.UP,
				Direction.LEFT);
		switchableRight.turnsClockwiseFirst = false;
		GameObject circleFloorRight = Templates.CIRCLE_FLOOR.makeCopy(Level.squares[posX + 7][posY + 7], null);
		circleFloorRight.drawOffsetRatioY = 0.375f;
		Templates.RAIL.makeCopy(Level.squares[posX + 6][posY + 7], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 5][posY + 7], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 4][posY + 7], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 3][posY + 7], null, Direction.LEFT, Direction.RIGHT);
		Rail switchableLeft = Templates.RAIL.makeCopy(Level.squares[posX + 2][posY + 7], null, Direction.RIGHT,
				Direction.UP);
		GameObject circleFloorLeft = Templates.CIRCLE_FLOOR.makeCopy(Level.squares[posX + 2][posY + 7], null);
		circleFloorLeft.drawOffsetRatioY = 0.375f;

		// left
		Templates.RAIL.makeCopy(Level.squares[posX + 2][posY + 3], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 2][posY + 4], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 2][posY + 5], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 2][posY + 6], null, Direction.UP, Direction.DOWN);

		Templates.PRESSURE_PLATE.makeCopy(Game.level.squares[posX + 4][posY + 5], null, Switch.SWITCH_TYPE.OPEN_CLOSE,
				5, switchableLeft, switchableRight);

		// Second loop

		// Left 2 (over gap)
		Templates.RAIL.makeCopy(Level.squares[posX + 2][posY + 8], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 2][posY + 9], null, Direction.UP, Direction.DOWN);
		Templates.RAIL_INVISIBLE.makeCopy(Level.squares[posX + 2][posY + 10], null, Direction.UP, Direction.DOWN);
		Templates.RAIL_INVISIBLE.makeCopy(Level.squares[posX + 2][posY + 11], null, Direction.UP, Direction.DOWN);
		Templates.RAIL_INVISIBLE.makeCopy(Level.squares[posX + 2][posY + 12], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 2][posY + 13], null, Direction.UP, Direction.DOWN);// lands here
		Templates.RAIL.makeCopy(Level.squares[posX + 2][posY + 14], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 2][posY + 15], null, Direction.UP, Direction.DOWN);

		// Bottom 2
		Templates.RAIL.makeCopy(Level.squares[posX + 2][posY + 16], null, Direction.UP, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 3][posY + 16], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 4][posY + 16], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 5][posY + 16], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 6][posY + 16], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 7][posY + 16], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 8][posY + 16], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 9][posY + 16], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 10][posY + 16], null, Direction.LEFT, Direction.RIGHT);
		Rail switchableBottomRight = Templates.RAIL.makeCopy(Level.squares[posX + 11][posY + 16], null, Direction.UP,
				Direction.LEFT);
		switchableBottomRight.turnsClockwiseFirst = false;
		GameObject circleFloorBottomRight = Templates.CIRCLE_FLOOR.makeCopy(Level.squares[posX + 11][posY + 16], null);
		circleFloorBottomRight.drawOffsetRatioY = 0.375f;
		Templates.RAIL.makeCopy(Level.squares[posX + 11][posY + 15], null, Direction.DOWN, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 12][posY + 15], null, Direction.UP, Direction.LEFT);

		// Right 2 (over gap)
		Templates.RAIL.makeCopy(Level.squares[posX + 12][posY + 14], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 12][posY + 13], null, Direction.UP, Direction.DOWN);// lands here
		Templates.RAIL_INVISIBLE.makeCopy(Level.squares[posX + 12][posY + 12], null, Direction.UP, Direction.DOWN);
		Templates.RAIL_INVISIBLE.makeCopy(Level.squares[posX + 12][posY + 11], null, Direction.UP, Direction.DOWN);
		Templates.RAIL_INVISIBLE.makeCopy(Level.squares[posX + 12][posY + 10], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 12][posY + 9], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 12][posY + 8], null, Direction.UP, Direction.DOWN);

		// Top 2
		Templates.RAIL.makeCopy(Level.squares[posX + 12][posY + 7], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 12][posY + 6], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 12][posY + 5], null, Direction.LEFT, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 11][posY + 5], null, Direction.RIGHT, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 11][posY + 6], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 11][posY + 7], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 11][posY + 8], null, Direction.LEFT, Direction.UP);
		Templates.RAIL.makeCopy(Level.squares[posX + 10][posY + 8], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 9][posY + 8], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 8][posY + 8], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 7][posY + 8], null, Direction.UP, Direction.RIGHT);

		// Bottom right exit
		Templates.RAIL.makeCopy(Level.squares[posX + 11][posY + 17], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 11][posY + 18], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 11][posY + 19], null, Direction.UP, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 12][posY + 19], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 13][posY + 19], null, Direction.LEFT, Direction.RIGHT);

		Templates.PRESSURE_PLATE.makeCopy(Game.level.squares[posX + 8][posY + 15], null, Switch.SWITCH_TYPE.OPEN_CLOSE,
				5, switchableBottomRight);

		// Island crate
		Templates.CRATE.makeCopy(Level.squares[posX + 11][posY + 4], false, null);

		// Test Crates
		Templates.CRATE.makeCopy(Level.squares[posX + 3][posY + 0], false, null);
		Templates.CRATE.makeCopy(Level.squares[posX + 4][posY + 0], false, null);
		Templates.CRATE.makeCopy(Level.squares[posX + 6][posY + 0], false, null);
		Templates.CRATE.makeCopy(Level.squares[posX + 7][posY + 0], false, null);

		Templates.CANDY.makeCopy(Level.squares[posX + 8][posY + 0], null);

		ArrayList<Square> islandSquares = new ArrayList<Square>(Square.class);
		islandSquares.add(Level.squares[posX + 11][posY + 4]);
		islandSquares.add(Level.squares[posX + 11][posY + 5]);
		islandSquares.add(Level.squares[posX + 12][posY + 4]);
		islandSquares.add(Level.squares[posX + 12][posY + 5]);

		// Single square on it's own to test dash
		Templates.VOID_HOLE.makeCopy(Level.squares[posX + 2][posY + 1], null, voidSquare);
		Level.squares[posX + 2][posY + 1].setFloorImageTexture(Square.VOID_SQUARE);

		// Bonus void to get the chest
		for (int i = 9; i < 13; i++) {
			for (int j = 2; j < 8; j++) {
				if (!islandSquares.contains(Level.squares[posX + i][posY + j])) {
					Templates.VOID_HOLE.makeCopy(Level.squares[posX + i][posY + j], null, voidSquare);
					Level.squares[posX + i][posY + j].setFloorImageTexture(Square.VOID_SQUARE);
				}
			}
		}

		// Long void in middle of level that needs to be cleared
		for (int i = 0; i < totalWidthInSquares; i++) {
			for (int j = 10; j < 10 + 3; j++) {
				if (Level.squares[posX + i][posY + j] != seesaw.square1
						&& Level.squares[posX + i][posY + j] != seesaw.square2) {
					Templates.VOID_HOLE.makeCopy(Level.squares[posX + i][posY + j], null, voidSquare);
				}
				Level.squares[posX + i][posY + j].setFloorImageTexture(Square.VOID_SQUARE);
			}
		}

		Templates.MINE_CART.makeCopy(Level.squares[posX + 3][posY + 2], null, Direction.RIGHT);

		Templates.PORTAL.makeCopy(Level.squares[posX + 1][posY + 1], null, voidSquare);
		Templates.LANDMINE.makeCopy(Level.squares[posX + 3][posY + 1], null, 1, 10);

	}

}
