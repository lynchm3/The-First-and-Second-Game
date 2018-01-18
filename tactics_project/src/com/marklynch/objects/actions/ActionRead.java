package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationResponseDisplay;
import com.marklynch.objects.Readable;
import com.marklynch.objects.units.Actor;

public class ActionRead extends Action {

	public static final String ACTION_NAME = "Read";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	public Actor performer;
	public Readable target;

	// Default for hostiles
	public ActionRead(Actor reader, Readable target) {
		super(ACTION_NAME, "action_read.png");
		this.performer = reader;
		this.target = target;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		Conversation conversation = null;
		conversation = target.getConversation();

		if (conversation != null) {
			conversation.currentConversationPart = conversation.openingConversationPart;
			Game.level.conversation = conversation;
			Game.level.conversation.updateFlags();
			ConversationResponseDisplay.updateStandardButtons();
		}

		target.wasRead();

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		if (performer.canSeeGameObject(target))
			return true;
		return false;
	}

	@Override
	public boolean checkLegality() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}
