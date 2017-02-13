package com.marklynch.level.constructs;

import java.util.ArrayList;

public class ConversationPart {

	public ArrayList<ConversationResponse> conversationResponses;
	public String text;

	public ConversationPart(String text, ArrayList<ConversationResponse> conversationResponses) {
		super();
		this.conversationResponses = conversationResponses;
		this.text = text;
	}

	public void drawStaticUI() {
		// TODO Auto-generated method stub

		for (ConversationResponse conversationResponse : conversationResponses) {
			conversationResponse.drawStaticUI();
		}

	}

}
