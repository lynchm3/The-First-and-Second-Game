package com.marklynch.level.quest.thesecretroom;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Path;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructurePath;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.constructs.bounds.structure.StructureRoom.RoomPart;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.RemoteDoor;
import com.marklynch.objects.Sign;
import com.marklynch.objects.Templates;
import com.marklynch.objects.Wall;
import com.marklynch.objects.WaterSource;
import com.marklynch.objects.units.Actor;

public class QuestTheSecretRoom extends Quest {

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
		structureFeatures
				.add(Templates.DOOR.makeCopy("Front Door", Game.level.squares[24][30], false, false, false, null));

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
		StructureRoom backRoom = new StructureRoom("GameObjectroom", 20, 37, false, new ArrayList<Actor>(),
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
		Templates.RAT.makeCopy("Rat", Game.level.squares[25][39], Game.level.factions.get(2), null, null,
				new GameObject[] {}, new GameObject[] {});

		Game.level.structures.add(new Structure("A Lovely House", structureSections, structureRooms, structurePaths,
				structureFeatures, structureEntranceSquares, null, 0, 0, 0, 0, true, null, structureSquaresToRemove,
				structureExtraWalls, Templates.WALL, Square.STONE_TEXTURE));

		// Path to town 24,21 -> 24,29
		Path pathToTown = new Path(24, 21, 24, 29);

		// Bushed along path to town
		Templates.BUSH.makeCopy(Game.level.squares[23][25], null);
		Templates.BUSH.makeCopy(Game.level.squares[25][25], null);
		Templates.BUSH.makeCopy(Game.level.squares[23][27], null);
		Templates.BUSH.makeCopy(Game.level.squares[25][27], null);
		Templates.BUSH.makeCopy(Game.level.squares[23][29], null);
		Templates.BUSH.makeCopy(Game.level.squares[25][29], null);

		// TownSquare Well 24,21
		WaterSource well = Templates.WELL.makeCopy(Game.level.squares[24][21], null);

		// Town square pavement
		Path townSquare = new Path(Game.level.squares[23][19], Game.level.squares[24][19], Game.level.squares[25][19],
				Game.level.squares[22][20], Game.level.squares[23][20], Game.level.squares[24][20],
				Game.level.squares[25][20], Game.level.squares[26][20], Game.level.squares[22][21],
				Game.level.squares[23][21], Game.level.squares[24][21], Game.level.squares[25][21],
				Game.level.squares[26][21], Game.level.squares[22][22], Game.level.squares[23][22],
				Game.level.squares[24][22], Game.level.squares[25][22], Game.level.squares[26][22],
				Game.level.squares[23][23], Game.level.squares[24][23], Game.level.squares[25][23]);

		// Path left of fountain
		new Path(4, 21, 21, 21);

		// Path to shop
		new Path(4, 4, 4, 21);

		// Path to farm
		new Path(4, 21, 4, 62);
		new Path(4, 62, 18, 62);
		new Path(18, 62, 18, 68);

		// Signpost 5,20
		Sign signpost = Templates.SIGNPOST.makeCopy(Game.level.squares[5][20], "Signpost",

				new Object[] { GameObject.upTexture, " Shop  ", GameObject.rightTexture, " Estates  ",
						GameObject.downTexture, " Farm" },
				null);

		// "North - Shop, Hunter's Lodge; East - Estates; South - Farm",

		// Trees around town square
		Templates.TREE.makeCopy(Game.level.squares[22][19], null);
		Templates.TREE.makeCopy(Game.level.squares[22][23], null);
		Templates.TREE.makeCopy(Game.level.squares[26][19], null);
		Templates.TREE.makeCopy(Game.level.squares[26][23], null);

	}

}
