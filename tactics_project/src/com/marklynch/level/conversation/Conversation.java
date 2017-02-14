package com.marklynch.level.conversation;

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
	public ConversationPart openingConversationPart;
	public ConversationPart currentConversationPart;

	public Conversation(ConversationPart openingConversationPart) {
		super();
		// this.conversationParts = new ArrayList<ConversationPart>();

		float height = 100;
		float bottomMargin = 50;
		float topMargin = 25;
		float width = 100;
		float halfTextWidth = Game.font.getWidth("End") / 2;
		float x1 = Game.halfWindowWidth - halfTextWidth;
		float y1 = Game.windowHeight - bottomMargin - height - topMargin;

		this.openingConversationPart = this.currentConversationPart = openingConversationPart;

		// ConversationResponse end = new ConversationResponse("End", null);
		// ConversationResponse[] conversationResponses2 = { end };
		// ConversationPart conversationPart2 = new ConversationPart("What are
		// you doing here?", conversationResponses2,
		// talker);
		//
		// ConversationResponse next = new ConversationResponse("Next",
		// conversationPart2);
		// ConversationResponse[] conversationResponses1 = { next, end };
		//
		// currentConversationPart = new ConversationPart("Hello, How are you?",
		// conversationResponses1, talker);
	}

	public void drawStaticUI() {

		width = Game.windowWidth;
		y1 = Game.windowHeight - bottomMargin - height;
		x2 = width;
		y2 = Game.windowHeight - bottomMargin;
		QuadUtils.drawQuad(new Color(0f, 0f, 0f, 0.5f), 0, Game.windowWidth, 0, Game.windowHeight);
		QuadUtils.drawQuad(Color.BLACK, x1, x2, y1, y2);
		currentConversationPart.drawStaticUI1();
		currentConversationPart.drawStaticUI2();
		// TextUtils.printTextWithImages(new Object[] { object }, realX, realY,
		// Integer.MAX_VALUE, true);

	}
}
