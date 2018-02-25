package com.marklynch.level.quest.thepigs;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructureFeature;
import com.marklynch.level.constructs.bounds.structure.StructurePath;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.constructs.bounds.structure.StructureRoom.RoomPart;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.constructs.journal.AreaList;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Node;
import com.marklynch.level.squares.Nodes;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Door;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Tree;
import com.marklynch.objects.Wall;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Human;
import com.marklynch.objects.units.Pig;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.utils.TextUtils;

public class QuestThePigs extends Quest {

	// Pen
	StructureSection penSection;
	StructureRoom penRoom;

	// Farmhouse
	StructureSection farmHouseFrontSection;
	StructureSection farmHouseBackSection;
	StructureRoom farmHouseFrontRoom;
	StructureRoom farmHouseStorageRoom;
	StructureRoom farmHouseGameObjectroom;
	StructureRoom farmHouseHallRoom;

	// Actors
	Pig larry, wendy, jane, steve, prescilla;
	Human farmer;
	Weapon hoe;

	// GameObjects
	// Mud mud;
	// DungPile dungPile;
	// Trough trough;
	// Swill swill;
	Tree tree;

	// Conversations
	ConversationForFarmer conversationForFarmer;

	public QuestThePigs() {
		super();
		name = "THE PIGS";

		// Pigs
		larry = Templates.PIG.makeCopy("Larry", Game.level.squares[30][76], Game.level.factions.townsPeople, null,
				new GameObject[] {}, new GameObject[] {}, null);
		larry.inventory.add(Templates.CLEAVER.makeCopy(null, null));
		wendy = Templates.PIG.makeCopy("Wendy", Game.level.squares[39][74], Game.level.factions.townsPeople, null,
				new GameObject[] {}, new GameObject[] {}, null);
		wendy.inventory.add(Templates.CLEAVER.makeCopy(null, null));
		jane = Templates.PIG.makeCopy("Jane", Game.level.squares[34][78], Game.level.factions.townsPeople, null,
				new GameObject[] {}, new GameObject[] {}, null);
		jane.inventory.add(Templates.CLEAVER.makeCopy(null, null));
		steve = Templates.PIG.makeCopy("Steve", Game.level.squares[35][74], Game.level.factions.townsPeople, null,
				new GameObject[] {}, new GameObject[] {}, null);
		steve.inventory.add(Templates.CLEAVER.makeCopy(null, null));
		prescilla = Templates.PIG.makeCopy("Prescilla", Game.level.squares[31][80], Game.level.factions.townsPeople,
				null, new GameObject[] {}, new GameObject[] {}, null);
		prescilla.inventory.add(Templates.CLEAVER.makeCopy(null, null));

		// Farmer
		farmer = Templates.FARMER.makeCopy("Farmer Pete", Game.level.squares[0][15], Game.level.factions.townsPeople,
				null, 203, new GameObject[] {}, new GameObject[] {}, AreaList.townForest);
		farmer.quest = this;
		hoe = Templates.HOE.makeCopy(null, farmer);
		farmer.inventory.add(hoe);
		conversationForFarmer = new ConversationForFarmer(this, farmer);

		// trees
		// cute, larry owns the tree
		Templates.TREE.makeCopy(Game.level.squares[30][75], larry);
		Templates.TREE.makeCopy(Game.level.squares[32][79], larry);
		Templates.TREE.makeCopy(Game.level.squares[38][74], larry);

		// Bow on the ground
		Templates.HUNTING_BOW.makeCopy(Game.level.squares[33][73], null);
		Templates.DINNER_KNIFE.makeCopy(Game.level.squares[34][73], null);
		// GameObject farmersGameObject =
		// Templates.BED.makeCopy(Game.level.squares[3][3]);
		// farmersGameObject.quest = this;
		// farmer = Templates.FARMER.makeCopy(Game.level.squares[2][2],
		// Game.level.factions.townsPeople, farmersGameObject);
		// farmer.quest = this;}
		//
		// // Conversations
		// conversationForFarmer = new ConversationForFarmer(this);

		// Pig pen
		ArrayList<Wall> pigPenWalls = new ArrayList<Wall>();
		ArrayList<StructureFeature> pigPenFeatures = new ArrayList<StructureFeature>();
		ArrayList<StructurePath> pigPenPaths = new ArrayList<StructurePath>();
		ArrayList<StructureSection> pigPenSections = new ArrayList<StructureSection>();
		ArrayList<StructureRoom> pigPenRooms = new ArrayList<StructureRoom>();
		ArrayList<Square> pigPenSquaresToRemove = new ArrayList<Square>();

		// gate
		Door gate = Templates.GATE.makeCopy("Gate", Game.level.squares[32][72], false, true, false, farmer);
		pigPenFeatures.add(new StructureFeature(gate, Nodes.farmInnerBend));

		penSection = new StructureSection("Pen", 28, 72, 46, 82, false, larry);
		penRoom = new StructureRoom("Pen", 29, 73, false, new ArrayList<Actor>(), new Node[] { Nodes.farmInnerBend },
				new RoomPart(29, 73, 45, 81));

		pigPenSections.add(penSection);
		pigPenRooms.add(penRoom);

		Game.level.structures.add(new Structure("Pen", pigPenSections, pigPenRooms, pigPenPaths, pigPenFeatures,
				new ArrayList<Square>(), null, 0, 0, 0, 0, true, larry, pigPenSquaresToRemove, pigPenWalls,
				Templates.FENCE, Square.MUD_TEXTURE, 3));

		// Farm house 2,69 -> 24,85
		ArrayList<Wall> farmHouseWalls = new ArrayList<Wall>();
		ArrayList<StructureFeature> farmHouseFeatures = new ArrayList<StructureFeature>();
		ArrayList<StructurePath> farmHousePaths = new ArrayList<StructurePath>();
		ArrayList<StructureSection> farmHouseSections = new ArrayList<StructureSection>();
		ArrayList<StructureRoom> farmHouseRooms = new ArrayList<StructureRoom>();
		ArrayList<Square> farmHouseSquaresToRemove = new ArrayList<Square>();

		// Farm house door
		Door farmHouseFrontDoor = Templates.DOOR.makeCopy("Door", Game.level.squares[18][69], false, false, false,
				farmer);
		farmHouseFeatures.add(new StructureFeature(farmHouseFrontDoor, Nodes.farmInnerBend));
		Door farmHouseHallDoor = Templates.DOOR.makeCopy("Door", Game.level.squares[18][75], true, true, true, farmer);
		farmHouseFeatures.add(new StructureFeature(farmHouseHallDoor, Nodes.farmInnerBend));
		Door farmHouseStorageDoor = Templates.DOOR.makeCopy("Door", Game.level.squares[21][78], true, true, true,
				farmer);
		farmHouseFeatures.add(new StructureFeature(farmHouseStorageDoor, Nodes.farmInnerBend));
		Door farmHouseGameObjectroomDoor = Templates.DOOR.makeCopy("Door", Game.level.squares[17][78], true, true, true,
				farmer);
		farmHouseFeatures.add(new StructureFeature(farmHouseGameObjectroomDoor, Nodes.farmInnerBend));
		Door farmHouseBackDoor = Templates.DOOR.makeCopy("Door", Game.level.squares[21][85], true, true, true, farmer);
		farmHouseFeatures.add(new StructureFeature(farmHouseBackDoor, Nodes.farmInnerBend));

		farmHouseFrontSection = new StructureSection("Farm House", 12, 69, 24, 74, false, farmer);
		farmHouseBackSection = new StructureSection("Farm House", 2, 75, 24, 85, false, farmer);
		farmHouseFrontRoom = new StructureRoom("Kitchen", 13, 70, false, new ArrayList<Actor>(),
				new Node[] { Nodes.farmInnerBend }, new RoomPart(13, 70, 23, 74));
		farmHouseHallRoom = new StructureRoom("Hall", 3, 76, false, new ArrayList<Actor>(),
				new Node[] { Nodes.farmInnerBend }, new RoomPart(3, 76, 23, 77));
		farmHouseGameObjectroom = new StructureRoom("GameObjectroom", 3, 79, false, new ArrayList<Actor>(),
				new Node[] { Nodes.farmInnerBend }, new RoomPart(3, 79, 17, 84));
		farmHouseStorageRoom = new StructureRoom("Storage", 19, 79, false, new ArrayList<Actor>(),
				new Node[] { Nodes.farmInnerBend }, new RoomPart(19, 79, 23, 84));

		farmHouseSections.add(farmHouseFrontSection);
		farmHouseSections.add(farmHouseBackSection);
		farmHouseRooms.add(farmHouseFrontRoom);
		farmHouseRooms.add(farmHouseHallRoom);
		farmHouseRooms.add(farmHouseGameObjectroom);
		farmHouseRooms.add(farmHouseStorageRoom);

		Game.level.structures.add(new Structure("Farm House", farmHouseSections, farmHouseRooms, farmHousePaths,
				farmHouseFeatures, new ArrayList<Square>(), null, 0, 0, 0, 0, true, farmer, farmHouseSquaresToRemove,
				farmHouseWalls, Templates.WALL, Square.STONE_TEXTURE, 3));

		Templates.SHELF.makeCopy(Game.level.squares[14][69], farmer);
		Templates.SHELF.makeCopy(Game.level.squares[15][69], farmer);
		Templates.FURNACE.makeCopy(Game.level.squares[13][72], farmer);
		GameObject pigSign = Templates.PIG_SIGN.makeCopy(Game.level.squares[25][70], farmer);
		Templates.BENCH.makeCopy(Game.level.squares[15][68], farmer);
		Templates.WELL.makeCopy(Game.level.squares[17][61], farmer);

		// Giant footprints
		Templates.GIANT_FOOTPRINT.makeCopy(Game.level.squares[1][93], null);
		Templates.GIANT_FOOTPRINT_LEFT.makeCopy(Game.level.squares[3][91], null);
		Templates.GIANT_FOOTPRINT.makeCopy(Game.level.squares[5][93], null);
		Templates.GIANT_FOOTPRINT_LEFT.makeCopy(Game.level.squares[7][91], null);
		Templates.GIANT_FOOTPRINT.makeCopy(Game.level.squares[9][93], null);
		Templates.GIANT_FOOTPRINT_LEFT.makeCopy(Game.level.squares[11][91], null);
		Templates.GIANT_FOOTPRINT.makeCopy(Game.level.squares[13][93], null);
		Templates.GIANT_FOOTPRINT_LEFT.makeCopy(Game.level.squares[15][91], null);
		Templates.GIANT_FOOTPRINT.makeCopy(Game.level.squares[17][93], null);
		Templates.GIANT_FOOTPRINT_LEFT.makeCopy(Game.level.squares[19][91], null);
		Templates.GIANT_FOOTPRINT.makeCopy(Game.level.squares[21][93], null);
		Templates.GIANT_FOOTPRINT_LEFT.makeCopy(Game.level.squares[23][91], null);
		Templates.GIANT_FOOTPRINT.makeCopy(Game.level.squares[25][93], null);
		Templates.GIANT_FOOTPRINT_LEFT.makeCopy(Game.level.squares[27][91], null);
		Templates.GIANT_FOOTPRINT.makeCopy(Game.level.squares[29][93], null);
		Templates.GIANT_FOOTPRINT_LEFT.makeCopy(Game.level.squares[31][91], null);
		Templates.GIANT_FOOTPRINT.makeCopy(Game.level.squares[33][93], null);
		Templates.GIANT_FOOTPRINT_LEFT.makeCopy(Game.level.squares[35][91], null);

		// Grass to east of pigs
		Templates.WHEAT.makeCopy(Game.level.squares[53][70], null);
		Templates.WHEAT.makeCopy(Game.level.squares[54][70], null);
		Templates.WHEAT.makeCopy(Game.level.squares[55][70], null);
		Templates.WHEAT.makeCopy(Game.level.squares[55][70], null);
		Templates.WHEAT.makeCopy(Game.level.squares[57][70], null);
		Templates.WHEAT.makeCopy(Game.level.squares[58][70], null);
		Templates.WHEAT.makeCopy(Game.level.squares[59][70], null);
		Templates.WHEAT.makeCopy(Game.level.squares[60][70], null);
		Templates.WHEAT.makeCopy(Game.level.squares[61][70], null);
		Templates.WHEAT.makeCopy(Game.level.squares[62][70], null);
		Templates.WHEAT.makeCopy(Game.level.squares[63][70], null);
		Templates.WHEAT.makeCopy(Game.level.squares[64][70], null);
		Templates.WHEAT.makeCopy(Game.level.squares[53][71], null);
		Templates.LONG_GRASS.makeCopy(Game.level.squares[54][71], null);
		Templates.WHEAT.makeCopy(Game.level.squares[56][71], null);
		Templates.WHEAT.makeCopy(Game.level.squares[57][71], null);
		Templates.WHEAT.makeCopy(Game.level.squares[58][71], null);
		Templates.WHEAT.makeCopy(Game.level.squares[59][71], null);
		Templates.WHEAT.makeCopy(Game.level.squares[60][71], null);
		Templates.WHEAT.makeCopy(Game.level.squares[63][71], null);
		Templates.WHEAT.makeCopy(Game.level.squares[64][71], null);
		Templates.WHEAT.makeCopy(Game.level.squares[54][72], null);
		Templates.WHEAT.makeCopy(Game.level.squares[56][72], null);
		Templates.WHEAT.makeCopy(Game.level.squares[57][72], null);
		Templates.WHEAT.makeCopy(Game.level.squares[58][72], null);
		Templates.WHEAT.makeCopy(Game.level.squares[60][72], null);
		Templates.WHEAT.makeCopy(Game.level.squares[62][72], null);
		Templates.WHEAT.makeCopy(Game.level.squares[53][73], null);
		Templates.LONG_GRASS.makeCopy(Game.level.squares[54][73], null);
		Templates.WHEAT.makeCopy(Game.level.squares[55][73], null);
		Templates.WHEAT.makeCopy(Game.level.squares[56][73], null);
		Templates.WHEAT.makeCopy(Game.level.squares[57][73], null);
		Templates.WHEAT.makeCopy(Game.level.squares[58][73], null);
		Templates.WHEAT.makeCopy(Game.level.squares[59][73], null);
		Templates.WHEAT.makeCopy(Game.level.squares[62][73], null);
		Templates.WHEAT.makeCopy(Game.level.squares[63][73], null);
		Templates.WHEAT.makeCopy(Game.level.squares[64][73], null);
		links = TextUtils.getLinks(true, this);

	}

	@Override
	public void update() {
		if (resolved)
			return;

		super.update();

	}

	@Override
	public boolean update(Actor actor) {
		return false;
	}

	@Override
	public Conversation getConversation(Actor actor) {
		if (actor == farmer) {
			conversationForFarmer.setup();
			return conversationForFarmer;
		}
		return null;
	}
}
