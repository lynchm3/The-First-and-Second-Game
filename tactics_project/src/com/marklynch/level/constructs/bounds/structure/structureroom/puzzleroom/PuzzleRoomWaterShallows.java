package com.marklynch.level.constructs.bounds.structure.structureroom.puzzleroom;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.ArrayList;

public class PuzzleRoomWaterShallows extends StructureRoom {
	int posX;
	int posY;
	final static int totalWidthInSquares = 10;
	final static int totalHeightInSquares = 10;

	public PuzzleRoomWaterShallows(int posX, int posY) {
		super("Chasm Room", posX, posY, false, false, new ArrayList<Actor>(Actor.class), 1, false, new RoomPart[] {
				new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;

		// Color ground black and put void hole where there'll be void squares
		for (int i = posX; i < posX + totalWidthInSquares; i++) {
			for (int j = posY; j < posY + totalHeightInSquares; j++) {
				// Level.squares[i][j].imageTexture = Square.VOID_SQUARE;
				Level.squares[i][j].inventory.add(Templates.WATER.makeCopy(null, null, 1));
			}
		}
	}
}
