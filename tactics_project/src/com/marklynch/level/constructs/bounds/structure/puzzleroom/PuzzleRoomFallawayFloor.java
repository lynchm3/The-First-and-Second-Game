package com.marklynch.level.constructs.bounds.structure.puzzleroom;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Switch;
import com.marklynch.objects.SwitchListener;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;

public class PuzzleRoomFallawayFloor extends StructureRoom implements SwitchListener {
	int posX;
	int posY;
	final static int totalWidthInSquares = 30;
	final static int totalHeightInSquares = 30;
	boolean floorFallenAway = false;
	ArrayList<Square> squaresToFallaway = new ArrayList<Square>();
	int safeBorderWidthOnLeft = 5;
	int safeBorderWidthOnRight = 5;
	Square voidSquare;

	public PuzzleRoomFallawayFloor(int posX, int posY) {
		super("Fallaway floor", posX, posY, false, false, new ArrayList<Actor>(), 1, false, new Node[] {},
				new RoomPart[] {
						new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;

		Templates.PRESSURE_PLATE.makeCopy(Game.level.squares[posX + 1][posY + 1], null, Switch.SWITCH_TYPE.OPEN_CLOSE,
				5, this);

		for (int i = posX + safeBorderWidthOnLeft; i < posX + totalWidthInSquares - safeBorderWidthOnRight; i++) {
			for (int j = posY; j < posY + totalHeightInSquares; j++) {
				squaresToFallaway.add(Game.level.squares[i][j]);
			}
		}

		voidSquare = Game.level.squares[posX + 1][posY + totalHeightInSquares / 2 - 2];
	}

	@Override
	public void zwitch(Switch zwitch) {
		// bridgeVertical = !bridgeVertical;
		// if (bridgeVertical)
		// activeBridgeSquares = verticalBridgeSquares;
		// else
		// activeBridgeSquares = horizontalBridgeSquares;
		fallawayTheFloor();
	}

	public void fallawayTheFloor() {

		if (floorFallenAway == true)
			return;

		for (Square square : squaresToFallaway) {
			square.inventory.add(Templates.VOID_HOLE.makeCopy(null, null, voidSquare));
			square.imageTexture = Square.VOID_SQUARE;
			// square.imageTexture = Square.BLACK???
		}

		floorFallenAway = true;

	}

}
