package com.marklynch.level.constructs.bounds.structure.puzzleroom;

import java.util.ArrayList;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.StructureFeature;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.squares.Node;
import com.marklynch.objects.RemoteDoor;
import com.marklynch.objects.Switch;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;

public class PuzzleRoomTeamwork extends StructureRoom {
	int posX;
	int posY;
	final static int totalWidthInSquares = 20;
	final static int totalHeightInSquares = 10;

	public PuzzleRoomTeamwork(int posX, int posY) {
		super("Hallway", posX, posY, false, false, new ArrayList<Actor>(), 1, false, new Node[] {}, new RoomPart[] {
				new RoomPart(posX, posY, posX + 4, posY + totalHeightInSquares - 1),
				new RoomPart(posX + 6, posY, posX + 9, posY + totalHeightInSquares - 1),
				new RoomPart(posX + 11, posY, posX + 14, posY + totalHeightInSquares - 1),
				new RoomPart(posX + 16, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;

		// GameObject rock = Templates.ROCK.makeCopy(Game.level.squares[posX + 2][posY +
		// 2], null);

		RemoteDoor remoteDoor1 = Templates.REMOTE_DOOR.makeCopy("Remote Door", Level.squares[posX + 5][posY + 5], false,
				null);
		RemoteDoor remoteDoor2 = Templates.REMOTE_DOOR.makeCopy("Remote Door", Level.squares[posX + 10][posY + 5],
				false, null);
		remoteDoor2.open();
		RemoteDoor remoteDoor3 = Templates.REMOTE_DOOR.makeCopy("Remote Door", Level.squares[posX + 15][posY + 5],
				false, null);

		features.add(new StructureFeature(remoteDoor1));
		features.add(new StructureFeature(remoteDoor2));
		features.add(new StructureFeature(remoteDoor3));

		Templates.PRESSURE_PLATE.makeCopy(Level.squares[posX + 1][posY + totalHeightInSquares / 2], null,
				Switch.SWITCH_TYPE.OPEN_CLOSE, 30, remoteDoor1, remoteDoor2, remoteDoor3);

	}
}
