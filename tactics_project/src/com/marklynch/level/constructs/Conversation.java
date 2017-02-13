package com.marklynch.level.constructs;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.utils.QuadUtils;

import mdesl.graphics.Color;

public class Conversation {

	ArrayList<ConversationPart> conversationParts;
	ConversationPart currentConversationPart;

	public Conversation(ArrayList<ConversationPart> conversationParts) {
		super();
		this.conversationParts = new ArrayList<ConversationPart>();
		ArrayList<ConversationResponse> conversationResponses = new ArrayList<ConversationResponse>();
		ConversationResponse end = new ConversationResponse("End", null);
		conversationResponses.add(end);

		ConversationPart conversationPart1 = new ConversationPart("Hello, How are you?", conversationResponses);
		this.conversationParts.add(conversationPart1);

		currentConversationPart = this.conversationParts.get(0);
	}

	public void drawStaticUI() {

		float height = 100;
		float bottomMargin = 50;
		float width = Game.windowWidth;
		float x1 = 0;
		float y1 = Game.windowHeight - bottomMargin - height;
		float x2 = width;
		float y2 = Game.windowHeight - bottomMargin;

		QuadUtils.drawQuad(Color.BLACK, x1, x2, y1, y2);
		// TextUtils.printTextWithImages(new Object[] { object }, realX, realY,
		// Integer.MAX_VALUE, true);

		currentConversationPart.drawStaticUI();

	}
}
