package com.marklynch.ai.routines;

import com.marklynch.level.constructs.conversation.Conversation;
import com.marklynch.level.constructs.conversation.ConversationPart;
import com.marklynch.level.constructs.conversation.ConversationResponse;
import com.marklynch.objects.actors.Actor;

public class AIRoutineForKidnapper extends AIRoutine {

	public AIRoutineForKidnapper() {

		super();
		aiType = AI_TYPE.FIGHTER;

		keepInBounds = true;
	}

	public AIRoutineForKidnapper(Actor mort) {

		super(mort);
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

	private Conversation getConversationLastResort() {

		ConversationResponse conversationReponseDone = new ConversationResponse("Done", null);
		ConversationPart conversationPartYouWontGetOut = new ConversationPart(
				new Object[] { "You won't get out of here alive [Mort rings his bell]" },
				new ConversationResponse[] { conversationReponseDone }, actor);

		return new Conversation(conversationPartYouWontGetOut, actor, true);

	}

	@Override
	public AIRoutine getInstance(Actor actor) {
		return new AIRoutineForKidnapper(actor);
	}
}
