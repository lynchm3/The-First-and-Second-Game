package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.conversation.Conversation;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class ActionRead extends Action {

	public static final String ACTION_NAME = "Read";

	public Actor reader;
	public GameObject target;

	// Default for hostiles
	public ActionRead(Actor reader, GameObject target) {
		super(ACTION_NAME);
		this.reader = reader;
		this.target = target;
	}

	@Override
	public void perform() {
		System.out.println("ActionRead.preform");
		Conversation conversation = null;
		conversation = target.getConversation();
		System.out.println("conversation = " + conversation);

		if (conversation != null) {
			conversation.currentConversationPart = conversation.openingConversationPart;
			Game.level.conversation = conversation;
		}

	}

}
