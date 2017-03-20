package com.marklynch.level.conversation;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

public class ConversationPart {

	protected ConversationResponse[] conversationResponses;
	public Object[] text;
	// int textWidth;
	int halfTextWidth;
	public WindowSelectConversationResponse windowSelectConversationResponse;
	public GameObject talker;

	public ConversationPart(Object[] text, ConversationResponse[] conversationResponses, GameObject talker) {
		super();
		this.conversationResponses = conversationResponses;
		this.text = text;
		this.talker = talker;
		// textWidth = Game.font.getWidth(text);
		// halfTextWidth = textWidth / 2;

		if (conversationResponses.length > 0)
			windowSelectConversationResponse = new WindowSelectConversationResponse(100, Game.level,
					conversationResponses);

	}

	public void drawStaticUI1() {

		// Speaker image
		TextureUtils.drawTexture(talker.imageTexture, 1.0f, 0, Game.halfWindowHeight, Game.halfWindowHeight,
				Game.windowHeight);

		// Speker 2 image (player)
		TextureUtils.drawTexture(Game.level.player.imageTexture, 1.0f, Game.windowWidth,
				Game.windowWidth - Game.halfWindowHeight, Game.halfWindowHeight, Game.windowHeight);

		windowSelectConversationResponse.draw();

	}

	public void drawStaticUI2() {

		float topMargin = 25;
		float maxWidth = Game.windowWidth;
		float x1 = Game.halfWindowWidth - halfTextWidth;
		float y1 = Game.windowHeight - Conversation.bottomMargin - Conversation.height + topMargin;

		TextUtils.printTextWithImages(text, x1, y1, maxWidth, true);

	}

	public void setConversationResponses(ConversationResponse[] conversationResponses) {
		this.conversationResponses = conversationResponses;
		windowSelectConversationResponse = new WindowSelectConversationResponse(100, Game.level, conversationResponses);
	}

}
