package com.marklynch.level.constructs.bounds.structure.puzzleroom;

import java.util.ArrayList;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Wall;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.templates.Templates;

public class PuzzleRoomArrows extends StructureRoom {
	int posX;
	int posY;
	final static int totalWidthInSquares = 53;
	final static int totalHeightInSquares = 53;
	final int diggableX = 44 - 4;
	final int diggableY = 12;

	public PuzzleRoomArrows(int posX, int posY) {
		super("Courtyard", posX, posY, false, false, new ArrayList<Actor>(), 1, false, new Node[] {}, new RoomPart[] {
				new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;

		// Diggable
		GameObject mound = Templates.MOUND.makeCopy(Level.squares[posX + diggableX][posY + diggableY], null, 50);
		mound.inventory.add(Templates.ROCK.makeCopy(null, null));
		mound.inventory.add(Templates.GOLD.makeCopy(null, null, 102));

		// Shovel. Could put shovel in box?
		Templates.SHOVEL.makeCopy(Level.squares[this.posX + 4][this.posY + 12], null);

		// A crate
		GameObject createWithEtching = Templates.CRATE_WITH_ETCHING.makeCopy(Level.squares[posX + 0][posY + 0], false,
				null);
		createWithEtching.conversation = createWithEtching.createConversation(new Object[] { "DO NOT OPEN" });

		buildHorizontalArrow();
		buildVerticalArrow();
	}

	private void buildHorizontalArrow() {

		ArrayList<Square> bigArrowSquares = new ArrayList<Square>();
		ArrayList<Wall> bigArrowWalls = new ArrayList<Wall>();
		// Body of arrow
		for (int x = 5; x <= 21; x++) {
			for (int y = 10; y <= 14; y++) {
				bigArrowSquares.add(Level.squares[posX + x][posY + y]);
			}
		}

		// Head of arrow
		int x = 22;
		for (int y = 5; y <= 19; y++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		x = 23;
		for (int y = 6; y <= 18; y++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		x = 24;
		for (int y = 7; y <= 17; y++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		x = 25;
		for (int y = 8; y <= 16; y++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		x = 26;
		for (int y = 9; y <= 15; y++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		x = 27;
		for (int y = 10; y <= 14; y++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		x = 28;
		for (int y = 11; y <= 13; y++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		x = 29;
		for (int y = 12; y <= 12; y++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		for (Square bigArrowSquare : bigArrowSquares) {
			bigArrowWalls.add(Templates.WALL.makeCopy(bigArrowSquare, null));
		}

		// fallaway walls 1
		ArrayList<Square> fallawayWallsSquares1 = new ArrayList<Square>();
		ArrayList<GameObject> fallawayWalls1 = new ArrayList<GameObject>();
		x = 23;
		for (int y = 5; y <= 5; y++) {
			fallawayWallsSquares1.add(Level.squares[posX + x][posY + y]);
		}
		x = 24;
		for (int y = 5; y <= 6; y++) {
			fallawayWallsSquares1.add(Level.squares[posX + x][posY + y]);
		}
		x = 25;
		for (int y = 5; y <= 7; y++) {
			fallawayWallsSquares1.add(Level.squares[posX + x][posY + y]);
		}
		x = 26;
		for (int y = 5; y <= 8; y++) {
			fallawayWallsSquares1.add(Level.squares[posX + x][posY + y]);
		}
		x = 27;
		for (int y = 5; y <= 9; y++) {
			fallawayWallsSquares1.add(Level.squares[posX + x][posY + y]);
		}
		x = 28;
		for (int y = 5; y <= 10; y++) {
			fallawayWallsSquares1.add(Level.squares[posX + x][posY + y]);
		}
		x = 29;
		for (int y = 5; y <= 11; y++) {
			fallawayWallsSquares1.add(Level.squares[posX + x][posY + y]);
		}

		for (Square fallawayWallsSquare1 : fallawayWallsSquares1) {
			fallawayWalls1.add(Templates.WALL.makeCopy(fallawayWallsSquare1, null));
		}

		Templates.WOODEN_SUPPORT.makeCopy(Level.squares[posX + 30][posY + 7], null, fallawayWalls1);

		// fallaway walls 2
		ArrayList<Square> fallawayWallsSquares2 = new ArrayList<Square>();
		ArrayList<GameObject> fallawayWalls2 = new ArrayList<GameObject>();

		x = 23;

		for (int y = 19; y <= 19; y++) {
			fallawayWallsSquares2.add(Level.squares[posX + x][posY + y]);
		}
		x = 24;
		for (int y = 18; y <= 19; y++) {
			fallawayWallsSquares2.add(Level.squares[posX + x][posY + y]);
		}
		x = 25;
		for (int y = 17; y <= 19; y++) {
			fallawayWallsSquares2.add(Level.squares[posX + x][posY + y]);
		}
		x = 26;
		for (int y = 16; y <= 19; y++) {
			fallawayWallsSquares2.add(Level.squares[posX + x][posY + y]);
		}
		x = 27;
		for (int y = 15; y <= 19; y++) {
			fallawayWallsSquares2.add(Level.squares[posX + x][posY + y]);
		}
		x = 28;
		for (int y = 14; y <= 19; y++) {
			fallawayWallsSquares2.add(Level.squares[posX + x][posY + y]);
		}
		x = 29;
		for (int y = 13; y <= 19; y++) {
			fallawayWallsSquares2.add(Level.squares[posX + x][posY + y]);
		}

		for (Square fallawayWallsSquare2 : fallawayWallsSquares2) {
			fallawayWalls2.add(Templates.WALL.makeCopy(fallawayWallsSquare2, null));
		}

		Templates.WOODEN_SUPPORT.makeCopy(Level.squares[posX + 30][posY + 18], null, fallawayWalls2);

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

	private void buildVerticalArrow() {

		ArrayList<Square> bigArrowSquares = new ArrayList<Square>();
		ArrayList<Wall> bigArrowWalls = new ArrayList<Wall>();

		// Body of arrow
		for (int x = 42 - 4; x <= 46 - 4; x++) {
			for (int y = 35 - 4; y <= 51 - 4; y++) {
				bigArrowSquares.add(Level.squares[posX + x][posY + y]);
			}
		}

		// Head of arrow
		int y = 34 - 4;
		for (int x = 37 - 4; x <= 51 - 4; x++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		y = 33 - 4;
		for (int x = 38 - 4; x <= 50 - 4; x++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		y = 32 - 4;
		for (int x = 39 - 4; x <= 49 - 4; x++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		y = 31 - 4;
		for (int x = 40 - 4; x <= 48 - 4; x++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		y = 30 - 4;
		for (int x = 41 - 4; x <= 47 - 4; x++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		y = 29 - 4;
		for (int x = 42 - 4; x <= 46 - 4; x++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		y = 28 - 4;
		for (int x = 43 - 4; x <= 45 - 4; x++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		y = 27 - 4;
		for (int x = 44 - 4; x <= 44 - 4; x++) {
			bigArrowSquares.add(Level.squares[posX + x][posY + y]);
		}

		for (Square bigArrowSquare : bigArrowSquares) {
			bigArrowWalls.add(Templates.WALL.makeCopy(bigArrowSquare, null));
		}

		// fallaway walls 1
		ArrayList<Square> fallawayWallsSquares1 = new ArrayList<Square>();
		ArrayList<GameObject> fallawayWalls1 = new ArrayList<GameObject>();
		y = 33 - 4;
		for (int x = 37 - 4; x <= 37 - 4; x++) {
			fallawayWallsSquares1.add(Level.squares[posX + x][posY + y]);
		}
		y = 32 - 4;
		for (int x = 37 - 4; x <= 38 - 4; x++) {
			fallawayWallsSquares1.add(Level.squares[posX + x][posY + y]);
		}
		y = 31 - 4;
		for (int x = 37 - 4; x <= 39 - 4; x++) {
			fallawayWallsSquares1.add(Level.squares[posX + x][posY + y]);
		}
		y = 30 - 4;
		for (int x = 37 - 4; x <= 40 - 4; x++) {
			fallawayWallsSquares1.add(Level.squares[posX + x][posY + y]);
		}
		y = 29 - 4;
		for (int x = 37 - 4; x <= 41 - 4; x++) {
			fallawayWallsSquares1.add(Level.squares[posX + x][posY + y]);
		}
		y = 28 - 4;
		for (int x = 37 - 4; x <= 42 - 4; x++) {
			fallawayWallsSquares1.add(Level.squares[posX + x][posY + y]);
		}
		y = 27 - 4;
		for (int x = 37 - 4; x <= 43 - 4; x++) {
			fallawayWallsSquares1.add(Level.squares[posX + x][posY + y]);
		}

		for (Square fallawayWallsSquare1 : fallawayWallsSquares1) {
			fallawayWalls1.add(Templates.WALL.makeCopy(fallawayWallsSquare1, null));
		}

		GameObject woodenSupport1 = Templates.WOODEN_SUPPORT.makeCopy(Level.squares[posX + 36 - 4][posY + 33 - 4], null,
				fallawayWalls1);
		woodenSupport1.backwards = true;

		// fallaway walls 2
		ArrayList<Square> fallawayWallsSquares2 = new ArrayList<Square>();
		ArrayList<GameObject> fallawayWalls2 = new ArrayList<GameObject>();
		y = 33 - 4;
		for (int x = 51 - 4; x <= 51 - 4; x++) {
			fallawayWallsSquares2.add(Level.squares[posX + x][posY + y]);
		}
		y = 32 - 4;
		for (int x = 50 - 4; x <= 51 - 4; x++) {
			fallawayWallsSquares2.add(Level.squares[posX + x][posY + y]);
		}
		y = 31 - 4;
		for (int x = 49 - 4; x <= 51 - 4; x++) {
			fallawayWallsSquares2.add(Level.squares[posX + x][posY + y]);
		}
		y = 30 - 4;
		for (int x = 48 - 4; x <= 51 - 4; x++) {
			fallawayWallsSquares2.add(Level.squares[posX + x][posY + y]);
		}
		y = 29 - 4;
		for (int x = 47 - 4; x <= 51 - 4; x++) {
			fallawayWallsSquares2.add(Level.squares[posX + x][posY + y]);
		}
		y = 28 - 4;
		for (int x = 46 - 4; x <= 51 - 4; x++) {
			fallawayWallsSquares2.add(Level.squares[posX + x][posY + y]);
		}
		y = 27 - 4;
		for (int x = 45 - 4; x <= 51 - 4; x++) {
			fallawayWallsSquares2.add(Level.squares[posX + x][posY + y]);
		}

		for (Square fallawayWallsSquare2 : fallawayWallsSquares2) {
			fallawayWalls2.add(Templates.WALL.makeCopy(fallawayWallsSquare2, null));
		}

		Templates.WOODEN_SUPPORT.makeCopy(Level.squares[posX + 52 - 4][posY + 28 - 4], null, fallawayWalls2);

		// wall inits

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
