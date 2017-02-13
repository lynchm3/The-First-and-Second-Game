package com.marklynch.level.constructs;

public class ConversationResponse {

	public String text;
	ConversationPart conversationPart;

	public ConversationResponse(String text, ConversationPart conversationPart) {
		super();
		this.text = text;
		this.conversationPart = conversationPart;
	}

	public void drawStaticUI() {
		// TODO Auto-generated method stub

	}

}
