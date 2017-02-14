package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.objects.units.Actor;

public class ActionTalk extends Action {

	public static final String ACTION_NAME = "Talk";

	// Default for hostiles
	public ActionTalk(Actor performer, Actor targetActor) {
		super(ACTION_NAME, performer, targetActor);
	}

	@Override
	public void perform() {
		Conversation conversation = null;
		if (target == Game.level.factions.get(0).actors.get(0)) {
			conversation = performer.getConversation();
		} else {
			conversation = ((Actor) target).getConversation();
		}

		if (conversation != null) {
			conversation.currentConversationPart = conversation.openingConversationPart;
			Game.level.conversation = conversation;

		}

	}

}
