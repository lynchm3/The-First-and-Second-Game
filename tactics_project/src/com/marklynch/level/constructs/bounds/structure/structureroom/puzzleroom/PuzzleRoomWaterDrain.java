package com.marklynch.level.constructs.bounds.structure.structureroom.puzzleroom;

import com.marklynch.Game;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Switch;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.utils.ArrayList;

public class PuzzleRoomWaterDrain extends StructureRoom implements SwitchListener {

	int posX;
	int posY;

	final static int totalWidthInSquares = 6;
	final static int totalHeightInSquares = 9;

	int windowWallY = 5;

	int inputDrainX = 1;
	int inputDrainY = 7;

	int outputDrainX = 1;
	int outputDrainY = 1;

	int electricalX = 1;
	int electricalY = 0;

	int pressurePlateX = 3;
	int pressurePlateY = 1;

	public PuzzleRoomWaterDrain(int posX, int posY) {
		super("Cave In Room", posX, posY, false, false, new ArrayList<Actor>(Actor.class), 1, false, new Node[] {},
				new RoomPart[] {
						new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;

		for (int i = 0; i < totalWidthInSquares; i++) {
			Templates.WALL_WINDOW.makeCopy(Game.level.squares[posX + i][posY + windowWallY], null);
			Game.level.squares[posX + i][posY + windowWallY].setFloorImageTexture(Square.STONE_TEXTURE);
		}

		GameObject inputDrain = Templates.INPUT_DRAIN.makeCopy(
				Game.level.squares[posX + inputDrainX][posY + inputDrainY], null,
				Game.level.squares[posX + outputDrainX][posY + outputDrainY]);

		GameObject outputDrain = Templates.DRAIN_FLOOR
				.makeCopy(Game.level.squares[posX + outputDrainX][posY + outputDrainY], null);

		inputDrain.linkedObjects.add(outputDrain);
		outputDrain.linkedObjects.add(inputDrain);

		Templates.ELECTRICAL_WIRING.makeCopy(Game.level.squares[posX + electricalX][posY + electricalY], null);

		Templates.PRESSURE_PLATE.makeCopy(Game.level.squares[posX + pressurePlateX][posY + pressurePlateY], null,
				Switch.SWITCH_TYPE.OPEN_CLOSE, 1, this);
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

	@Override
	public void zwitch(Switch zwitch) {
		System.out.println("switch");
	}

}
