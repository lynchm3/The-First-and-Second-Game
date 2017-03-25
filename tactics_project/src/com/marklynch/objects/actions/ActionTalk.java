package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.objects.units.Actor;

public class ActionTalk extends Action {

	public static final String ACTION_NAME = "Talk";

	public Actor performer;
	public Actor target;

	// Default for hostiles
	public ActionTalk(Actor talker, Actor target) {
		super(ACTION_NAME);
		this.performer = talker;
		this.target = target;
		legal = checkLegality();
	}

	@Override
	public void perform() {
		Conversation conversation = null;
		if (target == Game.level.player) {
			conversation = performer.getConversation();
		} else {
			conversation = target.getConversation();
		}

		if (conversation != null) {
			conversation.currentConversationPart = conversation.openingConversationPart;
			Game.level.conversation = conversation;

		}

		performer.actions.add(this);
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean checkLegality() {
		// TODO Auto-generated method stub
		return true;
	}

}