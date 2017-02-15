package com.marklynch.level.constructs;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Square;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationPart;
import com.marklynch.level.conversation.ConversationResponse;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.ActionGive;
import com.marklynch.objects.actions.ActionTalk;
import com.marklynch.objects.units.Actor;

public class QuestSmallGame extends Quest {

	// Quest text
	final String OBJECTIVE_FOLLOW_THE_HUNTERS_TO_SUPERWOLF = "Follow the hunters to Superwolf";

	// Activity Strings
	final String ACTIVITY_PLANNING_A_HUNT = "Planning a hunt";
	final String ACTIVITY_DESCRIPTION_HUNTING = "Goin' hunting";
	final String ACTIVITY_SPYING = "Spying";
	final String ACTIVITY_SAVING_THE_WORLD = "Saving the world";
	final String ACTIVITY_WAITING_FOR_YOU = "Waiting for you";

	// Flags
	boolean questAcceptedFromHunters;
	boolean talkedToEnvironmentalist;
	boolean readyToGo;
	boolean playerAttackedHunters;
	boolean playerAttackedWolves;
	boolean huntersDead;
	boolean wolvesDead;

	// Actors
	Group hunterPack;
	Actor environmentalist;
	Group wolfPack;
	Actor superWolf;
	Actor cub;

	// GameObjects
	ArrayList<GameObject> weaponsBehindLodge;

	// Squares
	Square squareBehindLodge;

	// Conversations
	public static Conversation conversationHuntersJoinTheHunt, conversationEnviromentalistImNotSpying,
			conversationEnviromentalistSaveTheWolf, conversationHuntersReadyToGo;

	public QuestSmallGame(Group hunterPack, Actor enviromenmentalist, Actor superWolf, Group wolfPack, Actor cub,
			ArrayList<GameObject> weaponsBehindLodge) {
		super();
		this.hunterPack = hunterPack;
		this.hunterPack.quest = this;
		for (GameObject hunter : hunterPack.getMembers()) {
			hunter.quest = this;
		}

		this.environmentalist = enviromenmentalist;
		enviromenmentalist.quest = this;

		this.wolfPack = wolfPack;
		this.wolfPack.quest = this;
		for (GameObject wolf : wolfPack.getMembers()) {
			wolf.quest = this;
		}

		this.superWolf = superWolf;
		this.superWolf.quest = this;

		if (cub != null) {
			this.cub = cub;
			cub.quest = this;
		}

		this.weaponsBehindLodge = weaponsBehindLodge;
		for (GameObject weaponBehindLodge : weaponsBehindLodge) {
			weaponBehindLodge.quest = this;
		}

		squareBehindLodge = Game.level.squares[12][9];

		setUpConversationJoinTheHunt();
		setUpConversationImNotSpying();
		setUpConversationSaveTheWolf();
		setUpConversationReadyToGo();

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
		if (playerAttackedWolves == false) {
			for (int i = 0; i < wolfPack.size(); i++) {
				if (wolfPack.hasAttackers() && wolfPack.getAttackers().contains(Game.level.player)) {
					playerAttackedWolves = true;
				}
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
		if (playerAttackedHunters == false) {
			for (int i = 0; i < hunterPack.size(); i++) {
				if (hunterPack.hasAttackers() && hunterPack.getAttackers().contains(Game.level.player)) {
					playerAttackedHunters = true;
				}
			}
		}

	}

	@Override
	public boolean update(Actor actor) {
		if (hunterPack.contains(actor)) {
			return updateHunter(actor);
		} else if (actor == environmentalist) {
			return updateEnvironmentalist(actor);
		} else if (wolfPack.contains(actor)) {
			return true;
		}
		return false;
	}

	private boolean updateHunter(Actor actor) {

		if (!readyToGo) {
			actor.activityDescription = ACTIVITY_PLANNING_A_HUNT;
			if (actor == hunterPack.getLeader()) {
				AIRoutineUtils.moveTowardsTargetSquare(Game.level.squares[5][8]);
			} else {
				AIRoutineUtils.moveTowardsTargetToBeAdjacent(hunterPack.getLeader());
			}
		} else if (readyToGo) {

			if (actor == hunterPack.getLeader()) {

				if (actor.straightLineDistanceTo(Game.level.player.squareGameObjectIsOn) > 5) {
					// activity is waiting
					actor.activityDescription = ACTIVITY_WAITING_FOR_YOU;
				} else {
					actor.activityDescription = ACTIVITY_DESCRIPTION_HUNTING;
					if (!AIRoutineUtils.attackTarget(superWolf)) {
						AIRoutineUtils.moveTowardsTargetToAttack(superWolf);
					}
				}
			} else {
				actor.activityDescription = hunterPack.getLeader().activityDescription;
				AIRoutineUtils.moveTowardsTargetToBeAdjacent(hunterPack.getLeader());
			}

			// this.questCurrentObjective =
			// OBJECTIVE_FOLLOW_THE_HUNTERS_TO_SUPERWOLF;
		}
		return true;

	}

	private boolean updateEnvironmentalist(Actor actor) {
		if (!questAcceptedFromHunters) {
			actor.activityDescription = ACTIVITY_SPYING;

		} else if (questAcceptedFromHunters && !talkedToEnvironmentalist) {
			actor.activityDescription = ACTIVITY_SAVING_THE_WORLD;

			if (environmentalist.squareGameObjectIsOn != squareBehindLodge) {
				// Move to weapons behind the lodge
				AIRoutineUtils.moveTowardsTargetSquare(squareBehindLodge);
			} else {
				// Pick up weapons behind the lodge
				for (GameObject weaponBehindLodge : weaponsBehindLodge) {
					if (weaponBehindLodge.squareGameObjectIsOn == squareBehindLodge) {
						AIRoutineUtils.pickupTarget(weaponBehindLodge);
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
		} else if (actor == environmentalist) {
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
				"There's spare equipment 'round back, help yourself! Joe runs a shop to the North if you think you need anything else. Let us know when you're ready.",
				new ConversationResponse[] { conversationReponseEnd }, hunterPack.getLeader());

		ConversationPart conversationPartSuitYourself = new ConversationPart("Suit yourself.",
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
				"We're just about to head out on the hunt, an extra man wouldn't go amiss.",
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
		ConversationPart conversationPartImNotSpying = new ConversationPart("What? NO! I'm not spying! You're spying!",
				new ConversationResponse[] { conversationReponseEndAfterAccepting }, environmentalist);
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
					if (environmentalist.inventory.contains(gameObject)) {
						new ActionGive(environmentalist, Game.level.player, gameObject).perform();
						talkedToEnvironmentalist = true;
					}
				}
			}

		};
		ConversationPart conversationPartSaveTheWolf = new ConversationPart("Save the wolf!",
				new ConversationResponse[] { conversationReponseEndAfterAccepting }, environmentalist);
		conversationEnviromentalistSaveTheWolf = new Conversation(conversationPartSaveTheWolf);

	}

	public void setUpConversationJoinTheHunt() {
		ConversationResponse conversationReponseEnd = new ConversationResponse("Leave", null);

		ConversationPart conversationAlrightLetsGo = new ConversationPart("Alright! Let's go bag us a some pelts!",
				new ConversationResponse[] { conversationReponseEnd }, hunterPack.getLeader());

		ConversationPart conversationPartWellHurryOn = new ConversationPart("Well hurry on!",
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

		ConversationPart conversationPartReadyToGo = new ConversationPart("Ready to go, pal?",
				new ConversationResponse[] { conversationResponseReady, conversationResponseNotYet },
				hunterPack.getLeader());

		conversationHuntersReadyToGo = new Conversation(conversationPartReadyToGo);

	}

}
