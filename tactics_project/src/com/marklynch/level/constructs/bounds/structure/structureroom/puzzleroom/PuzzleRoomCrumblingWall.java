package com.marklynch.level.constructs.bounds.structure.structureroom.puzzleroom;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.ArrayList;

public class PuzzleRoomCrumblingWall extends StructureRoom {
	int posX;
	int posY;
	final static int totalWidthInSquares = 5;
	final static int totalHeightInSquares = 5;
	GameObject woodenSupport;

	public PuzzleRoomCrumblingWall(int posX, int posY) {
		super("Crumbling Wall Room", posX, posY, false, false, new ArrayList<Actor>(Actor.class), 1, false,
				new RoomPart[] {
						new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;

		ArrayList<GameObject> walls = new ArrayList<GameObject>(GameObject.class);
		walls.add(Templates.WALL_CAVE.makeCopy(Level.squares[posX - 1][posY], null));
		walls.add(Templates.WALL_CAVE.makeCopy(Level.squares[posX - 1][posY + 1], null));
		walls.add(Templates.WALL_CAVE.makeCopy(Level.squares[posX - 1][posY + 2], null));
		walls.add(Templates.WALL_CAVE.makeCopy(Level.squares[posX - 1][posY + 3], null));
		walls.add(Templates.WALL_CAVE.makeCopy(Level.squares[posX - 1][posY + 4], null));

		woodenSupport = Templates.WOODEN_SUPPORT.makeCopy(Level.squares[posX][posY + 2], null, walls);
	}

}
