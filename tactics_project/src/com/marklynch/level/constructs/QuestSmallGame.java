package com.marklynch.level.constructs;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.Square;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationPart;
import com.marklynch.level.conversation.ConversationResponse;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class QuestSmallGame extends Quest {

	// Quest text
	final String OBJECTIVE_FOLLOW_THE_HUNTERS_TO_SUPERWOLF = "Follow the hunters to Superwolf";

	// Activity Strings
	final String ACTIVITY_PLANNING_A_HUNT = "Planning a hunt";
	final String ACTIVITY_DESCRIPTION_HUNTING = "Goin' hunting";
	final String ACTIVITY_SPYING = "Spying";
	final String ACTIVITY_SAVING_THE_WORLD = "Saving the world";

	// Flags
	boolean questAcceptedFromHunters;
	boolean playerAttackedHunters;
	boolean playerAttackedWolves;
	boolean huntersDead;
	boolean wolvesDead;

	// Actors
	Pack hunterPack;
	Actor environmentalist;
	Pack wolfPack;
	Actor superWolf;
	Actor cub;

	// GameObjects
	ArrayList<GameObject> weaponsBehindLodge;

	// Squares
	Square squareBehindLodge;

	// Conversations
	public static Conversation conversationHuntersJoinTheHunt, conversationEnviromentalistImNotSpying,
			conversationEnviromentalistSaveTheWolf, conversationHuntersAreYouReady;

	public QuestSmallGame(Pack hunterPack, Actor enviromenmentalist, Actor superWolf, Pack wolfPack, Actor cub,
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
				if (wolfPack.hasAttackers()
						&& wolfPack.getAttackers().contains(Game.level.factions.get(0).actors.get(0))) {
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
				if (hunterPack.hasAttackers()
						&& hunterPack.getAttackers().contains(Game.level.factions.get(0).actors.get(0))) {
					playerAttackedHunters = true;
				}
			}
		}

	}

	@Override
	public void update(Actor actor) {
		if (hunterPack.contains(actor)) {
			updateHunter(actor);
		} else if (actor == environmentalist) {
			updateEnvironmentalist(actor);
		}
	}

	private void updateHunter(Actor actor) {

		if (!questAcceptedFromHunters) {
			actor.activityDescription = ACTIVITY_PLANNING_A_HUNT;
			if (actor == hunterPack.getLeader()) {
				AIRoutineUtils.moveTowardsTargetSquare(Game.level.squares[5][8]);
			} else {
				AIRoutineUtils.moveTowardsTargetToBeAdjacent(hunterPack.getLeader());

			}

		} else if (questAcceptedFromHunters && !wolvesDead) {

			// this.questCurrentObjective =
			// OBJECTIVE_FOLLOW_THE_HUNTERS_TO_SUPERWOLF;
			//
			// Game.level.activeActor.activityDescription =
			// ACTIVITY_DESCRIPTION_HUNTING;
			//
			// if (actor == hunterPack.getLeader()) {
			// boolean attackedAnimal =
			// AIRoutineUtils.attackTarget(this.superWolf);
			// if (!attackedAnimal)
			// AIRoutineUtils.moveTowardsTargetToAttack(this.superWolf);
			// } else {
			// AIRoutineUtils.moveTowardsTargetToBeAdjacent(hunterPack.getLeader());
			//
			// }
		}

	}

	private void updateEnvironmentalist(Actor actor) {
		if (!questAcceptedFromHunters) {
			actor.activityDescription = ACTIVITY_SPYING;

		} else if (questAcceptedFromHunters) {
			actor.activityDescription = ACTIVITY_SAVING_THE_WORLD;

			if (environmentalist.squareGameObjectIsOn != squareBehindLodge) {
				AIRoutineUtils.moveTowardsTargetSquare(squareBehindLodge);
				return;
			}

			// GameObject weaponToPickUp = null;
			for (GameObject weaponBehindLodge : weaponsBehindLodge) {
				if (weaponBehindLodge.squareGameObjectIsOn == squareBehindLodge) {
					AIRoutineUtils.pickupTarget(weaponBehindLodge);
				}
			}
		}
	}

	@Override
	public Conversation getConversation(Actor actor) {
		if (hunterPack.contains(actor)) {
			// Talking to a hunter
			if (!questAcceptedFromHunters) {
				return conversationHuntersJoinTheHunt;
			}

		}

		if (actor == environmentalist) {
			// Talking to environmentalist
			if (!questAcceptedFromHunters) {
				return conversationEnviromentalistImNotSpying;
			}
		}
		return null;
	}

	public void setUpConversationJoinTheHunt() {
		ConversationResponse conversationReponseEndAfterAccepting = new ConversationResponse("Leave", null) {
			@Override
			public void select() {
				Game.level.conversation = null;
				// MAKE THE WEAPONS 'ROUND BACK STEALABLE
				// TURN ON THE ENVIRONMENTALIST
				// CHANGE THE HUNTERS CONVERSATION TO "READY TO GO?"

			}
		};

		ConversationPart conversationPartTheresEquipment = new ConversationPart(
				"There's spare equipment 'round back, help yourself! Joe runs a shop to the North if you think you need anything else. Let us know when you're ready.",
				new ConversationResponse[] { conversationReponseEndAfterAccepting }, hunterPack.getLeader());

		ConversationResponse conversationReponseEndAfterRefusing = new ConversationResponse("Leave", null);
		ConversationPart conversationPartSuitYourself = new ConversationPart("Suit yourself.",
				new ConversationResponse[] { conversationReponseEndAfterRefusing }, hunterPack.getLeader());

		ConversationResponse conversationResponseNoThanks = new ConversationResponse("No thanks",
				conversationPartSuitYourself);
		ConversationResponse conversationResponseYesPlease = new ConversationResponse("Yes please",
				conversationPartTheresEquipment) {
			@Override
			public void select() {
				Game.level.conversation.currentConversationPart = nextConversationPart;
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
		// TODO Auto-generated method stubConversationResponse
		// conversationReponseEndAfterAccepting = new
		// ConversationResponse("Leave",
		// null) {
		ConversationResponse conversationReponseEndAfterAccepting = new ConversationResponse("Leave", null);
		ConversationPart conversationPartImNotSpying = new ConversationPart(
				"What? NO! I'm not spying! You're spying!!!",
				new ConversationResponse[] { conversationReponseEndAfterAccepting }, environmentalist);
		conversationEnviromentalistImNotSpying = new Conversation(conversationPartImNotSpying);
	}

}
