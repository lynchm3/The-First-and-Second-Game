package com.marklynch.level.quest.thesecretroom;

import com.marklynch.ai.routines.AIRoutine;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationPart;
import com.marklynch.level.conversation.ConversationResponse;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;

public class AIRoutineForKidnapper extends AIRoutine {

	Kidnapper mort;

	enum FEEDING_DEMO_STATE {
		WALK_TO_TROUGH, PLACE_MEAT, RING_BELL, WALK_AWAY, WAIT_FOR_BLIND_TO_ENTER, WAIT_FOR_BLIND_TO_LEAVE
	};

	public FEEDING_DEMO_STATE feedingDemoState = FEEDING_DEMO_STATE.WALK_TO_TROUGH;

	final String ACTIVITY_DESCRIPTION_LOOTING = "Looting!";
	final String ACTIVITY_DESCRIPTION_SKINNING = "Skinning";
	final String ACTIVITY_DESCRIPTION_HUNTING = "Goin' hunting";
	final String ACTIVITY_DESCRIPTION_SELLING_LOOT = "Selling spoils";
	final String ACTIVITY_DESCRIPTION_GOING_TO_BED = "Bed time";
	final String ACTIVITY_DESCRIPTION_SLEEPING = "Zzzzzz";
	final String ACTIVITY_DESCRIPTION_FIGHTING = "Fighting";
	final String ACTIVITY_DESCRIPTION_SEARCHING = "Searching";
	final String ACTIVITY_DESCRIPTION_FOLLOWING = "Following";
	final String ACTIVITY_DESCRIPTION_RETREATING = "Retreating";
	final String ACTIVITY_DESCRIPTION_HIDING = "Hiding";
	final String ACTIVITY_DESCRIPTION_RINGING_DINNER_BELL = "Ringing Dinner Bell";

	int sleepCounter = 0;
	final int SLEEP_TIME = 1000;

	public AIRoutineForKidnapper(Actor mort) {

		super(mort);
		this.mort = (Kidnapper) mort;
		aiType = AI_TYPE.FIGHTER;

		keepInBounds = true;
	}

	@Override
	public void update() {

		aiRoutineStart();

		// Fight
		if (runFightRoutine(true))
			return;

		// Crime reaction
		if (runCrimeReactionRoutine())
			return;

		// Search
		if (runSearchRoutine())
			return;

		// Search cooldown
		if (runSearchCooldown())
			return;

		// Door maintenance routine
		if (runDoorRoutine())
			return;
	}

	public boolean targetInTerritory(Actor target) {
		if (target.squareGameObjectIsOn.structureRoomSquareIsIn != mort.mortsMine
				&& target.squareGameObjectIsOn.structureRoomSquareIsIn != mort.mortsRoom
				&& target.squareGameObjectIsOn.structureRoomSquareIsIn != mort.mortsVault
				&& target.squareGameObjectIsOn != mort.mortsRoomDoorway
				&& target.squareGameObjectIsOn != mort.mortsVaultDoorway)
			return false;

		return true;

	}

	public boolean squareInTerritory(Square square) {
		if (square.structureRoomSquareIsIn != mort.mortsMine && square.structureRoomSquareIsIn != mort.mortsRoom
				&& square.structureRoomSquareIsIn != mort.mortsVault && square != mort.mortsRoomDoorway
				&& square != mort.mortsVaultDoorway)
			return false;

		return true;

	}

	private Conversation getConversationLastResort() {

		ConversationResponse conversationReponseDone = new ConversationResponse("Done", null);
		ConversationPart conversationPartYouWontGetOut = new ConversationPart(
				new Object[] { "You won't get out of here alive [Mort rings his bell]" },
				new ConversationResponse[] { conversationReponseDone }, mort);

		return new Conversation(conversationPartYouWontGetOut, actor, true);

	}

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForKidnapper(actor);
	}
}
