package com.marklynch.level.constructs.bounds.structure.structureroom.puzzleroom;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.constructs.faction.FactionList;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.CopyOnWriteArrayList;

public class PuzzleRoomBushesFight extends StructureRoom {
	int posX;
	int posY;
	final static int totalWidthInSquares = 13;
	final static int totalHeightInSquares = 20;

	public PuzzleRoomBushesFight(int posX, int posY) {
		super("Cave In Room", posX, posY, false, false, new CopyOnWriteArrayList<Actor>(Actor.class), 1, false, new RoomPart[] {
				new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;

		for (int i = posX + 1; i < posX + 3; i++) {
			for (int j = posY; j < posY + totalHeightInSquares - 4; j++) {
				Templates.BUSH.makeCopy(Level.squares[i][j], null);
			}
		}

		Templates.BLIND.makeCopy(Level.squares[posX + 4][posY + 5], FactionList.blind, 14, this, new GameObject[] {},
				new GameObject[] {});

		Templates.BLIND.makeCopy(Level.squares[posX + 3][posY + 10], FactionList.blind, 29, this, new GameObject[] {},
				new GameObject[] {});

		Templates.BLIND.makeCopy(Level.squares[posX + 9][posY + 4], FactionList.blind, 13, this, new GameObject[] {},
				new GameObject[] {});

		Templates.BLIND.makeCopy(Level.squares[posX + 11][posY + 1], FactionList.blind, 22, this, new GameObject[] {},
				new GameObject[] {});

		Templates.BLIND.makeCopy(Level.squares[posX + 3][posY + 2], FactionList.blind, 9, this, new GameObject[] {},
				new GameObject[] {});

		Templates.BLIND.makeCopy(Level.squares[posX + 11][posY + 15], FactionList.blind, 22, this, new GameObject[] {},
				new GameObject[] {});

		Templates.BLIND.makeCopy(Level.squares[posX + 3][posY + 17], FactionList.blind, 9, this, new GameObject[] {},
				new GameObject[] {});

	}

}
