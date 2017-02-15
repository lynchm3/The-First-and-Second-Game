package com.marklynch.level.conversation;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

public class ConversationPart {

	public ConversationResponse[] conversationResponses;
	public String text;
	int textWidth;
	int halfTextWidth;
	public WindowSelectConversationResponse windowSelectConversationResponse;
	public GameObject talker;

	public ConversationPart(String text, ConversationResponse[] conversationResponses, GameObject talker) {
		super();
		this.conversationResponses = conversationResponses;
		this.text = text;
		this.talker = talker;
		textWidth = Game.font.getWidth(text);
		halfTextWidth = textWidth / 2;

		windowSelectConversationResponse = new WindowSelectConversationResponse(100, Game.level, conversationResponses);

	}

	public void drawStaticUI1() {

		TextureUtils.drawTexture(talker.imageTexture, 1.0f, 0, Game.halfWindowHeight, Game.halfWindowHeight,
				Game.windowHeight);

		TextureUtils.drawTexture(Game.level.player.imageTexture, 1.0f, Game.windowWidth,
				Game.windowWidth - Game.halfWindowHeight, Game.halfWindowHeight, Game.windowHeight);

		windowSelectConversationResponse.draw();

	}

	public void drawStaticUI2() {
		// Text

		float height = 100;
		float bottomMargin = 50;
		float topMargin = 25;
		float maxWidth = Game.windowWidth;
		float x1 = Game.halfWindowWidth - halfTextWidth;
		float y1 = Game.windowHeight - bottomMargin - height + topMargin;

		TextUtils.printTextWithImages(new Object[] { text }, x1, y1, maxWidth, true);

	}

}
