package com.marklynch.level.constructs.bounds.structure.structureroom.puzzleroom;

import com.marklynch.Game;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.ArrayList;

public class PuzzleRoomWaterDrain extends StructureRoom {
	int posX;
	int posY;
	final static int totalWidthInSquares = 6;
	final static int totalHeightInSquares = 9;

	public PuzzleRoomWaterDrain(int posX, int posY) {
		super("Cave In Room", posX, posY, false, false, new ArrayList<Actor>(Actor.class), 1, false, new Node[] {},
				new RoomPart[] {
						new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;

		int windowWallY = 5;
		for (int i = 0; i < totalWidthInSquares; i++) {
			Templates.WALL_WINDOW.makeCopy(Game.level.squares[posX + i][posY + windowWallY], null);
			Game.level.squares[posX + i][posY + windowWallY].setFloorImageTexture(Square.STONE_TEXTURE);
		}

		Templates.INPUT_DRAIN.makeCopy(Game.level.squares[posX + 2][posY + 7], null,
				Game.level.squares[posX + 2][posY + 2]);

		Templates.DRAIN_FLOOR.makeCopy(Game.level.squares[posX + 2][posY + 1], null);
//
//		Spikes spikeFloor1 = Templates.SPIKE_FLOOR.makeCopy(Level.squares[this.posX + 2][this.posY + 3], null, 1);
//
//		Templates.PRESSURE_PLATE.makeCopy(Level.squares[this.posX + 1][this.posY + 3], null,
//				Switch.SWITCH_TYPE.OPEN_CLOSE, 5, spikeFloor1);
//
//		Spikes spikeFloor2 = Templates.SPIKE_FLOOR.makeCopy(Level.squares[this.posX + 2][this.posY + 2], null, 1);
//		spikeFloor2.zwitch(null);
//
//		Templates.PRESSURE_PLATE.makeCopy(Level.squares[this.posX + 1][this.posY + 2], null,
//				Switch.SWITCH_TYPE.OPEN_CLOSE, 5, spikeFloor2);
//
//		Spikes spikeFloor3 = Templates.SPIKE_FLOOR.makeCopy(Level.squares[this.posX + 2][this.posY + 4], null, 1);
//		spikeFloor3.zwitch(null);
//
//		Templates.PRESSURE_PLATE.makeCopy(Level.squares[this.posX + 2][this.posY + 4], null,
//				Switch.SWITCH_TYPE.OPEN_CLOSE, 5, spikeFloor3);
//
//		Spikes spikeWallDown = Templates.SPIKE_WALL_DOWN.makeCopy(Level.squares[this.posX + 5][this.posY + 0], null, 1);
//		spikeWallDown.zwitch(null);
//
//		Templates.PRESSURE_PLATE.makeCopy(Level.squares[this.posX + 2][this.posY + 5], null,
//				Switch.SWITCH_TYPE.OPEN_CLOSE, 5, spikeWallDown);

	}

}
