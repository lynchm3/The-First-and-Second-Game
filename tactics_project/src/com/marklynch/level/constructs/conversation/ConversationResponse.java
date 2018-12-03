package com.marklynch.level.constructs.conversation;

import com.marklynch.Game;

public class ConversationResponse {

	public String text;
	public ConversationPart nextConversationPart;

	public ConversationResponse(String text, ConversationPart nextConversationPart) {
		this.text = text;
		this.nextConversationPart = nextConversationPart;
	}

	public void select() {
		if (nextConversationPart == null) {
			Game.level.conversation = null;
		} else {
			Game.level.conversation.currentConversationPart = nextConversationPart;
		}
	}

}
