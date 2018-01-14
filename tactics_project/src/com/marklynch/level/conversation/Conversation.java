package com.marklynch.level.conversation;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.QuadUtils;

import mdesl.graphics.Color;

public class Conversation {

	final static float height = 100;
	final static float bottomMargin = 100;
	static float width = Game.windowWidth;
	static float x1 = 0;
	static float y1 = Game.windowHeight - bottomMargin - height;
	static float x2 = width;
	static float y2 = Game.windowHeight - bottomMargin;

	// ArrayList<ConversationPart> conversationParts;
	public ConversationPart openingConversationPart;
	public ConversationPart currentConversationPart;
	public Actor originalConversationTarget;

	public Conversation(ConversationPart openingConversationPart) {
		super();
		// this.conversationParts = new ArrayList<ConversationPart>();

		// float height = 100;
		// float bottomMargin = 300;
		// float topMargin = 25;
		// float width = 100;
		// float halfTextWidth = Game.font.getWidth("End") / 2;
		// float x1 = Game.halfWindowWidth - halfTextWidth;
		// float y1 = Game.windowHeight - bottomMargin - height - topMargin;

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
		QuadUtils.drawQuad(new Color(0f, 0f, 0f, 0.5f), 0, 0, Game.windowWidth, Game.windowHeight);
		QuadUtils.drawQuad(Color.BLACK, x1, y1, x2, y2);
		currentConversationPart.drawStaticUI1();
		currentConversationPart.drawStaticUI2();
		// TextUtils.printTextWithImages(new Object[] { object }, realX, realY,
		// Integer.MAX_VALUE, true);

	}

	public static Conversation createConversation(Object[] text, GameObject gameObject) {
		ConversationPart conversationPartYouWontGetOut = new ConversationPart(text, new ConversationResponse[] {},
				gameObject);

		return new Conversation(conversationPartYouWontGetOut);
	}

	public static Conversation createConversation(String text, GameObject gameObject) {
		return createConversation(new Object[] { text }, gameObject);
	}

	public void resize() {
		currentConversationPart.resize();

	}

	public void selectDialogueOption(char character) {
		this.currentConversationPart.selectDialogueOption(character);

	}
}
