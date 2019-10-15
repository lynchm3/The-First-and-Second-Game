package com.marklynch.level.constructs.bounds.structure.structureroom.puzzleroom;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Actor.Direction;
import com.marklynch.objects.inanimateobjects.ConveyerBelt;
import com.marklynch.objects.inanimateobjects.Switch;
import com.marklynch.objects.inanimateobjects.Switch.SWITCH_TYPE;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.utils.CopyOnWriteArrayList;

public class PuzzleRoomConveyerBelt extends StructureRoom implements SwitchListener {

	int posX;
	int posY;
	final static int totalWidthInSquares = 14;
	final static int totalHeightInSquares = 8;

	CopyOnWriteArrayList<Square> extendedBridgeSquares = new CopyOnWriteArrayList<Square>(Square.class);

	Square voidSquare;

	CopyOnWriteArrayList<ConveyerBelt> conveyerBelts = new CopyOnWriteArrayList<ConveyerBelt>(ConveyerBelt.class);

	public PuzzleRoomConveyerBelt(int posX, int posY) {
		super("Bridge Room", posX, posY, false, false, new CopyOnWriteArrayList<Actor>(Actor.class), 1, false, new RoomPart[] {
				new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;

//		conveyerBelts.add(Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 1][posY+1]], null, Direction.RIGHT));
		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 2][posY + 1], null, Direction.RIGHT);
		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 3][posY + 1], null, Direction.RIGHT);
		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 4][posY + 1], null, Direction.RIGHT);
		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 5][posY + 1], null, Direction.RIGHT);
		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 6][posY + 1], null, Direction.RIGHT);
//		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 7][posY + 1], null, Direction.RIGHT);
//		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 8][posY + 1], null, Direction.RIGHT);
//		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 9][posY + 1], null, Direction.RIGHT);
//		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 10][posY + 1], null, Direction.RIGHT);
//		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 11][posY + 1], null, Direction.RIGHT);
//		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 12][posY + 1], null, Direction.RIGHT);

//		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 1][posY + 1], null, Direction.LEFT);
//		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 2][posY + 1], null, Direction.LEFT);
//		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 3][posY + 1], null, Direction.LEFT);
//		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 4][posY + 1], null, Direction.LEFT);
//		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 5][posY + 1], null, Direction.LEFT);
//		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 6][posY + 1], null, Direction.LEFT);
//		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 7][posY + 1], null, Direction.LEFT);
//		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 8][posY + 1], null, Direction.LEFT);
//		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 9][posY + 1], null, Direction.LEFT);
//		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 10][posY + 1], null, Direction.LEFT);
//		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 11][posY + 1], null, Direction.LEFT);
//		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 12][posY + 1], null, Direction.LEFT);
//
//		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 13][posY], null, Direction.DOWN);
//		Templates.CONVEYER_BELT.makeCopy(Level.squares[posX + 13][posY + 1], null, Direction.DOWN);

		Templates.PRESSURE_PLATE.makeCopy(Level.squares[posX + 4][posY + 4], null, SWITCH_TYPE.ON_OFF, 1,
				conveyerBelts.toArray(new ConveyerBelt[conveyerBelts.size()]));
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void zwitch(Switch zwitch) {
		for (ConveyerBelt conveyerBelt : conveyerBelts) {

		}

	}

}
