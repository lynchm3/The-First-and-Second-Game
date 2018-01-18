package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationResponseDisplay;
import com.marklynch.objects.units.Actor;

public class ActionTalk extends Action {

	public static final String ACTION_NAME = "Talk";

	public Actor performer;
	public Actor target;
	public Conversation conversation;

	public ActionTalk(Actor talker, Actor target) {
		this(talker, target, null);
	}

	public ActionTalk(Actor talker, Actor target, Conversation conversation) {
		super(ACTION_NAME, "action_talk.png");
		this.performer = talker;
		this.target = target;
		this.conversation = conversation;
		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {
		if (!enabled)
			return;

		if (conversation != null) {

		} else if (target == Game.level.player) {
			conversation = performer.getConversation();
		} else {
			conversation = target.getConversation();
		}

		if (conversation != null) {
			conversation.currentConversationPart = conversation.openingConversationPart;
			Game.level.conversation = conversation;
			ConversationResponseDisplay.updateStandardButtons();

		}

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		if (!performer.canSeeSquare(target.squareGameObjectIsOn)) {
			return false;
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Sound createSound() {
		return new Sound(performer, target, target.squareGameObjectIsOn, 1, legal, this.getClass());
	}

}