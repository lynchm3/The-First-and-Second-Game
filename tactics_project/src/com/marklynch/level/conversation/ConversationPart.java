package com.marklynch.level.conversation;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.utils.TextUtils;

public class ConversationPart {

	public ArrayList<ConversationResponse> conversationResponses;
	public String text;
	int textWidth;
	int halfTextWidth;
	public WindowSelectConversationResponse windowSelectConversationResponse;

	public ConversationPart(String text, ArrayList<ConversationResponse> conversationResponses) {
		super();
		this.conversationResponses = conversationResponses;
		this.text = text;
		textWidth = Game.font.getWidth(text);
		halfTextWidth = textWidth / 2;

		windowSelectConversationResponse = new WindowSelectConversationResponse(100, Game.level, conversationResponses);

	}

	public void drawStaticUI() {
		// TODO Auto-generated method stub

		float height = 100;
		float bottomMargin = 50;
		float topMargin = 25;
		float maxWidth = Game.windowWidth;
		float x1 = Game.halfWindowWidth - halfTextWidth;
		float y1 = Game.windowHeight - bottomMargin - height + topMargin;

		TextUtils.printTextWithImages(new Object[] { text }, x1, y1, maxWidth, true);

		windowSelectConversationResponse.draw();

	}

}
