package com.marklynch.level.quest.thesecretroom;

import com.marklynch.level.conversation.Conversation;
import com.marklynch.level.conversation.ConversationPart;
import com.marklynch.level.conversation.ConversationResponse;
import com.marklynch.level.conversation.LeaveConversationListener;

public class ConversationsTheSecretRoom {

	static QuestTheSecretRoom quest;

	// Conversations for bill
	public static Conversation conversationHi;

	public static void createConversations() {
		// Kidnapper
		setUpConversationHi();
	}

	private static void setUpConversationHi() {
		// Really like the "Now fuck off!" bit.
		ConversationPart conversationPartOnlyHuntersGetLoot = new ConversationPart(
				new Object[] { "Hi! I'm totally not a kidnapper!" }, new ConversationResponse[] {}, quest.kidnapper,
				quest);
		conversationPartOnlyHuntersGetLoot.leaveConversationListener = new LeaveConversationListener() {

			@Override
			public void leave() {
			}
		};

		conversationHi = new Conversation(conversationPartOnlyHuntersGetLoot, quest.kidnapper, true);

	}

}
