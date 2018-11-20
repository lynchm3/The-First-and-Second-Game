package com.marklynch.level.constructs.bounds.structure.puzzleroom;

import java.util.ArrayList;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;

public class PuzzleRoomDigPointer extends StructureRoom {
	int posX;
	int posY;
	final static int totalWidthInSquares = 49;
	final static int totalHeightInSquares = 49;

	public PuzzleRoomDigPointer(int posX, int posY) {
		super("Courtyard", posX, posY, false, false, new ArrayList<Actor>(), 1, false, new Node[] {}, new RoomPart[] {
				new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;

		Templates.SHOVEL.makeCopy(Level.squares[this.posX + 15][this.posY + 15], null);

		ArrayList<Square> bigArrowSquares = new ArrayList<Square>();

		// Body of arrow
		for (int x = 5; x <= 38; x++) {
			for (int y = 22; y <= 26; y++) {
				bigArrowSquares.add(Level.squares[posX + x][posY + y]);
			}
		}

		// Head of arrow
		int x = 39;
		for (int y = 19; y <= 29; y++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		x = 40;
		for (int y = 20; y <= 28; y++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		x = 41;
		for (int y = 21; y <= 27; y++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		x = 42;
		for (int y = 22; y <= 26; y++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		x = 43;
		for (int y = 23; y <= 25; y++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		x = 44;
		for (int y = 24; y <= 24; y++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		for (Square bigArrowSquare : bigArrowSquares) {
			Templates.WALL.makeCopy(bigArrowSquare, null);
		}
	}
}
