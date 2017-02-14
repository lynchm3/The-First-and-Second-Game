package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.objects.units.Actor;

public class ActionPickUp extends Action {

	public static final String ACTION_NAME = "Pick Up";

	// Default for hostiles
	public ActionPickUp(Actor performer, Actor targetActor) {
		super(ACTION_NAME, performer, targetActor);
	}

	@Override
	public void perform() {

		Conversation conversation = ((Actor) target).getConversation();
		if (conversation != null) {
			conversation.currentConversationPart = conversation.openingConversationPart;
			Game.level.conversation = conversation;
		}

	}

}