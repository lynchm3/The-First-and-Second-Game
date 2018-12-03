package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationResponseDisplay;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;

public class ActionRead extends Action {

	public static final String ACTION_NAME = "Read";

	// Default for hostiles
	public ActionRead(Actor performer, GameObject target) {
		super(ACTION_NAME, textureRead, performer, target);
		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {
		super.perform();

		if (!enabled)
			return;

		if (!checkRange())
			return;

		Conversation conversation = null;
		conversation = target.getConversation();

		if (conversation != null) {
			Level.pausePlayer();
			conversation.currentConversationPart = conversation.openingConversationPart;
			Game.level.conversation = conversation;
			Game.level.conversation.updateFlags();
			ConversationResponseDisplay.updateStandardButtons();
		}

		if (performer == Level.player)
			target.wasRead();

		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		// if (!target.squareGameObjectIsOn.visibleToPlayer)
		// return false;

		if (target.getConversation() == null)
			return false;

		return true;
	}

	@Override
	public boolean checkRange() {
		if (performer.canSeeGameObject(target))
			return true;
		return false;
	}

	@Override
	public boolean checkLegality() {
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}
