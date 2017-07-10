package com.marklynch.level.quest.thepigs;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.structure.Structure;
import com.marklynch.level.constructs.structure.StructurePath;
import com.marklynch.level.constructs.structure.StructureRoom;
import com.marklynch.level.constructs.structure.StructureRoom.RoomPart;
import com.marklynch.level.constructs.structure.StructureSection;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.quest.Quest;
import com.marklynch.objects.Door;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Templates;
import com.marklynch.objects.Tree;
import com.marklynch.objects.Wall;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Farmer;
import com.marklynch.objects.units.Pig;
import com.marklynch.objects.weapons.Weapon;

public class QuestThePigs extends Quest {

	// Pen
	StructureSection penSection;
	StructureRoom penRoom;

	// Farmhouse
	StructureSection farmHouseFrontSection;
	StructureSection farmHouseBackSection;
	StructureRoom farmHouseFrontRoom;
	StructureRoom farmHouseStorageRoom;
	StructureRoom farmHouseBedroom;
	StructureRoom farmHouseHallRoom;

	// Actors
	Pig larry, wendy, jane, steve, prescilla;
	Farmer farmer;
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

		// Pigs
		larry = Templates.PIG.makeCopy("Larry", Game.level.squares[30][76], Game.level.factions.get(1), null);
		larry.inventory.add(Templates.CLEAVER.makeCopy(null, null));
		wendy = Templates.PIG.makeCopy("Wendy", Game.level.squares[39][74], Game.level.factions.get(1), null);
		wendy.inventory.add(Templates.CLEAVER.makeCopy(null, null));
		jane = Templates.PIG.makeCopy("Jane", Game.level.squares[34][78], Game.level.factions.get(1), null);
		jane.inventory.add(Templates.CLEAVER.makeCopy(null, null));
		steve = Templates.PIG.makeCopy("Steve", Game.level.squares[35][74], Game.level.factions.get(1), null);
		steve.inventory.add(Templates.CLEAVER.makeCopy(null, null));
		prescilla = Templates.PIG.makeCopy("Prescilla", Game.level.squares[31][80], Game.level.factions.get(1), null);
		prescilla.inventory.add(Templates.CLEAVER.makeCopy(null, null));

		// Farmer
		farmer = Templates.FARMER.makeCopy(Game.level.squares[28][70], Game.level.factions.get(1), null);
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
		// Bed farmersBed = Templates.BED.makeCopy(Game.level.squares[3][3]);
		// farmersBed.quest = this;
		// farmer = Templates.FARMER.makeCopy(Game.level.squares[2][2],
		// Game.level.factions.get(1), farmersBed);
		// farmer.quest = this;}
		//
		// // Conversations
		// conversationForFarmer = new ConversationForFarmer(this);

		// Pig pen
		ArrayList<Wall> pigPenWalls = new ArrayList<Wall>();
		ArrayList<GameObject> pigPenFeatures = new ArrayList<GameObject>();
		ArrayList<StructurePath> pigPenPaths = new ArrayList<StructurePath>();
		ArrayList<StructureSection> pigPenSections = new ArrayList<StructureSection>();
		ArrayList<StructureRoom> pigPenRooms = new ArrayList<StructureRoom>();
		ArrayList<Square> pigPenSquaresToRemove = new ArrayList<Square>();

		// gate
		Door gate = Templates.GATE.makeCopy("Gate", Game.level.squares[32][72], false, larry);
		pigPenFeatures.add(gate);

		penSection = new StructureSection("Pen", 28, 72, 46, 82, false, larry);
		penRoom = new StructureRoom("Pen", 29, 73, false, new ArrayList<Actor>(), new RoomPart(29, 73, 45, 81));

		pigPenSections.add(penSection);
		pigPenRooms.add(penRoom);

		Game.level.structures.add(
				new Structure("Pen", pigPenSections, pigPenRooms, pigPenPaths, pigPenFeatures, new ArrayList<Square>(),
						null, 0, 0, 0, 0, true, larry, pigPenSquaresToRemove, pigPenWalls, Templates.FENCE, "mud.png"));

		// Farm house 2,69 -> 24,85
		ArrayList<Wall> farmHouseWalls = new ArrayList<Wall>();
		ArrayList<GameObject> farmHouseFeatures = new ArrayList<GameObject>();
		ArrayList<StructurePath> farmHousePaths = new ArrayList<StructurePath>();
		ArrayList<StructureSection> farmHouseSections = new ArrayList<StructureSection>();
		ArrayList<StructureRoom> farmHouseRooms = new ArrayList<StructureRoom>();
		ArrayList<Square> farmHouseSquaresToRemove = new ArrayList<Square>();

		// Farm house door
		Door farmHouseFrontDoor = Templates.DOOR.makeCopy("Door", Game.level.squares[18][69], false, farmer);
		farmHouseFeatures.add(farmHouseFrontDoor);
		Door farmHouseHallDoor = Templates.DOOR.makeCopy("Door", Game.level.squares[18][75], false, farmer);
		farmHouseFeatures.add(farmHouseHallDoor);
		Door farmHouseStorageDoor = Templates.DOOR.makeCopy("Door", Game.level.squares[21][78], false, farmer);
		farmHouseFeatures.add(farmHouseStorageDoor);
		Door farmHouseBedroomDoor = Templates.DOOR.makeCopy("Door", Game.level.squares[17][78], false, farmer);
		farmHouseFeatures.add(farmHouseBedroomDoor);
		Door farmHouseBackDoor = Templates.DOOR.makeCopy("Door", Game.level.squares[21][85], false, farmer);
		farmHouseFeatures.add(farmHouseBackDoor);

		farmHouseFrontSection = new StructureSection("Farm House", 12, 69, 24, 74, false, farmer);
		farmHouseBackSection = new StructureSection("Farm House", 2, 75, 24, 85, false, farmer);
		farmHouseFrontRoom = new StructureRoom("Kitchen", 13, 70, false, new ArrayList<Actor>(),
				new RoomPart(13, 70, 23, 74));
		farmHouseHallRoom = new StructureRoom("Hall", 3, 76, false, new ArrayList<Actor>(),
				new RoomPart(3, 76, 23, 77));
		farmHouseBedroom = new StructureRoom("Bedroom", 3, 79, false, new ArrayList<Actor>(),
				new RoomPart(3, 79, 17, 84));
		farmHouseStorageRoom = new StructureRoom("Storage", 19, 79, false, new ArrayList<Actor>(),
				new RoomPart(19, 79, 23, 84));

		farmHouseSections.add(farmHouseFrontSection);
		farmHouseSections.add(farmHouseBackSection);
		farmHouseRooms.add(farmHouseFrontRoom);
		farmHouseRooms.add(farmHouseHallRoom);
		farmHouseRooms.add(farmHouseBedroom);
		farmHouseRooms.add(farmHouseStorageRoom);

		Game.level.structures.add(new Structure("Farm House", farmHouseSections, farmHouseRooms, farmHousePaths,
				farmHouseFeatures, new ArrayList<Square>(), null, 0, 0, 0, 0, true, farmer, farmHouseSquaresToRemove,
				farmHouseWalls, Templates.WALL, "stone.png"));

		Templates.SHELF.makeCopy(Game.level.squares[14][69], farmer);
		Templates.SHELF.makeCopy(Game.level.squares[15][69], farmer);
		Templates.FURNACE.makeCopy(Game.level.squares[13][72], farmer);
		GameObject pigSign = Templates.PIG_SIGN.makeCopy(Game.level.squares[25][70], farmer);
		Templates.BENCH.makeCopy(Game.level.squares[15][68], farmer);

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

	}

	@Override
	public void update() {

	}

	@Override
	public boolean update(Actor actor) {
		return false;
	}

	@Override
	public Conversation getConversation(Actor actor) {
		System.out.println("QuestThePigs.getConversation");
		System.out.println("actor = " + actor);
		System.out.println("farmer = " + farmer);
		if (actor == farmer) {
			conversationForFarmer.setup();
			System.out.println("returning conversation");
			return conversationForFarmer;
		}
		System.out.println("returning null");
		return null;
	}
}
