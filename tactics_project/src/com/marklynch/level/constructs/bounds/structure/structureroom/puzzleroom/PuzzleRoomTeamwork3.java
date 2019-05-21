package com.marklynch.level.constructs.bounds.structure.structureroom.puzzleroom;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.StructureFeature;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.RemoteDoor;
import com.marklynch.objects.inanimateobjects.Switch;
import com.marklynch.objects.inanimateobjects.Wall;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.ArrayList;

public class PuzzleRoomTeamwork3 extends StructureRoom {
	int posX;
	int posY;
	final static int totalWidthInSquares = 20;
	final static int totalHeightInSquares = 10;

	public PuzzleRoomTeamwork3(int posX, int posY) {
		super("Hallway", posX, posY, false, false, new ArrayList<Actor>(Actor.class), 1, false, new RoomPart[] { new RoomPart(posX, posY, posX + 4, posY + totalHeightInSquares - 1),
				new RoomPart(posX + 6, posY, posX + 9, posY + totalHeightInSquares - 1),
				new RoomPart(posX + 11, posY, posX + 14, posY + totalHeightInSquares - 1), new RoomPart(
						posX + 16, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;

		GameObject rock = Templates.ROCK.makeCopy(Game.level.squares[posX + 1][posY + 4], null);

		RemoteDoor remoteDoor1 = Templates.REMOTE_DOOR.makeCopy("Remote Door", Level.squares[posX + 5][posY + 5], false,
				null);
		RemoteDoor remoteDoor2 = Templates.REMOTE_DOOR.makeCopy("Remote Door", Level.squares[posX + 10][posY + 5],
				false, null);
		RemoteDoor remoteDoor3 = Templates.REMOTE_DOOR.makeCopy("Remote Door", Level.squares[posX + 15][posY + 5],
				false, null);

		Wall wall1 = Templates.WALL_CAVE.makeCopy(Level.squares[posX + 2][posY + 1], null);
		Wall wall2 = Templates.WALL_CAVE.makeCopy(Level.squares[posX + 2][posY + 2], null);
		Wall wall3 = Templates.WALL_CAVE.makeCopy(Level.squares[posX + 2][posY + 3], null);
		Wall wall4 = Templates.WALL_CAVE.makeCopy(Level.squares[posX + 2][posY + 4], null);

		Wall wall5 = Templates.WALL_CAVE.makeCopy(Level.squares[posX + 8][posY + 3], null);
		Wall wall6 = Templates.WALL_CAVE.makeCopy(Level.squares[posX + 8][posY + 4], null);
		Wall wall7 = Templates.WALL_CAVE.makeCopy(Level.squares[posX + 8][posY + 5], null);

		Wall wall8 = Templates.WALL_CAVE.makeCopy(Level.squares[posX + 12][posY + 8], null);
		Wall wall9 = Templates.WALL_CAVE.makeCopy(Level.squares[posX + 13][posY + 8], null);
		Wall wall10 = Templates.WALL_CAVE.makeCopy(Level.squares[posX + 14][posY + 8], null);

		features.add(new StructureFeature(remoteDoor1));
		features.add(new StructureFeature(remoteDoor2));
		features.add(new StructureFeature(remoteDoor3));
		extraWalls.add(wall1);
		extraWalls.add(wall2);
		extraWalls.add(wall3);
		extraWalls.add(wall4);
		extraWalls.add(wall5);
		extraWalls.add(wall6);
		extraWalls.add(wall7);
		extraWalls.add(wall8);
		extraWalls.add(wall9);
		extraWalls.add(wall10);

		Templates.PRESSURE_PLATE_REQUIRING_SPECIFIC_ITEM.makeCopy(Level.squares[posX + 0][posY + 0], null,
				Switch.SWITCH_TYPE.OPEN_CLOSE, rock, remoteDoor1);

		Templates.PRESSURE_PLATE_REQUIRING_SPECIFIC_ITEM.makeCopy(Level.squares[posX + 7][posY + 4], null,
				Switch.SWITCH_TYPE.OPEN_CLOSE, rock, remoteDoor2);

		Templates.PRESSURE_PLATE_REQUIRING_SPECIFIC_ITEM.makeCopy(Level.squares[posX + 12][posY + 9], null,
				Switch.SWITCH_TYPE.OPEN_CLOSE, rock, remoteDoor3);

		Templates.BLIND.makeCopy(Level.squares[posX + 18][posY + 5], Level.factions.blind, 23, this,
				new GameObject[] {}, new GameObject[] {});

	}
}
