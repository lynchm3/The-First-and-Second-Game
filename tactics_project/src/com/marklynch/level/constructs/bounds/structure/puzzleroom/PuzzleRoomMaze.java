package com.marklynch.level.constructs.bounds.structure.puzzleroom;

import java.util.ArrayList;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.StructurePath;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class PuzzleRoomMaze { // extends StructureRoom {
	int posX;
	int posY;
	final static int totalWidthInSquares = 30;
	final static int totalHeightInSquares = 30;
	final static int xOffsetThatSquaresFallawayTo = 26;
	boolean floorFallenAway = false;
	ArrayList<Square> squaresToFallaway = new ArrayList<Square>();
	int safeBorderWidthOnLeft = 5;
	int safeBorderWidthOnRight = 5;
	Square voidSquare;
	public ArrayList<StructurePath> structurePaths = new ArrayList<StructurePath>();

	public PuzzleRoomMaze(int posX, int posY) {
		// super("Fallaway floor", posX, posY, false, false, new ArrayList<Actor>(), 1,
		// false, new Node[] {},
		// new RoomPart[] {
		// new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY +
		// totalHeightInSquares - 1) });

		structurePaths.add(new StructurePath("Goat's Maze", false, false, new ArrayList<Actor>(), new Node[] {},
				Level.squares[posX + 0][posY + 0], Level.squares[posX + 1][posY + 0], Level.squares[posX + 2][posY + 0],
				Level.squares[posX + 3][posY + 0], Level.squares[posX + 4][posY + 0], Level.squares[posX + 5][posY + 0],
				Level.squares[posX + 6][posY + 0], Level.squares[posX + 7][posY + 0], Level.squares[posX + 8][posY + 0],
				Level.squares[posX + 9][posY + 0], Level.squares[posX + 10][posY + 0],
				Level.squares[posX + 11][posY + 0], Level.squares[posX + 12][posY + 0],
				Level.squares[posX + 13][posY + 0], Level.squares[posX + 14][posY + 0],
				Level.squares[posX + 15][posY + 0]));

		this.posX = posX;
		this.posY = posY;
	}
}
