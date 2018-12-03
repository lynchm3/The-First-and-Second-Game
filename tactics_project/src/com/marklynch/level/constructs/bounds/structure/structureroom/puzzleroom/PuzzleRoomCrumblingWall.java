package com.marklynch.level.constructs.bounds.structure.structureroom.puzzleroom;

import java.util.ArrayList;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.squares.Node;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Wall;
import com.marklynch.objects.templates.Templates;

public class PuzzleRoomCrumblingWall extends StructureRoom {
	int posX;
	int posY;
	final static int totalWidthInSquares = 5;
	final static int totalHeightInSquares = 5;
	GameObject woodenSupport;

	public PuzzleRoomCrumblingWall(int posX, int posY) {
		super("Crumbling Wall Room", posX, posY, false, false, new ArrayList<Actor>(), 1, false, new Node[] {},
				new RoomPart[] {
						new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;
		// Wall wall = Templates.WALL.makeCopy(Level.squares[posX - 1][posY + 2], null);
		woodenSupport = Templates.WOODEN_SUPPORT.makeCopy(Level.squares[posX][posY + 2], null,
				Level.squares[posX - 1][posY].inventory.getGameObjectOfClass(Wall.class),
				Level.squares[posX - 1][posY + 1].inventory.getGameObjectOfClass(Wall.class),
				Level.squares[posX - 1][posY + 2].inventory.getGameObjectOfClass(Wall.class),
				Level.squares[posX - 1][posY + 3].inventory.getGameObjectOfClass(Wall.class),
				Level.squares[posX - 1][posY + 4].inventory.getGameObjectOfClass(Wall.class));
	}

}
