package com.marklynch.level.quest.caveoftheblind;

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
import com.marklynch.objects.Bed;
import com.marklynch.objects.Corpse;
import com.marklynch.objects.Door;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Junk;
import com.marklynch.objects.Key;
import com.marklynch.objects.Readable;
import com.marklynch.objects.Templates;
import com.marklynch.objects.Wall;
import com.marklynch.objects.tools.Lantern;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Weapon;

public class QuestCaveOfTheBlind extends Quest {

	// Quest text
	final String OBJECTIVE_FOLLOW_THE_HUNTERS_TO_SUPERWOLF = "SHOULD NOT BE VISIBLE";

	// Activity Strings
	final String ACTIVITY_PLANNING_A_HUNT = "Planning a hunt";

	// Flags
	boolean talkedToMort = false;
	boolean feedingDemoAvailable = true;
	int playerMinedOres = 0;

	// End
	boolean huntersReleasedFromQuest;

	boolean huntersAndWolvesFought = false;

	// Structure sections
	StructureRoom westEntrance;
	StructureRoom westAtriumPart1;
	StructureRoom westAtriumPart2;
	StructureRoom westSecurity1;
	StructureRoom outerMine;
	StructureRoom recRoom;
	StructureRoom quarters;
	StructureRoom dungeon;
	StructureRoom toilet;
	StructureRoom caveIn;
	StructureRoom equipment;
	StructureRoom diningRoom;
	StructureRoom kitchen;
	StructureRoom eastSecurity;
	StructureRoom eastAtrium;
	StructureRoom eastEntrance;
	// StructureSection mortsMine;
	// StructureSection mortsRoom;

	// Actors
	Mort mort;

	// Blind
	ArrayList<Blind> blind = new ArrayList<Blind>();

	// GameObjects
	Junk ore;
	Weapon serratedSpoon;
	Lantern lantern;
	Wall oreWall;
	Key mortsKey;
	Door mortsBedroomDoor;
	Door mortsStoreroomDoor;

	// Squares
	Square troughSquare;
	Square safeSquare;

	// Structure Areas

	// Conversations
	ConversationForMort conversationForMort;

	public QuestCaveOfTheBlind() {
		super();

		// Mort and his bed
		Bed mortsBed = Templates.BED.makeCopy(Game.level.squares[135][24]);
		mortsBed.quest = this;
		mortsKey = Templates.KEY.makeCopy(null, mort);
		mortsKey.quest = this;
		// [147][21]
		mort = Templates.MORT.makeCopy(Game.level.squares[81][41], Game.level.factions.get(1), mortsBed);
		mort.quest = this;
		mort.mortsBell = Templates.DINNER_BELL.makeCopy(null, mort);
		mort.mortsMeatChunk = Templates.MEAT_CHUNK.makeCopy("Meat Chunk", null, null);
		mort.inventory.add(Templates.CLEAVER.makeCopy(null, mort));
		mort.inventory.add(Templates.LANTERN.makeCopy(null, mort));
		mort.inventory.add(Templates.PICKAXE.makeCopy(null, mort));
		mort.inventory.add(mortsKey);
		mort.inventory.add(mort.mortsBell);
		mort.inventory.add(mort.mortsMeatChunk);
		mort.questCaveOfTheBlind = this;

		for (GameObject gameObject : mort.inventory.getGameObjects()) {
			gameObject.quest = this;
		}

		// Cave
		makeMortsStorage();
		makeCave();
		makeMortsMine();
		makeMortsRoom();
		makeBlind();

		Readable rockWithEtching = Templates.ROCK_WITH_ETCHING.makeCopy(Game.level.squares[44][15], "Rock with message",
				new Object[] { "Stay out or The Blind will get you! -Mort" }, null);

		// Add spoons
		serratedSpoon = Templates.SERRATED_SPOON.makeCopy(Game.level.squares[44][11], null);
		serratedSpoon.quest = this;

		// Conversations
		conversationForMort = new ConversationForMort(this);
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
		if (actor == mort) {
			conversationForMort.setup();
			talkedToMort = true;
			return conversationForMort;
		}
		return null;
	}

	public void makeCave() {
		ArrayList<Wall> extraWalls = new ArrayList<Wall>();
		ArrayList<GameObject> caveFeatures = new ArrayList<GameObject>();
		ArrayList<StructurePath> cavePaths = new ArrayList<StructurePath>();
		ArrayList<StructureSection> caveSections = new ArrayList<StructureSection>();
		ArrayList<StructureRoom> rooms = new ArrayList<StructureRoom>();

		// West Entrance section
		caveSections.add(new StructureSection("Mort & Mort Mining", 24, 6, 40, 20));
		// West entrance room
		westEntrance = new StructureRoom("West Entrance", 25, 13, new RoomPart(24, 13, 37, 18));
		rooms.add(westEntrance);

		// Path west entrance to west atrium
		cavePaths.add(new StructurePath("West Entrance <-> West Atrium", Game.level.squares[38][15],
				Game.level.squares[39][15], Game.level.squares[40][15], Game.level.squares[41][15],
				Game.level.squares[42][15]));

		// West atrium section
		caveSections.add(new StructureSection("Mort & Mort Mining", 41, 14, 49, 19));
		caveSections.add(new StructureSection("Mort & Mort Mining", 41, 5, 49, 13));
		// West Atrium room
		westAtriumPart1 = new StructureRoom("West Atrium", 43, 9, new RoomPart(43, 9, 47, 15));
		rooms.add(westAtriumPart1);
		westAtriumPart2 = new StructureRoom("West Atrium", 43, 7, new RoomPart(43, 7, 47, 8));
		rooms.add(westAtriumPart2);

		// West Atrium extras
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[43][7], null));

		// Path West Atrium to West Security
		cavePaths.add(new StructurePath("West Atrium <-> West Security", Game.level.squares[48][7],
				Game.level.squares[49][7], Game.level.squares[50][7], Game.level.squares[51][7]));

		// West Security section
		caveSections.add(new StructureSection("Mort & Mort Mining", 44, 0, 62, 18));
		// West Security room
		westSecurity1 = new StructureRoom("West Security", 52, 4, new RoomPart(52, 4, 59, 9),
				new RoomPart(54, 10, 60, 17));
		rooms.add(westSecurity1);

		// West security extras
		extraWalls.add(Templates.WALL.makeCopy(Game.level.squares[52][9], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[59][5], null));
		caveFeatures.add(Templates.BROKEN_LAMP.makeCopy(Game.level.squares[53][9], null));
		caveFeatures.add(Templates.BARRICADE.makeCopy(Game.level.squares[54][10], null));
		caveFeatures.add(Templates.BARRICADE.makeCopy(Game.level.squares[55][10], null));
		caveFeatures.add(Templates.BARRICADE.makeCopy(Game.level.squares[56][10], null));
		ArrayList<Key> keysForMortsDoor = new ArrayList<Key>();
		keysForMortsDoor.add(mortsKey);
		keysForMortsDoor.add((Key) Game.level.player.inventory.getGameObectOfClass(Key.class));
		caveFeatures.add(Templates.WEAK_WOODEN_DOOR.makeCopy(Game.level.squares[57][10], keysForMortsDoor, true, null));
		caveFeatures.add(Templates.BARRICADE.makeCopy(Game.level.squares[58][10], null));
		caveFeatures.add(Templates.BARRICADE.makeCopy(Game.level.squares[59][10], null));
		caveFeatures.add(Templates.BARRICADE.makeCopy(Game.level.squares[60][10], null));

		GameObject securityTable = Templates.TABLE.makeCopy(Game.level.squares[58][9], null);
		Readable securityDocuments = Templates.DOCUMENTS.makeCopy(Game.level.squares[58][9], "Ingoing/Outgoing",
				new Object[] {
						"In: 1.5kg Grain, 38x Steak, 38x Grapefruit, 1x Replacement pickaxe Out: .2kg Gold Ore, .3kg Silver Ore, 2kg General Waste, 10kg Fecal Matter" },
				null);
		// securityTable.inventory.add(glassForTable);
		caveFeatures.add(securityDocuments);
		caveFeatures.add(securityTable);

		// West Security to outer mine y 17 -> 21 55+56x
		cavePaths.add(new StructurePath("West Security <-> Outer Mine", Game.level.squares[55][17],
				Game.level.squares[55][18], Game.level.squares[55][19], Game.level.squares[55][20],
				Game.level.squares[55][21], Game.level.squares[56][17], Game.level.squares[56][18],
				Game.level.squares[56][19], Game.level.squares[56][20], Game.level.squares[56][21]));

		// Outer mine section
		caveSections.add(new StructureSection("Mort & Mort Mining", 45, 19, 74, 37));
		// Outer Mine room
		outerMine = new StructureRoom("Outer Mine", 55, 22, new RoomPart(55, 22, 73, 23), new RoomPart(55, 24, 66, 36));
		rooms.add(outerMine);

		// Outer Mine extra
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[55][30], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[60][29], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[60][30], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[66][33], null));

		// Path outer mine to rec room
		cavePaths.add(
				new StructurePath("Outer Mine <-> Rec Room", Game.level.squares[67][36], Game.level.squares[68][36],
						Game.level.squares[69][36], Game.level.squares[70][36], Game.level.squares[70][35],
						Game.level.squares[70][34], Game.level.squares[70][33], Game.level.squares[70][32],
						Game.level.squares[70][31], Game.level.squares[71][31], Game.level.squares[72][31],
						Game.level.squares[73][31], Game.level.squares[74][31], Game.level.squares[75][31]));

		// Rec room section
		caveSections.add(new StructureSection("Mort & Mort Mining", 75, 25, 91, 33));
		// Rec room room
		recRoom = new StructureRoom("Rec Room", 76, 26, new RoomPart(76, 26, 90, 32));
		rooms.add(recRoom);

		// REc room extras
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[85][26], null));

		// Tunnel section rec room to dungeon
		caveSections.add(new StructureSection("Mort & Mort Mining", 92, 25, 98, 27));
		caveSections.add(new StructureSection("Mort & Mort Mining", 94, 7, 105, 24));
		caveSections.add(new StructureSection("Mort & Mort Mining", 75, 2, 93, 10));

		// Path rec room to dungeon
		cavePaths.add(new StructurePath("Rec Room <-> Dungeon", Game.level.squares[91][26], Game.level.squares[92][26],
				Game.level.squares[93][26], Game.level.squares[94][26], Game.level.squares[95][26],
				Game.level.squares[95][25], Game.level.squares[95][24], Game.level.squares[95][23],
				Game.level.squares[95][22], Game.level.squares[95][21], Game.level.squares[95][20],
				Game.level.squares[95][19], Game.level.squares[95][18], Game.level.squares[95][17],
				Game.level.squares[95][16], Game.level.squares[95][15], Game.level.squares[95][14],
				Game.level.squares[95][13], Game.level.squares[95][12], Game.level.squares[95][11],
				Game.level.squares[95][10], Game.level.squares[95][9], Game.level.squares[94][9],
				Game.level.squares[93][9], Game.level.squares[92][9], Game.level.squares[91][9],
				Game.level.squares[90][9], Game.level.squares[89][9], Game.level.squares[88][9],
				Game.level.squares[87][9], Game.level.squares[86][9], Game.level.squares[85][9],
				Game.level.squares[84][9], Game.level.squares[83][9], Game.level.squares[82][9],
				Game.level.squares[81][9], Game.level.squares[80][9], Game.level.squares[79][9],
				Game.level.squares[78][9], Game.level.squares[77][9], Game.level.squares[76][9],
				Game.level.squares[75][9], Game.level.squares[74][9]));

		// Dungeon section
		caveSections.add(new StructureSection("Mort & Mort Mining", 63, 2, 74, 18));

		// Dungeon room
		dungeon = new StructureRoom("Dungeon", 66, 7, new RoomPart(65, 9, 71, 12), new RoomPart(66, 7, 73, 15),
				new RoomPart(68, 10, 72, 16));
		rooms.add(dungeon);

		// Dungeon extras
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[66][7], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[67][7], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[68][7], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[69][7], null));

		// Path rec room to toilet
		cavePaths.add(new StructurePath("Rec Room <-> Lavatory", Game.level.squares[91][28], Game.level.squares[92][28],
				Game.level.squares[93][28], Game.level.squares[94][28], Game.level.squares[95][28],
				Game.level.squares[96][28]));

		// Toilet section
		caveSections.add(new StructureSection("Lavatory", 92, 20, 105, 33));

		// toilet room
		toilet = new StructureRoom("Lavatory", 96, 29, new RoomPart(96, 29, 98, 30), new RoomPart(99, 29, 103, 31),
				new RoomPart(68, 10, 72, 16));
		rooms.add(toilet);

		// toilet extras
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[103][31], null));

		// Path rec room to Caved In Corridor
		cavePaths.add(new StructurePath("Caved In Corridor", Game.level.squares[89][33]));

		// Caved In Corridor section
		caveSections.add(new StructureSection("Mort & Mort Mining", 88, 33, 97, 37));

		// Caved In Corridor room
		caveIn = new StructureRoom("Caved In Corridor", 89, 35, new RoomPart(89, 34, 94, 35),
				new RoomPart(91, 36, 95, 36));
		rooms.add(caveIn);

		// Path Caved In Corridor to dining room
		cavePaths.add(new StructurePath("Caved In Corridor", Game.level.squares[95][37], Game.level.squares[95][38]));

		// Path inner mine to dining room
		cavePaths.add(new StructurePath("Inner Mine <-> Dining Room", Game.level.squares[87][46],
				Game.level.squares[88][46]));

		// Dining room section
		caveSections.add(new StructureSection("Dining Room", 88, 38, 99, 55));

		// Dining room
		diningRoom = new StructureRoom("Dining Room", 89, 39, new RoomPart(89, 39, 97, 54));
		rooms.add(diningRoom);

		// Dining room extras
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[97][39], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[97][40], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[89][49], null));

		// Path rec room to quarters
		cavePaths.add(
				new StructurePath("Rec Room <-> Quarters", Game.level.squares[83][24], Game.level.squares[83][25]));

		// Section Quarters
		caveSections.add(new StructureSection("Mort & Mort Mining", 75, 11, 93, 24));
		// Room Quarters
		quarters = new StructureRoom("Quarters", 76, 12, new RoomPart(76, 12, 88, 21), new RoomPart(79, 13, 92, 22),
				new RoomPart(81, 13, 92, 23));
		rooms.add(quarters);

		// Quarters extras
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[92][13], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[83][17], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[83][18], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[84][17], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[84][18], null));

		// Path rec room to quarters
		cavePaths.add(
				new StructurePath("Rec Room <-> Inner Mine", Game.level.squares[85][33], Game.level.squares[85][34]));

		// Morts Mine Section
		caveSections.add(new StructureSection("Mort & Mort Mining", 73, 34, 87, 50));
		// Morts Mine Room
		mort.mortsMine = new StructureRoom("Inner Mine", 76, 36, new RoomPart(78, 35, 86, 47),
				new RoomPart(76, 36, 86, 46), new RoomPart(77, 41, 84, 48));
		rooms.add(mort.mortsMine);

		// Inner mine extras
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[80][35], mort));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[81][35], mort));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[86][39], mort));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[86][47], mort));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[80][43], mort));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[81][44], mort));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[83][38], mort));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[86][37], mort));

		// Morts Rooms Section
		caveSections.add(new StructureSection("Mort & Mort Mining", 43, 36, 75, 49));

		// Morts Quarters
		mort.mortsRoom = new StructureRoom("Management", 65, 39, new RoomPart(65, 39, 74, 42));
		rooms.add(mort.mortsRoom);

		// Morts Vault Room
		rooms.add(new StructureRoom("Vault", 49, 39, new RoomPart(49, 39, 63, 42)));

		// Cave featues for Mort
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[76][45], mort));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[76][46], mort));
		mortsBedroomDoor = Templates.DOOR.makeCopy(Game.level.squares[75][40], keysForMortsDoor, true, mort);
		caveFeatures.add(mortsBedroomDoor);
		mortsStoreroomDoor = Templates.DOOR.makeCopy(Game.level.squares[64][40], keysForMortsDoor, true, mort);
		caveFeatures.add(mortsStoreroomDoor);

		// Path Dining room to equipment room
		cavePaths.add(new StructurePath("Dining Room <-> Equipment", Game.level.squares[86][51],
				Game.level.squares[87][51], Game.level.squares[88][51]));

		// Path Inner Mine to Equipment room
		cavePaths.add(
				new StructurePath("Inner Mine <-> Equipment", Game.level.squares[85][50], Game.level.squares[85][51],
						Game.level.squares[84][51], Game.level.squares[84][50], Game.level.squares[84][49]));

		// Equipment section
		caveSections.add(new StructureSection("Equipment", 74, 51, 87, 58));

		// Equipment room
		equipment = new StructureRoom("Equipment", 76, 52, new RoomPart(76, 52, 85, 55));
		rooms.add(equipment);

		// Equipment extras
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[76][55], null));

		// Path Dining Room <-> Kitchen
		cavePaths.add(new StructurePath("Dining Room <-> Kitchen", Game.level.squares[93][55],
				Game.level.squares[93][56], Game.level.squares[93][57]));

		// Kitchen section
		caveSections.add(new StructureSection("Kitchen", 80, 56, 110, 75));

		// Kitchen room
		kitchen = new StructureRoom("Kitchen", 92, 58, new RoomPart(92, 58, 97, 69), new RoomPart(93, 59, 99, 72));
		rooms.add(kitchen);

		// Kitchen extras
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[99][59], null));

		// Path Dining Room <-> East Security
		cavePaths.add(new StructurePath("Dining Room <-> East Security", Game.level.squares[98][51],
				Game.level.squares[99][51], Game.level.squares[99][52], Game.level.squares[99][53],
				Game.level.squares[100][53], Game.level.squares[101][53]));

		// East Security section
		caveSections.add(new StructureSection("East Security", 99, 43, 115, 56));

		// East Security room
		eastSecurity = new StructureRoom("East Security", 101, 45, new RoomPart(101, 45, 106, 50),
				new RoomPart(102, 46, 105, 54));
		rooms.add(eastSecurity);

		// East security extras
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[102][46], null));

		// East Security <-> East Atrium
		cavePaths.add(new StructurePath("East Security <-> East Atrium", Game.level.squares[107][45],
				Game.level.squares[108][45], Game.level.squares[109][45], Game.level.squares[110][45]));

		// East Atrium Section
		caveSections.add(new StructureSection("East Atrium", 108, 36, 117, 46));

		// East Atrium Room
		eastAtrium = new StructureRoom("East Atrium", 110, 40, new RoomPart(113, 37, 115, 40),
				new RoomPart(110, 40, 114, 44));
		rooms.add(eastAtrium);

		// East Atrium Extras
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[112][42], null));

		// East Atrium <-> East Entrance
		cavePaths.add(new StructurePath("East Atrium <-> East Entrance", Game.level.squares[115][34],
				Game.level.squares[115][35], Game.level.squares[115][36]));

		// East Entrance
		caveSections.add(new StructureSection("East Entrance", 107, 20, 121, 35));

		// East Entrance
		eastEntrance = new StructureRoom("East Entrance", 109, 30, new RoomPart(109, 30, 117, 33),
				new RoomPart(110, 30, 121, 30));
		rooms.add(eastEntrance);

		ArrayList<Square> squaresToRemove = new ArrayList<Square>();
		squaresToRemove.add(Game.level.squares[24][6]);
		squaresToRemove.add(Game.level.squares[24][7]);
		squaresToRemove.add(Game.level.squares[24][8]);
		squaresToRemove.add(Game.level.squares[24][9]);
		squaresToRemove.add(Game.level.squares[24][10]);

		Game.level.structures.add(new Structure("Mort & Mort Mining", caveSections, rooms, cavePaths, caveFeatures,
				new ArrayList<Square>(), null, 0, 0, 0, 0, true, mort, squaresToRemove, extraWalls));

		// Dirty Sheet
		// Templates.DIRTY_SHEET.makeCopy(Game.level.squares[47][11]);
		// Templates.DIRTY_SHEET_2.makeCopy(Game.level.squares[47][10]);
		Templates.DIRTY_SHEET_3.makeCopy(Game.level.squares[47][9], null);
		// Smashed Glass
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[43][12], null);
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[44][11], null);
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[44][12], null);
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[45][11], null);
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[45][12], null);
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[45][13], null);
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[46][12], null);
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[47][9], null);
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[47][10], null);
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[47][11], null);
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[47][12], null);

	}

	public void makeMortsMine() {

		Readable noEntry = Templates.SIGN.makeCopy(Game.level.squares[76][39], "Sign",
				new Object[] { "PRIVATE! - Mort" }, mort);
		noEntry.quest = this;

		GameObject trough = Templates.TROUGH.makeCopy(Game.level.squares[86][43], mort);
		troughSquare = Game.level.squares[86][43];
		safeSquare = Game.level.squares[76][42];

	}

	public void makeMortsRoom() {
	}

	public void makeMortsStorage() {

		GameObject outsideBlood1 = Templates.BLOOD.makeCopy(Game.level.squares[49][39], null);
		GameObject outsideBlood2 = Templates.BLOOD.makeCopy(Game.level.squares[48][39], null);
		GameObject outsideBlood3 = Templates.BLOOD.makeCopy(Game.level.squares[47][39], null);
		GameObject outsideBlood4 = Templates.BLOOD.makeCopy(Game.level.squares[46][39], null);
		GameObject outsideBlood5 = Templates.BLOOD.makeCopy(Game.level.squares[45][39], null);
		GameObject outsideBlood6 = Templates.BLOOD.makeCopy(Game.level.squares[44][39], null);
		GameObject outsideBlood7 = Templates.BLOOD.makeCopy(Game.level.squares[43][39], null);
		GameObject blood = Templates.BLOOD.makeCopy(Game.level.squares[24][21], null);
		Corpse carcass1 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[49][39], null);
		Corpse carcass2 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[49][39], null);
		Corpse carcass3 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[49][39], null);
		Corpse carcass4 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[50][39], null);
		Corpse carcass5 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[50][39], null);
		Corpse carcass6 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[49][40], null);
		Corpse carcass7 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[49][41], null);
		ore = Templates.ORE.makeCopy(Game.level.squares[51][41], mort);
		lantern = Templates.LANTERN.makeCopy(Game.level.squares[52][41], mort);
		GameObject table = Templates.TABLE.makeCopy(Game.level.squares[53][41], mort);

		// Link to the quest to keep them safe
		blood.quest = this;
		carcass1.quest = this;
		carcass2.quest = this;
		carcass3.quest = this;
		carcass4.quest = this;
		carcass5.quest = this;
		carcass6.quest = this;
		carcass7.quest = this;
		ore.quest = this;
		lantern.quest = this;
		table.quest = this;
	}

	public void makeBlind() {

		// Entrance 2
		Blind blind11 = Templates.BLIND.makeCopy(Game.level.squares[46][7], Game.level.factions.get(3),
				westAtriumPart2);
		blind11.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind11.quest = this;
		blind.add(blind11);

		// East security
		Blind blindEastSecurity1 = Templates.BLIND.makeCopy(Game.level.squares[60][12], Game.level.factions.get(3),
				westSecurity1);
		blindEastSecurity1.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blindEastSecurity1.quest = this;
		blind.add(blindEastSecurity1);

		Blind blindEastSecurity2 = Templates.BLIND.makeCopy(Game.level.squares[60][15], Game.level.factions.get(3),
				westSecurity1);
		blindEastSecurity2.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blindEastSecurity2.quest = this;
		blind.add(blindEastSecurity2);

		Blind blindEastSecurity3 = Templates.BLIND.makeCopy(Game.level.squares[54][6], Game.level.factions.get(3),
				westSecurity1);
		blindEastSecurity3.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blindEastSecurity3.quest = this;
		blind.add(blindEastSecurity3);

		Blind blindEastSecurity4 = Templates.BLIND.makeCopy(Game.level.squares[57][5], Game.level.factions.get(3),
				westSecurity1);
		blindEastSecurity4.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blindEastSecurity4.quest = this;
		blind.add(blindEastSecurity4);

		// Atrium 2
		Blind blind3 = Templates.BLIND.makeCopy(Game.level.squares[55][23], Game.level.factions.get(3), outerMine);
		blind3.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind3.quest = this;
		blind.add(blind3);

		Blind blind4 = Templates.BLIND.makeCopy(Game.level.squares[60][25], Game.level.factions.get(3), outerMine);
		blind4.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind4.quest = this;
		blind.add(blind4);

		Blind blind5 = Templates.BLIND.makeCopy(Game.level.squares[55][24], Game.level.factions.get(3), outerMine);
		blind5.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind5.quest = this;
		blind.add(blind5);

		Blind blind6 = Templates.BLIND.makeCopy(Game.level.squares[59][25], Game.level.factions.get(3), outerMine);
		blind6.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind6.quest = this;
		blind.add(blind6);

		Blind blind7 = Templates.BLIND.makeCopy(Game.level.squares[60][26], Game.level.factions.get(3), outerMine);
		blind7.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind7.quest = this;
		blind.add(blind7);

		Blind blind8 = Templates.BLIND.makeCopy(Game.level.squares[57][24], Game.level.factions.get(3), outerMine);
		blind8.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind8.quest = this;
		blind.add(blind8);

		Blind blind9 = Templates.BLIND.makeCopy(Game.level.squares[56][25], Game.level.factions.get(3), outerMine);
		blind9.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind9.quest = this;
		blind.add(blind9);

		Blind blind10 = Templates.BLIND.makeCopy(Game.level.squares[57][27], Game.level.factions.get(3), outerMine);
		blind10.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind10.quest = this;
		blind.add(blind10);

		// Rec Room Blind
		Blind blindRecRoom1 = Templates.BLIND.makeCopy(Game.level.squares[78][28], Game.level.factions.get(3), recRoom);
		blind10.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind10.quest = this;
		blind.add(blindRecRoom1);

		Blind blindRecRoom2 = Templates.BLIND.makeCopy(Game.level.squares[78][30], Game.level.factions.get(3), recRoom);
		blind10.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind10.quest = this;
		blind.add(blindRecRoom2);

		Blind blindRecRoom3 = Templates.BLIND.makeCopy(Game.level.squares[79][30], Game.level.factions.get(3), recRoom);
		blind10.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind10.quest = this;
		blind.add(blindRecRoom3);

		Blind blindRecRoom4 = Templates.BLIND.makeCopy(Game.level.squares[80][31], Game.level.factions.get(3), recRoom);
		blind10.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind10.quest = this;
		blind.add(blindRecRoom4);

		Blind blindRecRoom5 = Templates.BLIND.makeCopy(Game.level.squares[81][29], Game.level.factions.get(3), recRoom);
		blind10.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind10.quest = this;
		blind.add(blindRecRoom5);

		// Dining Room Blind
		Blind blindDiningRoom1 = Templates.BLIND.makeCopy(Game.level.squares[91][42], Game.level.factions.get(3),
				diningRoom);
		blind10.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind10.quest = this;
		blind.add(blindDiningRoom1);

		Blind blindDiningRoom2 = Templates.BLIND.makeCopy(Game.level.squares[92][49], Game.level.factions.get(3),
				diningRoom);
		blind10.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind10.quest = this;
		blind.add(blindDiningRoom2);

		Blind blindDiningRoom3 = Templates.BLIND.makeCopy(Game.level.squares[93][46], Game.level.factions.get(3),
				diningRoom);
		blind10.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind10.quest = this;
		blind.add(blindDiningRoom3);

		Blind blindDiningRoom4 = Templates.BLIND.makeCopy(Game.level.squares[94][43], Game.level.factions.get(3),
				diningRoom);
		blind10.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind10.quest = this;
		blind.add(blindDiningRoom4);

		Blind blindDiningRoom5 = Templates.BLIND.makeCopy(Game.level.squares[95][45], Game.level.factions.get(3),
				diningRoom);
		blind10.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind10.quest = this;
		blind.add(blindDiningRoom5);

		Blind blindDiningRoom6 = Templates.BLIND.makeCopy(Game.level.squares[93][44], Game.level.factions.get(3),
				diningRoom);
		blind10.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind10.quest = this;
		blind.add(blindDiningRoom6);

		// Templates.MEAT_CHUNK.makeCopy("Meat Chunk",
		// Game.level.squares[57][28]);
		// Templates.MEAT_CHUNK.makeCopy("Meat Chunk",
		// Game.level.squares[57][25]);
		// Templates.MEAT_CHUNK.makeCopy("Meat Chunk",
		// Game.level.squares[58][24]);
		// Templates.MEAT_CHUNK.makeCopy("Meat Chunk",
		// Game.level.squares[59][28]);
		// Templates.MEAT_CHUNK.makeCopy("Meat Chunk",
		// Game.level.squares[52][28]);
		// Templates.MEAT_CHUNK.makeCopy("Meat Chunk",
		// Game.level.squares[51][28]);
	}

	public void makeConversation() {

	}

}
