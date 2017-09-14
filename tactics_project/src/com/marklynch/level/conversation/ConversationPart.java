package com.marklynch.level.conversation;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

public class ConversationPart {

	protected ConversationResponse[] conversationResponses;
	public Object[] text;
	// int textWidth;
	int halfTextWidth;
	public ConversationResponseDisplay windowSelectConversationResponse;
	public GameObject talker;

	public ConversationPart(Object[] text, ConversationResponse[] conversationResponses, GameObject talker) {
		super();
		this.conversationResponses = conversationResponses;
		this.text = text;
		this.talker = talker;
		// textWidth = Game.font.getWidth(text);
		// halfTextWidth = textWidth / 2;

		// if (conversationResponses.length > 0)
		windowSelectConversationResponse = new ConversationResponseDisplay(100, Game.level, conversationResponses,
				talker);

	}

	public void drawStaticUI1() {

		if (talker.group != null) {
			for (Actor actor : talker.group.getMembers()) {

				float offsetX = -(System.identityHashCode(actor) % Game.halfWindowHeight);// -
																							// 128f;//
																							// -64;
				float offsetY = 0;
				TextureUtils.drawTexture(actor.imageTexture, 1.0f, 0 + offsetX, Game.halfWindowHeight + offsetY,
						Game.halfWindowHeight + offsetX, Game.windowHeight + offsetY);

			}
		}

		// Speaker image
		TextureUtils.drawTexture(talker.imageTexture, 1.0f, 0, Game.halfWindowHeight, Game.halfWindowHeight,
				Game.windowHeight);

		// Speker 2 image (player)
		TextureUtils.drawTexture(Game.level.player.imageTexture, 1.0f, Game.windowWidth, Game.halfWindowHeight,
				Game.windowWidth - Game.halfWindowHeight, Game.windowHeight);

		windowSelectConversationResponse.draw();

	}

	public void drawStaticUI2() {

		float topMargin = 25;
		float maxWidth = Game.windowWidth;
		float x1 = Game.halfWindowWidth - halfTextWidth;
		float y1 = Game.windowHeight - Conversation.bottomMargin - Conversation.height + topMargin;

		TextUtils.printTextWithImages(x1, y1, maxWidth, true, true, text);

	}

	public void setConversationResponses(ConversationResponse[] conversationResponses) {
		this.conversationResponses = conversationResponses;
		windowSelectConversationResponse = new ConversationResponseDisplay(100, Game.level, conversationResponses,
				talker);
	}

	public void resize() {
		windowSelectConversationResponse.resize();

	}

}
