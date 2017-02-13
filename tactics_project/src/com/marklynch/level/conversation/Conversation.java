package com.marklynch.level.conversation;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.utils.QuadUtils;

import mdesl.graphics.Color;

public class Conversation {

	final float height = 100;
	final float bottomMargin = 50;
	float width = Game.windowWidth;
	float x1 = 0;
	float y1 = Game.windowHeight - bottomMargin - height;
	float x2 = width;
	float y2 = Game.windowHeight - bottomMargin;

	// ArrayList<ConversationPart> conversationParts;
	public ConversationPart currentConversationPart;

	public Conversation(ArrayList<ConversationPart> conversationParts) {
		super();
		// this.conversationParts = new ArrayList<ConversationPart>();

		float height = 100;
		float bottomMargin = 50;
		float topMargin = 25;
		float width = 100;
		float halfTextWidth = Game.font.getWidth("End") / 2;
		float x1 = Game.halfWindowWidth - halfTextWidth;
		float y1 = Game.windowHeight - bottomMargin - height - topMargin;

		ConversationResponse end = new ConversationResponse("End", null, x1, y1, width, height);
		ArrayList<ConversationResponse> conversationResponses2 = new ArrayList<ConversationResponse>();
		conversationResponses2.add(end);
		ConversationPart conversationPart2 = new ConversationPart("What are you doing here?", conversationResponses2);

		ConversationResponse next = new ConversationResponse("Next", conversationPart2, x1, y1, width, height);
		ArrayList<ConversationResponse> conversationResponses1 = new ArrayList<ConversationResponse>();
		conversationResponses1.add(next);
		conversationResponses1.add(end);

		currentConversationPart = new ConversationPart("Hello, How are you?", conversationResponses1);
		// this.conversationParts.add(conversationPart1);

		// currentConversationPart = this.conversationParts.get(0);
	}

	public void drawStaticUI() {

		width = Game.windowWidth;
		y1 = Game.windowHeight - bottomMargin - height;
		x2 = width;
		y2 = Game.windowHeight - bottomMargin;
		QuadUtils.drawQuad(Color.BLACK, x1, x2, y1, y2);
		// TextUtils.printTextWithImages(new Object[] { object }, realX, realY,
		// Integer.MAX_VALUE, true);

		currentConversationPart.drawStaticUI();

	}
}
