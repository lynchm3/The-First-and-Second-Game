package com.marklynch.level.constructs.bounds.structure.structureroom.puzzleroom;

import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.squares.Node;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.ArrayList;

public class PuzzleRoomSpikeFloor1 extends StructureRoom {
	int posX;
	int posY;
	final static int totalWidthInSquares = 21;
	final static int totalHeightInSquares = 6;

	public PuzzleRoomSpikeFloor1(int posX, int posY) {
		super("Cave In Room", posX, posY, false, false, new ArrayList<Actor>(Actor.class), 1, false, new Node[] {},
				new RoomPart[] {
						new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;
	}

}
