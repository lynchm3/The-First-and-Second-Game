package com.marklynch.level.constructs;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIRoutineUtils;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationPart;
import com.marklynch.level.conversation.ConversationResponse;
import com.marklynch.objects.units.Actor;

public class QuestSmallGame extends Quest {

	// Quest text
	final String OBJECTIVE_FOLLOW_THE_HUNTERS_TO_SUPERWOLF = "Follow the hunters to Superwolf";

	// Activity Strings
	final String ACTIVITY_PLANNING_A_HUNT = "Planning a hunt";
	final String ACTIVITY_DESCRIPTION_HUNTING = "Goin' hunting";

	// Flags
	boolean questAcceptedFromHunters;
	boolean playerAttackedHunters;
	boolean playerAttackedWolves;
	boolean huntersDead;
	boolean wolvesDead;

	// Actors
	Pack hunterPack;
	Actor enviromenmentalist;
	Pack wolfPack;
	Actor superWolf;
	Actor cub;

	public static Conversation conversationHuntersJoinTheHunt, conversationEnviromentalistSaveTheWolf,
			conversationHuntersAreYouReady;

	// Items

	public QuestSmallGame(Pack hunterPack, Actor enviromenmentalist, Actor superWolf, Pack wolfPack, Actor cub) {
		super();
		this.hunterPack = hunterPack;
		this.hunterPack.quest = this;

		this.enviromenmentalist = enviromenmentalist;
		enviromenmentalist.quest = this;

		this.wolfPack = wolfPack;
		this.wolfPack.quest = this;

		this.superWolf = superWolf;
		this.superWolf.quest = this;

		if (cub != null) {
			this.cub = cub;
			cub.quest = this;
		}

		setUpConversationJoinTheHunt();

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
		} else if (actor == enviromenmentalist) {
			updateEnvironmentalist(actor);
		}
	}

	private void updateHunter(Actor actor) {

		if (!questAcceptedFromHunters) {
			Game.level.activeActor.activityDescription = ACTIVITY_PLANNING_A_HUNT;
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

		} else if (questAcceptedFromHunters) {
			AIRoutineUtils.moveTowardsTargetSquare(Game.level.squares[12][9]);
			// HE COULD MAYBE STAND ON THE WEAPONS? OR PICK UP THE WEAPONS!!!!
			// YUS!!!
		}

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
				"There's spare equipment 'round back, help yourself! Let us know when you're ready.",
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

	@Override
	public Conversation getConversation(Actor actor) {
		System.out.println("quest.getConversation()");
		if (hunterPack.contains(actor)) {
			System.out.println("quest.getConversation() a");
			// Talking to a hunter
			if (!questAcceptedFromHunters) {
				System.out.println("quest.getConversation() b");
				return conversationHuntersJoinTheHunt;
			}

		}
		return null;
	}

}
