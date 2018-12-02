package com.marklynch.ai.routines;

import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationPart;
import com.marklynch.level.conversation.ConversationResponse;
import com.marklynch.level.quest.thesecretroom.Kidnapper;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;

public class AIRoutineForKidnapper extends AIRoutine {

	Kidnapper kidnapper;

	public AIRoutineForKidnapper(Actor mort) {

		super(mort);
		this.kidnapper = (Kidnapper) mort;
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
		if (target.squareGameObjectIsOn.structureRoomSquareIsIn != kidnapper.mortsMine
				&& target.squareGameObjectIsOn.structureRoomSquareIsIn != kidnapper.mortsRoom
				&& target.squareGameObjectIsOn.structureRoomSquareIsIn != kidnapper.mortsVault
				&& target.squareGameObjectIsOn != kidnapper.mortsRoomDoorway
				&& target.squareGameObjectIsOn != kidnapper.mortsVaultDoorway)
			return false;

		return true;

	}

	public boolean squareInTerritory(Square square) {
		if (square.structureRoomSquareIsIn != kidnapper.mortsMine
				&& square.structureRoomSquareIsIn != kidnapper.mortsRoom
				&& square.structureRoomSquareIsIn != kidnapper.mortsVault && square != kidnapper.mortsRoomDoorway
				&& square != kidnapper.mortsVaultDoorway)
			return false;

		return true;

	}

	private Conversation getConversationLastResort() {

		ConversationResponse conversationReponseDone = new ConversationResponse("Done", null);
		ConversationPart conversationPartYouWontGetOut = new ConversationPart(
				new Object[] { "You won't get out of here alive [Mort rings his bell]" },
				new ConversationResponse[] { conversationReponseDone }, kidnapper);

		return new Conversation(conversationPartYouWontGetOut, actor, true);

	}

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForKidnapper(actor);
	}
}
