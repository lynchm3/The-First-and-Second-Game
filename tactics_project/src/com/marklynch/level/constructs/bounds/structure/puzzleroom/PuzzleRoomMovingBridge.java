package com.marklynch.level.constructs.bounds.structure.puzzleroom;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Switch;
import com.marklynch.objects.SwitchListener;
import com.marklynch.objects.templates.Templates;
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

	boolean bridgeVertical = false;
	static int posX = 100;
	static int posY = 50;
	static int totalWidthInSquares = 10;
	static int totalHeightInSquares = totalWidthInSquares;
	int bridgeWidth = 2;
	int bridgeLength = totalWidthInSquares - 2;
	int bridgePosX = posX + 1;
	int bridgePosY = posY + 1;
	int gapsWidth = (totalWidthInSquares - bridgeWidth) / 2;
	int bridgeConnectorsWidth = (totalWidthInSquares - bridgeWidth) / 2;

	public PuzzleRoomMovingBridge() {
		super("Bridge Room", posX, posY, false, new ArrayList<Actor>(), 1, false, new Node[] {},
				new RoomPart[] { new RoomPart(posX, posY, posX + totalWidthInSquares, posY + totalHeightInSquares) });

		// Templates.ANTLERS_SWITCH.makeCopy(Game.level.squares[posX][posY - 1], null,
		// this, Switch.SWITCH_TYPE.OPEN_CLOSE,
		// new RequirementToMeet[] { new
		// StatRequirementToMeet(HIGH_LEVEL_STATS.STRENGTH, 1) });
		Templates.PRESSURE_PLATE.makeCopy(Game.level.squares[posX][posY - 1], null, this, Switch.SWITCH_TYPE.OPEN_CLOSE,
				5);
		setup();

	}

	@Override
	public void zwitch(Switch zwitch) {
		// TODO Auto-generated method stub
		bridgeVertical = !bridgeVertical;
		setup();

	}

	public void setup() {

		for (int i = posX; i < posX + totalWidthInSquares; i++) {

			for (int j = posY; j < posY + totalHeightInSquares; j++) {
				Level.squares[i][j].imageTexture = Square.WHITE_SQUARE;
				if (Level.squares[i][j].inventory.containsObjectWithTemplateId(Templates.VOID_HOLE.templateId)) {

				} else {
					Level.squares[i][j].inventory.add(Templates.VOID_HOLE.makeCopy(null, null));
				}
			}
		}

		Square square;
		// bridge connector parts on the edges
		for (int i = 0; i < bridgeWidth; i++) {
			// left

			square = Level.squares[posX][posY + gapsWidth + i];
			square.imageTexture = Square.MUD_TEXTURE;
			if (square.inventory.containsObjectWithTemplateId(Templates.VOID_HOLE.templateId)) {
				square.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			}

			// Level.squares[posX][posY + gapsWidth + i].passable = true;
			// right
			square = Level.squares[posX + totalWidthInSquares - 1][posY + gapsWidth + i];
			square.imageTexture = Square.MUD_TEXTURE;
			if (square.inventory.containsObjectWithTemplateId(Templates.VOID_HOLE.templateId)) {
				square.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			}
			// Level.squares[posX + totalWidthInSquares - 1][posY + gapsWidth + i].passable
			// = true;
			// top
			square = Level.squares[posX + gapsWidth + i][posY];
			square.imageTexture = Square.MUD_TEXTURE;
			if (square.inventory.containsObjectWithTemplateId(Templates.VOID_HOLE.templateId)) {
				square.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			}
			// Level.squares[posX + gapsWidth + i][posY].passable = true;
			// bottom
			square = Level.squares[posX + gapsWidth + i][posY + totalHeightInSquares - 1];
			square.imageTexture = Square.MUD_TEXTURE;
			if (square.inventory.containsObjectWithTemplateId(Templates.VOID_HOLE.templateId)) {
				square.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
			}
			// Level.squares[posX + gapsWidth + i][posY + totalHeightInSquares - 1].passable
			// = true;
		}

		// bridge
		if (bridgeVertical) {
			for (int i = 0; i < bridgeWidth; i++) {
				for (int j = 0; j < bridgeLength; j++) {
					square = Level.squares[posX + gapsWidth + i][bridgePosY + j];
					square.imageTexture = Square.MUD_TEXTURE;
					if (square.inventory.containsObjectWithTemplateId(Templates.VOID_HOLE.templateId)) {
						square.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
					}
					// Level.squares[bridgePosX + i][posY + gapsWidth + j].passable = true;
				}

			}
		} else {
			for (int i = 0; i < bridgeWidth; i++) {
				for (int j = 0; j < bridgeLength; j++) {
					square = Level.squares[bridgePosX + j][posY + gapsWidth + i];
					square.imageTexture = Square.MUD_TEXTURE;
					if (square.inventory.containsObjectWithTemplateId(Templates.VOID_HOLE.templateId)) {
						square.inventory.removeObjecstWithTemplateId(Templates.VOID_HOLE.templateId);
					}
					// Level.squares[bridgePosX + j][posY + gapsWidth + i].passable = true;
				}

			}
		}
	}

}
