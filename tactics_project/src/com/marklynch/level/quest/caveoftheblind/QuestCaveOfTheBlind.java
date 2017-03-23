package com.marklynch.level.quest.caveoftheblind;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.structure.Structure;
import com.marklynch.level.constructs.structure.StructureHall;
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
import com.marklynch.objects.Sign;
import com.marklynch.objects.Templates;
import com.marklynch.objects.Wall;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Lantern;
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
	StructureRoom wolfDen;
	StructureRoom entrancePart1;
	StructureRoom entrancePart2;
	StructureRoom atrium1;
	StructureRoom atrium2;
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
		mortsKey = Templates.KEY.makeCopy(null);
		mortsKey.quest = this;

		mort = Templates.MORT.makeCopy(Game.level.squares[147][21], Game.level.factions.get(1), mortsBed);
		mort.quest = this;
		mort.mortsBell = Templates.DINNER_BELL.makeCopy(null);
		mort.mortsMeatChunk = Templates.MEAT_CHUNK.makeCopy("Meat Chunk", null);
		mort.inventory.add(Templates.CLEAVER.makeCopy(null));
		mort.inventory.add(Templates.LANTERN.makeCopy(null));
		mort.inventory.add(Templates.PICKAXE.makeCopy(null));
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

		Sign rockWithEtching = Templates.ROCK_WITH_ETCHING.makeCopy(Game.level.squares[43][15], "Rock with etching",
				new Object[] { "SHHHHHhhhhhhhh!!! -Mort" });

		// Add spoons
		serratedSpoon = Templates.SERRATED_SPOON.makeCopy(Game.level.squares[44][11]);
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
		ArrayList<GameObject> caveFeatures = new ArrayList<GameObject>();

		ArrayList<StructureHall> cavePaths = new ArrayList<StructureHall>();
		ArrayList<Square> cavePathSquares = new ArrayList<Square>();
		cavePathSquares.add(Game.level.squares[24][14]);
		cavePathSquares.add(Game.level.squares[25][14]);
		cavePaths.add(new StructureHall("Path", cavePathSquares));
		ArrayList<Square> cavePathSquares2 = new ArrayList<Square>();
		cavePathSquares.add(Game.level.squares[38][16]);
		cavePathSquares.add(Game.level.squares[39][16]);
		cavePathSquares.add(Game.level.squares[39][15]);
		cavePathSquares.add(Game.level.squares[39][14]);
		cavePathSquares.add(Game.level.squares[40][14]);
		cavePathSquares.add(Game.level.squares[41][14]);
		cavePathSquares.add(Game.level.squares[42][14]);
		cavePathSquares.add(Game.level.squares[40][15]);
		cavePathSquares.add(Game.level.squares[41][15]);
		cavePathSquares.add(Game.level.squares[42][15]);
		cavePaths.add(new StructureHall("Path", cavePathSquares2));
		ArrayList<Square> cavePathSquares3 = new ArrayList<Square>();
		cavePathSquares.add(Game.level.squares[48][7]);
		cavePathSquares.add(Game.level.squares[49][7]);
		cavePathSquares.add(Game.level.squares[50][7]);
		cavePathSquares.add(Game.level.squares[51][7]);
		cavePathSquares.add(Game.level.squares[57][18]);
		cavePathSquares.add(Game.level.squares[57][19]);
		cavePathSquares.add(Game.level.squares[57][20]);
		cavePathSquares.add(Game.level.squares[57][21]);
		cavePaths.add(new StructureHall("Path", cavePathSquares3));

		ArrayList<StructureRoom> caveAtriums = new ArrayList<StructureRoom>();
		wolfDen = new StructureRoom("Wolf's Den", new RoomPart(25, 13, 37, 18));
		caveAtriums.add(wolfDen);
		entrancePart1 = new StructureRoom("Entrance of the Blind", new RoomPart(43, 9, 47, 15));
		caveAtriums.add(entrancePart1);
		entrancePart2 = new StructureRoom("Entrance of the Blind", new RoomPart(43, 7, 47, 8));
		caveAtriums.add(entrancePart2);

		atrium1 = new StructureRoom("Atrium of the Blind", new RoomPart(52, 4, 56, 9), new RoomPart(54, 10, 60, 17));
		caveAtriums.add(atrium1);
		atrium2 = new StructureRoom("Atrium 2 of the Blind", new RoomPart(55, 22, 60, 25),
				new RoomPart(57, 26, 60, 30));
		caveAtriums.add(atrium2);

		ArrayList<StructureSection> caveSections = new ArrayList<StructureSection>();
		caveSections.add(new StructureSection("Cave of the Blind", 24, 12, 40, 19));
		caveSections.add(new StructureSection("Cave of the Blind", 41, 14, 49, 16));
		caveSections.add(new StructureSection("Cave of the Blind", 41, 5, 49, 13));
		caveSections.add(new StructureSection("Cave of the Blind", 44, 2, 62, 18));
		caveSections.add(new StructureSection("Cave of the Blind", 54, 19, 61, 31));

		// Morts Mine Room
		mort.mortsMine = new StructureRoom("Morty's Mine", new RoomPart(149, 18, 152, 25),
				new RoomPart(142, 19, 150, 26), new RoomPart(144, 23, 151, 32));
		caveAtriums.add(mort.mortsMine);
		// Morts Room
		mort.mortsRoom = new StructureRoom("Morty's Room", new RoomPart(132, 21, 140, 24));
		caveAtriums.add(mort.mortsRoom);
		// Morts stash
		caveAtriums.add(new StructureRoom("Morty's Stash", new RoomPart(124, 21, 130, 24)));

		// Morts Mine Section
		caveSections.add(new StructureSection("Cave of the Blind", 141, 17, 153, 33));

		// Morts Rooms Section
		caveSections.add(new StructureSection("Cave of the Blind", 120, 20, 140, 27));

		// Cave featues for Mort
		ArrayList<Key> mortsKeys = new ArrayList();
		mortsKeys.add(mortsKey);
		mortsKeys.add((Key) Game.level.player.inventory.getGameObectOfClass(Key.class));
		caveFeatures.add(Templates.VEIN.makeCopy(Game.level.squares[142][23]));
		mortsBedroomDoor = Templates.DOOR.makeCopy(Game.level.squares[141][21], mortsKeys, true);
		caveFeatures.add(mortsBedroomDoor);
		mortsStoreroomDoor = Templates.DOOR.makeCopy(Game.level.squares[131][21], mortsKeys, true);
		caveFeatures.add(mortsStoreroomDoor);

		Game.level.structures.add(new Structure("Cave of the Blind", caveSections, caveAtriums, cavePaths, caveFeatures,
				new ArrayList<Square>(), null, 0, 0, 0, 0, true));

		// Dirty Sheet
		// Templates.DIRTY_SHEET.makeCopy(Game.level.squares[47][11]);
		// Templates.DIRTY_SHEET_2.makeCopy(Game.level.squares[47][10]);
		Templates.DIRTY_SHEET_3.makeCopy(Game.level.squares[47][9]);
		// Smashed Glass
		Templates.BROKEN_GLASS.makeCopy(Game.level.squares[43][12]);
		Templates.BROKEN_GLASS.makeCopy(Game.level.squares[44][11]);
		Templates.BROKEN_GLASS.makeCopy(Game.level.squares[44][12]);
		Templates.BROKEN_GLASS.makeCopy(Game.level.squares[45][11]);
		Templates.BROKEN_GLASS.makeCopy(Game.level.squares[45][12]);
		Templates.BROKEN_GLASS.makeCopy(Game.level.squares[45][13]);
		Templates.BROKEN_GLASS.makeCopy(Game.level.squares[46][12]);
		Templates.BROKEN_GLASS.makeCopy(Game.level.squares[47][9]);
		Templates.BROKEN_GLASS.makeCopy(Game.level.squares[47][10]);
		Templates.BROKEN_GLASS.makeCopy(Game.level.squares[47][11]);
		Templates.BROKEN_GLASS.makeCopy(Game.level.squares[47][12]);

	}

	public void makeMortsMine() {

		Sign noEntry = Templates.SIGN.makeCopy(Game.level.squares[142][20], "Sign", new Object[] { "PRIVATE! - Mort" });
		noEntry.quest = this;

		GameObject trough = Templates.TROUGH.makeCopy(Game.level.squares[150][25]);
		troughSquare = Game.level.squares[150][25];
		safeSquare = Game.level.squares[144][25];

	}

	public void makeMortsRoom() {
	}

	public void makeMortsStorage() {

		GameObject outsideBlood1 = Templates.BLOOD.makeCopy(Game.level.squares[117][21]);
		GameObject outsideBlood2 = Templates.BLOOD.makeCopy(Game.level.squares[118][21]);
		GameObject outsideBlood3 = Templates.BLOOD.makeCopy(Game.level.squares[119][21]);
		GameObject outsideBlood4 = Templates.BLOOD.makeCopy(Game.level.squares[120][21]);
		GameObject outsideBlood5 = Templates.BLOOD.makeCopy(Game.level.squares[121][21]);
		GameObject outsideBlood6 = Templates.BLOOD.makeCopy(Game.level.squares[122][21]);
		GameObject outsideBlood7 = Templates.BLOOD.makeCopy(Game.level.squares[123][21]);
		GameObject blood = Templates.BLOOD.makeCopy(Game.level.squares[24][21]);
		Corpse carcass1 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[124][21]);
		Corpse carcass2 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[124][21]);
		Corpse carcass3 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[124][21]);
		Corpse carcass4 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[125][21]);
		Corpse carcass5 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[125][21]);
		Corpse carcass6 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[124][22]);
		Corpse carcass7 = Templates.CORPSE.makeCopy("Corpse", Game.level.squares[124][24]);
		ore = Templates.ORE.makeCopy(Game.level.squares[124][24]);
		lantern = Templates.LANTERN.makeCopy(Game.level.squares[126][24]);
		GameObject table = Templates.TABLE.makeCopy(Game.level.squares[127][24]);

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
		Blind blind11 = Templates.BLIND.makeCopy(Game.level.squares[46][7], Game.level.factions.get(3), entrancePart2);
		blind11.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		blind11.quest = this;
		blind.add(blind11);

		// Atrium 1
		Blind blind1 = Templates.BLIND.makeCopy(Game.level.squares[60][12], Game.level.factions.get(3), atrium1);
		blind1.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		blind1.quest = this;
		blind.add(blind1);

		Blind blind2 = Templates.BLIND.makeCopy(Game.level.squares[60][15], Game.level.factions.get(3), atrium1);
		blind2.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		blind2.quest = this;
		blind.add(blind2);

		// Atrium 2
		Blind blind3 = Templates.BLIND.makeCopy(Game.level.squares[55][23], Game.level.factions.get(3), atrium2);
		blind3.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		blind3.quest = this;
		blind.add(blind3);

		Blind blind4 = Templates.BLIND.makeCopy(Game.level.squares[60][25], Game.level.factions.get(3), atrium2);
		blind4.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		blind4.quest = this;
		blind.add(blind4);

		Blind blind5 = Templates.BLIND.makeCopy(Game.level.squares[55][24], Game.level.factions.get(3), atrium2);
		blind5.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		blind5.quest = this;
		blind.add(blind5);

		Blind blind6 = Templates.BLIND.makeCopy(Game.level.squares[59][25], Game.level.factions.get(3), atrium2);
		blind6.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		blind6.quest = this;
		blind.add(blind6);

		Blind blind7 = Templates.BLIND.makeCopy(Game.level.squares[60][26], Game.level.factions.get(3), atrium2);
		blind7.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		blind7.quest = this;
		blind.add(blind7);

		Blind blind8 = Templates.BLIND.makeCopy(Game.level.squares[57][24], Game.level.factions.get(3), atrium2);
		blind8.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		blind8.quest = this;
		blind.add(blind8);

		Blind blind9 = Templates.BLIND.makeCopy(Game.level.squares[56][25], Game.level.factions.get(3), atrium2);
		blind9.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		blind9.quest = this;
		blind.add(blind9);

		Blind blind10 = Templates.BLIND.makeCopy(Game.level.squares[57][27], Game.level.factions.get(3), atrium2);
		blind10.inventory.add(Templates.SERRATED_SPOON.makeCopy(null));
		blind10.quest = this;
		blind.add(blind10);

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
