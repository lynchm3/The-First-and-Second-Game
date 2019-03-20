package com.marklynch.level.constructs.bounds.structure.structureroom.puzzleroom;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.squares.Node;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Actor.Direction;
import com.marklynch.objects.inanimateobjects.Fuse;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.ArrayList;

public class PuzzleRoomFuse extends StructureRoom {
	int posX;
	int posY;
	final static int totalWidthInSquares = 21;
	final static int totalHeightInSquares = 7;

	public PuzzleRoomFuse(int posX, int posY) {
		super("Cave In Room", posX, posY, false, false, new ArrayList<Actor>(Actor.class), 1, false, new Node[] {},
				new RoomPart[] {
						new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;

		GameObject crate = Templates.EXPLOSIVE_CRATE.makeCopy(Level.squares[posX + 4][posY + 3], null);

		Templates.FUSE.makeCopy(Level.squares[posX + 2][posY + 2], null, Direction.LEFT, Direction.RIGHT, true);
		Templates.FUSE.makeCopy(Level.squares[posX + 3][posY + 2], null, Direction.LEFT, Direction.RIGHT, false);
		Templates.FUSE.makeCopy(Level.squares[posX + 4][posY + 2], null, Direction.LEFT, Direction.RIGHT, false);
		Templates.FUSE.makeCopy(Level.squares[posX + 5][posY + 2], null, Direction.LEFT, Direction.DOWN, false);
		Templates.FUSE.makeCopy(Level.squares[posX + 5][posY + 3], null, Direction.UP, Direction.DOWN, false);
		Templates.FUSE.makeCopy(Level.squares[posX + 5][posY + 4], null, Direction.UP, Direction.LEFT, false);
		Templates.FUSE.makeCopy(Level.squares[posX + 4][posY + 4], null, Direction.LEFT, Direction.RIGHT, false);
		Templates.FUSE.makeCopy(Level.squares[posX + 3][posY + 4], null, Direction.UP, Direction.RIGHT, false);
		Fuse endFuse = Templates.FUSE.makeCopy(Level.squares[posX + 3][posY + 3], null, Direction.DOWN, Direction.RIGHT,
				false);
		endFuse.connectedToExplosiveDirection = Direction.RIGHT;
		endFuse.gameObjectsToExplode.add(crate);

	}

}
