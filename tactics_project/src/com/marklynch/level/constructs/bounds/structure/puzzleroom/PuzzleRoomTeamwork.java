package com.marklynch.level.constructs.bounds.structure.puzzleroom;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.squares.Node;
import com.marklynch.objects.GameObject;
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
		super("Fallaway floor", posX, posY, false, false, new ArrayList<Actor>(), 1, false, new Node[] {},
				new RoomPart[] {
						new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;

		GameObject rock = Templates.ROCK.makeCopy(Game.level.squares[posX + 2][posY + 2], null);

		RemoteDoor remoteDoor1 = Templates.REMOTE_DOOR.makeCopy("Remote Door", Game.level.squares[posX + 5][posY + 5],
				false, null);
		RemoteDoor remoteDoor2 = Templates.REMOTE_DOOR.makeCopy("Remote Door", Game.level.squares[posX + 10][posY + 5],
				false, null);
		remoteDoor2.open();
		RemoteDoor remoteDoor3 = Templates.REMOTE_DOOR.makeCopy("Remote Door", Game.level.squares[posX + 15][posY + 5],
				false, null);

		Templates.PRESSURE_PLATE_REQUIRING_SPECIFIC_ITEM.makeCopy(
				Game.level.squares[posX + 1][posY + totalHeightInSquares / 2], null, Switch.SWITCH_TYPE.OPEN_CLOSE,
				rock, remoteDoor1, remoteDoor2);

	}
}
