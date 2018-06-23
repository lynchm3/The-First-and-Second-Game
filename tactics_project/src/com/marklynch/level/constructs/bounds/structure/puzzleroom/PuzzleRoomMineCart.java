package com.marklynch.level.constructs.bounds.structure.puzzleroom;

import java.util.ArrayList;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.squares.Node;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;

public class PuzzleRoomMineCart extends StructureRoom {
	int posX;
	int posY;
	final static int totalWidthInSquares = 20;
	final static int totalHeightInSquares = 20;

	public PuzzleRoomMineCart(int posX, int posY) {
		super("Crumbling Wall Room", posX, posY, false, new ArrayList<Actor>(), 1, false, new Node[] {},
				new RoomPart[] {
						new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;

		Templates.RAIL.makeCopy(Level.squares[posX + 2][posY + 2], null, false, false, true, true);
		Templates.RAIL.makeCopy(Level.squares[posX + 3][posY + 2], null, false, false, true, true);
		Templates.RAIL.makeCopy(Level.squares[posX + 4][posY + 2], null, false, false, true, true);
		Templates.RAIL.makeCopy(Level.squares[posX + 5][posY + 2], null, false, false, true, true);
		Templates.RAIL.makeCopy(Level.squares[posX + 6][posY + 2], null, false, false, true, true);
		Templates.MINE_CART.makeCopy(Level.squares[posX + 2][posY + 2], null, false, false, false, true);
	}

}
