package com.marklynch.level.constructs.bounds.structure.puzzleroom;

import java.util.ArrayList;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.StructureFeature;
import com.marklynch.level.constructs.bounds.structure.StructurePath;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.templates.Templates;
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
	public ArrayList<StructureFeature> features = new ArrayList<StructureFeature>();

	public PuzzleRoomMaze(int posX, int posY) {
		// super("Fallaway floor", posX, posY, false, false, new ArrayList<Actor>(), 1,
		// false, new Node[] {},
		// new RoomPart[] {
		// new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY +
		// totalHeightInSquares - 1) });

		structurePaths.add(new StructurePath("Goat's Maze", false, false, new ArrayList<Actor>(), new Node[] {},
				// x = 0
				Level.squares[posX + 0][posY + 0], Level.squares[posX + 0][posY + 28],
				Level.squares[posX + 0][posY + 29], //
				// x= 1
				Level.squares[posX + 1][posY + 0], Level.squares[posX + 1][posY + 5],
				Level.squares[posX + 1][posY + 10], Level.squares[posX + 1][posY + 14],
				Level.squares[posX + 1][posY + 15], Level.squares[posX + 1][posY + 16],
				Level.squares[posX + 1][posY + 17], Level.squares[posX + 1][posY + 18],
				Level.squares[posX + 1][posY + 19], Level.squares[posX + 1][posY + 23],
				Level.squares[posX + 1][posY + 24], Level.squares[posX + 1][posY + 25],
				Level.squares[posX + 1][posY + 26], Level.squares[posX + 1][posY + 27],
				Level.squares[posX + 1][posY + 28],
				// x=2
				Level.squares[posX + 2][posY + 0], Level.squares[posX + 2][posY + 3], Level.squares[posX + 2][posY + 4],
				Level.squares[posX + 2][posY + 5], Level.squares[posX + 2][posY + 6], Level.squares[posX + 2][posY + 7],
				Level.squares[posX + 2][posY + 8], Level.squares[posX + 2][posY + 10],
				Level.squares[posX + 2][posY + 12], Level.squares[posX + 2][posY + 14],
				Level.squares[posX + 2][posY + 21], Level.squares[posX + 2][posY + 22],
				Level.squares[posX + 2][posY + 23],
				// x=3
				Level.squares[posX + 3][posY + 0], Level.squares[posX + 3][posY + 3], Level.squares[posX + 3][posY + 7],
				Level.squares[posX + 3][posY + 10], Level.squares[posX + 3][posY + 11],
				Level.squares[posX + 3][posY + 12], Level.squares[posX + 3][posY + 14],
				Level.squares[posX + 3][posY + 19], Level.squares[posX + 3][posY + 20],
				Level.squares[posX + 3][posY + 21], Level.squares[posX + 3][posY + 22],
				Level.squares[posX + 3][posY + 23], Level.squares[posX + 3][posY + 24],
				Level.squares[posX + 3][posY + 25], Level.squares[posX + 3][posY + 27],
				Level.squares[posX + 3][posY + 28], Level.squares[posX + 3][posY + 29],
				// x=4
				Level.squares[posX + 4][posY + 0], Level.squares[posX + 4][posY + 3], Level.squares[posX + 4][posY + 4],
				Level.squares[posX + 4][posY + 7], Level.squares[posX + 4][posY + 10],
				Level.squares[posX + 4][posY + 12], Level.squares[posX + 4][posY + 14],
				Level.squares[posX + 4][posY + 18], Level.squares[posX + 4][posY + 19],
				Level.squares[posX + 4][posY + 20], Level.squares[posX + 4][posY + 21],
				Level.squares[posX + 4][posY + 24], Level.squares[posX + 4][posY + 25],
				Level.squares[posX + 4][posY + 29],
				// x=5
				Level.squares[posX + 5][posY + 0], Level.squares[posX + 5][posY + 6], Level.squares[posX + 5][posY + 7],
				Level.squares[posX + 5][posY + 9], Level.squares[posX + 5][posY + 10],
				Level.squares[posX + 5][posY + 12], Level.squares[posX + 5][posY + 13],
				Level.squares[posX + 5][posY + 14], Level.squares[posX + 5][posY + 18],
				Level.squares[posX + 5][posY + 19], Level.squares[posX + 5][posY + 20],
				Level.squares[posX + 5][posY + 24], Level.squares[posX + 5][posY + 25],
				Level.squares[posX + 5][posY + 26], Level.squares[posX + 5][posY + 27],
				Level.squares[posX + 5][posY + 29],
				// x=6
				Level.squares[posX + 6][posY + 0], Level.squares[posX + 6][posY + 1], Level.squares[posX + 6][posY + 2],
				Level.squares[posX + 6][posY + 3], Level.squares[posX + 6][posY + 4], Level.squares[posX + 6][posY + 5],
				Level.squares[posX + 6][posY + 6], Level.squares[posX + 6][posY + 9],
				Level.squares[posX + 6][posY + 12], Level.squares[posX + 6][posY + 14],
				Level.squares[posX + 6][posY + 15], Level.squares[posX + 6][posY + 18],
				Level.squares[posX + 6][posY + 19], Level.squares[posX + 6][posY + 20],
				Level.squares[posX + 6][posY + 23], Level.squares[posX + 6][posY + 24],
				Level.squares[posX + 6][posY + 25], Level.squares[posX + 6][posY + 26],
				Level.squares[posX + 6][posY + 29],
				// x=7
				Level.squares[posX + 7][posY + 0], Level.squares[posX + 7][posY + 4], Level.squares[posX + 7][posY + 6],
				Level.squares[posX + 7][posY + 7], Level.squares[posX + 7][posY + 8], Level.squares[posX + 7][posY + 9],
				Level.squares[posX + 7][posY + 10], Level.squares[posX + 7][posY + 11],
				Level.squares[posX + 7][posY + 12], Level.squares[posX + 7][posY + 15],
				Level.squares[posX + 7][posY + 18], Level.squares[posX + 7][posY + 19],
				Level.squares[posX + 7][posY + 20], Level.squares[posX + 7][posY + 21],
				Level.squares[posX + 7][posY + 22], Level.squares[posX + 7][posY + 23],
				Level.squares[posX + 7][posY + 24], Level.squares[posX + 7][posY + 26],
				Level.squares[posX + 7][posY + 27], Level.squares[posX + 7][posY + 29],
				// x=8
				Level.squares[posX + 8][posY + 0], Level.squares[posX + 8][posY + 4], Level.squares[posX + 8][posY + 6],
				Level.squares[posX + 8][posY + 10], Level.squares[posX + 8][posY + 15],
				Level.squares[posX + 8][posY + 18], Level.squares[posX + 8][posY + 19],
				Level.squares[posX + 8][posY + 20], Level.squares[posX + 8][posY + 23],
				Level.squares[posX + 8][posY + 24], Level.squares[posX + 8][posY + 25],
				Level.squares[posX + 8][posY + 26], Level.squares[posX + 8][posY + 29],
				// x=9
				Level.squares[posX + 9][posY + 0], Level.squares[posX + 9][posY + 2], Level.squares[posX + 9][posY + 4],
				Level.squares[posX + 9][posY + 6], Level.squares[posX + 9][posY + 9],
				Level.squares[posX + 9][posY + 10], Level.squares[posX + 9][posY + 11],
				Level.squares[posX + 9][posY + 14], Level.squares[posX + 9][posY + 15],
				Level.squares[posX + 9][posY + 18], Level.squares[posX + 9][posY + 19],
				Level.squares[posX + 9][posY + 20], Level.squares[posX + 9][posY + 24],
				Level.squares[posX + 9][posY + 25], Level.squares[posX + 9][posY + 26],
				Level.squares[posX + 9][posY + 27], Level.squares[posX + 9][posY + 29],
				// x=10
				Level.squares[posX + 10][posY + 0], Level.squares[posX + 10][posY + 2],
				Level.squares[posX + 10][posY + 4], Level.squares[posX + 10][posY + 11],
				Level.squares[posX + 10][posY + 12], Level.squares[posX + 10][posY + 15],
				Level.squares[posX + 10][posY + 18], Level.squares[posX + 10][posY + 19],
				Level.squares[posX + 10][posY + 20], Level.squares[posX + 10][posY + 21],
				Level.squares[posX + 10][posY + 24], Level.squares[posX + 10][posY + 25],
				Level.squares[posX + 10][posY + 29],
				// x=11
				Level.squares[posX + 11][posY + 0], Level.squares[posX + 11][posY + 2],
				Level.squares[posX + 11][posY + 4], Level.squares[posX + 11][posY + 5],
				Level.squares[posX + 11][posY + 6], Level.squares[posX + 11][posY + 7],
				Level.squares[posX + 11][posY + 8], Level.squares[posX + 11][posY + 14],
				Level.squares[posX + 11][posY + 15], Level.squares[posX + 11][posY + 19],
				Level.squares[posX + 11][posY + 20], Level.squares[posX + 11][posY + 21],
				Level.squares[posX + 11][posY + 22], Level.squares[posX + 11][posY + 23],
				Level.squares[posX + 11][posY + 24], Level.squares[posX + 11][posY + 25],
				Level.squares[posX + 11][posY + 27], Level.squares[posX + 11][posY + 28],
				Level.squares[posX + 11][posY + 29],
				// x=12
				Level.squares[posX + 12][posY + 0], Level.squares[posX + 12][posY + 2],
				Level.squares[posX + 12][posY + 15], Level.squares[posX + 12][posY + 21],
				Level.squares[posX + 12][posY + 22], Level.squares[posX + 12][posY + 23],
				Level.squares[posX + 12][posY + 28],
				// x=13
				Level.squares[posX + 13][posY + 0], Level.squares[posX + 13][posY + 2],
				Level.squares[posX + 13][posY + 9], Level.squares[posX + 13][posY + 10],
				Level.squares[posX + 13][posY + 11], Level.squares[posX + 13][posY + 15],
				Level.squares[posX + 13][posY + 16], Level.squares[posX + 13][posY + 17],
				Level.squares[posX + 13][posY + 18], Level.squares[posX + 13][posY + 19],
				Level.squares[posX + 13][posY + 23], Level.squares[posX + 13][posY + 28],
				// x=14
				Level.squares[posX + 14][posY + 0], Level.squares[posX + 14][posY + 1],
				Level.squares[posX + 14][posY + 2], Level.squares[posX + 14][posY + 3],
				Level.squares[posX + 14][posY + 4], Level.squares[posX + 14][posY + 5],
				Level.squares[posX + 14][posY + 6], Level.squares[posX + 14][posY + 11],
				Level.squares[posX + 14][posY + 12], Level.squares[posX + 14][posY + 15],
				Level.squares[posX + 14][posY + 19], Level.squares[posX + 14][posY + 20],
				Level.squares[posX + 14][posY + 21], Level.squares[posX + 14][posY + 22],
				Level.squares[posX + 14][posY + 23], Level.squares[posX + 14][posY + 24],
				Level.squares[posX + 14][posY + 25], Level.squares[posX + 14][posY + 28],
				Level.squares[posX + 14][posY + 29],
				// x=15
				Level.squares[posX + 15][posY + 0], Level.squares[posX + 15][posY + 3],
				Level.squares[posX + 15][posY + 6], Level.squares[posX + 15][posY + 7],
				Level.squares[posX + 15][posY + 8], Level.squares[posX + 15][posY + 12],
				Level.squares[posX + 15][posY + 13], Level.squares[posX + 15][posY + 14],
				Level.squares[posX + 15][posY + 15], Level.squares[posX + 15][posY + 16],
				Level.squares[posX + 15][posY + 17], Level.squares[posX + 15][posY + 18],
				Level.squares[posX + 15][posY + 19], Level.squares[posX + 15][posY + 25],
				Level.squares[posX + 15][posY + 27], Level.squares[posX + 15][posY + 28],
				// x=16
				Level.squares[posX + 16][posY + 0], Level.squares[posX + 16][posY + 6],
				Level.squares[posX + 16][posY + 8], Level.squares[posX + 16][posY + 11],
				Level.squares[posX + 16][posY + 12], Level.squares[posX + 16][posY + 14],
				Level.squares[posX + 16][posY + 17], Level.squares[posX + 16][posY + 21],
				Level.squares[posX + 16][posY + 22], Level.squares[posX + 16][posY + 23],
				Level.squares[posX + 16][posY + 24], Level.squares[posX + 16][posY + 25],
				Level.squares[posX + 16][posY + 27],
				// x=17
				Level.squares[posX + 17][posY + 0], Level.squares[posX + 17][posY + 1],
				Level.squares[posX + 17][posY + 2], Level.squares[posX + 17][posY + 3],
				Level.squares[posX + 17][posY + 4], Level.squares[posX + 17][posY + 5],
				Level.squares[posX + 17][posY + 6], Level.squares[posX + 17][posY + 7],
				Level.squares[posX + 17][posY + 8], Level.squares[posX + 17][posY + 9],
				Level.squares[posX + 17][posY + 10], Level.squares[posX + 17][posY + 11],
				Level.squares[posX + 17][posY + 14], Level.squares[posX + 17][posY + 17],
				Level.squares[posX + 17][posY + 20], Level.squares[posX + 17][posY + 21],
				Level.squares[posX + 17][posY + 27],
				// x=18
				Level.squares[posX + 18][posY + 14], Level.squares[posX + 18][posY + 15],
				Level.squares[posX + 18][posY + 17], Level.squares[posX + 18][posY + 20],
				Level.squares[posX + 18][posY + 24], Level.squares[posX + 18][posY + 25],
				Level.squares[posX + 18][posY + 26], Level.squares[posX + 18][posY + 27],
				// x=19
				Level.squares[posX + 19][posY + 0], Level.squares[posX + 19][posY + 1],
				Level.squares[posX + 19][posY + 2], Level.squares[posX + 19][posY + 3],
				Level.squares[posX + 19][posY + 4], Level.squares[posX + 19][posY + 5],
				Level.squares[posX + 19][posY + 6], Level.squares[posX + 19][posY + 7],
				Level.squares[posX + 19][posY + 8], Level.squares[posX + 19][posY + 9],
				Level.squares[posX + 19][posY + 10], Level.squares[posX + 19][posY + 12],
				Level.squares[posX + 19][posY + 15], Level.squares[posX + 19][posY + 17],
				Level.squares[posX + 19][posY + 20], Level.squares[posX + 19][posY + 27],
				// x=20
				Level.squares[posX + 20][posY + 0], Level.squares[posX + 20][posY + 4],
				Level.squares[posX + 20][posY + 10], Level.squares[posX + 20][posY + 12],
				Level.squares[posX + 20][posY + 17], Level.squares[posX + 20][posY + 19],
				Level.squares[posX + 20][posY + 20], Level.squares[posX + 20][posY + 21],
				Level.squares[posX + 20][posY + 22], Level.squares[posX + 20][posY + 23],
				Level.squares[posX + 20][posY + 27], Level.squares[posX + 20][posY + 28],
				Level.squares[posX + 20][posY + 29],
				// x=21
				Level.squares[posX + 21][posY + 0], Level.squares[posX + 21][posY + 4],
				Level.squares[posX + 21][posY + 10], Level.squares[posX + 21][posY + 12],
				Level.squares[posX + 21][posY + 13], Level.squares[posX + 21][posY + 14],
				Level.squares[posX + 21][posY + 17], Level.squares[posX + 21][posY + 23],
				Level.squares[posX + 21][posY + 24], Level.squares[posX + 21][posY + 27],
				// x=22
				Level.squares[posX + 22][posY + 0], Level.squares[posX + 22][posY + 4],
				Level.squares[posX + 22][posY + 10], Level.squares[posX + 22][posY + 12],
				Level.squares[posX + 22][posY + 14], Level.squares[posX + 22][posY + 17],
				Level.squares[posX + 22][posY + 24], Level.squares[posX + 22][posY + 25],
				Level.squares[posX + 22][posY + 26], Level.squares[posX + 22][posY + 27],
				// x=23
				Level.squares[posX + 23][posY + 0], Level.squares[posX + 23][posY + 2],
				Level.squares[posX + 23][posY + 4], Level.squares[posX + 23][posY + 10],
				Level.squares[posX + 23][posY + 12], Level.squares[posX + 23][posY + 14],
				Level.squares[posX + 23][posY + 15], Level.squares[posX + 23][posY + 16],
				Level.squares[posX + 23][posY + 17], Level.squares[posX + 23][posY + 21],
				Level.squares[posX + 23][posY + 22], Level.squares[posX + 23][posY + 23],
				Level.squares[posX + 23][posY + 24],
				// x=24
				Level.squares[posX + 24][posY + 0], Level.squares[posX + 24][posY + 2],
				Level.squares[posX + 24][posY + 3], Level.squares[posX + 24][posY + 4],
				Level.squares[posX + 24][posY + 5], Level.squares[posX + 24][posY + 6],
				Level.squares[posX + 24][posY + 10], Level.squares[posX + 24][posY + 12],
				Level.squares[posX + 24][posY + 14], Level.squares[posX + 24][posY + 21],
				Level.squares[posX + 24][posY + 24], Level.squares[posX + 24][posY + 25],
				Level.squares[posX + 24][posY + 26], Level.squares[posX + 24][posY + 27],
				// x=25
				Level.squares[posX + 25][posY + 0], Level.squares[posX + 25][posY + 2],
				Level.squares[posX + 25][posY + 4], Level.squares[posX + 25][posY + 10],
				Level.squares[posX + 25][posY + 12], Level.squares[posX + 25][posY + 14],
				Level.squares[posX + 25][posY + 15], Level.squares[posX + 25][posY + 16],
				Level.squares[posX + 25][posY + 17], Level.squares[posX + 25][posY + 18],
				Level.squares[posX + 25][posY + 21], Level.squares[posX + 25][posY + 27],
				// x=26
				Level.squares[posX + 26][posY + 0], Level.squares[posX + 26][posY + 4],
				Level.squares[posX + 26][posY + 10], Level.squares[posX + 26][posY + 11],
				Level.squares[posX + 26][posY + 12], Level.squares[posX + 26][posY + 13],
				Level.squares[posX + 26][posY + 14], Level.squares[posX + 26][posY + 18],
				Level.squares[posX + 26][posY + 19], Level.squares[posX + 26][posY + 20],
				Level.squares[posX + 26][posY + 21], Level.squares[posX + 26][posY + 22],
				Level.squares[posX + 26][posY + 23], Level.squares[posX + 26][posY + 27],
				// x=27
				Level.squares[posX + 27][posY + 0], Level.squares[posX + 27][posY + 4],
				Level.squares[posX + 27][posY + 10], Level.squares[posX + 27][posY + 12],
				Level.squares[posX + 27][posY + 14], Level.squares[posX + 27][posY + 19],
				Level.squares[posX + 27][posY + 23], Level.squares[posX + 27][posY + 27],
				// x=28
				Level.squares[posX + 28][posY + 0], Level.squares[posX + 28][posY + 4],
				Level.squares[posX + 28][posY + 10], Level.squares[posX + 28][posY + 12],
				Level.squares[posX + 28][posY + 19], Level.squares[posX + 28][posY + 27],
				// x=29
				Level.squares[posX + 29][posY + 0], Level.squares[posX + 29][posY + 1],
				Level.squares[posX + 29][posY + 2], Level.squares[posX + 29][posY + 3],
				Level.squares[posX + 29][posY + 4], Level.squares[posX + 29][posY + 5],
				Level.squares[posX + 29][posY + 6], Level.squares[posX + 29][posY + 7],
				Level.squares[posX + 29][posY + 8], Level.squares[posX + 29][posY + 9],
				Level.squares[posX + 29][posY + 10], Level.squares[posX + 29][posY + 19],
				Level.squares[posX + 29][posY + 27], Level.squares[posX + 29][posY + 28],
				Level.squares[posX + 29][posY + 29]));

		// @5,27 give the skull a gold tooth. Maybe I could put in gold ore or a gold
		// vein instead...
		this.features.add(new StructureFeature(
				Templates.VEIN.makeCopy(Level.squares[posX + 5][posY + 27], null, false, Templates.ORE, 0.5f)));

		// Veins blocking the way to the monster
		// 13,23
		this.features.add(new StructureFeature(
				Templates.VEIN.makeCopy(Level.squares[posX + 13][posY + 23], null, false, Templates.ORE, 0.5f)));

		// Cracked walls blocking the way to the treasure
		// x = 26, y = 11
		this.features.add(
				new StructureFeature(Templates.WALL_WITH_CRACK.makeCopy(Level.squares[posX + 26][posY + 11], null)));

		// Monsters

		this.posX = posX;
		this.posY = posY;
	}
}
