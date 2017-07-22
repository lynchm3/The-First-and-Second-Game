package com.marklynch.level.quest.betweenthewalls;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Path;
import com.marklynch.level.constructs.requirementtomeet.RequirementToMeet;
import com.marklynch.level.constructs.requirementtomeet.StatRequirementToMeet;
import com.marklynch.level.constructs.structure.Structure;
import com.marklynch.level.constructs.structure.StructurePath;
import com.marklynch.level.constructs.structure.StructureRoom;
import com.marklynch.level.constructs.structure.StructureRoom.RoomPart;
import com.marklynch.level.constructs.structure.StructureSection;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.RemoteDoor;
import com.marklynch.objects.SwitchForOpenables;
import com.marklynch.objects.Templates;
import com.marklynch.objects.Wall;
import com.marklynch.objects.units.Actor;

public class QuestBetweenTheWalls {

	public QuestBetweenTheWalls() {

		ArrayList<Wall> structureExtraWalls = new ArrayList<Wall>();
		ArrayList<GameObject> structureFeatures = new ArrayList<GameObject>();
		ArrayList<StructurePath> structurePaths = new ArrayList<StructurePath>();
		ArrayList<StructureSection> structureSections = new ArrayList<StructureSection>();
		ArrayList<StructureRoom> structureRooms = new ArrayList<StructureRoom>();
		ArrayList<Square> structureSquaresToRemove = new ArrayList<Square>();
		ArrayList<Square> structureEntranceSquares = new ArrayList<Square>();

		// Front section
		structureSections.add(new StructureSection("A Lovely House", 41, 19, 56, 28, false));

		// Front door
		structureFeatures.add(Templates.DOOR.makeCopy("Front Door", Game.level.squares[41][21], false, null));

		// Front room
		StructureRoom frontRoom = new StructureRoom("Front Room", 42, 20, false, new ArrayList<Actor>(),
				new RoomPart(42, 20, 55, 26));
		structureRooms.add(frontRoom);

		// Front room decorative walls
		structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[55][20], null));
		structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[55][26], null));
		structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[46][23], null));
		structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[51][23], null));

		// Bedroom Section
		structureSections.add(new StructureSection("A Lovely House", 41, 28, 56, 38, false));

		// Bedroom door
		structurePaths.add(new StructurePath("Front Room", false, new ArrayList(), Game.level.squares[42][27]));
		structureFeatures.add(Templates.DOOR.makeCopy("Bedroom Door", Game.level.squares[42][28], false, null));
		structurePaths.add(new StructurePath("Bedroom", false, new ArrayList(), Game.level.squares[42][29]));

		// Bedroom room
		StructureRoom bedRoom = new StructureRoom("Bedroom", 42, 30, false, new ArrayList<Actor>(),
				new RoomPart(42, 30, 53, 34));
		structureRooms.add(bedRoom);

		// Bedroom decorative walls
		structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[53][30], null));
		structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[53][34], null));
		structureExtraWalls.add(Templates.WALL.makeCopy(Game.level.squares[42][34], null));

		// Path between the walls
		StructurePath pathBetweenTheWalls = new StructurePath("Between the walls", false, new ArrayList(),
				Game.level.squares[44][28], Game.level.squares[45][28], Game.level.squares[46][28],
				Game.level.squares[47][28], Game.level.squares[48][28], Game.level.squares[49][28],
				Game.level.squares[50][28], Game.level.squares[51][28], Game.level.squares[52][28],
				Game.level.squares[53][28], Game.level.squares[54][28], Game.level.squares[55][28],
				Game.level.squares[55][29], Game.level.squares[55][30], Game.level.squares[55][31],
				Game.level.squares[55][32], Game.level.squares[55][33], Game.level.squares[55][34],
				Game.level.squares[55][35]);
		structurePaths.add(pathBetweenTheWalls);

		// False wall
		RemoteDoor falseWall = Templates.FALSE_WALL.makeCopy("Wall", Game.level.squares[52][27], false, null);
		structureFeatures.add(falseWall);

		// Rat
		Templates.RAT.makeCopy("Rat", Game.level.squares[44][28], Game.level.factions.get(2), null);

		// Hidden room
		StructureRoom hiddenRoom = new StructureRoom("Hidey-Hole", 42, 36, false, new ArrayList<Actor>(),
				new RoomPart(42, 36, 55, 37));
		structureRooms.add(hiddenRoom);

		// Path west entrance to west atrium
		// structurePaths.add(new StructurePath("Between the walls", false, new
		// ArrayList<Actor>(),
		// Game.level.squares[238][15], Game.level.squares[239][15],
		// Game.level.squares[240][15],
		// Game.level.squares[241][15], Game.level.squares[242][15]));
		// West Atrium extras
		// structureExtraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[243][7],
		// null));
		Game.level.structures.add(new Structure("A Lovely House", structureSections, structureRooms, structurePaths,
				structureFeatures, structureEntranceSquares, null, 0, 0, 0, 0, true, null, structureSquaresToRemove,
				structureExtraWalls, Templates.WALL, Square.STONE_TEXTURE));

		// Antler switch
		Templates.ANTLERS_SWITCH_FOR_OPENABLES.makeCopy(Game.level.squares[53][19], null, falseWall,
				SwitchForOpenables.SWITCH_TYPE.OPEN_CLOSE,
				new RequirementToMeet[] { new StatRequirementToMeet(StatRequirementToMeet.Stat.STAT_STRENGTH, 1) });

		// Path to town 24,21 -> 40,21
		Path pathToTown = new Path(Game.level.squares[24][21], Game.level.squares[25][21], Game.level.squares[26][21],
				Game.level.squares[27][21], Game.level.squares[28][21], Game.level.squares[29][21],
				Game.level.squares[30][21], Game.level.squares[31][21], Game.level.squares[32][21],
				Game.level.squares[33][21], Game.level.squares[34][21], Game.level.squares[35][21],
				Game.level.squares[36][21], Game.level.squares[37][21], Game.level.squares[38][21],
				Game.level.squares[39][21], Game.level.squares[40][21]);

		// Bushed along path
		Templates.BUSH.makeCopy(Game.level.squares[28][20], null);
		Templates.BUSH.makeCopy(Game.level.squares[28][22], null);
		Templates.BUSH.makeCopy(Game.level.squares[30][20], null);
		Templates.BUSH.makeCopy(Game.level.squares[30][22], null);
		Templates.BUSH.makeCopy(Game.level.squares[32][20], null);
		Templates.BUSH.makeCopy(Game.level.squares[32][22], null);
		Templates.BUSH.makeCopy(Game.level.squares[34][20], null);
		Templates.BUSH.makeCopy(Game.level.squares[34][22], null);
		Templates.BUSH.makeCopy(Game.level.squares[36][20], null);
		Templates.BUSH.makeCopy(Game.level.squares[36][22], null);
		Templates.BUSH.makeCopy(Game.level.squares[38][20], null);
		Templates.BUSH.makeCopy(Game.level.squares[38][22], null);
		Templates.BUSH.makeCopy(Game.level.squares[40][20], null);
		Templates.BUSH.makeCopy(Game.level.squares[40][22], null);

	}

}
