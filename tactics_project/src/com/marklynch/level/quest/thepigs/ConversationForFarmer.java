package com.marklynch.level.quest.thepigs;

import com.marklynch.level.conversation.Conversation;

public class ConversationForFarmer extends Conversation {

	QuestThePigs questThePigs;

	public ConversationForFarmer(final QuestThePigs questThePigs) {
		super(null);
		this.questThePigs = questThePigs;
	}

}
