package com.marklynch.level.constructs.bounds.structure.structureroom.puzzleroom;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.bounds.structure.StructureFeature;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.Vein;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.ArrayList;

public class PuzzleRoomMineThroughWall extends StructureRoom {
	int posX;
	int posY;
	final static int totalWidthInSquares = 5;
	final static int totalHeightInSquares = 10;

	public PuzzleRoomMineThroughWall(int posX, int posY) {
		super("Hallway", posX, posY, false, false, new ArrayList<Actor>(Actor.class), 1, false, new RoomPart[] {
				new RoomPart(posX, posY, posX + totalWidthInSquares - 1, posY + totalHeightInSquares - 1) });

		this.posX = posX;
		this.posY = posY;

		// GameObject rock = Templates.ROCK.makeCopy(Level.squares[posX + 2][posY +
		// 2], null);

		Vein vein1 = Templates.VEIN.makeCopy(Level.squares[posX + 0][posY + 0], null, false, Templates.ORE, 0.5f);
		Vein vein2 = Templates.VEIN.makeCopy(Level.squares[posX + 1][posY + 0], null, false, Templates.ORE, 0.5f);
		Vein vein3 = Templates.VEIN.makeCopy(Level.squares[posX + 2][posY + 0], null, false, Templates.ORE, 0.5f);
		Vein vein4 = Templates.VEIN.makeCopy(Level.squares[posX + 3][posY + 0], null, false, Templates.ORE, 0.5f);
		Vein vein5 = Templates.VEIN.makeCopy(Level.squares[posX + 4][posY + 0], null, false, Templates.ORE, 0.5f);
		Vein vein6 = Templates.VEIN.makeCopy(Level.squares[posX + 1][posY + 1], null, false, Templates.ORE, 0.5f);
		Vein vein7 = Templates.VEIN.makeCopy(Level.squares[posX + 2][posY + 1], null, false, Templates.ORE, 0.5f);
		Vein vein8 = Templates.VEIN.makeCopy(Level.squares[posX + 3][posY + 1], null, false, Templates.ORE, 0.5f);
		Vein vein9 = Templates.VEIN.makeCopy(Level.squares[posX + 4][posY + 1], null, false, Templates.ORE, 0.5f);
		Vein vein10 = Templates.VEIN.makeCopy(Level.squares[posX + 0][posY + 2], null, false, Templates.ORE, 0.5f);
		Vein vein11 = Templates.VEIN.makeCopy(Level.squares[posX + 1][posY + 2], null, false, Templates.ORE, 0.5f);
		Vein vein12 = Templates.VEIN.makeCopy(Level.squares[posX + 4][posY + 2], null, false, Templates.ORE, 0.5f);

		features.add(new StructureFeature(vein1));
		features.add(new StructureFeature(vein2));
		features.add(new StructureFeature(vein3));
		features.add(new StructureFeature(vein4));
		features.add(new StructureFeature(vein5));
		features.add(new StructureFeature(vein6));
		features.add(new StructureFeature(vein7));
		features.add(new StructureFeature(vein8));
		features.add(new StructureFeature(vein9));
		features.add(new StructureFeature(vein10));
		features.add(new StructureFeature(vein11));
		features.add(new StructureFeature(vein12));

	}
}
