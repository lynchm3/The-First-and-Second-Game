package com.marklynch.level.quest.thepigs;

import com.marklynch.Game;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructureFeature;
import com.marklynch.level.constructs.bounds.structure.StructurePath;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom.RoomPart;
import com.marklynch.level.constructs.conversation.Conversation;
import com.marklynch.level.constructs.conversation.ConversationForFarmer;
import com.marklynch.level.constructs.journal.AreaList;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Actor.HOBBY;
import com.marklynch.objects.actors.Human;
import com.marklynch.objects.actors.Pig;
import com.marklynch.objects.armor.Weapon;
import com.marklynch.objects.inanimateobjects.Door;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Tree;
import com.marklynch.objects.inanimateobjects.Wall;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.CopyOnWriteArrayList;
import com.marklynch.utils.TextUtils;

public class QuestThePigs extends Quest {

	// Pen
	StructureSection penSection;
	StructureRoom penRoom;

	// Farmhouse
	public static Structure farmHouseStructure;
	StructureSection farmHouseFrontSection;
	StructureSection farmHouseBackSection;
	StructureRoom farmHouseFrontRoom;
	StructureRoom farmHouseStorageRoom;
	StructureRoom farmHouseBedroom;
	StructureRoom farmHouseHallRoom;

	// Barn
	public static Structure barnStructure;
	StructureSection barnSection;
	StructureRoom barnRoom;

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
				Templates.BED.makeCopy(Game.level.squares[5][81], null), 203, new GameObject[] {}, new GameObject[] {},
				AreaList.town, new int[] { Templates.HUNTING_BOW.templateId, Templates.HUNTING_KNIFE.templateId },
				new HOBBY[] { HOBBY.HUNTING });
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
		CopyOnWriteArrayList<Wall> pigPenWalls = new CopyOnWriteArrayList<Wall>(Wall.class);
		CopyOnWriteArrayList<StructureFeature> pigPenFeatures = new CopyOnWriteArrayList<StructureFeature>(StructureFeature.class);
		CopyOnWriteArrayList<StructurePath> pigPenPaths = new CopyOnWriteArrayList<StructurePath>(StructurePath.class);
		CopyOnWriteArrayList<StructureSection> pigPenSections = new CopyOnWriteArrayList<StructureSection>(StructureSection.class);
		CopyOnWriteArrayList<StructureRoom> pigPenRooms = new CopyOnWriteArrayList<StructureRoom>(StructureRoom.class);
		CopyOnWriteArrayList<Square> pigPenSquaresToRemove = new CopyOnWriteArrayList<Square>(Square.class);

		// gate
		Door gate = Templates.GATE.makeCopy("Gate", Game.level.squares[32][72], false, true, false, farmer);
		pigPenFeatures.add(new StructureFeature(gate));

		penSection = new StructureSection("Pen", 28, 72, 46, 82, false, false, larry);
		penRoom = new StructureRoom("Pen", 29, 73, false, false, new CopyOnWriteArrayList<Actor>(Actor.class),
				new RoomPart(29, 73, 45, 81));

		pigPenSections.add(penSection);
		pigPenRooms.add(penRoom);

		Game.level.structures.add(new Structure("Pen", pigPenSections, pigPenRooms, pigPenPaths, pigPenFeatures,
				new CopyOnWriteArrayList<Square>(Square.class), Templates.PIG.imageTexture, 0, 0, 0, 0, true, larry, pigPenSquaresToRemove,
				pigPenWalls, Templates.FENCE, Square.MUD_TEXTURE, 3));

		// Farm house 2,69 -> 24,85
		CopyOnWriteArrayList<Wall> farmHouseWalls = new CopyOnWriteArrayList<Wall>(Wall.class);
		CopyOnWriteArrayList<StructureFeature> farmHouseFeatures = new CopyOnWriteArrayList<StructureFeature>(StructureFeature.class);
		CopyOnWriteArrayList<StructurePath> farmHousePaths = new CopyOnWriteArrayList<StructurePath>(StructurePath.class);
		CopyOnWriteArrayList<StructureSection> farmHouseSections = new CopyOnWriteArrayList<StructureSection>(StructureSection.class);
		CopyOnWriteArrayList<StructureRoom> farmHouseRooms = new CopyOnWriteArrayList<StructureRoom>(StructureRoom.class);
		CopyOnWriteArrayList<Square> farmHouseSquaresToRemove = new CopyOnWriteArrayList<Square>(Square.class);

		// Farm house door
		Door farmHouseFrontDoor = Templates.DOOR.makeCopy("Door", Game.level.squares[18][69], false, false, false,
				farmer);
		farmHouseFeatures.add(new StructureFeature(farmHouseFrontDoor));
		Door farmHouseHallDoor = Templates.DOOR.makeCopy("Door", Game.level.squares[18][75], false, false, false,
				farmer);
		farmHouseFeatures.add(new StructureFeature(farmHouseHallDoor));
		Door farmHouseStorageDoor = Templates.DOOR.makeCopy("Door", Game.level.squares[21][78], false, false, false,
				farmer);
		farmHouseFeatures.add(new StructureFeature(farmHouseStorageDoor));
		Door farmHouseBedRoomDoor = Templates.DOOR.makeCopy("Door", Game.level.squares[17][78], false, false, false,
				farmer);
		farmHouseFeatures.add(new StructureFeature(farmHouseBedRoomDoor));
		Door farmHouseBackDoor = Templates.DOOR.makeCopy("Door", Game.level.squares[21][85], false, false, false,
				farmer);
		farmHouseFeatures.add(new StructureFeature(farmHouseBackDoor));

		farmHouseFrontSection = new StructureSection("Farm House", 12, 69, 24, 74, false, false, farmer);
		farmHouseBackSection = new StructureSection("Farm House", 2, 75, 24, 85, false, false, farmer);
		farmHouseFrontRoom = new StructureRoom("Kitchen", 13, 70, false, false, new CopyOnWriteArrayList<Actor>(Actor.class),
				new RoomPart(13, 70, 23, 74));
		farmHouseHallRoom = new StructureRoom("Hall", 3, 76, false, false, new CopyOnWriteArrayList<Actor>(Actor.class),
				new RoomPart(3, 76, 23, 77));
		farmHouseBedroom = new StructureRoom("Bedroom", 3, 79, false, false, new CopyOnWriteArrayList<Actor>(Actor.class),
				new RoomPart(3, 79, 17, 84));
		farmHouseStorageRoom = new StructureRoom("Storage", 19, 79, false, false, new CopyOnWriteArrayList<Actor>(Actor.class),
				new RoomPart(19, 79, 23, 84));

		farmHouseSections.add(farmHouseFrontSection);
		farmHouseSections.add(farmHouseBackSection);
		farmHouseRooms.add(farmHouseFrontRoom);
		farmHouseRooms.add(farmHouseHallRoom);
		farmHouseRooms.add(farmHouseBedroom);
		farmHouseRooms.add(farmHouseStorageRoom);

		farmHouseStructure = new Structure("Farm House", farmHouseSections, farmHouseRooms, farmHousePaths,
				farmHouseFeatures, new CopyOnWriteArrayList<Square>(Square.class), Templates.PIG.imageTexture, 2, 69, 24, 85, true,
				farmer, farmHouseSquaresToRemove, farmHouseWalls, Templates.WALL_CAVE, Square.STONE_TEXTURE, 3);

		Game.level.structures.add(farmHouseStructure);

		Templates.SHELF.makeCopy(Game.level.squares[14][69], farmer);
		Templates.SHELF.makeCopy(Game.level.squares[15][69], farmer);
		Templates.FURNACE.makeCopy("Furnace", Game.level.squares[13][72], false, farmer);
		GameObject pigSign = Templates.PIG_SIGN.makeCopy(Game.level.squares[25][70], farmer);
		Templates.BENCH.makeCopy(Game.level.squares[15][68], farmer);
		GameObject well = Templates.WELL.makeCopy(Game.level.squares[17][61], farmer);

		/// BARN

		CopyOnWriteArrayList<Wall> barnWalls = new CopyOnWriteArrayList<Wall>(Wall.class);
		CopyOnWriteArrayList<StructureFeature> barnFeatures = new CopyOnWriteArrayList<StructureFeature>(StructureFeature.class);
		CopyOnWriteArrayList<StructurePath> barnPaths = new CopyOnWriteArrayList<StructurePath>(StructurePath.class);
		CopyOnWriteArrayList<StructureSection> barnSections = new CopyOnWriteArrayList<StructureSection>(StructureSection.class);
		CopyOnWriteArrayList<StructureRoom> barnRooms = new CopyOnWriteArrayList<StructureRoom>(StructureRoom.class);
		CopyOnWriteArrayList<Square> barnSquaresToRemove = new CopyOnWriteArrayList<Square>(Square.class);

		// Barn door
		Door barnDoor = Templates.DOOR.makeCopy("Door", Game.level.squares[21][95], false, false, false, farmer);
		barnFeatures.add(new StructureFeature(barnDoor));

		barnSection = new StructureSection("Barn", 14, 95, 27, 110, false, false, farmer);
		barnRoom = new StructureRoom("Barn", 15, 96, false, false, new CopyOnWriteArrayList<Actor>(Actor.class),
				new RoomPart(15, 96, 26, 109));

		barnSections.add(barnSection);
		barnRooms.add(barnRoom);

		barnStructure = new Structure("Farm House", barnSections, barnRooms, barnPaths, barnFeatures,
				new CopyOnWriteArrayList<Square>(Square.class), Templates.PIG.imageTexture, 14, 95, 27, 110, true, farmer, barnSquaresToRemove,
				barnWalls, Templates.WALL_CAVE, Square.STONE_TEXTURE, 3);

		Game.level.structures.add(barnStructure);
		// END OF BARN

		well.inventory.add(Templates.GOLD.makeCopy(null, farmer, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, farmer, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, farmer, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, farmer, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, farmer, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, farmer, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, farmer, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, farmer, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, farmer, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, farmer, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, farmer, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, farmer, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, farmer, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, farmer, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, farmer, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, farmer, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, farmer, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, farmer, 1));
		well.inventory.add(Templates.GOLD.makeCopy(null, farmer, 1));

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
