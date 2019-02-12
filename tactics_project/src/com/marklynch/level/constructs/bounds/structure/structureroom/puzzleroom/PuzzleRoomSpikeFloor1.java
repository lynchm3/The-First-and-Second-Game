package com.marklynch.level.constructs.bounds.structure.structureroom.puzzleroom;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.squares.Node;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.Spikes;
import com.marklynch.objects.inanimateobjects.Switch;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.ArrayList;

public class PuzzleRoomSpikeFloor1 extends StructureRoom {
	int posX;
	int posY;
	final static int totalWidthInSquares = 21;
	final static int totalHeightInSquares = 6;

	public PuzzleRoomSpikeFloor1(int posX, int posY) {
		super("Cave In Room", posX, posY, false, false, new ArrayList<Actor>(Actor.class), 1, false, new Node[] {},
				new RoomPart[] {
						new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;

		Spikes spikeFloor1 = Templates.SPIKE_FLOOR.makeCopy(Level.squares[this.posX + 2][this.posY + 3], null, 1);

		Templates.PRESSURE_PLATE.makeCopy(Level.squares[this.posX + 1][this.posY + 3], null,
				Switch.SWITCH_TYPE.OPEN_CLOSE, 5, spikeFloor1);

		Spikes spikeFloor2 = Templates.SPIKE_FLOOR.makeCopy(Level.squares[this.posX + 2][this.posY + 2], null, 1);
		spikeFloor2.zwitch(null);

		Templates.PRESSURE_PLATE.makeCopy(Level.squares[this.posX + 1][this.posY + 2], null,
				Switch.SWITCH_TYPE.OPEN_CLOSE, 5, spikeFloor2);

		Spikes spikeFloor3 = Templates.SPIKE_FLOOR.makeCopy(Level.squares[this.posX + 2][this.posY + 4], null, 1);
		spikeFloor3.zwitch(null);

		Templates.PRESSURE_PLATE.makeCopy(Level.squares[this.posX + 2][this.posY + 4], null,
				Switch.SWITCH_TYPE.OPEN_CLOSE, 5, spikeFloor3);

		Spikes spikeWallDown = Templates.SPIKE_WALL_DOWN.makeCopy(Level.squares[this.posX + 5][this.posY + 0], null, 1);
		spikeWallDown.zwitch(null);

		Templates.PRESSURE_PLATE.makeCopy(Level.squares[this.posX + 2][this.posY + 5], null,
				Switch.SWITCH_TYPE.OPEN_CLOSE, 5, spikeWallDown);

	}

}
