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

	// Flags
	boolean questAcceptedFromHunters;
	boolean talkedToEnvironmentalist;
	boolean readyToGo;
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
			conversationEnviromentalistSaveTheWolf, conversationHuntersReadyToGo;

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
	public void update(Actor actor) {
		if (hunterPack.contains(actor)) {
			updateHunter(actor);
		} else if (actor == environmentalist) {
			updateEnvironmentalist(actor);
		}
	}

	private void updateHunter(Actor actor) {

		if (!readyToGo) {
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

		} else if (questAcceptedFromHunters && !talkedToEnvironmentalist) {
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

			if (actor.straightLineDistanceTo(Game.level.player.squareGameObjectIsOn) < 2) {
				new ActionTalk(actor, Game.level.player).perform();
			}
		}
	}

	@Override
	public Conversation getConversation(Actor actor) {
		if (hunterPack.contains(actor)) {
			// Talking to a hunter
			if (!questAcceptedFromHunters) {
				return conversationHuntersJoinTheHunt;
			} else if (!readyToGo) {
				return conversationHuntersReadyToGo;
			}

		}

		if (actor == environmentalist) {
			// Talking to environmentalist
			if (!questAcceptedFromHunters) {
				return conversationEnviromentalistImNotSpying;
			} else if (!talkedToEnvironmentalist) {
				return conversationEnviromentalistSaveTheWolf;
			}
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
		// TODO Auto-generated method stubConversationResponse
		// conversationReponseEndAfterAccepting = new
		// ConversationResponse("Leave",
		// null) {
		ConversationResponse conversationReponseEndAfterAccepting = new ConversationResponse("Leave", null);
		ConversationPart conversationPartImNotSpying = new ConversationPart("What? NO! I'm not spying! You're spying!",
				new ConversationResponse[] { conversationReponseEndAfterAccepting }, environmentalist);
		conversationEnviromentalistImNotSpying = new Conversation(conversationPartImNotSpying);
	}

	private void setUpConversationSaveTheWolf() {
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
