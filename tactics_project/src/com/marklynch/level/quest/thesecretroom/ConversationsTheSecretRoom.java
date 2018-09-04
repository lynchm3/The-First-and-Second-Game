package com.marklynch.level.quest.thesecretroom;

import com.marklynch.level.conversation.Conversation;

public class ConversationsTheSecretRoom {

	static QuestTheSecretRoom quest;

	// Conversations for bill
	public static Conversation conversationRangerImNotSpying;

	public static void createConversations() {
		// Bill
		setUpConversationYouDidntHelp();
	}

	private static void setUpConversationYouDidntHelp() {
		// Really like the "Now fuck off!" bit.
		// ConversationPart conversationPartOnlyHuntersGetLoot = new ConversationPart(
		// new Object[] { "Only hunters get loot. Now fuck off!" }, new
		// ConversationResponse[] {},
		// quest.hunterPack.getLeader(), quest);
		// conversationPartOnlyHuntersGetLoot.leaveConversationListener = new
		// LeaveConversationListener() {
		//
		// @Override
		// public void leave() {
		// quest.addJournalLog(quest.journalLogToldToFuckOffByHunters);
		// quest.resolve(quest.hunterPack.getLeader().squareGameObjectIsOn);
		// }
		// };
		//
		// conversationHuntersOnlyHuntersGetLoot = new
		// Conversation(conversationPartOnlyHuntersGetLoot,
		// quest.hunterPack.getLeader(), true);

	}

}
