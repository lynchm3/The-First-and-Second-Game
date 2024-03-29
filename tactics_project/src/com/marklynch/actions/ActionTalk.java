package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.conversation.Conversation;
import com.marklynch.level.constructs.conversation.ConversationResponseDisplay;
import com.marklynch.objects.actors.Actor;

public class ActionTalk extends Action {

	public static final String ACTION_NAME = "Talk";

	public Conversation conversation;

	public ActionTalk(Actor talker, Actor target) {
		this(talker, target, null);
	}

	public ActionTalk(Actor performer, Actor target, Conversation conversation) {
		super(ACTION_NAME, textureTalk, performer, target);
		this.conversation = conversation;
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

		if (conversation != null) {

		} else if (targetGameObject == Game.level.player) {
			conversation = performer.getConversation();
		} else {
			conversation = targetGameObject.getConversation();
		}

		if (conversation == null)
			return;

		Level.pausePlayer();
		conversation.currentConversationPart = conversation.openingConversationPart;
		Game.level.conversation = conversation;
		Game.level.conversation.updateFlags();
		ConversationResponseDisplay.updateStandardButtons();
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {

		return true;
	}

	@Override
	public boolean checkRange() {
		// TODO Auto-generated method stub
		if (!performer.canSeeSquare(targetGameObject.squareGameObjectIsOn)) {
			return false;
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		return true;
	}

	@Override
	public Sound createSound() {
		return new Sound(performer, targetGameObject, targetGameObject.squareGameObjectIsOn, 1, legal, this.getClass());
	}

}