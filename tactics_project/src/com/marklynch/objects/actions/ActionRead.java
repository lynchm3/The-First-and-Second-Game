package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class ActionRead extends Action {

	public static final String ACTION_NAME = "Read";

	public Actor performer;
	public GameObject target;

	// Default for hostiles
	public ActionRead(Actor reader, GameObject target) {
		super(ACTION_NAME, "action_read.png");
		this.performer = reader;
		this.target = target;
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
		}

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();
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

	@Override
	public Sound createSound() {
		return null;
	}

}
