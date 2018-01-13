package com.marklynch.level.quest.smallgame;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Group;
import com.marklynch.level.constructs.actionlisteners.ActionListener;
import com.marklynch.level.constructs.beastiary.BestiaryKnowledge;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructurePath;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.constructs.bounds.structure.StructureRoom.RoomPart;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.constructs.journal.JournalLog;
import com.marklynch.level.constructs.journal.Objective;
import com.marklynch.level.constructs.power.PowerSuperPeek;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Discoverable;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Readable;
import com.marklynch.objects.Storage;
import com.marklynch.objects.Wall;
import com.marklynch.objects.actions.ActionTalk;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Human;
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

	// O M F G the enviromentalist wants to enslave super wolf. O M F G
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

	// special case if u kill enviromentalist early on :P

	// Could lead in to larger overarching story with spirit animals

	// U can rob the hunters blind when they leave on the hunt, u can go back
	// and steal all their shit :P I like this.

	// quest flag on the weapons round back should be enough to stop AIs
	// stealing shit + guards nearby + put ownership = hunters on it until ur
	// told to go get them

	// Make the enviromentalist seem dopey until the end. What do u call an
	// enviromentalist magic user? Sage? Forest Sage? Woodland sage? Sage is
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
	Group hunterPack;
	Actor environmentalistBill;
	Group wolfPack;
	Actor superWolf;
	Actor cub;

	// GameObjects
	ArrayList<GameObject> weaponsBehindLodge;

	// Squares
	Square squareBehindLodge;
	Square huntPlanningArea;
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
	JournalLog journalLogSeenHunters = new JournalLog("I've spotted some hunters planning a hunt");
	JournalLog journalLogSeenWolves = new JournalLog("I've spotted a pack of wolves.");
	JournalLog journalLogAgreedToJoinHunters = new JournalLog(
			"I've agreed to join a group of hunters in town on a hunt for The Super Wolf, they told me there's some weapons around the back of their Lodge");
	JournalLog journalLogSetOffWithHunters = new JournalLog(
			"I've set off with the hunters towards the creature's lair");
	JournalLog journalLogEnviromentalistWasSpying = new JournalLog(
			"I met a strange figure spying on the hunters of Town Lodge");
	JournalLog journalLogSaveTheWolfVariant1 = new JournalLog(
			"Behind the hunting lodge, where the weapons were meant to be, stood a strange figure. He told me the the hunters' mark is an intelligent being should be spared.");
	JournalLog journalLogSaveTheWolfVariant2 = new JournalLog(
			"Behind the hunting lodge, where the weapons were meant to be, stood the strange figure from before. He told me the the hunters' mark is an intelligent being and should be spared.");

	JournalLog journalLogRetrievedWeapons = new JournalLog("I've retrieved the weapons from behind the hunter's lodge");
	JournalLog journalLogReadHuntPlan1 = new JournalLog("In the staging area for a hunt I found the plan for the hunt");
	JournalLog journalLogReadHuntPlan2 = new JournalLog(
			"In the staging area for the hunt I found the plan for the hunt");

	JournalLog journalLogTalkedToWolves = new JournalLog(
			"A wolf, talked to me. He told me \"They plot\". He showed me hunters in the town nearby planning a hunt.");

	JournalLog journalLogAttackedHunters = new JournalLog("I attacked the hunters");
	JournalLog journalLogAttackedWolves = new JournalLog("I attacked the wolves");
	JournalLog journalLogHuntersEngagedWolves = new JournalLog("The hunters have engaged the wolves");
	JournalLog journalLogHuntersDead = new JournalLog("All the hunters are dead");
	JournalLog journalLogWolvesDead = new JournalLog("All the wolves are dead");

	// Resolutions
	JournalLog journalLogToldToFuckOffByHunters = new JournalLog(
			"I didn't help the hunters and they're giving me nothing");
	JournalLog journalLogRewardedByHunters = new JournalLog("The hunters rewarded me for helping them");
	JournalLog journalLogIgnoredByWolves = new JournalLog("I didn't help the wolves and they are ignoring me");
	JournalLog journalLogThankedByWolves = new JournalLog("The wolves thanked me for helping them");
	JournalLog journalLogAllDead = new JournalLog("The hunters and wolves are all dead");

	// Flags

	// boolean playerAttackedWolves;
	// boolean huntersDead;
	// boolean wolvesDead;

	public QuestSmallGame() {
		super();

		name = "SMALL GAME";
		// addObjective("No objective");

		squareBehindLodge = Game.level.squares[111][16];
		huntPlanningArea = Game.level.squares[105][8];

		// BRENT

		// Add lead hunter
		GameObject brentsBed = Templates.BED.makeCopy(Game.level.squares[110][10], null);
		hunterBrent = Templates.HUNTER.makeCopy("Hunter Brent", Game.level.squares[105][8],
				Game.level.factions.townsPeople, brentsBed, 203, new GameObject[] {
						Templates.HUNTING_BOW.makeCopy(null, null), Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {}, null);
		hunter = hunterBrent;

		// Hunting lodge
		ArrayList<GameObject> lodgeFeatures = new ArrayList<GameObject>();
		lodgeFeatures
				.add(Templates.DOOR.makeCopy("Door", Game.level.squares[105][12], false, false, false, hunterBrent));
		ArrayList<StructureRoom> lodgeRooms = new ArrayList<StructureRoom>();
		lodgeRooms.add(new StructureRoom("Hunting Lodge", 107, 9, false, new ArrayList<Actor>(),
				new RoomPart(106, 10, 110, 14)));
		ArrayList<StructureSection> lodgeSections = new ArrayList<StructureSection>();
		lodgeSections.add(new StructureSection("Hunting Lodge", 105, 9, 111, 15, false));
		Structure lodge = new Structure("Hunting Lodge", lodgeSections, lodgeRooms, new ArrayList<StructurePath>(),
				lodgeFeatures, new ArrayList<Square>(), "building.png", 896, 896 + 640, 896, 896 + 640, true,
				hunterBrent, new ArrayList<Square>(), new ArrayList<Wall>(), Templates.WALL, Square.STONE_TEXTURE, 2);
		Game.level.structures.add(lodge);

		// Add hunters
		GameObject brontsBed = Templates.BED.makeCopy(Game.level.squares[108][10], null);
		Actor hunterBront1 = Templates.HUNTER.makeCopy("Hunter Bront", Game.level.squares[103][7],
				Game.level.factions.townsPeople, brontsBed, 124, new GameObject[] {
						Templates.HUNTING_BOW.makeCopy(null, null), Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {}, null);
		GameObject bront2sBed = Templates.BED.makeCopy(Game.level.squares[106][10], null);
		Human hunterBront2 = Templates.HUNTER.makeCopy("Hunter Brunt", Game.level.squares[103][8],
				Game.level.factions.townsPeople, bront2sBed, 73, new GameObject[] {
						Templates.HATCHET.makeCopy(null, null), Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {}, null);
		GameObject bront3sBed = Templates.BED.makeCopy(Game.level.squares[110][12], null);
		Human hunterBront3 = hunterBront2.makeCopy("Hunter Brant", Game.level.squares[103][9],
				Game.level.factions.townsPeople, bront3sBed, 30, new GameObject[] {
						Templates.HATCHET.makeCopy(null, null), Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {}, null);
		// (String name, Square square, Faction faction, GameObject bed, int
		// gold, GameObject[] mustHaves,
		// GameObject[] mightHaves, Area area)
		GameObject bront4sBed = Templates.BED.makeCopy(Game.level.squares[110][14], null);
		Human hunterBront4 = hunterBront2.makeCopy("Hunter Brint", Game.level.squares[102][7],
				Game.level.factions.townsPeople, bront4sBed, 83, new GameObject[] {
						Templates.HATCHET.makeCopy(null, null), Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {}, null);
		GameObject bront5sBed = Templates.BED.makeCopy(Game.level.squares[108][14], null);
		Human hunterBront5 = hunterBront2.makeCopy("Hunter Brynt", Game.level.squares[102][8],
				Game.level.factions.townsPeople, bront5sBed, 23, new GameObject[] {
						Templates.HATCHET.makeCopy(null, null), Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {}, null);
		GameObject bront6sBed = Templates.BED.makeCopy(Game.level.squares[106][14], null);
		Human hunterBront6 = hunterBront2.makeCopy("Hunter Brint the Younger", Game.level.squares[102][9],
				Game.level.factions.townsPeople, bront6sBed, 43, new GameObject[] {
						Templates.HATCHET.makeCopy(null, null), Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {}, null);

		Human thief = Templates.THIEF.makeCopy("Thief Carl",
				Game.level.squares[12][13], Game.level.factions.outsiders, null, 64, new GameObject[] {
						Templates.HATCHET.makeCopy(null, null), Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {}, null);

		// Some ground hatchets
		Templates.HATCHET.makeCopy(Game.level.squares[3][6], Game.level.player);
		Templates.HATCHET.makeCopy(Game.level.squares[5][6], thief);
		Templates.BLOOD.makeCopy(Game.level.squares[5][6], Game.level.player);
		Templates.HATCHET.makeCopy(Game.level.squares[1][6], Game.level.player);

		hunterPack = new Group("Hunting Party", hunterBrent, hunterBront1, hunterBront2, hunterBront3, hunterBront4,
				hunterBront5, hunterBront6);

		this.hunterPack.quest = this;
		for (GameObject hunter : hunterPack.getMembers()) {
			hunter.quest = this;
		}

		final Readable huntingPlan = Templates.SIGN.makeCopy(Game.level.squares[106][8], "Hunt Action Plan",
				new Object[] { "Super Wolf - Weaknesses: Water Strengths: Fire will heal the beast" }, hunterBrent);

		// IF I ATTACK OR TELEPORT ON TO THE SIGN THEY SHOULD BE ANGRY..

		Storage chest = Templates.CHEST.makeCopy("Chest", Game.level.squares[103][1], false, null);
		chest.inventory.add(Templates.CLEAVER.makeCopy(null, null));
		chest.inventory.add(Templates.HUNTING_KNIFE.makeCopy(null, thief));
		chest.inventory.add(Templates.GOLD.makeCopy(null, null, 101));

		environmentalistBill = hunterBront2.makeCopy("Enviromentalist Bill",
				Game.level.squares[105][16], Game.level.factions.townsPeople, null, 83, new GameObject[] {
						Templates.HATCHET.makeCopy(null, null), Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {}, null);
		environmentalistBill.quest = this;

		superWolf = Templates.WOLF.makeCopy("Wolf Queen", Game.level.squares[128][12], Game.level.factions.wolves, null,
				new GameObject[] {}, new GameObject[] {}, null);
		superWolf.powers.add(new PowerSuperPeek(superWolf));

		BestiaryKnowledge bestiaryKnowledge = Level.bestiaryKnowledgeCollection.get(superWolf.templateId);
		bestiaryKnowledge.name = true;
		bestiaryKnowledge.image = true;
		bestiaryKnowledge.fireResistance = true;
		bestiaryKnowledge.waterResistance = true;
		bestiaryKnowledge.bluntResistance = true;
		bestiaryKnowledge.pierceResistance = true;
		bestiaryKnowledge.slashResistance = true;
		bestiaryKnowledge.poisonResistance = true;
		bestiaryKnowledge.electricResistance = true;
		bestiaryKnowledge.powers = true;

		Actor wolf2 = Templates.WOLF.makeCopy("Wolf", Game.level.squares[128][11], Game.level.factions.wolves, null,
				new GameObject[] {}, new GameObject[] {}, null);

		Actor wolf3 = Templates.WOLF.makeCopy("Wolf", Game.level.squares[127][11], Game.level.factions.wolves, null,
				new GameObject[] {}, new GameObject[] {}, null);

		// [207][16]

		wolfPack = new Group("Wolf pack", superWolf, wolf2, wolf3);

		this.wolfPack.quest = this;
		for (GameObject wolf : wolfPack.getMembers()) {
			wolf.quest = this;
			wolf.inventory.add(Templates.CLEAVER.makeCopy(null, null));
		}

		weaponsBehindLodge = new ArrayList<GameObject>();
		weaponsBehindLodge.add(Templates.HATCHET.makeCopy(squareBehindLodge, hunterBrent));
		weaponsBehindLodge.add(Templates.HUNTING_BOW.makeCopy(squareBehindLodge, hunterBrent));

		for (GameObject weaponBehindLodge : weaponsBehindLodge) {
			weaponBehindLodge.quest = this;
		}
		// END OF FROM EDITOR

		AreaTownForest.createForest();

		objectiveWolves = new Objective("The Wolves", superWolf, null, superWolf.imageTexture);
		allObjectives.add(objectiveWolves);
		objectiveWeaponsBehindLodge = new Objective("Hunting Weapons", weaponsBehindLodge.get(0), null,
				weaponsBehindLodge.get(0).imageTexture);
		allObjectives.add(objectiveWeaponsBehindLodge);
		objectiveHunters = new Objective("The Hunters", hunterBrent, null, hunterBrent.imageTexture);
		allObjectives.add(objectiveHunters);
		objectiveEnvironmentalist = new Objective("Environmentalist", environmentalistBill, null,
				environmentalistBill.imageTexture);
		allObjectives.add(objectiveEnvironmentalist);

		ConversationsSmallGame.quest = this;
		ConversationsSmallGame.createConversations();

		huntingPlan.setOnReadListener(new ActionListener() {
			@Override
			public void onRead() {
				if (!haveJournalLog(journalLogReadHuntPlan1) && !haveJournalLog(journalLogReadHuntPlan2)) {
					if (!started) {
						addJournalLog(journalLogReadHuntPlan1);
						addObjective(objectiveHunters);
					} else {
						addJournalLog(journalLogReadHuntPlan2);
					}
					turnUpdated = Level.turn;
					addJournalLog(new JournalLog(huntingPlan));
					addJournalLog(new JournalLog(superWolf));

					BestiaryKnowledge bestiaryKnowledge = Level.bestiaryKnowledgeCollection.get(superWolf.templateId);
					bestiaryKnowledge.name = true;
					bestiaryKnowledge.image = true;
					bestiaryKnowledge.fireResistance = true;
					bestiaryKnowledge.waterResistance = true;
					bestiaryKnowledge.bluntResistance = true;
					bestiaryKnowledge.pierceResistance = true;
					bestiaryKnowledge.slashResistance = true;
					bestiaryKnowledge.poisonResistance = true;
					bestiaryKnowledge.electricResistance = true;

				}
			}
		});

		Discoverable mound = Templates.MOUND.makeCopy(Game.level.squares[122][7], null, 1);
		Storage crate = Templates.CRATE.makeCopy("Crate", Game.level.squares[123][9], false, null);
		crate.inventory.add(Templates.HATCHET.makeCopy(null, null));
		// mound.discovered();
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
		if (resolved)
			return false;
		if (hunterPack.contains(actor)) {
			return updateHunter(actor);
		} else if (actor == environmentalistBill) {
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
							AIRoutineUtils.moveTowardsTargetToAttack(superWolf);
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
					AIRoutineUtils.moveTowardsSquareToBeAdjacent(Game.level.player.squareGameObjectIsOn);
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
			if (environmentalistBill.canSeeGameObject(superWolf)) {
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

			if (environmentalistBill.squareGameObjectIsOn != squareBehindLodge) {
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
		} else if (actor == environmentalistBill) {
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
			return ConversationsSmallGame.conversationEnviromentalistImNotSpying;
		} else if (!haveJournalLog(journalLogSaveTheWolfVariant1) && !haveJournalLog(journalLogSaveTheWolfVariant2)) {
			return ConversationsSmallGame.conversationEnviromentalistSaveTheWolf;
		}
		return environmentalistBill.createConversation("...");
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
