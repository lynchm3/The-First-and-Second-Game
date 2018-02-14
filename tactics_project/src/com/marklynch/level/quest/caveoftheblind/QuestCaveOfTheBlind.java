package com.marklynch.level.quest.caveoftheblind;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructurePath;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.constructs.bounds.structure.StructureRoom.RoomPart;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.constructs.journal.JournalLog;
import com.marklynch.level.constructs.journal.Objective;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Corpse;
import com.marklynch.objects.Door;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Junk;
import com.marklynch.objects.Key;
import com.marklynch.objects.Readable;
import com.marklynch.objects.Searchable;
import com.marklynch.objects.Storage;
import com.marklynch.objects.Wall;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.Lantern;
import com.marklynch.objects.tools.Pickaxe;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.RockGolem;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.utils.TextUtils;

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
	Door mortsGameRoomDoor;
	Door mortsStoreroomDoor;
	Searchable dropHole;

	// Squares
	Square troughSquare;
	Square safeSquare;

	// Structure Areas

	// Conversations
	ConversationForMort conversationForMort;

	Key alsKey;
	Key joesKey;
	Key seansKey;
	Key paulsKey;

	Structure cave;

	public Objective objectiveCave;
	public Objective objectiveHunters;

	public JournalLog journalLogHeardFromHunters = new JournalLog(
			"I heard from some hunters about a now defunct mining operation to the east of town, past the forest, it might be worth having a look.");
	JournalLog journalLogArrivedAtMine = new JournalLog("I've arrived at Mort and Mort Mining");

	public QuestCaveOfTheBlind() {
		super();
		name = "CAVE OF THE BLIND";

		// Mort and his bed
		GameObject mortsGameObject = Templates.BED.makeCopy(Game.level.squares[267][42], null);
		mortsGameObject.quest = this;
		mortsKey = Templates.KEY.makeCopy("Mort's Key", null, mort);
		mortsKey.quest = this;
		// [147][21]
		mort = Templates.MORT.makeCopy("Mort", Game.level.squares[281][41], Game.level.factions.townsPeople,
				mortsGameObject, 100, new GameObject[] {}, new GameObject[] {}, null);
		mort.quest = this;
		mort.mortsBell = Templates.DINNER_BELL.makeCopy(null, mort);
		mort.mortsMeatChunk = Templates.MEAT_CHUNK.makeCopy(null, null);
		mort.inventory.add(Templates.CLEAVER.makeCopy(null, mort));
		mort.inventory.add(Templates.LANTERN.makeCopy(null, mort));
		mort.inventory.add(Templates.PICKAXE.makeCopy(null, mort));
		mort.inventory.add(mortsKey);
		mort.inventory.add(mort.mortsBell);
		mort.inventory.add(mort.mortsMeatChunk);
		mort.questCaveOfTheBlind = this;

		// Hide Quarters keys
		// Als Key - in management confiscated desk
		alsKey = Templates.KEY.makeCopy("Al's Key", null, mort);
		// On blind3 in dining room
		seansKey = Templates.KEY.makeCopy("Sean's Key", null, null);
		// In latrine drop hole
		paulsKey = Templates.KEY.makeCopy("Paul's Key", null, null);
		// Key on dining table
		joesKey = Templates.KEY.makeCopy("Joe's Key", null, null);

		for (GameObject gameObject : mort.inventory.getGameObjects()) {
			gameObject.quest = this;
		}

		// Cave
		makeMortsStorage();
		makeCave();
		makeMortsMine();
		makeMortsRoom();
		makeBlind();

		Game.level.squares[294][45].inventory.add(joesKey);
		dropHole.inventory.add(paulsKey);

		Readable rockWithEtching = Templates.ROCK_WITH_ETCHING.makeCopy(Game.level.squares[244][15],
				"Rock with etching", new Object[] { "Stay out or The Blind will get you! -Mort" }, null);

		// Add spoons
		serratedSpoon = Templates.SERRATED_SPOON.makeCopy(Game.level.squares[244][11], null);
		serratedSpoon.quest = this;

		// Conversations
		conversationForMort = new ConversationForMort(this);

		// Objectives
		objectiveCave = new Objective("Cave", null, Game.level.squares[281][41], cave.image);
		allObjectives.add(objectiveCave);

		links = TextUtils.getLinks(true, this);

		AreaMinorMine.createMine();
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
		ArrayList<Square> squaresToRemove = new ArrayList<Square>();

		// West Entrance section
		caveSections.add(new StructureSection("Mort & Mort Mining", 224, 6, 240, 20, false));
		// West entrance room
		westEntrance = new StructureRoom("West Entrance", 225, 13, false, new ArrayList<Actor>(),
				new RoomPart(224, 13, 237, 18));
		rooms.add(westEntrance);

		// Path west entrance to west atrium
		cavePaths.add(new StructurePath("West Entrance <-> West Atrium", false, new ArrayList<Actor>(),
				Game.level.squares[238][15], Game.level.squares[239][15], Game.level.squares[240][15],
				Game.level.squares[241][15], Game.level.squares[242][15]));

		// West atrium section
		caveSections.add(new StructureSection("Mort & Mort Mining", 241, 14, 249, 19, false));
		caveSections.add(new StructureSection("Mort & Mort Mining", 241, 5, 249, 13, false));
		// West Atrium room
		westAtriumPart1 = new StructureRoom("West Atrium", 243, 9, false, new ArrayList<Actor>(),
				new RoomPart(243, 9, 247, 15));
		rooms.add(westAtriumPart1);
		westAtriumPart2 = new StructureRoom("West Atrium", 243, 7, false, new ArrayList<Actor>(),
				new RoomPart(243, 7, 247, 8));
		rooms.add(westAtriumPart2);

		// West Atrium extras
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[243][7], null));

		// Path West Atrium to West Security
		cavePaths.add(new StructurePath("West Atrium <-> West Security", false, new ArrayList<Actor>(),
				Game.level.squares[248][7], Game.level.squares[249][7], Game.level.squares[250][7],
				Game.level.squares[251][7]));

		// West Security section
		caveSections.add(new StructureSection("Mort & Mort Mining", 244, 0, 262, 18, false));
		// West Security room
		westSecurity1 = new StructureRoom("West Security", 252, 4, false, new ArrayList<Actor>(),
				new RoomPart(252, 4, 259, 9), new RoomPart(254, 10, 260, 17));
		rooms.add(westSecurity1);

		// West security extras
		extraWalls.add(Templates.WALL.makeCopy(Game.level.squares[252][9], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[259][5], null));
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[253][9], null);
		caveFeatures.add(Templates.BARRICADE.makeCopy(Game.level.squares[254][10], null));
		caveFeatures.add(Templates.BARRICADE.makeCopy(Game.level.squares[255][10], null));
		caveFeatures.add(Templates.BARRICADE.makeCopy(Game.level.squares[256][10], null));
		caveFeatures.add(Templates.WEAK_WOODEN_DOOR.makeCopy("Security Door", Game.level.squares[257][10], true, true,
				true, null, mortsKey, (Key) Game.level.player.inventory.getGameObjectOfClass(Key.class)));
		caveFeatures.add(Templates.BARRICADE.makeCopy(Game.level.squares[258][10], null));
		caveFeatures.add(Templates.BARRICADE.makeCopy(Game.level.squares[259][10], null));
		caveFeatures.add(Templates.BARRICADE.makeCopy(Game.level.squares[260][10], null));
		Templates.TABLE.makeCopy(Game.level.squares[258][9], null);
		Templates.DOCUMENTS.makeCopy(Game.level.squares[258][9], "Ingoing/Outgoing",
				new Object[] {
						"In: 1.5kg Grain, 38x Steak, 38x Grapefruit, 1x Replacement pickaxe Out: .2kg Gold Ore, .3kg Silver Ore, 2kg General Waste, 10kg Fecal Matter" },
				null);
		Storage securityChest = Templates.CHEST.makeCopy("Security Chest", Game.level.squares[259][9], false, null);
		securityChest.inventory.add(Templates.PICKAXE.makeCopy(null, null));

		// West Security to outer mine y 17 -> 21 55+56x
		cavePaths.add(new StructurePath("West Security <-> Outer Mine", false, new ArrayList<Actor>(),
				Game.level.squares[255][17], Game.level.squares[255][18], Game.level.squares[255][19],
				Game.level.squares[255][20], Game.level.squares[255][21], Game.level.squares[256][17],
				Game.level.squares[256][18], Game.level.squares[256][19], Game.level.squares[256][20],
				Game.level.squares[256][21]));

		// Outer mine section
		caveSections.add(new StructureSection("Mort & Mort Mining", 245, 19, 274, 37, false));
		// Outer Mine room
		outerMine = new StructureRoom("Outer Mine", 255, 22, false, new ArrayList<Actor>(),
				new RoomPart(255, 22, 273, 23), new RoomPart(255, 24, 266, 36));
		rooms.add(outerMine);

		// Outer Mine extra
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[255][30], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[260][29], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[260][30], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[266][33], null));
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[256][20], null);

		// Path outer mine to rec room
		cavePaths.add(new StructurePath("Outer Mine <-> Rec Room", false, new ArrayList<Actor>(),
				Game.level.squares[267][36], Game.level.squares[268][36], Game.level.squares[269][36],
				Game.level.squares[270][36], Game.level.squares[270][35], Game.level.squares[270][34],
				Game.level.squares[270][33], Game.level.squares[270][32], Game.level.squares[270][31],
				Game.level.squares[271][31], Game.level.squares[272][31], Game.level.squares[273][31],
				Game.level.squares[274][31], Game.level.squares[275][31]));

		// Rec room section
		caveSections.add(new StructureSection("Mort & Mort Mining", 275, 25, 291, 33, false));
		// Rec room room
		recRoom = new StructureRoom("Rec Room", 276, 26, false, new ArrayList<Actor>(), new RoomPart(276, 26, 290, 32));
		rooms.add(recRoom);

		// REc room extras
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[285][26], null));

		// Tunnel section rec room to dungeon
		caveSections.add(new StructureSection("Mort & Mort Mining", 292, 25, 298, 27, false));
		caveSections.add(new StructureSection("Mort & Mort Mining", 294, 7, 305, 24, false));
		caveSections.add(new StructureSection("Mort & Mort Mining", 275, 2, 293, 10, false));

		// Path rec room to dungeon
		cavePaths.add(
				new StructurePath("Rec Room <-> Dungeon", false, new ArrayList<Actor>(), Game.level.squares[291][26],
						Game.level.squares[292][26], Game.level.squares[293][26], Game.level.squares[294][26],
						Game.level.squares[295][26], Game.level.squares[295][25], Game.level.squares[295][24],
						Game.level.squares[295][23], Game.level.squares[295][22], Game.level.squares[295][21],
						Game.level.squares[295][20], Game.level.squares[295][19], Game.level.squares[295][18],
						Game.level.squares[295][17], Game.level.squares[295][16], Game.level.squares[295][15],
						Game.level.squares[295][14], Game.level.squares[295][13], Game.level.squares[295][12],
						Game.level.squares[295][11], Game.level.squares[295][10], Game.level.squares[295][9],
						Game.level.squares[294][9], Game.level.squares[293][9], Game.level.squares[292][9],
						Game.level.squares[291][9], Game.level.squares[290][9], Game.level.squares[289][9],
						Game.level.squares[288][9], Game.level.squares[287][9], Game.level.squares[286][9],
						Game.level.squares[285][9], Game.level.squares[284][9], Game.level.squares[283][9],
						Game.level.squares[282][9], Game.level.squares[281][9], Game.level.squares[280][9],
						Game.level.squares[279][9], Game.level.squares[278][9], Game.level.squares[277][9],
						Game.level.squares[276][9], Game.level.squares[275][9], Game.level.squares[274][9]));

		// Dungeon section
		caveSections.add(new StructureSection("Mort & Mort Mining", 263, 2, 274, 18, false));

		// Dungeon room
		dungeon = new StructureRoom("Dungeon", 266, 7, false, new ArrayList<Actor>(), new RoomPart(265, 9, 271, 12),
				new RoomPart(266, 7, 273, 15), new RoomPart(268, 10, 272, 16));
		rooms.add(dungeon);

		// Dungeon extras
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[266][7], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[267][7], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[268][7], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[269][7], null));

		// Path rec room to toilet
		cavePaths.add(new StructurePath("Rec Room <-> Latrine", false, new ArrayList<Actor>(),
				Game.level.squares[291][28], Game.level.squares[292][28], Game.level.squares[293][28],
				Game.level.squares[294][28], Game.level.squares[295][28], Game.level.squares[296][28]));

		// Toilet section
		caveSections.add(new StructureSection("Latrine", 292, 20, 305, 33, false));

		// toilet room
		toilet = new StructureRoom("Latrine", 296, 29, false, new ArrayList<Actor>(), new RoomPart(296, 29, 298, 30),
				new RoomPart(299, 29, 303, 31), new RoomPart(268, 10, 272, 16));
		rooms.add(toilet);

		// toilet extras
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[303][31], null));
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[292][28], null);

		dropHole = Templates.DROP_HOLE.makeCopy(Game.level.squares[302][30], null);
		// DROP_HOLE

		// Path rec room to Caved In Corridor
		cavePaths.add(
				new StructurePath("Caved In Corridor", false, new ArrayList<Actor>(), Game.level.squares[289][33]));

		// Caved In Corridor section
		caveSections.add(new StructureSection("Mort & Mort Mining", 288, 33, 297, 37, false));

		// Caved In Corridor room
		caveIn = new StructureRoom("Caved In Corridor", 289, 35, false, new ArrayList<Actor>(),
				new RoomPart(289, 34, 294, 35), new RoomPart(291, 36, 295, 36));
		rooms.add(caveIn);

		// Cave in features
		Templates.BOULDER.makeCopy(Game.level.squares[290][34], null);
		Templates.BOULDER.makeCopy(Game.level.squares[291][34], null);
		Templates.BOULDER.makeCopy(Game.level.squares[293][34], null);
		Templates.CHEST.makeCopy("Chest", Game.level.squares[291][36], false, null);
		Templates.BOULDER.makeCopy(Game.level.squares[293][35], null);
		Templates.BOULDER.makeCopy(Game.level.squares[292][36], null);
		Templates.BOULDER.makeCopy(Game.level.squares[292][35], null);

		// A bush in cave in
		Templates.POISON_BUSH.makeCopy(Game.level.squares[289][35], null);
		Templates.POISON_BUSH.makeCopy(Game.level.squares[289][34], null);

		// Rock Golem
		RockGolem rockGolem = Templates.ROCK_GOLEM.makeCopy("Suspicious Boulder", Game.level.squares[291][35],
				Game.level.factions.rockGolems, caveIn, new GameObject[] {}, new GameObject[] {});

		// Path Caved In Corridor to dining room
		cavePaths.add(new StructurePath("Caved In Corridor", false, new ArrayList<Actor>(), Game.level.squares[295][37],
				Game.level.squares[295][38]));

		// Path inner mine to dining room
		cavePaths.add(new StructurePath("Inner Mine <-> Dining Room", false, new ArrayList<Actor>(),
				Game.level.squares[287][46], Game.level.squares[288][46]));

		// Dining room section
		caveSections.add(new StructureSection("Dining Room", 288, 38, 299, 55, false));

		// Dining room
		diningRoom = new StructureRoom("Dining Room", 289, 39, false, new ArrayList<Actor>(),
				new RoomPart(289, 39, 297, 54));
		rooms.add(diningRoom);

		// Dining room extras
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[297][39], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[297][40], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[289][49], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[298][37], null));

		// Floor stuff
		Templates.PLATE.makeCopy(Game.level.squares[296][44], null);
		Templates.PLATE.makeCopy(Game.level.squares[297][54], null);
		Templates.BROKEN_PLATE.makeCopy(Game.level.squares[290][53], null);
		Templates.BROKEN_PLATE.makeCopy(Game.level.squares[290][40], null);
		Templates.BROKEN_GLASS.makeCopy(Game.level.squares[293][39], null);
		Templates.DINNER_KNIFE.makeCopy(Game.level.squares[296][52], null);
		Templates.DINNER_KNIFE.makeCopy(Game.level.squares[291][51], null);
		Templates.DINNER_FORK.makeCopy(Game.level.squares[293][46], null);
		Templates.DINNER_FORK.makeCopy(Game.level.squares[294][52], null);
		Templates.DINNER_FORK.makeCopy(Game.level.squares[293][52], null);
		// Chairs 1
		Templates.CHAIR.makeCopy(Game.level.squares[290][42], null);
		Templates.CHAIR.makeCopy(Game.level.squares[291][43], null);
		Templates.CHAIR.makeCopy(Game.level.squares[291][44], null);
		Templates.CHAIR.makeCopy(Game.level.squares[289][45], null);
		Templates.DRIED_BLOOD.makeCopy(Game.level.squares[291][47], null);
		Templates.CHAIR.makeCopy(Game.level.squares[291][47], null);
		Templates.CHAIR.makeCopy(Game.level.squares[291][48], null);
		// Templates.CHAIR_FALLEN.makeCopy(Game.level.squares[290][49], null);
		Templates.CHAIR.makeCopy(Game.level.squares[291][50], null);
		// Chairs 2
		Templates.CHAIR.makeCopy(Game.level.squares[297][42], null);
		Templates.CHAIR.makeCopy(Game.level.squares[295][43], null);
		Templates.CHAIR.makeCopy(Game.level.squares[296][44], null);
		Templates.CHAIR_FALLEN.makeCopy(Game.level.squares[295][45], null);
		Templates.CHAIR.makeCopy(Game.level.squares[295][46], null);
		Templates.CHAIR.makeCopy(Game.level.squares[295][47], null);
		Templates.CHAIR.makeCopy(Game.level.squares[295][49], null);
		Templates.CHAIR_FALLEN.makeCopy(Game.level.squares[297][50], null);
		Templates.CHAIR_FALLEN.makeCopy(Game.level.squares[295][51], null);
		// Table 1
		Templates.TABLE.makeCopy(Game.level.squares[292][42], null);
		Templates.PLATE.makeCopy(Game.level.squares[292][42], null);
		Templates.TABLE.makeCopy(Game.level.squares[292][43], null);
		Templates.TABLE.makeCopy(Game.level.squares[292][44], null);
		Templates.TABLE.makeCopy(Game.level.squares[292][45], null);
		Templates.TABLE.makeCopy(Game.level.squares[292][46], null);
		Templates.BROKEN_PLATE.makeCopy(Game.level.squares[292][46], null);
		Templates.DINNER_FORK.makeCopy(Game.level.squares[292][46], null);
		Templates.DINNER_KNIFE.makeCopy(Game.level.squares[292][46], null);
		Templates.TABLE.makeCopy(Game.level.squares[292][47], null);
		Templates.TABLE.makeCopy(Game.level.squares[292][48], null);
		Templates.DINNER_KNIFE.makeCopy(Game.level.squares[292][48], null);
		Templates.PLATE.makeCopy(Game.level.squares[292][48], null);
		Templates.TABLE.makeCopy(Game.level.squares[292][49], null);
		Templates.DINNER_FORK.makeCopy(Game.level.squares[292][49], null);
		Templates.TABLE.makeCopy(Game.level.squares[292][50], null);
		Templates.TABLE.makeCopy(Game.level.squares[292][51], null);
		Templates.DINNER_KNIFE.makeCopy(Game.level.squares[292][51], null);
		Templates.PLATE.makeCopy(Game.level.squares[292][51], null);
		Templates.DINNER_FORK.makeCopy(Game.level.squares[292][51], null);
		// Table 2
		Templates.TABLE.makeCopy(Game.level.squares[294][42], null);
		Templates.TABLE.makeCopy(Game.level.squares[294][43], null);
		Templates.DINNER_FORK.makeCopy(Game.level.squares[294][43], null);
		Templates.PLATE.makeCopy(Game.level.squares[294][43], null);
		Templates.TABLE.makeCopy(Game.level.squares[294][44], null);
		Templates.BROKEN_PLATE.makeCopy(Game.level.squares[294][44], null);
		Templates.DINNER_FORK.makeCopy(Game.level.squares[294][44], null);
		Templates.TABLE.makeCopy(Game.level.squares[294][45], null);
		Templates.TABLE.makeCopy(Game.level.squares[294][46], null);
		Templates.DINNER_KNIFE.makeCopy(Game.level.squares[294][46], null);
		Templates.TABLE.makeCopy(Game.level.squares[294][47], null);
		Templates.BROKEN_PLATE.makeCopy(Game.level.squares[294][47], null);
		Templates.TABLE.makeCopy(Game.level.squares[294][48], null);
		Templates.TABLE.makeCopy(Game.level.squares[294][49], null);
		Templates.TABLE.makeCopy(Game.level.squares[294][50], null);
		Templates.DINNER_KNIFE.makeCopy(Game.level.squares[294][50], null);
		Templates.DINNER_FORK.makeCopy(Game.level.squares[294][50], null);
		Templates.PLATE.makeCopy(Game.level.squares[294][50], null);
		Templates.TABLE.makeCopy(Game.level.squares[294][51], null);

		// Path rec room to quarters
		cavePaths.add(new StructurePath("Rec Room <-> Quarters", false, new ArrayList<Actor>(),
				Game.level.squares[283][25], Game.level.squares[283][25]));

		cavePaths.add(new StructurePath("Rec Room <-> Quarters", false, new ArrayList<Actor>(),
				Game.level.squares[283][24], Game.level.squares[283][25]));

		// Section Quarters
		caveSections.add(new StructureSection("Mort & Mort Mining", 275, 11, 293, 24, false));
		// Room Quarters
		quarters = new StructureRoom("Quarters", 276, 12, false, new ArrayList<Actor>(), new RoomPart(276, 12, 288, 21),
				new RoomPart(279, 13, 292, 22), new RoomPart(281, 13, 292, 23));
		rooms.add(quarters);

		// Quarters extras
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[292][13], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[282][17], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[282][18], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[283][17], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[283][18], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[284][17], null));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[284][18], null));
		Templates.BED.makeCopy(Game.level.squares[277][13], null);
		Templates.BED.makeCopy(Game.level.squares[279][13], null);
		Templates.BED.makeCopy(Game.level.squares[281][13], null);
		Templates.BED.makeCopy(Game.level.squares[283][13], null);
		Templates.BED.makeCopy(Game.level.squares[285][13], null);
		Templates.BED.makeCopy(Game.level.squares[287][13], null);
		Templates.BED.makeCopy(Game.level.squares[277][15], null);
		Templates.BED.makeCopy(Game.level.squares[279][15], null);
		Templates.BED.makeCopy(Game.level.squares[281][15], null);
		Templates.BED.makeCopy(Game.level.squares[283][15], null);
		Templates.BED.makeCopy(Game.level.squares[285][15], null);
		Templates.BED.makeCopy(Game.level.squares[287][15], null);
		Templates.BED.makeCopy(Game.level.squares[289][15], null);
		Templates.BED.makeCopy(Game.level.squares[291][15], null);
		Templates.BED.makeCopy(Game.level.squares[277][17], null);
		Templates.BED.makeCopy(Game.level.squares[279][17], null);
		Templates.BED.makeCopy(Game.level.squares[287][17], null);
		Templates.BED.makeCopy(Game.level.squares[289][17], null);
		Templates.BED.makeCopy(Game.level.squares[291][17], null);
		Templates.BED.makeCopy(Game.level.squares[277][19], null);
		Templates.BED.makeCopy(Game.level.squares[279][19], null);
		Templates.BED.makeCopy(Game.level.squares[287][19], null);
		Templates.BED.makeCopy(Game.level.squares[289][19], null);

		Templates.CHEST.makeCopy("Al's Gear", Game.level.squares[287][23], true, null, alsKey);
		Templates.CHEST.makeCopy("Joe's Gear", Game.level.squares[288][23], true, null, joesKey);
		Templates.CHEST.makeCopy("Sean's Gear", Game.level.squares[289][23], true, null, seansKey);
		Templates.CHEST.makeCopy("Steve's Gear", Game.level.squares[290][23], false, null);
		Templates.CHEST.makeCopy("Paul's Gear", Game.level.squares[291][23], true, null, paulsKey);

		// Path rec room to quarters
		cavePaths.add(new StructurePath("Rec Room <-> Inner Mine", false, new ArrayList<Actor>(),
				Game.level.squares[285][33], Game.level.squares[285][34]));

		// Morts Mine Section
		caveSections.add(new StructureSection("Mort & Mort Mining", 273, 34, 287, 50, false));
		// Morts Mine Room
		mort.mortsMine = new StructureRoom("Inner Mine", 276, 36, false, new ArrayList<Actor>(),
				new RoomPart(278, 35, 286, 47), new RoomPart(276, 36, 286, 46), new RoomPart(277, 41, 284, 48));
		rooms.add(mort.mortsMine);
		if (!((AIRoutineForMort) mort.aiRoutine).retreatedToRoom)
			mort.aiRoutine.roomBounds.add(mort.mortsMine);

		// Inner mine extras
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[280][35], mort));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[281][35], mort));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[286][39], mort));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[286][47], mort));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[280][43], mort));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[280][44], mort));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[281][43], mort));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[281][44], mort));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[283][38], mort));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[286][37], mort));

		Templates.BUSH.makeCopy(Game.level.squares[278][46], null);
		Templates.BUSH.makeCopy(Game.level.squares[279][46], null);
		Templates.BUSH.makeCopy(Game.level.squares[280][46], null);
		Templates.BUSH.makeCopy(Game.level.squares[281][46], null);
		Templates.BUSH.makeCopy(Game.level.squares[282][46], null);
		Templates.BUSH.makeCopy(Game.level.squares[278][47], null);
		Templates.BUSH.makeCopy(Game.level.squares[279][47], null);
		Templates.BUSH.makeCopy(Game.level.squares[280][47], null);
		Templates.BUSH.makeCopy(Game.level.squares[281][47], null);
		Templates.BUSH.makeCopy(Game.level.squares[282][47], null);
		Templates.BUSH.makeCopy(Game.level.squares[278][48], null);
		Templates.BUSH.makeCopy(Game.level.squares[279][48], null);
		Templates.BUSH.makeCopy(Game.level.squares[280][48], null);
		Templates.BUSH.makeCopy(Game.level.squares[281][48], null);
		Templates.BUSH.makeCopy(Game.level.squares[282][48], null);
		Templates.BUSH.makeCopy(Game.level.squares[279][37], null);

		// Morts Rooms Section
		mort.mortsRooms = new StructureSection("Mort & Mort Mining", 243, 38, 275, 49, false, mort);
		caveSections.add(mort.mortsRooms);

		// Morts Quarters
		mort.mortsRoom = new StructureRoom("Management", 265, 39, false, new ArrayList<Actor>(),
				new RoomPart(265, 39, 274, 42));
		rooms.add(mort.mortsRoom);
		Storage confiscatedChest = Templates.CHEST.makeCopy("Confiscated", Game.level.squares[269][42], false, mort,
				mortsKey);
		confiscatedChest.inventory.add(alsKey);
		mort.aiRoutine.roomBounds.add(mort.mortsRoom);
		extraWalls.add(Templates.WALL.makeCopy(Game.level.squares[274][39], mort));
		extraWalls.add(Templates.WALL.makeCopy(Game.level.squares[274][42], mort));

		// Morts Vault Room
		mort.mortsVault = new StructureRoom("Vault", 249, 39, false, new ArrayList<Actor>(),
				new RoomPart(249, 39, 263, 42));
		rooms.add(mort.mortsVault);
		mort.aiRoutine.roomBounds.add(mort.mortsVault);

		// Cave featues for Mort
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[276][45], mort));
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[276][46], mort));
		mortsGameRoomDoor = Templates.DOOR.makeCopy("Management Door", Game.level.squares[275][40], true, true, true,
				mort, mortsKey, (Key) Game.level.player.inventory.getGameObjectOfClass(Key.class));
		mort.mortsRoomDoorway = mortsGameRoomDoor.squareGameObjectIsOn;
		caveFeatures.add(mortsGameRoomDoor);
		mort.aiRoutine.squareBounds.add(mort.mortsRoomDoorway);
		mortsStoreroomDoor = Templates.DOOR.makeCopy("Vault Door", Game.level.squares[264][40], true, true, true, mort,
				mortsKey, (Key) Game.level.player.inventory.getGameObjectOfClass(Key.class));
		mort.mortsVaultDoorway = mortsStoreroomDoor.squareGameObjectIsOn;
		caveFeatures.add(mortsStoreroomDoor);

		// Path Dining room to equipment room
		cavePaths.add(new StructurePath("Dining Room <-> Equipment", false, new ArrayList<Actor>(),
				Game.level.squares[286][51], Game.level.squares[287][51], Game.level.squares[288][51]));

		// Path Inner Mine to Equipment room
		cavePaths.add(new StructurePath("Inner Mine <-> Equipment", false, new ArrayList<Actor>(),
				Game.level.squares[285][50], Game.level.squares[285][51], Game.level.squares[284][51],
				Game.level.squares[284][50], Game.level.squares[284][49]));

		// Equipment section
		caveSections.add(new StructureSection("Equipment", 274, 51, 287, 58, false));

		// Equipment room
		equipment = new StructureRoom("Equipment", 276, 52, false, new ArrayList<Actor>(),
				new RoomPart(276, 52, 285, 55));
		rooms.add(equipment);

		// Equipment extras
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[276][55], null));

		// Path Dining Room <-> Kitchen
		cavePaths.add(new StructurePath("Dining Room <-> Kitchen", false, new ArrayList<Actor>(),
				Game.level.squares[293][55], Game.level.squares[293][56], Game.level.squares[293][57]));

		// Kitchen section
		caveSections.add(new StructureSection("Kitchen", 280, 56, 310, 75, false));

		// Kitchen room
		kitchen = new StructureRoom("Kitchen", 292, 58, false, new ArrayList<Actor>(), new RoomPart(292, 58, 297, 69),
				new RoomPart(293, 59, 299, 72));
		rooms.add(kitchen);

		// Kitchen extras
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[299][59], null));

		// Path Dining Room <-> East Security
		cavePaths.add(new StructurePath("Dining Room <-> East Security", false, new ArrayList<Actor>(),
				Game.level.squares[298][51], Game.level.squares[299][51], Game.level.squares[299][52],
				Game.level.squares[299][53], Game.level.squares[300][53], Game.level.squares[301][53]));

		// East Security section
		caveSections.add(new StructureSection("East Security", 299, 43, 315, 56, false));

		// East Security room
		eastSecurity = new StructureRoom("East Security", 301, 45, false, new ArrayList<Actor>(),
				new RoomPart(301, 45, 306, 50), new RoomPart(302, 46, 305, 54));
		rooms.add(eastSecurity);

		// East security extras
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[302][46], null));

		// East Security <-> East Atrium
		cavePaths.add(new StructurePath("East Security <-> East Atrium", false, new ArrayList<Actor>(),
				Game.level.squares[307][45], Game.level.squares[308][45], Game.level.squares[309][45],
				Game.level.squares[310][45]));

		// East Atrium Section
		caveSections.add(new StructureSection("East Atrium", 308, 36, 317, 46, false));

		// East Atrium Room
		eastAtrium = new StructureRoom("East Atrium", 310, 40, false, new ArrayList<Actor>(),
				new RoomPart(313, 37, 315, 40), new RoomPart(310, 40, 314, 44));
		rooms.add(eastAtrium);

		// East Atrium Extras
		extraWalls.add(Templates.VEIN.makeCopy(Game.level.squares[312][42], null));

		// East Atrium <-> East Entrance
		cavePaths.add(new StructurePath("East Atrium <-> East Entrance", false, new ArrayList<Actor>(),
				Game.level.squares[315][34], Game.level.squares[315][35], Game.level.squares[315][36]));

		// East Entrance
		caveSections.add(new StructureSection("East Entrance", 307, 20, 321, 35, false));

		// East Entrance
		eastEntrance = new StructureRoom("East Entrance", 309, 30, false, new ArrayList<Actor>(),
				new RoomPart(309, 30, 317, 33), new RoomPart(310, 30, 321, 30));
		rooms.add(eastEntrance);

		squaresToRemove.add(Game.level.squares[224][6]);
		squaresToRemove.add(Game.level.squares[224][7]);
		squaresToRemove.add(Game.level.squares[224][8]);
		squaresToRemove.add(Game.level.squares[224][9]);
		squaresToRemove.add(Game.level.squares[224][10]);

		cave = new Structure("Mort & Mort Mining", caveSections, rooms, cavePaths, caveFeatures,
				new ArrayList<Square>(), "map_cave.png", 223, 0, 321, 74, true, mort, squaresToRemove, extraWalls,
				Templates.WALL, Square.STONE_TEXTURE, 5);
		Game.level.structures.add(cave);

		// Dirty Sheet
		// Templates.DIRTY_SHEET.makeCopy(Game.level.squares[247][11]);
		// Templates.DIRTY_SHEET_2.makeCopy(Game.level.squares[247][10]);
		Templates.DIRTY_SHEET_3.makeCopy(Game.level.squares[247][9], null);
		// Smashed Glass
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[243][12], null);
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[244][11], null);
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[244][12], null);
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[245][11], null);
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[245][12], null);
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[245][13], null);
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[246][12], null);
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[247][9], null);
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[247][10], null);
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[247][11], null);
		Templates.BROKEN_LAMP.makeCopy(Game.level.squares[247][12], null);

	}

	public void makeMortsMine() {

		Readable noEntry = Templates.SIGN.makeCopy(Game.level.squares[276][39], "Sign",
				new Object[] { "PRIVATE! - Mort" }, mort);
		noEntry.quest = this;

		Pickaxe pickaxe = Templates.PICKAXE.makeCopy(Game.level.squares[276][38], mort);
		pickaxe.quest = this;

		GameObject trough = Templates.TROUGH.makeCopy(Game.level.squares[286][43], mort);
		trough.quest = this;

		troughSquare = Game.level.squares[286][43];
		safeSquare = Game.level.squares[276][42];

	}

	public void makeMortsRoom() {
	}

	public void makeMortsStorage() {

		GameObject outsideBlood1 = Templates.DRIED_BLOOD.makeCopy(Game.level.squares[249][39], null);
		GameObject outsideBlood2 = Templates.DRIED_BLOOD.makeCopy(Game.level.squares[248][39], null);
		GameObject outsideBlood3 = Templates.DRIED_BLOOD.makeCopy(Game.level.squares[247][39], null);
		GameObject outsideBlood4 = Templates.DRIED_BLOOD.makeCopy(Game.level.squares[246][39], null);
		GameObject outsideBlood5 = Templates.DRIED_BLOOD.makeCopy(Game.level.squares[245][39], null);
		GameObject outsideBlood6 = Templates.DRIED_BLOOD.makeCopy(Game.level.squares[244][39], null);
		GameObject outsideBlood7 = Templates.DRIED_BLOOD.makeCopy(Game.level.squares[243][39], null);
		GameObject blood = Templates.DRIED_BLOOD.makeCopy(Game.level.squares[224][21], null);
		Corpse carcass1 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[249][39], null, 25f);
		Corpse carcass2 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[249][39], null, 25f);
		Corpse carcass3 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[249][39], null, 25f);
		Corpse carcass4 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[250][39], null, 25f);
		Corpse carcass5 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[250][39], null, 25f);
		Corpse carcass6 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[249][40], null, 25f);
		Corpse carcass7 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[249][41], null, 25f);
		ore = Templates.ORE.makeCopy(Game.level.squares[251][41], mort);
		lantern = Templates.LANTERN.makeCopy(Game.level.squares[252][41], mort);
		GameObject table = Templates.TABLE.makeCopy(Game.level.squares[253][41], mort);

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
		Blind blind11 = Templates.BLIND.makeCopy(Game.level.squares[246][7], Game.level.factions.blind, westAtriumPart2,
				new GameObject[] {}, new GameObject[] {});
		blind11.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind11.quest = this;
		blind.add(blind11);

		// East security
		Blind blindWestSecurity1 = Templates.BLIND.makeCopy(Game.level.squares[260][12], Game.level.factions.blind,
				westSecurity1, new GameObject[] {}, new GameObject[] {});
		blindWestSecurity1.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blindWestSecurity1.quest = this;
		blind.add(blindWestSecurity1);

		Blind blindWestSecurity2 = Templates.BLIND.makeCopy(Game.level.squares[260][15], Game.level.factions.blind,
				westSecurity1, new GameObject[] {}, new GameObject[] {});
		blindWestSecurity2.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blindWestSecurity2.quest = this;
		blind.add(blindWestSecurity2);

		Blind blindWestSecurity3 = Templates.BLIND.makeCopy(Game.level.squares[254][6], Game.level.factions.blind,
				westSecurity1, new GameObject[] {}, new GameObject[] {});
		blindWestSecurity3.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blindWestSecurity3.quest = this;
		blind.add(blindWestSecurity3);

		Blind blindWestSecurity4 = Templates.BLIND.makeCopy(Game.level.squares[257][5], Game.level.factions.blind,
				westSecurity1, new GameObject[] {}, new GameObject[] {});
		blindWestSecurity4.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blindWestSecurity4.quest = this;
		blind.add(blindWestSecurity4);

		// Atrium 2
		Blind blind3 = Templates.BLIND.makeCopy(Game.level.squares[255][23], Game.level.factions.blind, outerMine,
				new GameObject[] {}, new GameObject[] {});
		blind3.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind3.quest = this;
		blind.add(blind3);

		Blind blind4 = Templates.BLIND.makeCopy(Game.level.squares[260][25], Game.level.factions.blind, outerMine,
				new GameObject[] {}, new GameObject[] {});
		blind4.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind4.quest = this;
		blind.add(blind4);

		Blind blind5 = Templates.BLIND.makeCopy(Game.level.squares[255][24], Game.level.factions.blind, outerMine,
				new GameObject[] {}, new GameObject[] {});
		blind5.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind5.quest = this;
		blind.add(blind5);

		Blind blind6 = Templates.BLIND.makeCopy(Game.level.squares[259][25], Game.level.factions.blind, outerMine,
				new GameObject[] {}, new GameObject[] {});
		blind6.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind6.quest = this;
		blind.add(blind6);

		Blind blind7 = Templates.BLIND.makeCopy(Game.level.squares[260][26], Game.level.factions.blind, outerMine,
				new GameObject[] {}, new GameObject[] {});
		blind7.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind7.quest = this;
		blind.add(blind7);

		Blind blind8 = Templates.BLIND.makeCopy(Game.level.squares[257][24], Game.level.factions.blind, outerMine,
				new GameObject[] {}, new GameObject[] {});
		blind8.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind8.quest = this;
		blind.add(blind8);

		Blind blind9 = Templates.BLIND.makeCopy(Game.level.squares[256][25], Game.level.factions.blind, outerMine,
				new GameObject[] {}, new GameObject[] {});
		blind9.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind9.quest = this;
		blind.add(blind9);

		Blind blind10 = Templates.BLIND.makeCopy(Game.level.squares[257][27], Game.level.factions.blind, outerMine,
				new GameObject[] {}, new GameObject[] {});
		blind10.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blind10.quest = this;
		blind.add(blind10);

		// Rec Room Blind
		Blind blindRecRoom1 = Templates.BLIND.makeCopy(Game.level.squares[278][28], Game.level.factions.blind, recRoom,
				new GameObject[] {}, new GameObject[] {});
		blindRecRoom1.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blindRecRoom1.quest = this;
		blind.add(blindRecRoom1);

		Blind blindRecRoom2 = Templates.BLIND.makeCopy(Game.level.squares[278][30], Game.level.factions.blind, recRoom,
				new GameObject[] {}, new GameObject[] {});
		blindRecRoom2.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blindRecRoom2.quest = this;
		blind.add(blindRecRoom2);

		Blind blindRecRoom3 = Templates.BLIND.makeCopy(Game.level.squares[279][30], Game.level.factions.blind, recRoom,
				new GameObject[] {}, new GameObject[] {});
		blindRecRoom3.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blindRecRoom3.quest = this;
		blind.add(blindRecRoom3);

		Blind blindRecRoom4 = Templates.BLIND.makeCopy(Game.level.squares[280][31], Game.level.factions.blind, recRoom,
				new GameObject[] {}, new GameObject[] {});
		blindRecRoom4.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blindRecRoom4.quest = this;
		blind.add(blindRecRoom4);

		Blind blindRecRoom5 = Templates.BLIND.makeCopy(Game.level.squares[281][29], Game.level.factions.blind, recRoom,
				new GameObject[] {}, new GameObject[] {});
		blindRecRoom5.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blindRecRoom5.quest = this;
		blind.add(blindRecRoom5);

		// Dining Room Blind
		Blind blindDiningRoom1 = Templates.BLIND.makeCopy(Game.level.squares[293][42], Game.level.factions.blind,
				diningRoom, new GameObject[] {}, new GameObject[] {});
		blindDiningRoom1.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blindDiningRoom1.quest = this;
		blind.add(blindDiningRoom1);

		Blind blindDiningRoom2 = Templates.BLIND.makeCopy(Game.level.squares[293][49], Game.level.factions.blind,
				diningRoom, new GameObject[] {}, new GameObject[] {});
		blindDiningRoom2.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blindDiningRoom2.quest = this;
		blind.add(blindDiningRoom2);

		Blind blindDiningRoom3 = Templates.BLIND.makeCopy(Game.level.squares[293][46], Game.level.factions.blind,
				diningRoom, new GameObject[] {}, new GameObject[] {});
		blindDiningRoom3.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blindDiningRoom3.inventory.add(seansKey);
		blindDiningRoom3.quest = this;
		blind.add(blindDiningRoom3);

		Blind blindDiningRoom4 = Templates.BLIND.makeCopy(Game.level.squares[296][43], Game.level.factions.blind,
				diningRoom, new GameObject[] {}, new GameObject[] {});
		blindDiningRoom4.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blindDiningRoom4.quest = this;
		blind.add(blindDiningRoom4);

		Blind blindDiningRoom5 = Templates.BLIND.makeCopy(Game.level.squares[296][45], Game.level.factions.blind,
				diningRoom, new GameObject[] {}, new GameObject[] {});
		blindDiningRoom5.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blindDiningRoom5.quest = this;
		blind.add(blindDiningRoom5);

		Blind blindDiningRoom6 = Templates.BLIND.makeCopy(Game.level.squares[293][44], Game.level.factions.blind,
				diningRoom, new GameObject[] {}, new GameObject[] {});
		blindDiningRoom6.inventory.add(Templates.SERRATED_SPOON.makeCopy(null, null));
		blindDiningRoom6.quest = this;
		blind.add(blindDiningRoom6);

		// Templates.MEAT_CHUNK.makeCopy("Meat Chunk",
		// Game.level.squares[257][28]);
		// Templates.MEAT_CHUNK.makeCopy("Meat Chunk",
		// Game.level.squares[257][25]);
		// Templates.MEAT_CHUNK.makeCopy("Meat Chunk",
		// Game.level.squares[258][24]);
		// Templates.MEAT_CHUNK.makeCopy("Meat Chunk",
		// Game.level.squares[259][28]);
		// Templates.MEAT_CHUNK.makeCopy("Meat Chunk",
		// Game.level.squares[252][28]);
		// Templates.MEAT_CHUNK.makeCopy("Meat Chunk",
		// Game.level.squares[251][28]);
	}

	public void makeConversation() {

	}

}
