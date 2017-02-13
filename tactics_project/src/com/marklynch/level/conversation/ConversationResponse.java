package com.marklynch.level.conversation;

public class ConversationResponse {

	public String text;
	ConversationPart nextConversationPart;

	public ConversationResponse(String text, ConversationPart nextConversationPart, float x, float y, float width,
			float height) {
		this.text = text;
		this.nextConversationPart = nextConversationPart;
	}

}
