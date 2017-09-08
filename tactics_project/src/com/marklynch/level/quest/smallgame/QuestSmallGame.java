package com.marklynch.level.quest.smallgame;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.constructs.Group;
import com.marklynch.level.constructs.bounds.structure.Structure;
import com.marklynch.level.constructs.bounds.structure.StructurePath;
import com.marklynch.level.constructs.bounds.structure.StructureRoom;
import com.marklynch.level.constructs.bounds.structure.StructureRoom.RoomPart;
import com.marklynch.level.constructs.bounds.structure.StructureSection;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationPart;
import com.marklynch.level.conversation.ConversationResponse;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Chest;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Readable;
import com.marklynch.objects.Templates;
import com.marklynch.objects.Wall;
import com.marklynch.objects.actions.ActionGiveSpecificItem;
import com.marklynch.objects.actions.ActionTalk;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.AggressiveWildAnimal;
import com.marklynch.objects.units.Hunter;

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

	// Flags
	boolean questAcceptedFromHunters;
	boolean talkedToEnvironmentalist;
	boolean readyToGo;
	boolean playerAttackedHunters;
	boolean playerAttackedWolves;
	boolean huntersDead;
	boolean wolvesDead;

	// End
	boolean huntersReleasedFromQuest;

	boolean huntersAndWolvesFought = false;

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

	// Conversations
	public static Conversation conversationHuntersJoinTheHunt;
	public static Conversation conversationEnviromentalistImNotSpying;
	public static Conversation conversationEnviromentalistSaveTheWolf;
	public static Conversation conversationHuntersReadyToGo;
	public static Conversation conversationHuntersOnlyHuntersGetLoot;

	public QuestSmallGame() {
		super();

		squareBehindLodge = Game.level.squares[12][9];
		huntPlanningArea = Game.level.squares[5][8];

		// BRENT

		// Add lead hunter
		GameObject brentsBed = Templates.BED.makeCopy(Game.level.squares[10][10]);
		Actor hunterBrent = Templates.HUNTER.makeCopy(Game.level.squares[5][8],
				Game.level.factions.get(1), brentsBed, 203, new GameObject[] {
						Templates.HUNTING_BOW.makeCopy(null, null), Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {});

		// Hunting lodge
		ArrayList<GameObject> lodgeFeatures = new ArrayList<GameObject>();
		lodgeFeatures.add(Templates.DOOR.makeCopy("Door", Game.level.squares[5][12], false, true, false, hunterBrent));
		ArrayList<StructureRoom> lodgeRooms = new ArrayList<StructureRoom>();
		lodgeRooms.add(
				new StructureRoom("Hunting Lodge", 6, 8, false, new ArrayList<Actor>(), new RoomPart(6, 10, 10, 14)));
		ArrayList<StructureSection> lodgeSections = new ArrayList<StructureSection>();
		lodgeSections.add(new StructureSection("Hunting Lodge", 5, 9, 11, 15, false));
		Structure lodge = new Structure("Hunting Lodge", lodgeSections, lodgeRooms, new ArrayList<StructurePath>(),
				lodgeFeatures, new ArrayList<Square>(), "building.png", 896, 896 + 640, 896, 896 + 640, true,
				hunterBrent, new ArrayList<Square>(), new ArrayList<Wall>(), Templates.WALL, Square.STONE_TEXTURE);
		Game.level.structures.add(lodge);

		// Add hunters
		GameObject brontsBed = Templates.BED.makeCopy(Game.level.squares[8][10]);
		Actor hunterBront1 = Templates.HUNTER.makeCopy(Game.level.squares[3][7],
				Game.level.factions.get(1), brontsBed, 124, new GameObject[] {
						Templates.HUNTING_BOW.makeCopy(null, null), Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {});
		GameObject bront2sBed = Templates.BED.makeCopy(Game.level.squares[6][10]);
		Actor hunterBront2 = Templates.HUNTER.makeCopy(
				Game.level.squares[3][8], Game.level.factions.get(1), bront2sBed, 73, new GameObject[] {
						Templates.HATCHET.makeCopy(null, null), Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {});
		GameObject bront3sBed = Templates.BED.makeCopy(Game.level.squares[10][12]);
		Actor hunterBront3 = hunterBront2.makeCopy(
				Game.level.squares[3][9], Game.level.factions.get(1), bront3sBed, 30, new GameObject[] {
						Templates.HATCHET.makeCopy(null, null), Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {});
		GameObject bront4sBed = Templates.BED.makeCopy(Game.level.squares[10][14]);
		Actor hunterBront4 = hunterBront2.makeCopy(
				Game.level.squares[2][7], Game.level.factions.get(1), bront4sBed, 83, new GameObject[] {
						Templates.HATCHET.makeCopy(null, null), Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {});
		GameObject bront5sBed = Templates.BED.makeCopy(Game.level.squares[8][14]);
		Actor hunterBront5 = hunterBront2.makeCopy(
				Game.level.squares[2][8], Game.level.factions.get(1), bront5sBed, 23, new GameObject[] {
						Templates.HATCHET.makeCopy(null, null), Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {});
		GameObject bront6sBed = Templates.BED.makeCopy(Game.level.squares[6][14]);
		Actor hunterBront6 = hunterBront2.makeCopy(
				Game.level.squares[2][9], Game.level.factions.get(1), bront6sBed, 43, new GameObject[] {
						Templates.HATCHET.makeCopy(null, null), Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {});

		Actor thief = Templates.THIEF.makeCopy(
				Game.level.squares[2][13], Game.level.factions.get(2), null, 64, new GameObject[] {
						Templates.HATCHET.makeCopy(null, null), Templates.HUNTING_KNIFE.makeCopy(null, null) },
				new GameObject[] {});

		// Some ground hatchets
		Templates.HATCHET.makeCopy(Game.level.squares[3][6], Game.level.player);
		Templates.HATCHET.makeCopy(Game.level.squares[5][6], Game.level.player);
		Templates.BLOOD.makeCopy(Game.level.squares[5][6], Game.level.player);
		Templates.HATCHET.makeCopy(Game.level.squares[1][6], Game.level.player);

		hunterPack = new Group("Hunting Party", hunterBrent, hunterBront1, hunterBront2, hunterBront3, hunterBront4,
				hunterBront5, hunterBront6);

		// this.hunterPack.quest = this;
		// for (GameObject hunter : hunterPack.getMembers()) {
		// hunter.quest = this;
		// }

		Readable huntingPlan = Templates.SIGN.makeCopy(Game.level.squares[6][8], "Hunt Action Plan",
				new Object[] { "Super Wolf - Weaknesses: Water Strengths: Fire will heal the beast" }, hunterBrent);

		Chest chest = Templates.CHEST.makeCopy("Chest", Game.level.squares[3][1], false, null);
		chest.inventory.add(Templates.CLEAVER.makeCopy(null, null));

		environmentalistBill = new Hunter("Environmentalist Bill", "Environmentalist", 1, 10, 0, 0, 0, 0,
				"environmentalist.png", Game.level.squares[5][16], 1, 10, null, new Inventory(), 1, 1, 0f, 0f, 1f, 1f,
				1f, null, 0.5f, 0.5f, false, 0f, 0f, 0f, 0f, 0f, 110f, null, Game.level.factions.get(1), 0, 0, 0, 0, 0,
				0, 0, 0, 10, new GameObject[] {}, new GameObject[] {});
		environmentalistBill.inventory.add(Templates.HATCHET.makeCopy(null, environmentalistBill));

		environmentalistBill.quest = this;

		superWolf = new AggressiveWildAnimal("Wolf Queen", "Wild animal", 1, 10, 0, 0, 0, 0, "fire_wolf.png",
				Game.level.squares[207][16], 1, 10, null, new Inventory(), 1, 1, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f,
				false, 0f, 0f, 0f, 0f, 0f, 150f, null, Game.level.factions.get(2), 0, 0, 0, 0, 0, 0, 0, 0,
				new GameObject[] {}, new GameObject[] {});

		Actor wolf2 = new AggressiveWildAnimal("Wolf", "Wild animal", 1, 10, 0, 0, 0, 0, "wolf_green.png",
				Game.level.squares[208][15], 1, 10, null, new Inventory(), 1, 1, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f,
				false, 0f, 0f, 0f, 0f, 0f, 60f, null, Game.level.factions.get(2), 0, 0, 0, 0, 0, 0, 0, 0,
				new GameObject[] {}, new GameObject[] {});

		Actor wolf3 = new AggressiveWildAnimal("Wolf", "Wild animal", 1, 10, 0, 0, 0, 0, "wolf_pink.png",
				Game.level.squares[208][17], 1, 10, null, new Inventory(), 1, 1, 0f, 0f, 1f, 1f, 1f, null, 0.5f, 0.5f,
				false, 0f, 0f, 0f, 0f, 0f, 60f, null, Game.level.factions.get(2), 0, 0, 0, 0, 0, 0, 0, 0,
				new GameObject[] {}, new GameObject[] {});

		wolfPack = new Group("Wolf pack", superWolf, wolf2, wolf3);

		this.wolfPack.quest = this;
		for (GameObject wolf : wolfPack.getMembers()) {
			wolf.quest = this;
			wolf.inventory.add(Templates.CLEAVER.makeCopy(null, null));
		}

		weaponsBehindLodge = new ArrayList<GameObject>();
		weaponsBehindLodge.add(Templates.HATCHET.makeCopy(Game.level.squares[12][9], hunterBrent));
		weaponsBehindLodge.add(Templates.HUNTING_BOW.makeCopy(Game.level.squares[12][9], hunterBrent));

		for (GameObject weaponBehindLodge : weaponsBehindLodge) {
			weaponBehindLodge.quest = this;
		}
		// END OF FROM EDITOR

		AreaTownForest.createForest();

		setUpConversationJoinTheHunt();
		setUpConversationImNotSpying();
		setUpConversationSaveTheWolf();
		setUpConversationReadyToGo();
		setUpConversationYouDidntHelp();

	}

	@Override
	public void update() {
		// Set flags

		// The wolves are dead
		if (wolvesDead == false) {
			wolvesDead = true;
			for (int i = 0; i < wolfPack.size(); i++) {
				if (wolfPack.getMember(i).remainingHealth > 0) {
					wolvesDead = false;
					break;
				}
			}
		}

		// Player has attacked the wolves
		if (playerAttackedWolves == false && wolfPack.hasAttackers()) {
			if (wolfPack.getAttackers().contains(Game.level.player)) {
				playerAttackedWolves = true;
			}
		}

		// The hunters are dead
		if (huntersDead == false) {
			huntersDead = true;
			for (int i = 0; i < hunterPack.size(); i++) {
				if (hunterPack.getMember(i).remainingHealth > 0) {
					huntersDead = false;
					break;
				}
			}
		}

		// Player has attacked the hunters
		if (playerAttackedHunters == false && hunterPack.hasAttackers()) {
			if (hunterPack.getAttackers().contains(Game.level.player)) {
				playerAttackedHunters = true;
			}
		}

		// Hunters and wolves have fought
		if (huntersAndWolvesFought == false && hunterPack.hasAttackers()) {
			for (int j = 0; j < wolfPack.size(); j++) {
				if (hunterPack.getAttackers().contains(wolfPack.getMember(j))) {
					huntersAndWolvesFought = true;
					break;
				}
			}
		}

	}

	@Override
	public boolean update(Actor actor) {
		update();
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

		if (!readyToGo) {
			actor.activityDescription = ACTIVITY_PLANNING_A_HUNT;
			if (actor == hunterPack.getLeader()) {
				goToHuntPlanningArea(actor);
			}
		} else if (readyToGo && !this.wolvesDead) {

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
		} else if (this.wolvesDead && this.playerAttackedWolves && !questAcceptedFromHunters) {
			// Wolves were killed by player before accepting the mission
			goToHuntPlanningArea(actor);
		} else if (this.wolvesDead && this.playerAttackedWolves && !readyToGo) {
			// Wolves were killed by player after accepting the mission, but
			// before he told the hunters he's ready to go
			goToHuntPlanningArea(actor);
		} else if (this.wolvesDead && this.playerAttackedWolves) {
			// Wolves were killed after departing for the hunt, and the player
			// helped kill them
			// Talk to them... for some reason
			if (actor == hunterPack.getLeader()) {
				if (actor.straightLineDistanceTo(Game.level.player.squareGameObjectIsOn) < 2) {
					new ActionTalk(actor, Game.level.player).perform();
				} else {
					AIRoutineUtils.moveTowardsTargetToBeAdjacent(Game.level.player);
				}
			}
		} else if (this.wolvesDead && !this.playerAttackedWolves) {
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
		if (!questAcceptedFromHunters) {
			actor.activityDescription = ACTIVITY_SPYING;

		} else if (questAcceptedFromHunters && !talkedToEnvironmentalist) {
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
		if (hunterPack.contains(actor)) {
			return getConversationForHunter(actor);
		} else if (actor == environmentalistBill) {
			return getConversationForEnvironmentalist(actor);
		}
		return null;
	}

	public Conversation getConversationForHunter(Actor actor) {
		// Talking to a hunter
		if (!questAcceptedFromHunters) {
			return conversationHuntersJoinTheHunt;
		} else if (!readyToGo) {
			return conversationHuntersReadyToGo;
		} else if (this.wolvesDead && !this.playerAttackedWolves) {
			return conversationHuntersOnlyHuntersGetLoot;
		}
		return null;
	}

	public Conversation getConversationForEnvironmentalist(Actor actor) {
		// Talking to environmentalist
		if (!questAcceptedFromHunters) {
			return conversationEnviromentalistImNotSpying;
		} else if (!talkedToEnvironmentalist) {
			return conversationEnviromentalistSaveTheWolf;
		}
		return null;
	}

	public void setUpConversationReadyToGo() {
		ConversationResponse conversationReponseEnd = new ConversationResponse("Leave", null);

		ConversationPart conversationPartTheresEquipment = new ConversationPart(
				new Object[] {
						"There's spare equipment 'round back, help yourself! Joe runs a shop to the North if you think you need anything else. Let us know when you're ready." },
				new ConversationResponse[] { conversationReponseEnd }, hunterPack.getLeader());

		ConversationPart conversationPartSuitYourself = new ConversationPart(new Object[] { "Suit yourself." },
				new ConversationResponse[] { conversationReponseEnd }, hunterPack.getLeader());

		ConversationResponse conversationResponseNoThanks = new ConversationResponse("No thanks",
				conversationPartSuitYourself);
		ConversationResponse conversationResponseYesPlease = new ConversationResponse("Yes please",
				conversationPartTheresEquipment) {
			@Override
			public void select() {
				super.select();
				// ADD QUEST TO QUEST LOG IF NO IN HARDCORE MODE
				// THIS ALSO COMES WITH A TOAST / POPUP SAYING "QUEST STARTED -
				// PACK HUNTERS"
				questAcceptedFromHunters = true;
			}
		};

		ConversationPart conversationPartWantToComeHunting = new ConversationPart(
				new Object[] { "We're just about to head out on the hunt, an extra man wouldn't go amiss." },
				new ConversationResponse[] { conversationResponseYesPlease, conversationResponseNoThanks },
				hunterPack.getLeader());

		conversationHuntersJoinTheHunt = new Conversation(conversationPartWantToComeHunting);

	}

	private void setUpConversationImNotSpying() {

		// Environmentalist could have emoticon over his head showing his
		// feelings
		// Anime style
		// try it out
		ConversationResponse conversationReponseEndAfterAccepting = new ConversationResponse("Leave", null);
		ConversationPart conversationPartImNotSpying = new ConversationPart(
				new Object[] { "What? NO! I'm not spying! You're spying!" },
				new ConversationResponse[] { conversationReponseEndAfterAccepting }, environmentalistBill);
		conversationEnviromentalistImNotSpying = new Conversation(conversationPartImNotSpying);
	}

	private void setUpConversationSaveTheWolf() {

		// Should Be
		// 1. Plead
		// 2. Here's your hunting equipment
		// 3. And here, this should help if you choose to do the right thing
		// (give you remove imbument or imbue with fire.

		ConversationResponse conversationReponseEndAfterAccepting = new ConversationResponse("Leave", null) {
			@Override
			public void select() {
				super.select();
				for (GameObject gameObject : weaponsBehindLodge) {
					if (environmentalistBill.inventory.contains(gameObject)) {
						new ActionGiveSpecificItem(environmentalistBill, Game.level.player, gameObject, false)
								.perform();
						talkedToEnvironmentalist = true;
					}
				}
			}

		};
		ConversationPart conversationPartSaveTheWolf = new ConversationPart(new Object[] { "Save the wolf!" },
				new ConversationResponse[] { conversationReponseEndAfterAccepting }, environmentalistBill);
		conversationEnviromentalistSaveTheWolf = new Conversation(conversationPartSaveTheWolf);

	}

	public void setUpConversationJoinTheHunt() {
		ConversationResponse conversationReponseEnd = new ConversationResponse("Leave", null);

		ConversationPart conversationAlrightLetsGo = new ConversationPart(
				new Object[] { "Alright! Let's go bag us a some pelts!" },
				new ConversationResponse[] { conversationReponseEnd }, hunterPack.getLeader());

		ConversationPart conversationPartWellHurryOn = new ConversationPart(new Object[] { "Well hurry on!" },
				new ConversationResponse[] { conversationReponseEnd }, hunterPack.getLeader());

		ConversationResponse conversationResponseNotYet = new ConversationResponse("Not yet",
				conversationPartWellHurryOn);
		ConversationResponse conversationResponseReady = new ConversationResponse("Ready!", conversationAlrightLetsGo) {
			@Override
			public void select() {
				super.select();
				// Update quest log
				// Set enviromentalist to come watch
				// Hunters on the way
				readyToGo = true;
			}
		};

		ConversationPart conversationPartReadyToGo = new ConversationPart(new Object[] { "Ready to go, pal?" },
				new ConversationResponse[] { conversationResponseReady, conversationResponseNotYet },
				hunterPack.getLeader());

		conversationHuntersReadyToGo = new Conversation(conversationPartReadyToGo);

	}

	private void setUpConversationYouDidntHelp() {
		// Really like the "Now fuck off!" bit.
		ConversationResponse conversationReponseEnd = new ConversationResponse("Leave", null);
		ConversationPart conversationPartOnlyHuntersGetLoot = new ConversationPart(
				new Object[] { "Only hunters get loot. Now fuck off!" },
				new ConversationResponse[] { conversationReponseEnd }, hunterPack.getLeader());
		conversationHuntersOnlyHuntersGetLoot = new Conversation(conversationPartOnlyHuntersGetLoot);
	}

}
