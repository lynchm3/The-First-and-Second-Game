package com.marklynch.level.constructs.bounds.structure.puzzleroom;

import java.util.ArrayList;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Wall;
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
		ArrayList<Wall> bigArrowWalls = new ArrayList<Wall>();

		// Body of arrow
		for (int x = 5; x <= 36; x++) {
			for (int y = 22; y <= 26; y++) {
				bigArrowSquares.add(Level.squares[posX + x][posY + y]);
			}
		}

		// Head of arrow
		int x = 37;
		for (int y = 17; y <= 31; y++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		x = 38;
		for (int y = 18; y <= 30; y++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		x = 39;
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
			bigArrowWalls.add(Templates.WALL.makeCopy(bigArrowSquare, null));
		}

		// fallaway walls 1
		ArrayList<Square> fallawayWallsSquares1 = new ArrayList<Square>();
		ArrayList<GameObject> fallawayWalls1 = new ArrayList<GameObject>();
		x = 38;
		for (int y = 17; y <= 17; y++) {
			fallawayWallsSquares1.add(Level.squares[posX + x][posY + y]);
		}
		x = 39;
		for (int y = 17; y <= 18; y++) {
			fallawayWallsSquares1.add(Level.squares[posX + x][posY + y]);
		}
		x = 40;
		for (int y = 17; y <= 19; y++) {
			fallawayWallsSquares1.add(Level.squares[posX + x][posY + y]);
		}
		x = 41;
		for (int y = 17; y <= 20; y++) {
			fallawayWallsSquares1.add(Level.squares[posX + x][posY + y]);
		}
		x = 42;
		for (int y = 17; y <= 21; y++) {
			fallawayWallsSquares1.add(Level.squares[posX + x][posY + y]);
		}
		x = 43;
		for (int y = 17; y <= 22; y++) {
			fallawayWallsSquares1.add(Level.squares[posX + x][posY + y]);
		}
		x = 44;
		for (int y = 17; y <= 23; y++) {
			fallawayWallsSquares1.add(Level.squares[posX + x][posY + y]);
		}

		for (Square fallawayWallsSquare1 : fallawayWallsSquares1) {
			fallawayWalls1.add(Templates.WALL.makeCopy(fallawayWallsSquare1, null));
		}

		Templates.WOODEN_SUPPORT.makeCopy(Level.squares[posX + 45][posY + 19], null, fallawayWalls1);

		// fallaway walls 2
		ArrayList<Square> fallawayWallsSquares2 = new ArrayList<Square>();
		ArrayList<GameObject> fallawayWalls2 = new ArrayList<GameObject>();

		x = 38;

		for (int y = 31; y <= 31; y++) {
			fallawayWallsSquares2.add(Level.squares[posX + x][posY + y]);
		}
		x = 39;
		for (int y = 30; y <= 31; y++) {
			fallawayWallsSquares2.add(Level.squares[posX + x][posY + y]);
		}
		x = 40;
		for (int y = 29; y <= 31; y++) {
			fallawayWallsSquares2.add(Level.squares[posX + x][posY + y]);
		}
		x = 41;
		for (int y = 28; y <= 31; y++) {
			fallawayWallsSquares2.add(Level.squares[posX + x][posY + y]);
		}
		x = 42;
		for (int y = 27; y <= 31; y++) {
			fallawayWallsSquares2.add(Level.squares[posX + x][posY + y]);
		}
		x = 43;
		for (int y = 26; y <= 31; y++) {
			fallawayWallsSquares2.add(Level.squares[posX + x][posY + y]);
		}
		x = 44;
		for (int y = 25; y <= 31; y++) {
			fallawayWallsSquares2.add(Level.squares[posX + x][posY + y]);
		}

		for (Square fallawayWallsSquare2 : fallawayWallsSquares2) {
			fallawayWalls2.add(Templates.WALL.makeCopy(fallawayWallsSquare2, null));
		}

		Templates.WOODEN_SUPPORT.makeCopy(Level.squares[posX + 45][posY + 30], null, fallawayWalls2);

		/// wall inits

		for (Wall bigArrowWall : bigArrowWalls) {
			bigArrowWall.checkIfFullWall();
		}

		for (GameObject fallawayWall1 : fallawayWalls1) {
			((Wall) fallawayWall1).checkIfFullWall();
		}

		for (GameObject fallawayWall2 : fallawayWalls2) {
			((Wall) fallawayWall2).checkIfFullWall();
		}
	}
}
