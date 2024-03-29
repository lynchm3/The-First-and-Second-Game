package com.marklynch.level.quest.smallgame;

import com.marklynch.Game;
import com.marklynch.actions.ActionTalk;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.constructs.GroupOfActors;
import com.marklynch.level.constructs.area.TownForestBuilder;
import com.marklynch.level.constructs.area.town.AreaTown;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructureFeature;
import com.marklynch.level.constructs.bounds.structure.StructurePath;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom;
import com.marklynch.level.constructs.bounds.structure.structureroom.StructureRoom.RoomPart;
import com.marklynch.level.constructs.conversation.Conversation;
import com.marklynch.level.constructs.conversation.ConversationsSmallGame;
import com.marklynch.level.constructs.journal.AreaList;
import com.marklynch.level.constructs.journal.JournalLog;
import com.marklynch.level.constructs.journal.Objective;
import com.marklynch.level.constructs.power.PowerSuperPeek;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Actor.HOBBY;
import com.marklynch.objects.actors.Guard;
import com.marklynch.objects.actors.Human;
import com.marklynch.objects.actors.Thief;
import com.marklynch.objects.armor.LegArmor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Storage;
import com.marklynch.objects.inanimateobjects.Wall;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.CopyOnWriteArrayList;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextUtils;

public class QuestSmallGame extends Quest {

	// Needs to be a large forest w/ animals near te lodge for the hunters after
	// the quest, if they survive.... it just makes sense. Another village could
	// be a fishing village and another one could be a farming village or
	// another could be a big trading hub and they trade to get food, and a
	// foraging village, and mix and match some..... make it clear in
	// conversations that this is a farming/fishing/hunting/trading village.
	// There could be two farming villages, one of them does only veg and
	// foraging, and theyre all hippy vegans. Another could be deep in the
	// woods, and the houses are made out of giant trees and they forage and
	// hunt.

	// The hunting party leader says out loud to the pack, in a way that u can
	// see it "so, use the water imbued weapons on the the beast. No fire, it
	// feeds off the stuff."

	// Then at the start of the conversation it's him talking to the gorup going
	// "...and that's how we're going to pull off the greatest hunt of all
	// time." Then turning to u.... begin explaining quest.

	// Hunting plan could have a crude drawying of the wolf. I think all signs
	// should have pngs to display instead of text.
	// "Hello stranger. I'm (creating) the most formidable hunting party this
	// town has ever seen, would you be interested in joining us? -yes -no
	// .Living in the craggle cave just south of here is a ferocious beast. She
	// may look nothing more than an obersizef wolf, but dpnt let her fool you.
	// Flames spit forth from his eyes and she can kill a man with nary a howl"
	// -why would u want to kill this beast? -from what u say it soounds like an
	// impossible task -i love a challenge, im in." I love the ridiculous
	// description of the beast, make it more ridiculous, theres definitely
	// dialogue like this in the witcher, look it up. AND THEN make the
	// ridiculous description true :D. Also new condition where u tell them u
	// wont be part of it, and they say last chance, u say ur sure, then the
	// mage runs over to u to tell u to save the beast. Everytging the hunter
	// says should be over the top, pirate weaving tales in a bar stupid. Look
	// up some similar adventure time and pirate show dialogs like this.
	// And the head hunter lost and arm to the beast! :p "-no beast like that
	// exists, you're ridiculous". "Tis the same beast that claimed my arm! Well
	// come with us and see with your own eyes". In the cave theres his arm!!!
	// :'D "rotted arm" With a ring beside it. U could give him the ring if u
	// want and he goes "nono, that's very kind of you, but keep it laddie, it
	// just brings back painful memories for me".
	// On the sign for the hunt plan have even more ridiculous facts about the
	// beast that are still actually facts.

	// "There should be some spare equipment round the back, help yourself"

	// Need to change ownership of the stuff around the back from master hunter
	// to you when you accept the quest. 2 guys around the back to stop u
	// stealing shit beforehand.

	// They're all standing around in a circle with awesome equipment in the
	// middle

	// For stealing show a dialogue if u'll get caught doing it.

	// O M F G the ranger wants to enslave super wolf. O M F G
	// O M F G. And he like tried to get the wolves power before but he was
	// rejected.
	// And now that the wolf is weakened after the fight he puts him in an evil
	// restriction
	// And u can choose to fight the environmentalist or not. If u fight him he
	// has a skill "entrap" or "restriction" or something, and he has the skill
	// book for that power on his body. And he has a note on his body,
	// instructions on how to entrap a wolf spirit god thing. SO u can try
	// entrap the wolf or let it go. Entrap it to get ++fire resistance and if u
	// let the wolf go he'll give u something deadly from the cave.
	// And if u kill the wolf he attacks u.. and hes powerful and it'll take a
	// bunch of ur new hunter pals to save ur ass. Or something.

	// SO powerwise - hunters == wolfpack > envirnmentalist.

	// special case if u kill ranger early on :P

	// Could lead in to larger overarching story with spirit animals

	// U can rob the hunters blind when they leave on the hunt, u can go back
	// and steal all their shit :P I like this.

	// quest flag on the weapons round back should be enough to stop AIs
	// stealing shit + guards nearby + put ownership = hunters on it until ur
	// told to go get them

	// Make the ranger seem dopey until the end. What do u call an
	// ranger magic user? Sage? Forest Sage? Woodland sage? Sage is
	// good, makes him sound benevolent and omniscient.

	// Some sort of reaction from the wolves if u see them before accepting the
	// hunt.Walk over and have a look at u? Nothing agressive. Maybe
	// unintentionally intimidating?

	// Quest text
	final String OBJECTIVE_FOLLOW_THE_HUNTERS_TO_SUPERWOLF = "Follow the hunters to Superwolf";

	// Activity Strings
	final String ACTIVITY_PLANNING_A_HUNT = "Planning a hunt";
	final String ACTIVITY_DESCRIPTION_HUNTING = "Goin' hunting";
	final String ACTIVITY_SPYING = "Spying";
	final String ACTIVITY_SAVING_THE_WORLD = "Saving the world";
	final String ACTIVITY_WAITING_FOR_YOU = "Waiting for you";
	final String ACTIVITY_DESCRIPTION_GOING_HOME = "Going home";

	// End
	boolean huntersReleasedFromQuest;

	// Actors
	public GroupOfActors hunterPack;
	public Actor rangerBill;
	public GroupOfActors wolfPack;
	public Actor superWolf;
	Actor cub;

	// GameObjects
	public CopyOnWriteArrayList<GameObject> weaponsBehindLodge = new CopyOnWriteArrayList<GameObject>(GameObject.class);

	// Squares
	public Square squareBehindLodge;
	public Square huntPlanningArea;
	public Actor hunterBrent;

	// Conversations

	public static Actor hunter;

	// public String objectiveTheWolves;// = "The Wolves";
	// public String objectiveTheWeapons = "Weapons behind lodge";
	// public String objectiveTheHunters = "Hunters";
	public Objective objectiveWolves;
	public Objective objectiveWeaponsBehindLodge;
	public Objective objectiveHunters;
	public Objective objectiveEnvironmentalist;

	// journalLog strings
	public JournalLog journalLogSeenHunters = new JournalLog("I've spotted some hunters planning a hunt");
	public JournalLog journalLogSeenWolves = new JournalLog("I've spotted a pack of wolves.");
	public JournalLog journalLogAgreedToJoinHunters = new JournalLog(
			"I've agreed to join a group of hunters in town on a hunt for The Super Wolf, they told me there's some weapons around the back of their Lodge");
	public JournalLog journalLogSetOffWithHunters = new JournalLog(
			"I've set off with the hunters towards the creature's lair");
	public JournalLog journalLogRangerWasSpying = new JournalLog(
			"I met a strange figure spying on the hunters of Town Lodge");
	public JournalLog journalLogSaveTheWolfVariant1 = new JournalLog(
			"Behind the hunting lodge, where the weapons were meant to be, stood a strange figure. He told me the the hunters' mark is an intelligent being should be spared.");
	public JournalLog journalLogSaveTheWolfVariant2 = new JournalLog(
			"Behind the hunting lodge, where the weapons were meant to be, stood the strange figure from before. He told me the the hunters' mark is an intelligent being and should be spared.");

	public JournalLog journalLogRetrievedWeapons = new JournalLog(
			"I've retrieved the weapons from behind the hunter's lodge");
	public JournalLog journalLogReadHuntPlan1 = new JournalLog(
			"In the staging area for a hunt I found the plan for the hunt");
	public JournalLog journalLogReadHuntPlan2 = new JournalLog(
			"In the staging area for the hunt I found the plan for the hunt");

	public JournalLog journalLogTalkedToWolves = new JournalLog(
			"A wolf, talked to me. He told me \"They plot\". He showed me hunters in the town nearby planning a hunt.");

	public JournalLog journalLogAttackedHunters = new JournalLog("I attacked the hunters");
	public JournalLog journalLogAttackedWolves = new JournalLog("I attacked the wolves");
	public JournalLog journalLogHuntersEngagedWolves = new JournalLog("The hunters have engaged the wolves");
	public JournalLog journalLogHuntersDead = new JournalLog("All the hunters are dead");
	public JournalLog journalLogWolvesDead = new JournalLog("All the wolves are dead");

	// Resolutions
	public JournalLog journalLogToldToFuckOffByHunters = new JournalLog(
			"I didn't help the hunters and they're giving me nothing");
	public JournalLog journalLogRewardedByHunters = new JournalLog("The hunters rewarded me for helping them");
	public JournalLog journalLogIgnoredByWolves = new JournalLog("I didn't help the wolves and they are ignoring me");
	public JournalLog journalLogThankedByWolves = new JournalLog("The wolves thanked me for helping them");
	public JournalLog journalLogAllDead = new JournalLog("The hunters and wolves are all dead");

	// Flags

	// boolean playerAttackedWolves;
	// boolean huntersDead;
	// boolean wolvesDead;

	public QuestSmallGame() {
		super();

		TownForestBuilder.createForest();

		name = "SMALL GAME";
		// addObjective("No objective");

		squareBehindLodge = Game.level.squares[111][16];
		huntPlanningArea = Game.level.squares[105][8];

		// BRENT

		// Add lead hunter
		GameObject brentsBed = Templates.BED.makeCopy(Game.level.squares[110][10], null);
		hunterBrent = Templates.HUNTER.makeCopy("Lead Hunter Brent", Game.level.squares[105][8],
				Game.level.factions.townsPeople, brentsBed, 203,
				new GameObject[] { Templates.HUNTING_BOW.makeCopy(null, null),
						Templates.HUNTING_KNIFE.makeCopy(null, null), Templates.LEATHERS.makeCopy(null, null),
						Templates.PANTS.makeCopy(null, null) },
				new GameObject[] {}, AreaList.townForest,
				new int[] { Templates.HUNTING_BOW.templateId, Templates.HUNTING_KNIFE.templateId },
				new HOBBY[] { HOBBY.HUNTING });
		hunter = hunterBrent;

		// Hunting lodge
		CopyOnWriteArrayList<StructureFeature> lodgeFeatures = new CopyOnWriteArrayList<StructureFeature>(StructureFeature.class);
		lodgeFeatures.add(new StructureFeature(
				Templates.DOOR.makeCopy("Door", Game.level.squares[105][12], false, false, false, hunterBrent)));
		CopyOnWriteArrayList<StructureRoom> lodgeRooms = new CopyOnWriteArrayList<StructureRoom>(StructureRoom.class);
		lodgeRooms.add(new StructureRoom("Hunting Lodge", 107, 9, false, false, new CopyOnWriteArrayList<Actor>(Actor.class),
				new RoomPart(106, 10, 110, 14)));
		CopyOnWriteArrayList<StructureSection> lodgeSections = new CopyOnWriteArrayList<StructureSection>(StructureSection.class);
		lodgeSections.add(new StructureSection("Hunting Lodge", 105, 9, 111, 15, false, false));
		Structure lodge = new Structure("Hunting Lodge", lodgeSections, lodgeRooms,
				new CopyOnWriteArrayList<StructurePath>(StructurePath.class), lodgeFeatures, new CopyOnWriteArrayList<Square>(Square.class),
				ResourceUtils.getGlobalImage("icon_house.png", false), AreaTown.posX + 105, AreaTown.posY + 9,
				AreaTown.posX + 111, AreaTown.posY + 15, true, hunterBrent, new CopyOnWriteArrayList<Square>(Square.class),
				new CopyOnWriteArrayList<Wall>(Wall.class), Templates.WALL_CAVE, Square.STONE_TEXTURE, 2);
		Game.level.structures.add(lodge);
		Templates.HATCHET.makeCopy(Game.level.squares[AreaTown.posX + 3][AreaTown.posY + 3], hunterBrent);
		Templates.HATCHET.makeCopy(Game.level.squares[AreaTown.posX + 3][AreaTown.posY + 6], hunterBrent);

		// Add hunters
		GameObject brontsBed = Templates.BED.makeCopy(Game.level.squares[108][10], null);
		Actor hunterBront1 = Templates.HUNTER.makeCopy("Hunter Bront", Game.level.squares[103][7],
				Game.level.factions.townsPeople, brontsBed, 124,
				new GameObject[] { Templates.HUNTING_BOW.makeCopy(null, null),
						Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {}, AreaList.townForest,
				new int[] { Templates.HUNTING_BOW.templateId, Templates.HUNTING_KNIFE.templateId },
				new HOBBY[] { HOBBY.HUNTING });
		GameObject bront2sBed = Templates.BED.makeCopy(Game.level.squares[106][10], null);
		Human hunterBront2 = Templates.HUNTER.makeCopy("Hunter Brunt", Game.level.squares[103][8],
				Game.level.factions.townsPeople, bront2sBed, 73,
				new GameObject[] { Templates.HATCHET.makeCopy(null, null),
						Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {}, AreaList.townForest,
				new int[] { Templates.HUNTING_BOW.templateId, Templates.HUNTING_KNIFE.templateId },
				new HOBBY[] { HOBBY.HUNTING });
		GameObject bront3sBed = Templates.BED.makeCopy(Game.level.squares[110][12], null);
		Human hunterBront3 = hunterBront2.makeCopy("Hunter Brant", Game.level.squares[103][9],
				Game.level.factions.townsPeople, bront3sBed, 30,
				new GameObject[] { Templates.HATCHET.makeCopy(null, null),
						Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {}, AreaList.townForest,
				new int[] { Templates.HUNTING_BOW.templateId, Templates.HUNTING_KNIFE.templateId },
				new HOBBY[] { HOBBY.HUNTING });
		// (String name, Square square, Faction faction, GameObject bed, int
		// gold, GameObject[] mustHaves,
		// GameObject[] mightHaves, Area area)
		GameObject bront4sBed = Templates.BED.makeCopy(Game.level.squares[110][14], null);
		Human hunterBront4 = hunterBront2.makeCopy("Hunter Brint", Game.level.squares[102][7],
				Game.level.factions.townsPeople, bront4sBed, 83,
				new GameObject[] { Templates.HATCHET.makeCopy(null, null),
						Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {}, AreaList.townForest,
				new int[] { Templates.HUNTING_BOW.templateId, Templates.HUNTING_KNIFE.templateId },
				new HOBBY[] { HOBBY.HUNTING });
		GameObject bront5sBed = Templates.BED.makeCopy(Game.level.squares[108][14], null);
		Human hunterBront5 = hunterBront2.makeCopy("Hunter Brynt", Game.level.squares[102][8],
				Game.level.factions.townsPeople, bront5sBed, 23,
				new GameObject[] { Templates.HATCHET.makeCopy(null, null),
						Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {}, AreaList.townForest,
				new int[] { Templates.HUNTING_BOW.templateId, Templates.HUNTING_KNIFE.templateId },
				new HOBBY[] { HOBBY.HUNTING });
		GameObject bront6sBed = Templates.BED.makeCopy(Game.level.squares[106][14], null);
		Human hunterBront6 = hunterBront2.makeCopy("Hunter Brint the Younger", Game.level.squares[102][9],
				Game.level.factions.townsPeople, bront6sBed, 43,
				new GameObject[] { Templates.HATCHET.makeCopy(null, null),
						Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {}, AreaList.townForest,
				new int[] { Templates.HUNTING_BOW.templateId, Templates.HUNTING_KNIFE.templateId },
				new HOBBY[] { HOBBY.HUNTING });

		// Guards and their barracks
		// Structure 74,52 -> 85, 61
		Templates.GUARD.makeCopy("Guard Paul", Game.level.squares[77][53], Game.level.factions.townsPeople,
				Templates.BED.makeCopy(Game.level.squares[79][56], null), 34,
				new GameObject[] { Templates.SWORD.makeCopy(null, null) }, new GameObject[] {}, AreaList.town,
				new int[] { Templates.SWORD.templateId }, new HOBBY[] { HOBBY.HUNTING, HOBBY.FISHING }, Guard.dayShift,
				Game.level.squares[77][53]);
		Templates.GUARD.makeCopy("Guard John", Game.level.squares[29][12], Game.level.factions.townsPeople,
				Templates.BED.makeCopy(Game.level.squares[81][56], null), 37,
				new GameObject[] { Templates.SWORD.makeCopy(null, null) }, new GameObject[] {}, AreaList.town,
				new int[] { Templates.SWORD.templateId }, new HOBBY[] { HOBBY.HUNTING, HOBBY.FISHING }, Guard.dayShift,
				Game.level.squares[4][21], Game.level.squares[32][21]);
		CopyOnWriteArrayList<Wall> extraWallsBarracks = new CopyOnWriteArrayList<Wall>(Wall.class);
		CopyOnWriteArrayList<StructureFeature> featuresBarracks = new CopyOnWriteArrayList<StructureFeature>(StructureFeature.class);
		CopyOnWriteArrayList<StructurePath> pathsBarracks = new CopyOnWriteArrayList<StructurePath>(StructurePath.class);
		CopyOnWriteArrayList<StructureSection> sectionsBarracks = new CopyOnWriteArrayList<StructureSection>(StructureSection.class);
		CopyOnWriteArrayList<StructureRoom> roomsBarracks = new CopyOnWriteArrayList<StructureRoom>(StructureRoom.class);
		CopyOnWriteArrayList<Square> squaresToRemoveBarracks = new CopyOnWriteArrayList<Square>(Square.class);
		featuresBarracks.add(new StructureFeature(
				Templates.DOOR.makeCopy("Door", Game.level.squares[80][52], false, false, false, null)));
		featuresBarracks.add(new StructureFeature(
				Templates.DOOR.makeCopy("Door", Game.level.squares[80][61], false, false, false, null)));
		roomsBarracks.add(new StructureRoom("Barracks", 75, 53, false, false, new CopyOnWriteArrayList<Actor>(Actor.class),
				new RoomPart(75, 53, 84, 60)));
		sectionsBarracks.add(new StructureSection("Barracks", 74, 52, 85, 61, false, false));
		Structure barracks = new Structure("Barracks", sectionsBarracks, roomsBarracks, pathsBarracks, featuresBarracks,
				new CopyOnWriteArrayList<Square>(Square.class), ResourceUtils.getGlobalImage("icon_house.png", false), 74, 52, 85,
				61, true, null, squaresToRemoveBarracks, extraWallsBarracks, Templates.WALL_CAVE, Square.STONE_TEXTURE,
				5);
		Game.level.structures.add(barracks);

		// Fisherman
		Human fishermanJake = Templates.FISHERMAN.makeCopy("Fisherman Jake", Game.level.squares[103][37],
				Game.level.factions.townsPeople, null, 38,
				new GameObject[] { Templates.FISHING_ROD.makeCopy(null, null) }, new GameObject[] {}, AreaList.town,
				new int[] { Templates.FISHING_ROD.templateId }, new HOBBY[] { HOBBY.HUNTING, HOBBY.FISHING });
		LegArmor dungarees = Templates.DUNGAREES.makeCopy(null, fishermanJake);
		fishermanJake.inventory.add(dungarees);
		fishermanJake.legArmor = dungarees;

		// Thieves and their hut
		Templates.THIEF.makeCopy("Thief Ed", Game.level.squares[12][13], Game.level.factions.outsiders,
				Templates.BED.makeCopy(Game.level.squares[116][53], null), 64,
				new GameObject[] { Templates.HATCHET.makeCopy(null, null),
						Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {}, AreaList.town, new int[] {}, new HOBBY[] { HOBBY.HUNTING });

		Templates.THIEF.makeCopy("Thief Carl", Game.level.squares[11][13], Game.level.factions.outsiders,
				Templates.BED.makeCopy(Game.level.squares[116][55], null), 64,
				new GameObject[] { Templates.HATCHET.makeCopy(null, null),
						Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {}, AreaList.town, new int[] {}, new HOBBY[] { HOBBY.HUNTING });

		Thief t3 = Templates.THIEF.makeCopy("Thief Pete", Game.level.squares[10][13], Game.level.factions.outsiders,
				Templates.BED.makeCopy(Game.level.squares[114][55], null), 64,
				new GameObject[] { Templates.HATCHET.makeCopy(null, null),
						Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {}, AreaList.town, new int[] {}, new HOBBY[] { HOBBY.HUNTING });
		t3.aiRoutine = null;
		CopyOnWriteArrayList<Wall> extraWallsThievesHut = new CopyOnWriteArrayList<Wall>(Wall.class);
		CopyOnWriteArrayList<StructureFeature> featuresThievesHut = new CopyOnWriteArrayList<StructureFeature>(StructureFeature.class);
		CopyOnWriteArrayList<StructurePath> pathsThievesHut = new CopyOnWriteArrayList<StructurePath>(StructurePath.class);
		CopyOnWriteArrayList<StructureSection> sectionsThievesHut = new CopyOnWriteArrayList<StructureSection>(StructureSection.class);
		CopyOnWriteArrayList<StructureRoom> roomsThievesHut = new CopyOnWriteArrayList<StructureRoom>(StructureRoom.class);
		CopyOnWriteArrayList<Square> squaresToRemoveThievesHut = new CopyOnWriteArrayList<Square>(Square.class);
		featuresThievesHut.add(new StructureFeature(
				Templates.DOOR.makeCopy("Door", Game.level.squares[113][53], false, false, false, null)));
		roomsThievesHut.add(new StructureRoom("Hut", 114, 53, false, false, new CopyOnWriteArrayList<Actor>(Actor.class),
				new RoomPart(114, 53, 116, 55)));
		sectionsThievesHut.add(new StructureSection("Hut", 113, 52, 117, 56, false, false));
		Structure thievesHut = new Structure("Hut", sectionsThievesHut, roomsThievesHut, pathsThievesHut,
				featuresThievesHut, new CopyOnWriteArrayList<Square>(Square.class),
				ResourceUtils.getGlobalImage("icon_house.png", false), 113, 52, 117, 56, true, null,
				squaresToRemoveThievesHut, extraWallsThievesHut, Templates.WALL_CAVE, Square.STONE_TEXTURE, 5);
		Game.level.structures.add(thievesHut);

		Templates.HATCHET.makeCopy(Game.level.squares[10][17], t3);

		hunterPack = new GroupOfActors("Hunting Party", hunterBrent, hunterBront1, hunterBront2, hunterBront3,
				hunterBront4, hunterBront5, hunterBront6);

		this.hunterPack.quest = this;
		for (GameObject hunter : hunterPack.getMembers()) {
			hunter.quest = this;
		}

		final GameObject huntingPlan = Templates.SIGN.makeCopy(Game.level.squares[106][8], hunterBrent);
		huntingPlan.name = "Hunt Action Plan";
		huntingPlan.conversation = huntingPlan.createConversation(
				new Object[] { "Super Wolf - Weaknesses: Water Strengths: Fire will heal the beast" });

		// IF I ATTACK OR TELEPORT ON TO THE SIGN THEY SHOULD BE ANGRY..

		Storage chest = Templates.CHEST.makeCopy(Game.level.squares[103][1], false, null);
		chest.inventory.add(Templates.CLEAVER.makeCopy(null, null));
		chest.inventory.add(Templates.HUNTING_KNIFE.makeCopy(null, null));
		chest.inventory.add(Templates.GOLD.makeCopy(null, null, 101));

		// Ranget + hut
		rangerBill = hunterBront2.makeCopy("Ranger Bill", Game.level.squares[105][16], Game.level.factions.townsPeople,
				Templates.BED.makeCopy(Game.level.squares[131][35], null), 83,
				new GameObject[] { Templates.HATCHET.makeCopy(null, null),
						Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {}, AreaList.townForest,
				new int[] { Templates.HUNTING_BOW.templateId, Templates.HUNTING_KNIFE.templateId },
				new HOBBY[] { HOBBY.HUNTING });
		rangerBill.quest = this;

		CopyOnWriteArrayList<Wall> extraWallsRangersHut = new CopyOnWriteArrayList<Wall>(Wall.class);
		CopyOnWriteArrayList<StructureFeature> featuresRangersHut = new CopyOnWriteArrayList<StructureFeature>(StructureFeature.class);
		CopyOnWriteArrayList<StructurePath> pathsRangersHut = new CopyOnWriteArrayList<StructurePath>(StructurePath.class);
		CopyOnWriteArrayList<StructureSection> sectionsRangersHut = new CopyOnWriteArrayList<StructureSection>(StructureSection.class);
		CopyOnWriteArrayList<StructureRoom> roomsRangersHut = new CopyOnWriteArrayList<StructureRoom>(StructureRoom.class);
		CopyOnWriteArrayList<Square> squaresToRemoveRangersHut = new CopyOnWriteArrayList<Square>(Square.class);
		featuresRangersHut.add(new StructureFeature(
				Templates.DOOR.makeCopy("Door", Game.level.squares[133][34], false, false, false, rangerBill)));
		roomsRangersHut.add(new StructureRoom("Ranger's Hut", 130, 34, false, false, new CopyOnWriteArrayList<Actor>(Actor.class),
				new RoomPart(130, 34, 132, 36)));
		sectionsRangersHut.add(new StructureSection("Ranger's Hut", 129, 33, 133, 37, false, false));
		Structure rangerHut = new Structure("Ranger's Hut", sectionsRangersHut, roomsRangersHut, pathsRangersHut,
				featuresRangersHut, new CopyOnWriteArrayList<Square>(Square.class),
				ResourceUtils.getGlobalImage("icon_house.png", false), 129, 33, 133, 37, true, null,
				squaresToRemoveRangersHut, extraWallsRangersHut, Templates.WALL_CAVE, Square.STONE_TEXTURE, 5);
		Game.level.structures.add(rangerHut);

		// wolf
		superWolf = Templates.WOLF.makeCopy("Wolf Queen", Game.level.squares[203][12], Game.level.factions.wolves, null,
				new GameObject[] {}, new GameObject[] {}, null);
		superWolf.powers.add(new PowerSuperPeek(superWolf));

		Actor wolf2 = Templates.WOLF.makeCopy("Wolf", Game.level.squares[204][11], Game.level.factions.wolves, null,
				new GameObject[] {}, new GameObject[] {}, null);

		Actor wolf3 = Templates.WOLF.makeCopy("Wolf", Game.level.squares[205][11], Game.level.factions.wolves, null,
				new GameObject[] {}, new GameObject[] {}, null);

		// [207][16]

		wolfPack = new GroupOfActors("Wolf pack", superWolf, wolf2, wolf3);

		this.wolfPack.quest = this;
		for (GameObject wolf : wolfPack.getMembers()) {
			wolf.quest = this;
			wolf.inventory.add(Templates.CLEAVER.makeCopy(null, null));
		}

		weaponsBehindLodge = new CopyOnWriteArrayList<GameObject>(GameObject.class);
		weaponsBehindLodge.add(Templates.HATCHET.makeCopy(squareBehindLodge, hunterBrent));
		weaponsBehindLodge.add(Templates.HUNTING_BOW.makeCopy(squareBehindLodge, hunterBrent));

		for (GameObject weaponBehindLodge : weaponsBehindLodge) {
			weaponBehindLodge.quest = this;
		}
		// END OF FROM EDITOR

		objectiveWolves = new Objective("The Wolves", superWolf, null, superWolf.imageTexture);
		allObjectives.add(objectiveWolves);
		objectiveWeaponsBehindLodge = new Objective("Hunting Weapons", weaponsBehindLodge.get(0), null,
				weaponsBehindLodge.get(0).imageTexture);
		allObjectives.add(objectiveWeaponsBehindLodge);
		objectiveHunters = new Objective("The Hunters", hunterBrent, null, hunterBrent.imageTexture);
		allObjectives.add(objectiveHunters);
		objectiveEnvironmentalist = new Objective("Environmentalist", rangerBill, null, rangerBill.imageTexture);
		allObjectives.add(objectiveEnvironmentalist);

		ConversationsSmallGame.quest = this;
		ConversationsSmallGame.createConversations();

		// WAS HAVING TROUBLE SAVING
		// huntingPlan.setOnReadListener(new ActionListener() {
		// @Override
		// public void onRead() {
		// if (!haveJournalLog(journalLogReadHuntPlan1) &&
		// !haveJournalLog(journalLogReadHuntPlan2)) {
		// if (!started) {
		// addJournalLog(journalLogReadHuntPlan1);
		// addObjective(objectiveHunters);
		// } else {
		// addJournalLog(journalLogReadHuntPlan2);
		// }
		// turnUpdated = Level.turn;
		// addJournalLog(new JournalLog(huntingPlan));
		// addJournalLog(new JournalLog(superWolf));
		//
		// BestiaryKnowledge bestiaryKnowledge =
		// Level.bestiaryKnowledgeCollection.get(superWolf.templateId);
		// bestiaryKnowledge.name = true;
		// bestiaryKnowledge.level = true;
		// bestiaryKnowledge.image = true;
		// bestiaryKnowledge.totalHealth = true;
		// bestiaryKnowledge.faction = true;
		// bestiaryKnowledge.groupOfActors = true;
		//
		// // Stats
		// for (HIGH_LEVEL_STATS statType : HIGH_LEVEL_STATS.values()) {
		// bestiaryKnowledge.putHighLevel(statType, true);
		// }
		//
		// // Powers
		// bestiaryKnowledge.powers = true;
		//
		// }
		// }
		// });

		GameObject mound1 = Templates.MOUND.makeCopy(Game.level.squares[1][1], null);
		mound1.inventory.add(Templates.ROCK.makeCopy(null, null));

		GameObject mound2 = Templates.MOUND.makeCopy(Game.level.squares[2][1], null);
		mound2.inventory.add(Templates.ROCK.makeCopy(null, null));

		GameObject mound3 = Templates.MOUND.makeCopy(Game.level.squares[3][1], null);
		mound3.inventory.add(Templates.ROCK.makeCopy(null, null));

		Storage crate = Templates.CRATE.makeCopy(Game.level.squares[123][9], false, null);
		crate.inventory.add(Templates.HATCHET.makeCopy(null, null));
		links = TextUtils.getLinks(true, this);
	}

	@Override
	public void update() {
		if (resolved)
			return;

		super.update();

		// See hunters for first time
		if (!haveJournalLog(journalLogSeenHunters)) {
			if (hunterBrent.squareGameObjectIsOn.visibleToPlayer) {
				addJournalLog(journalLogSeenHunters);
				addObjective(objectiveHunters);
			}
		}

		// See wolves for first time
		if (!haveJournalLog(journalLogSeenWolves)) {
			if (superWolf.squareGameObjectIsOn.visibleToPlayer) {
				addJournalLog(journalLogSeenWolves);
				addObjective(objectiveWolves);
			}
		}

		// You have the pick up weapons objective and pick them up
		if (currentObjectives.contains(objectiveWeaponsBehindLodge)) {
			for (GameObject weapon : weaponsBehindLodge) {
				if (Game.level.player.inventory.contains(weapon)) {
					currentObjectives.remove(this.objectiveWeaponsBehindLodge);
					if (!haveJournalLog(journalLogSaveTheWolfVariant1)
							&& !haveJournalLog(journalLogSaveTheWolfVariant2)) {
						addJournalLog(journalLogRetrievedWeapons);
					}
				}
			}
		}

		// The wolves are dead
		if (!haveJournalLog(journalLogWolvesDead)) {
			if (wolfPack.size() == 0) {
				addJournalLog(journalLogWolvesDead);
			}
		}

		// Player has attacked the wolves
		if (!haveJournalLog(journalLogAttackedWolves) && wolfPack.getAttackers().contains(Game.level.player)) {
			addJournalLog(journalLogSeenWolves);
			addJournalLog(journalLogAttackedWolves);

		}

		// The hunters are dead
		if (!haveJournalLog(journalLogHuntersDead)) {
			if (hunterPack.size() == 0) {
				addJournalLog(journalLogHuntersDead);
			}
		}

		// Player has attacked the hunters after accepting quest
		if (!haveJournalLog(journalLogAttackedHunters) && hunterPack.getAttackers().contains(Game.level.player)) {
			addJournalLog(journalLogSeenHunters);
			addJournalLog(journalLogAttackedHunters);
		}

		// Hunters and wolves have fought
		if (!haveJournalLog(journalLogHuntersEngagedWolves) && hunterPack.hasAttackers()) {
			for (int j = 0; j < wolfPack.size(); j++) {
				if (hunterPack.getAttackers().contains(wolfPack.getMember(j))) {
					addJournalLog(journalLogSeenWolves);
					addJournalLog(journalLogHuntersEngagedWolves);
					break;
				}
			}
		}

	}

	@Override
	public boolean update(Actor actor) {

		if (1 == 1)
			return false;

		if (resolved)
			return false;
		if (hunterPack.contains(actor)) {
			return updateHunter(actor);
		} else if (actor == rangerBill) {
			return updateEnvironmentalist(actor);
		} else if (wolfPack.contains(actor)) {
			return true;
		}
		return false;
	}

	private boolean updateHunter(Actor actor) {

		if (huntersReleasedFromQuest) {
			return false;
		}

		if (!haveJournalLog(journalLogSetOffWithHunters)) {
			actor.activityDescription = ACTIVITY_PLANNING_A_HUNT;
			if (actor == hunterPack.getLeader()) {
				goToHuntPlanningArea(actor);
			}
		} else if (haveJournalLog(journalLogSetOffWithHunters) && !haveJournalLog(journalLogWolvesDead)) {

			if (actor == hunterPack.getLeader()) {

				if (actor.straightLineDistanceTo(Game.level.player.squareGameObjectIsOn) > 5
						&& actor.straightLineDistanceTo(superWolf.squareGameObjectIsOn) < Game.level.player
								.straightLineDistanceTo(superWolf.squareGameObjectIsOn)) {
					// Wait for the player
					actor.activityDescription = ACTIVITY_WAITING_FOR_YOU;
				} else {
					actor.activityDescription = ACTIVITY_DESCRIPTION_HUNTING;
					if (superWolf.remainingHealth > 0) {
						if (!AIRoutineUtils.attackTarget(superWolf)) {
							AIRoutineUtils.moveTowards(superWolf);
						}
					}
				}
			}
		} else if (haveJournalLog(journalLogWolvesDead) && haveJournalLog(journalLogAttackedWolves)
				&& !haveJournalLog(journalLogAgreedToJoinHunters)) {
			// Wolves were killed by player before accepting the mission
			goToHuntPlanningArea(actor);
		} else if (haveJournalLog(journalLogWolvesDead) && haveJournalLog(journalLogAttackedWolves)
				&& !haveJournalLog(journalLogSetOffWithHunters)) {
			// Wolves were killed by player after accepting the mission, but
			// before he told the hunters he's ready to go
			goToHuntPlanningArea(actor);
		} else if (haveJournalLog(journalLogWolvesDead) && haveJournalLog(journalLogAttackedWolves)) {
			// Wolves were killed after departing for the hunt, and the player
			// helped kill them
			// Talk to them... for some reason
			if (actor == hunterPack.getLeader()) {
				if (actor.straightLineDistanceTo(Game.level.player.squareGameObjectIsOn) < 2) {
					new ActionTalk(actor, Game.level.player).perform();
				} else {
					AIRoutineUtils.moveTowards(Game.level.player.squareGameObjectIsOn);
				}
			}
		} else if (haveJournalLog(journalLogWolvesDead) && !haveJournalLog(journalLogAttackedWolves)) {
			// Wolves were killed, but player didnt help
			if (actor == hunterPack.getLeader()) {
				if (actor.squareGameObjectIsOn != huntPlanningArea) {
					actor.activityDescription = ACTIVITY_DESCRIPTION_GOING_HOME;
					AIRoutineUtils.moveTowardsTargetSquare(huntPlanningArea);
				} else {
					huntersReleasedFromQuest = true;
				}
			}
		}
		return true;

	}

	public void goToHuntPlanningArea(Actor actor) {
		AIRoutineUtils.moveTowardsTargetSquare(huntPlanningArea);
	}

	private boolean updateEnvironmentalist(Actor actor) {
		if (haveJournalLog(journalLogSetOffWithHunters)) {
			// Hunters are on the move, head to wolf
			if (rangerBill.canSeeGameObject(superWolf)) {
			} else {
				AIRoutineUtils.moveTowardsTargetSquare(superWolf.squareGameObjectIsOn);
			}
			return true;
		} else if (!haveJournalLog(journalLogAgreedToJoinHunters)) {
			// Starting point, spying
			actor.activityDescription = ACTIVITY_SPYING;
		} else if (haveJournalLog(journalLogAgreedToJoinHunters)
				&& (!haveJournalLog(journalLogSaveTheWolfVariant1) && !haveJournalLog(journalLogSaveTheWolfVariant2))) {
			// Go get the weapons

			actor.activityDescription = ACTIVITY_SAVING_THE_WORLD;

			if (rangerBill.squareGameObjectIsOn != squareBehindLodge) {
				// Move to weapons behind the lodge
				AIRoutineUtils.moveTowardsTargetSquare(squareBehindLodge);
			} else {
				// Pick up weapons behind the lodge
				for (GameObject weaponBehindLodge : weaponsBehindLodge) {
					if (weaponBehindLodge.squareGameObjectIsOn == squareBehindLodge) {
						AIRoutineUtils.pickupTarget(weaponBehindLodge);

						// MAYBE put loot in a chest in the lodge

					}
				}
				// If the player is near, tell them not to kill the wolf and
				// give them the weapons
				if (actor.straightLineDistanceTo(Game.level.player.squareGameObjectIsOn) < 2) {
					new ActionTalk(actor, Game.level.player).perform();
				}
			}
		}
		return true;
	}

	@Override
	public Conversation getConversation(Actor actor) {
		if (resolved)
			return null;
		if (hunterPack.contains(actor)) {
			return getConversationForHunter(actor);
		} else if (actor == rangerBill) {
			return getConversationForEnvironmentalist(actor);
		} else if (wolfPack.contains(actor)) {
			return getConversationForWolf(actor);
		}
		return null;
	}

	public Conversation getConversationForHunter(Actor actor) {
		// Talking to a hunter
		if (!haveJournalLog(journalLogAgreedToJoinHunters)) {
			return ConversationsSmallGame.conversationHuntersJoinTheHunt;
		} else if (!haveJournalLog(journalLogSetOffWithHunters)) {
			return ConversationsSmallGame.conversationHuntersReadyToGo;
		} else if (haveJournalLog(journalLogWolvesDead) && !haveJournalLog(journalLogAttackedWolves)) {
			return ConversationsSmallGame.conversationHuntersOnlyHuntersGetLoot;
		}
		return null;
	}

	public Conversation getConversationForEnvironmentalist(Actor actor) {
		// Talking to environmentalist
		if (!haveJournalLog(journalLogAgreedToJoinHunters)) {
			return ConversationsSmallGame.conversationRangerImNotSpying;
		} else if (!haveJournalLog(journalLogSaveTheWolfVariant1) && !haveJournalLog(journalLogSaveTheWolfVariant2)) {
			return ConversationsSmallGame.conversationRangerSaveTheWolf;
		}
		return rangerBill.createConversation("...");
	}

	public Conversation getConversationForWolf(Actor actor) {

		// Talking to environmentalist
		if (haveJournalLog(journalLogAttackedWolves)) {
			return null;
		} else if (haveJournalLog(journalLogHuntersDead) && !haveJournalLog(journalLogAttackedHunters)) {
			return ConversationsSmallGame.conversationWolfISurvivedNoThanksToYou;
		} else if (haveJournalLog(journalLogHuntersDead) && haveJournalLog(journalLogAttackedHunters)) {
			return ConversationsSmallGame.conversationWolfThankYou;
		} else if (haveJournalLog(journalLogHuntersEngagedWolves)) {
			return null;
		} else if (haveJournalLog(journalLogSetOffWithHunters)) {
			return ConversationsSmallGame.conversationWolfTheyCome;
		} else if (!haveJournalLog(journalLogSetOffWithHunters)) {
			return ConversationsSmallGame.conversationWolfTheyPlot;
		}
		return null;
	}

}
