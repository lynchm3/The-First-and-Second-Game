package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationResponseDisplay;
import com.marklynch.objects.Readable;
import com.marklynch.objects.units.Actor;

public class ActionRead extends Action {

	public static final String ACTION_NAME = "Read";

	Readable readable;

	// Default for hostiles
	public ActionRead(Actor reader, Readable target) {
		super(ACTION_NAME, textureRead, reader, target, null);
		if (!check()) {
			enabled = false;
		}
		this.readable = target;
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
			readable.wasRead();

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
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
