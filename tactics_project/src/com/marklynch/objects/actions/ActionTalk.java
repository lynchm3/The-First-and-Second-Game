package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.objects.units.Actor;

public class ActionTalk extends Action {

	public static final String ACTION_NAME = "Talk";

	public Actor talker;
	public Actor target;

	// Default for hostiles
	public ActionTalk(Actor talker, Actor target) {
		super(ACTION_NAME);
		this.talker = talker;
		this.target = target;
	}

	@Override
	public void perform() {
		Conversation conversation = null;
		if (target == Game.level.player) {
			conversation = talker.getConversation();
		} else {
			conversation = target.getConversation();
		}

		if (conversation != null) {
			conversation.currentConversationPart = conversation.openingConversationPart;
			Game.level.conversation = conversation;

		}

	}

}