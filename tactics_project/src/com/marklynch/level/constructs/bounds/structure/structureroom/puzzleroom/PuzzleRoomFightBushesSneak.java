package com.marklynch.level.constructs.bounds.structure.structureroom.puzzleroom;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.squares.Node;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.ArrayList;

public class PuzzleRoomFightBushesSneak extends StructureRoom {
	int posX;
	int posY;
	final static int totalWidthInSquares = 13;
	final static int totalHeightInSquares = 20;

	public PuzzleRoomFightBushesSneak(int posX, int posY) {
		super("Cave In Room", posX, posY, false, false, new ArrayList<Actor>(Actor.class), 1, false, new Node[] {},
				new RoomPart[] {
						new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;

		for (int i = posX + 1; i < posX + 3; i++) {
			for (int j = posY; j < posY + totalHeightInSquares - 4; j++) {
				Templates.BUSH.makeCopy(Level.squares[i][j], null);
			}
		}

	}

}
