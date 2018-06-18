package com.marklynch.level.constructs.bounds.structure.puzzleroom;

import java.util.ArrayList;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Switch;
import com.marklynch.objects.SwitchListener;
import com.marklynch.objects.units.Actor;

public class PuzzleRoomMovingBridge extends StructureRoom implements SwitchListener {

	// oooxxooo
	// oooxxooo
	// oooxxooo
	// oooxxooo

	// to

	// oooooooo
	// xxxxxxxx
	// xxxxxxxx
	// oooooooo

	// When switch is hit

	// o = chasm / drop /death/ impassable
	// x = bridge

	boolean bridgeVertical = true;
	static int posX = 100;
	static int posY = 100;
	static int totalWidthInSquares = 10;
	static int totalHeightInSquares = totalWidthInSquares;
	int bridgeWidth = 2;
	int bridgeLength = totalWidthInSquares - 2;
	int bridgeAreaX = posX + 1;
	int bridgeAreaY = posY + 1;
	int gapsWidth = (bridgeLength - bridgeWidth) / 2;
	int bridgeConnectorsX = (totalWidthInSquares - bridgeWidth) / 2;

	public PuzzleRoomMovingBridge() {
		super("Bridge Room", posX, posY, false, new ArrayList<Actor>(), 1, false, null,
				new RoomPart[] { new RoomPart(posX, posY, posX + totalWidthInSquares, posY + totalHeightInSquares) });
		setup();

	}

	@Override
	public void zwitch(Switch zwitch) {
		// TODO Auto-generated method stub
		bridgeVertical = !bridgeVertical;

	}

	public void setup() {
		// bridge connector parts
		for (int i = 0; i < bridgeWidth; i++) {
			Level.squares[posX][posY + gapsWidth + i].imageTexture = Square.MUD_TEXTURE;
			Level.squares[posX + totalWidthInSquares - 1][posY + gapsWidth + i].imageTexture = Square.MUD_TEXTURE;
			Level.squares[posX + gapsWidth + i][posY].imageTexture = Square.MUD_TEXTURE;
			Level.squares[posX + gapsWidth + i][posY + totalHeightInSquares - 1].imageTexture = Square.MUD_TEXTURE;
		}
	}

}
