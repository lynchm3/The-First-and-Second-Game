package com.marklynch.level.quest.betweenthewalls;

import com.marklynch.Game;
import com.marklynch.level.constructs.PavedPathway;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructureFeature;
import com.marklynch.level.constructs.bounds.structure.StructurePath;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom.RoomPart;
import com.marklynch.level.constructs.requirementtomeet.RequirementToMeet;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Nodes;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.RemoteDoor;
import com.marklynch.objects.inanimateobjects.Switch;
import com.marklynch.objects.inanimateobjects.Wall;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextUtils;

public class QuestBetweenTheWalls extends Quest {

	// Info objects
	String theresSomethingInTheWalls = "There's something in the walls...";

	public QuestBetweenTheWalls(int posX, int posY) {
		name = "BETWEEN THE WALLS";

		ArrayList<Wall> structureExtraWalls = new ArrayList<Wall>(Wall.class);
		ArrayList<StructureFeature> structureFeatures = new ArrayList<StructureFeature>(StructureFeature.class);
		ArrayList<StructurePath> structurePaths = new ArrayList<StructurePath>(StructurePath.class);
		ArrayList<StructureSection> structureSections = new ArrayList<StructureSection>(StructureSection.class);
		ArrayList<StructureRoom> structureRooms = new ArrayList<StructureRoom>(StructureRoom.class);
		ArrayList<Square> structureSquaresToRemove = new ArrayList<Square>(Square.class);
		ArrayList<Square> structureEntranceSquares = new ArrayList<Square>(Square.class);

		// Front section
		structureSections
				.add(new StructureSection("A Lovely House", posX + 41, posY + 19, posX + 56, posY + 28, false, false));

		// Front door
		structureFeatures.add(new StructureFeature(Templates.DOOR.makeCopy("Front Door",
				Game.level.squares[posX + 41][posY + 21], false, false, false, null), Nodes.wallHouseOuter));

		// Front room
		StructureRoom frontRoom = new StructureRoom("Front Room", posX + 42, posY + 20, false, false,
				new ArrayList<Actor>(Actor.class),
				new Node[] { Nodes.wallHouseOuter, Nodes.wallHouseFalseWall, Nodes.wallHouseBedroom },
				new RoomPart(posX + 42, posY + 20, posX + 55, posY + 26));
		structureRooms.add(frontRoom);

		// Front room decorative walls
		structureExtraWalls.add(Templates.WALL_CAVE.makeCopy(Game.level.squares[posX + 55][posY + 20], null));
		structureExtraWalls.add(Templates.WALL_CAVE.makeCopy(Game.level.squares[posX + 55][posY + 26], null));
		structureExtraWalls.add(Templates.WALL_CAVE.makeCopy(Game.level.squares[posX + 46][posY + 23], null));
		structureExtraWalls.add(Templates.WALL_CAVE.makeCopy(Game.level.squares[posX + 51][posY + 23], null));

		// Bedroom Section
		structureSections
				.add(new StructureSection("A Lovely House", posX + 41, posY + 28, posX + 56, posY + 38, false, false));

		// Bedroom door
		structurePaths.add(new StructurePath("Front Room", false, false, new ArrayList(Actor.class),
				new Node[] { Nodes.wallHouseOuter }, Game.level.squares[posX + 42][posY + 27]));
		structureFeatures.add(new StructureFeature(Templates.DOOR.makeCopy("Bedroom Door",
				Game.level.squares[posX + 42][posY + 28], false, true, true, null), Nodes.wallHouseBedroom));
		structureFeatures.add(
				new StructureFeature(Templates.FIRE_PLACE.makeCopy(Game.level.squares[posX + 48][posY + 29], null)));

		// 57,39
		structurePaths.add(new StructurePath("Bedroom", false, false, new ArrayList(Actor.class),
				new Node[] { Nodes.wallHouseBedroom }, Game.level.squares[posX + 42][posY + 29]));

		// Bedroom room
		StructureRoom bedRoom = new StructureRoom("wallHouseBedroom", posX + 42, posY + 30, false, false,
				new ArrayList<Actor>(Actor.class), new Node[] { Nodes.wallHouseBedroom, Nodes.wallHouseFireplace },
				new RoomPart(posX + 42, posY + 30, posX + 53, posY + 34));
		structureRooms.add(bedRoom);

		// Bedroom decorative walls
		structureExtraWalls.add(Templates.WALL_CAVE.makeCopy(Game.level.squares[posX + 53][posY + 30], null));
		structureExtraWalls.add(Templates.WALL_CAVE.makeCopy(Game.level.squares[posX + 53][posY + 34], null));
		structureExtraWalls.add(Templates.WALL_CAVE.makeCopy(Game.level.squares[posX + 42][posY + 34], null));

		// Path between the walls
		StructurePath pathBetweenTheWalls = new StructurePath("Between the walls", false, false,
				new ArrayList(Actor.class), new Node[] { Nodes.wallHouseFalseWall, Nodes.wallHouseFireplace },
				Game.level.squares[posX + 44][posY + 28], Game.level.squares[posX + 45][posY + 28],
				Game.level.squares[posX + 46][posY + 28], Game.level.squares[posX + 47][posY + 28],
				Game.level.squares[posX + 48][posY + 28], Game.level.squares[posX + 49][posY + 28],
				Game.level.squares[posX + 50][posY + 28], Game.level.squares[posX + 51][posY + 28],
				Game.level.squares[posX + 52][posY + 28], Game.level.squares[posX + 53][posY + 28],
				Game.level.squares[posX + 54][posY + 28], Game.level.squares[posX + 55][posY + 28],
				Game.level.squares[posX + 55][posY + 29], Game.level.squares[posX + 55][posY + 30],
				Game.level.squares[posX + 55][posY + 31], Game.level.squares[posX + 55][posY + 32],
				Game.level.squares[posX + 55][posY + 33], Game.level.squares[posX + 55][posY + 34],
				Game.level.squares[posX + 55][posY + 35]);
		structurePaths.add(pathBetweenTheWalls);

		// False wall
		RemoteDoor falseWall = Templates.OPENABLE_WALL.makeCopy("Wall", Game.level.squares[posX + 52][posY + 27], false,
				null);
		structureFeatures.add(new StructureFeature(falseWall, Nodes.wallHouseFalseWall));

		// Rat
		Templates.RAT.makeCopy("Rat", Game.level.squares[posX + 44][posY + 28], Game.level.factions.rats, null,
				new GameObject[] {}, new GameObject[] {}, null);

		// Hidden room
		StructureRoom hiddenRoom = new StructureRoom("Hidey-Hole", posX + 42, posY + 36, false, false,
				new ArrayList<Actor>(Actor.class), 4, new Node[] { Nodes.wallHouseFalseWall, Nodes.wallHouseFireplace },
				new RoomPart(posX + 42, posY + 36, posX + 55, posY + 37));
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
				structureFeatures, structureEntranceSquares, ResourceUtils.getGlobalImage("icon_house.png", false),
				posX + 0, posY + 0, posX + 0, posY + 0, true, null,
				structureSquaresToRemove, structureExtraWalls, Templates.WALL_CAVE, Square.STONE_TEXTURE, 2));

		// Antler switch
		Templates.ANTLERS_SWITCH.makeCopy(Game.level.squares[posX + 53][posY + 19], null, Switch.SWITCH_TYPE.OPEN_CLOSE,
				new RequirementToMeet[] { new RequirementToMeet(HIGH_LEVEL_STATS.STRENGTH, 1) }, falseWall);

		// Pressure Plate
		Templates.PRESSURE_PLATE.makeCopy(Game.level.squares[posX + 51][posY + 21], null, Switch.SWITCH_TYPE.OPEN_CLOSE,
				new RequirementToMeet[] { new RequirementToMeet(HIGH_LEVEL_STATS.STRENGTH, 1) }, falseWall);

		// Remote switch
		Switch remoteSwitch = Templates.REMOTE_SWITCH.makeCopy(Game.level.squares[posX + 53][posY + 21], null,
				Switch.SWITCH_TYPE.OPEN_CLOSE,
				new RequirementToMeet[] { new RequirementToMeet(HIGH_LEVEL_STATS.STRENGTH, 1) }, falseWall);
		remoteSwitch.quest = this;

		// Path to town 24,21 -> 40,21
		PavedPathway pathToTown = new PavedPathway(posX + 24, posY + 21, posX + 40, posY + 21);

		// Bushed along path
		Templates.BUSH.makeCopy(Game.level.squares[posX + 28][posY + 20], null);
		Templates.BUSH.makeCopy(Game.level.squares[posX + 28][posY + 22], null);
		Templates.BUSH.makeCopy(Game.level.squares[posX + 30][posY + 20], null);
		Templates.BUSH.makeCopy(Game.level.squares[posX + 30][posY + 22], null);
		Templates.BUSH.makeCopy(Game.level.squares[posX + 32][posY + 20], null);
		Templates.BUSH.makeCopy(Game.level.squares[posX + 32][posY + 22], null);
		Templates.BUSH.makeCopy(Game.level.squares[posX + 34][posY + 20], null);
		Templates.BUSH.makeCopy(Game.level.squares[posX + 34][posY + 22], null);
		Templates.BUSH.makeCopy(Game.level.squares[posX + 36][posY + 20], null);
		Templates.BUSH.makeCopy(Game.level.squares[posX + 36][posY + 22], null);
		Templates.BUSH.makeCopy(Game.level.squares[posX + 38][posY + 20], null);
		Templates.BUSH.makeCopy(Game.level.squares[posX + 38][posY + 22], null);
		Templates.BUSH.makeCopy(Game.level.squares[posX + 40][posY + 20], null);
		Templates.BUSH.makeCopy(Game.level.squares[posX + 40][posY + 22], null);

		links = TextUtils.getLinks(true, this);
	}

	@Override
	public void update() {
		if (resolved)
			return;

		super.update();

	}

}
