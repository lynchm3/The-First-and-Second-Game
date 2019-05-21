package com.marklynch.level.constructs.bounds.structure.structureroom.puzzleroom;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.constructs.journal.AreaList;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Actor.Direction;
import com.marklynch.objects.actors.Actor.HOBBY;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Rail;
import com.marklynch.objects.inanimateobjects.Switch;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.ArrayList;

public class PuzzleRoomMineCart2 extends StructureRoom {
	int posX;
	int posY;
	final static int totalWidthInSquares = 20;
	final static int totalHeightInSquares = 20;

	public PuzzleRoomMineCart2(int posX, int posY) {
		super("Minecart Room 2", posX, posY, false, false, new ArrayList<Actor>(Actor.class), 1, false, new RoomPart[] {
				new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;

		// Middle path
		Templates.RAIL.makeCopy(Level.squares[posX + 0][posY + 11], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 1][posY + 11], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 2][posY + 11], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 3][posY + 11], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 4][posY + 11], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 5][posY + 11], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 6][posY + 11], null, Direction.LEFT, Direction.RIGHT);
		Rail switchableMiddleLeft = Templates.RAIL.makeCopy(Level.squares[posX + 7][posY + 11], null, Direction.LEFT,
				Direction.RIGHT);
		switchableMiddleLeft.turnsClockwiseFirst = false;
		GameObject circleFloorMiddleLeft = Templates.CIRCLE_FLOOR.makeCopy(Level.squares[posX + 7][posY + 11], null);
		circleFloorMiddleLeft.drawOffsetRatioY = 0.375f;
		Templates.RAIL.makeCopy(Level.squares[posX + 8][posY + 11], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 9][posY + 11], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 10][posY + 11], null, Direction.LEFT, Direction.RIGHT);
		Rail switchableRailMiddleRight = Templates.RAIL.makeCopy(Level.squares[posX + 11][posY + 11], null,
				Direction.LEFT, Direction.RIGHT);
		switchableRailMiddleRight.turnsClockwiseFirst = true;
		GameObject circleFloorMiddleRight = Templates.CIRCLE_FLOOR.makeCopy(Level.squares[posX + 11][posY + 11], null);
		circleFloorMiddleRight.drawOffsetRatioY = 0.375f;
		Templates.RAIL.makeCopy(Level.squares[posX + 12][posY + 11], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 13][posY + 11], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 14][posY + 11], null, Direction.LEFT, Direction.RIGHT);

		// Top loop
		Rail switchableRailTopLeft = Templates.RAIL.makeCopy(Level.squares[posX + 7][posY + 10], null, Direction.LEFT,
				Direction.UP);
		switchableRailTopLeft.turnsClockwiseFirst = false;
		GameObject circleFloorTopLeft = Templates.CIRCLE_FLOOR.makeCopy(Level.squares[posX + 7][posY + 10], null);
		circleFloorTopLeft.drawOffsetRatioY = 0.375f;
		Templates.RAIL.makeCopy(Level.squares[posX + 7][posY + 9], null, Direction.DOWN, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 8][posY + 9], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 9][posY + 9], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 10][posY + 9], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 11][posY + 9], null, Direction.LEFT, Direction.DOWN);
		Rail switchableRailTopRight = Templates.RAIL.makeCopy(Level.squares[posX + 11][posY + 10], null, Direction.UP,
				Direction.RIGHT);
		switchableRailTopRight.turnsClockwiseFirst = true;
		GameObject circleFloorTopRight = Templates.CIRCLE_FLOOR.makeCopy(Level.squares[posX + 11][posY + 10], null);
		circleFloorTopRight.drawOffsetRatioY = 0.375f;

		Templates.RAIL.makeCopy(Level.squares[posX + 12][posY + 10], null, Direction.LEFT, Direction.UP);
		Templates.RAIL.makeCopy(Level.squares[posX + 12][posY + 9], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 12][posY + 8], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 12][posY + 7], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 12][posY + 6], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 12][posY + 5], null, Direction.LEFT, Direction.DOWN);

		Templates.RAIL.makeCopy(Level.squares[posX + 11][posY + 5], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 10][posY + 5], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 9][posY + 5], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 8][posY + 5], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 7][posY + 5], null, Direction.LEFT, Direction.RIGHT);

		Templates.RAIL.makeCopy(Level.squares[posX + 6][posY + 5], null, Direction.RIGHT, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 6][posY + 6], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 6][posY + 7], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 6][posY + 8], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 6][posY + 9], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 6][posY + 10], null, Direction.RIGHT, Direction.UP);

		// Bottom loop
		Rail switchableRailBottomLeft = Templates.RAIL.makeCopy(Level.squares[posX + 7][posY + 12], null,
				Direction.LEFT, Direction.DOWN);
		switchableRailBottomLeft.turnsClockwiseFirst = true;
		GameObject circleFloorBottomLeft = Templates.CIRCLE_FLOOR.makeCopy(Level.squares[posX + 7][posY + 12], null);
		circleFloorBottomLeft.drawOffsetRatioY = 0.375f;
		Templates.RAIL.makeCopy(Level.squares[posX + 7][posY + 13], null, Direction.UP, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 8][posY + 13], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 9][posY + 13], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 10][posY + 13], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 11][posY + 13], null, Direction.LEFT, Direction.UP);
		Rail switchableRailBottomRight = Templates.RAIL.makeCopy(Level.squares[posX + 11][posY + 12], null,
				Direction.DOWN, Direction.RIGHT);
		switchableRailBottomRight.turnsClockwiseFirst = false;
		GameObject circleFloorBottomRight = Templates.CIRCLE_FLOOR.makeCopy(Level.squares[posX + 11][posY + 12], null);
		circleFloorBottomRight.drawOffsetRatioY = 0.375f;

		Templates.RAIL.makeCopy(Level.squares[posX + 12][posY + 12], null, Direction.LEFT, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 12][posY + 13], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 12][posY + 14], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 12][posY + 15], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 12][posY + 16], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 12][posY + 17], null, Direction.LEFT, Direction.UP);

		Templates.RAIL.makeCopy(Level.squares[posX + 11][posY + 17], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 10][posY + 17], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 9][posY + 17], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 8][posY + 17], null, Direction.LEFT, Direction.RIGHT);
		Templates.RAIL.makeCopy(Level.squares[posX + 7][posY + 17], null, Direction.LEFT, Direction.RIGHT);

		Templates.RAIL.makeCopy(Level.squares[posX + 6][posY + 17], null, Direction.RIGHT, Direction.UP);
		Templates.RAIL.makeCopy(Level.squares[posX + 6][posY + 16], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 6][posY + 15], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 6][posY + 14], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 6][posY + 13], null, Direction.UP, Direction.DOWN);
		Templates.RAIL.makeCopy(Level.squares[posX + 6][posY + 12], null, Direction.RIGHT, Direction.DOWN);

		Templates.PRESSURE_PLATE.makeCopy(Game.level.squares[posX + 9][posY + 10], null, Switch.SWITCH_TYPE.OPEN_CLOSE,
				5, switchableMiddleLeft, switchableRailMiddleRight, switchableRailTopLeft, switchableRailTopRight,
				switchableRailBottomLeft, switchableRailBottomRight);

		Templates.ROCK.makeCopy(Game.level.squares[posX + 9][posY + 10], null);

		Templates.MINE_CART.makeCopy(Level.squares[posX + 12][posY + 10], null, Direction.RIGHT);
		Templates.MINECART_RIDER.makeCopy("Dan", Level.squares[posX + 12][posY + 10], Game.level.factions.townsPeople,
				null, 14, new GameObject[] { Templates.HUNTING_BOW.makeCopy(null, null) }, new GameObject[] {},
				AreaList.town, new int[] {}, new HOBBY[] {});
//		Templates.MINE_CART.makeCopy(Level.squares[posX + 6][posY + 10], null, Direction.DOWN);
//		Templates.MINECART_RIDER.makeCopy("Den", Level.squares[posX + 6][posY + 10], Game.level.factions.townsPeople,
//				null, 14, new GameObject[] { Templates.HUNTING_BOW.makeCopy(null, null) }, new GameObject[] {},
//				AreaList.town, new int[] {}, new HOBBY[] {});
//		Templates.MINE_CART.makeCopy(Level.squares[posX + 12][posY + 12], null, Direction.UP);
//		Templates.MINECART_RIDER.makeCopy("Dun", Level.squares[posX + 12][posY + 12], Game.level.factions.townsPeople,
//				null, 14, new GameObject[] { Templates.HUNTING_BOW.makeCopy(null, null) }, new GameObject[] {},
//				AreaList.town, new int[] {}, new HOBBY[] {});
		Templates.MINE_CART.makeCopy(Level.squares[posX + 6][posY + 12], null, Direction.LEFT);
		Templates.MINECART_RIDER.makeCopy("Don", Level.squares[posX + 6][posY + 12], Game.level.factions.townsPeople,
				null, 14, new GameObject[] { Templates.HUNTING_BOW.makeCopy(null, null) }, new GameObject[] {},
				AreaList.town, new int[] {}, new HOBBY[] {});

		// Queue
		ArrayList<Square> queueSquares = new ArrayList<Square>(Square.class);
		queueSquares.add(Level.squares[posX + 5][posY + 6]);
		queueSquares.add(Level.squares[posX + 5][posY + 7]);
		queueSquares.add(Level.squares[posX + 5][posY + 8]);
		queueSquares.add(Level.squares[posX + 5][posY + 9]);
		queueSquares.add(Level.squares[posX + 5][posY + 10]);
		queueSquares.add(Level.squares[posX + 4][posY + 10]);
		queueSquares.add(Level.squares[posX + 3][posY + 10]);

		for (Square square : queueSquares) {
			square.setFloorImageTexture(Square.MUD_TEXTURE);
		}

		Templates.MINECART_RIDER.makeCopy("Den", queueSquares.get(0), Game.level.factions.townsPeople, null, 14,
				new GameObject[] { Templates.HUNTING_BOW.makeCopy(null, null) }, new GameObject[] {}, AreaList.town,
				new int[] {}, new HOBBY[] {});
		Templates.MINECART_RIDER.makeCopy("Dun", queueSquares.get(1), Game.level.factions.townsPeople, null, 14,
				new GameObject[] { Templates.HUNTING_BOW.makeCopy(null, null) }, new GameObject[] {}, AreaList.town,
				new int[] {}, new HOBBY[] {});

	}

}
