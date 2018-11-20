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

		Level.squares[this.posX + 1][this.posY + 1].floorImageTexture = Square.GRASS_TEXTURE;
		Level.squares[this.posX + 2][this.posY + 1].floorImageTexture = Square.GRASS_TEXTURE;
		Level.squares[this.posX + 3][this.posY + 1].floorImageTexture = Square.GRASS_TEXTURE;
		Level.squares[this.posX + 4][this.posY + 1].floorImageTexture = Square.GRASS_TEXTURE;
		Level.squares[this.posX + 5][this.posY + 1].floorImageTexture = Square.GRASS_TEXTURE;
	}
}
