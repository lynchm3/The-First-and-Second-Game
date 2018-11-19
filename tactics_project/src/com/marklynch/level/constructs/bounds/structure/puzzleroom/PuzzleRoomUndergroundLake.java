package com.marklynch.level.constructs.bounds.structure.puzzleroom;

import java.util.ArrayList;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;

public class PuzzleRoomUndergroundLake extends StructureRoom {
	int posX;
	int posY;
	final static int totalWidthInSquares = 20;
	final static int totalHeightInSquares = 20;

	public PuzzleRoomUndergroundLake(int posX, int posY) {
		super("Chasm Room", posX, posY, false, false, new ArrayList<Actor>(), 1, false, new Node[] {}, new RoomPart[] {
				new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;

		// Ledges
		// 69,0
		ArrayList<Square> ledges = new ArrayList<Square>();// { Level.squares[posX + 69][posY + 0] };

		// Top
		ledges.add(Level.squares[posX + 0][posY + 0]);
		ledges.add(Level.squares[posX + 4][posY + 0]);
		ledges.add(Level.squares[posX + 5][posY + 0]);
		ledges.add(Level.squares[posX + 9][posY + 0]);
		ledges.add(Level.squares[posX + 10][posY + 0]);
		ledges.add(Level.squares[posX + 14][posY + 0]);
		ledges.add(Level.squares[posX + 19][posY + 0]);

		// Left
		ledges.add(Level.squares[posX + 0][posY + 3]);
		ledges.add(Level.squares[posX + 0][posY + 8]);
		ledges.add(Level.squares[posX + 0][posY + 10]);
		ledges.add(Level.squares[posX + 0][posY + 14]);
		ledges.add(Level.squares[posX + 0][posY + 19]);

		// Bottom
		ledges.add(Level.squares[posX + 6][posY + 19]);
		ledges.add(Level.squares[posX + 14][posY + 19]);
		ledges.add(Level.squares[posX + 19][posY + 19]);

		// Right
		ledges.add(Level.squares[posX + 19][posY + 5]);
		ledges.add(Level.squares[posX + 19][posY + 6]);
		ledges.add(Level.squares[posX + 19][posY + 16]);

		// Color ground black and put void hole where there'll be void squares
		for (int i = posX; i < posX + totalWidthInSquares; i++) {
			for (int j = posY; j < posY + totalHeightInSquares; j++) {
				if (!ledges.contains(Level.squares[i][j])) {
					// Level.squares[i][j].imageTexture = Square.VOID_SQUARE;
					Level.squares[i][j].inventory.add(Templates.WATER_BODY.makeCopy(null, null));
				}
			}
		}
	}
}
