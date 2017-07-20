package com.marklynch.level.quest.thesecretroom;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Path;
import com.marklynch.level.constructs.structure.Structure;
import com.marklynch.level.constructs.structure.StructurePath;
import com.marklynch.level.constructs.structure.StructureRoom;
import com.marklynch.level.constructs.structure.StructureRoom.RoomPart;
import com.marklynch.level.constructs.structure.StructureSection;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.RemoteDoor;
import com.marklynch.objects.Templates;
import com.marklynch.objects.Wall;
import com.marklynch.objects.WaterSource;
import com.marklynch.objects.units.Actor;

public class QuestTheSecretRoom {

	public QuestTheSecretRoom() {

		ArrayList<Wall> structureExtraWalls = new ArrayList<Wall>();
		ArrayList<GameObject> structureFeatures = new ArrayList<GameObject>();
		ArrayList<StructurePath> structurePaths = new ArrayList<StructurePath>();
		ArrayList<StructureSection> structureSections = new ArrayList<StructureSection>();
		ArrayList<StructureRoom> structureRooms = new ArrayList<StructureRoom>();
		ArrayList<Square> structureSquaresToRemove = new ArrayList<Square>();
		ArrayList<Square> structureEntranceSquares = new ArrayList<Square>();

		// Front section
		structureSections.add(new StructureSection("A Cozy Place", 21, 30, 29, 36, false));

		// Front door
		structureFeatures.add(Templates.DOOR.makeCopy("Front Door", Game.level.squares[24][30], false, null));

		// Front room
		StructureRoom livingRoom = new StructureRoom("Living Room", 22, 31, false, new ArrayList<Actor>(),
				new RoomPart(22, 31, 28, 35));
		structureRooms.add(livingRoom);

		// Front room decorative walls
		// structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[55][20],
		// null));
		// structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[55][26],
		// null));
		// structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[46][23],
		// null));
		// structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[51][23],
		// null));

		// Back Section
		structureSections.add(new StructureSection("A Cozy Place", 19, 36, 36, 46, false));

		// False wall
		RemoteDoor falseWall = Templates.FALSE_WALL.makeCopy("Wall", Game.level.squares[22][36], false, null);
		structureFeatures.add(falseWall);

		// Back room
		StructureRoom backRoom = new StructureRoom("Bedroom", 20, 37, false, new ArrayList<Actor>(),
				new RoomPart(20, 37, 35, 45));
		structureRooms.add(backRoom);

		// back room decorative walls
		// structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[53][30],
		// null));
		// structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[53][34],
		// null));
		// structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[42][34],
		// null));

		// Rat
		Templates.RAT.makeCopy("Rat", Game.level.squares[25][39], Game.level.factions.get(2), null);

		Game.level.structures.add(new Structure("A Lovely House", structureSections, structureRooms, structurePaths,
				structureFeatures, structureEntranceSquares, null, 0, 0, 0, 0, true, null, structureSquaresToRemove,
				structureExtraWalls, Templates.WALL, Square.STONE_TEXTURE));

		// Path to town 24,21 -> 24,29
		Path pathToTown = new Path(Game.level.squares[24][21], Game.level.squares[24][22], Game.level.squares[24][23],
				Game.level.squares[24][24], Game.level.squares[24][25], Game.level.squares[24][26],
				Game.level.squares[24][27], Game.level.squares[24][28], Game.level.squares[24][29]);

		// Well 24,21
		WaterSource well = Templates.WELL.makeCopy(Game.level.squares[24][21], null);

		Path townSquare = new Path(Game.level.squares[23][19], Game.level.squares[24][19], Game.level.squares[25][19],
				Game.level.squares[22][20], Game.level.squares[23][20], Game.level.squares[24][20],
				Game.level.squares[25][20], Game.level.squares[26][20], Game.level.squares[22][21],
				Game.level.squares[23][21], Game.level.squares[24][21], Game.level.squares[25][21],
				Game.level.squares[26][21], Game.level.squares[22][22], Game.level.squares[23][22],
				Game.level.squares[24][22], Game.level.squares[25][22], Game.level.squares[26][22],
				Game.level.squares[23][23], Game.level.squares[24][23], Game.level.squares[25][23]);

	}

}
