package com.marklynch.level.constructs.bounds.structure.structureroom.puzzleroom;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.primary.AnimationLowerInToGround;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.AttackableSwitch;
import com.marklynch.objects.inanimateobjects.ElectricalWiring;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.PressurePlate;
import com.marklynch.objects.inanimateobjects.Switch;
import com.marklynch.objects.inanimateobjects.Switch.SWITCH_TYPE;
import com.marklynch.objects.inanimateobjects.Wall;
import com.marklynch.objects.inanimateobjects.WaterSource;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.utils.CopyOnWriteArrayList;

public class PuzzleRoomWaterDrain extends StructureRoom implements SwitchListener {

	int posX;
	int posY;

	final static int totalWidthInSquares = 6;
	final static int totalHeightInSquares = 9;

	int windowWallY = 5;

	int inputDrainX = 3;
	int inputDrainY = 7;

	int outputDrainX = 3;
	int outputDrainY = 1;

	int electricalX = 3;
	int electricalY = 0;

	int pressurePlateX = 5;
	int pressurePlateY = 1;

	int attackableSwitchX = 0;
	int attackableSwitchY = 0;

	int wellX = 6;
	int wellY = 7;

	CopyOnWriteArrayList<GameObject> glassWalls = new CopyOnWriteArrayList<GameObject>(GameObject.class);
	PressurePlate pressurePlate;

	public PuzzleRoomWaterDrain(int posX, int posY) {
		super("Cave In Room", posX, posY, false, false, new CopyOnWriteArrayList<Actor>(Actor.class), 1, false, new RoomPart[] {
				new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;
		pressurePlate = Templates.PRESSURE_PLATE.makeCopy(Level.squares[posX + pressurePlateX][posY + pressurePlateY],
				null, Switch.SWITCH_TYPE.OPEN_CLOSE, 1, this);
		for (int i = 0; i < totalWidthInSquares; i++) {
			Wall glassWall = Templates.WALL_GLASS.makeCopy(Level.squares[posX + i][posY + windowWallY], null);
			glassWalls.add(glassWall);
			glassWall.linkedObjects.add(pressurePlate);
			Game.level.squares[posX + i][posY + windowWallY].setFloorImageTexture(Square.STONE_TEXTURE);
		}

		pressurePlate.linkedObjects.add(glassWalls.get(glassWalls.size() - 1));

		GameObject inputDrain = Templates.INPUT_DRAIN.makeCopy(
				Game.level.squares[posX + inputDrainX][posY + inputDrainY], null,
				Game.level.squares[posX + outputDrainX][posY + outputDrainY]);

		GameObject outputDrain = Templates.DRAIN_FLOOR
				.makeCopy(Game.level.squares[posX + outputDrainX][posY + outputDrainY], null);

		inputDrain.linkedObjects.add(outputDrain);
		outputDrain.linkedObjects.add(inputDrain);

		ElectricalWiring eletricalWiring = Templates.ELECTRICAL_WIRING
				.makeCopy(Game.level.squares[posX + electricalX][posY + electricalY], null);

		WaterSource well = Templates.WELL.makeCopy(Game.level.squares[posX + wellX][posY + wellY], null);
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, null, 1));
		well.inventory.add(Templates.SWORD.makeCopy(null, null));
		Templates.WATER.makeCopy(Game.level.squares[posX + wellX - 1][posY + wellY], null);

		// Left hand side, water and switch
		AttackableSwitch attackableSwitch = Templates.ATTACKABLE_SWITCH.makeCopy(
				Level.squares[posX + attackableSwitchX][posY + attackableSwitchY], null, SWITCH_TYPE.ON_OFF,
				eletricalWiring);

		Templates.WATER.makeCopy(Level.squares[posX - 1][posY + 1], null);
		Templates.WATER.makeCopy(Level.squares[posX + 0][posY + 0], null);
		Templates.WATER.makeCopy(Level.squares[posX + 0][posY + 1], null);
		Templates.WATER.makeCopy(Level.squares[posX + 0][posY + 2], null);
		Templates.WATER.makeCopy(Level.squares[posX + 0][posY + 3], null);
		Templates.WATER.makeCopy(Level.squares[posX + 1][posY + 1], null);
		Templates.WATER.makeCopy(Level.squares[posX + 1][posY + 2], null);
	}

//	boolean switchTriggered = false;

	@Override
	public void zwitch(Switch zwitch) {
		if (zwitch instanceof PressurePlate) {
//			if (switchTriggered)
//				return;
//			switchTriggered = true;

			for (GameObject glassWall : glassWalls) {
				glassWall.setPrimaryAnimation(new AnimationLowerInToGround(glassWall, null) {

				});

			}
		}
	}

}
