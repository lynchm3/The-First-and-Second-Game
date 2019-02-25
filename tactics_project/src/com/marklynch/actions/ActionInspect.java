package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.conversation.Conversation;
import com.marklynch.level.constructs.conversation.ConversationResponseDisplay;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;

public class ActionInspect extends Action {

	public static final String ACTION_NAME = "Inspect";

	// Default for hostiles
	public ActionInspect(Actor reader, GameObject target) {
		super(ACTION_NAME, textureSearch, reader, target);
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
		conversation = targetGameObject.getConversation();

		if (conversation != null) {
			Level.pausePlayer();
			if (performer.equippedBeforePickingUpObject != null) {
				performer.equipped = performer.equippedBeforePickingUpObject;
				performer.equippedBeforePickingUpObject = null;
			}
			conversation.currentConversationPart = conversation.openingConversationPart;
			Game.level.conversation = conversation;
			Game.level.conversation.updateFlags();
			ConversationResponseDisplay.updateStandardButtons();
		}

		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean checkRange() {
		// TODO Auto-generated method stub
		return true;
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
