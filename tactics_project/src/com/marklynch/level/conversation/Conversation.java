package com.marklynch.level.conversation;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Human;
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
	public GameObject originalConversationTarget;

	public boolean enableTrade = true;
	public boolean enableEsc = true;

	public Conversation(ConversationPart openingConversationPart, GameObject originalConversationTarget,
			boolean enableEsc) {
		super();
		updateFlags();
		this.enableEsc = enableEsc;

		this.openingConversationPart = this.currentConversationPart = openingConversationPart;
		this.originalConversationTarget = originalConversationTarget;
	}

	public void updateFlags() {
		if (originalConversationTarget instanceof Human) {
			Actor actor = (Actor) originalConversationTarget;
			if (actor.knownCriminals.contains(Game.level.player)) {
				enableTrade = false;
			} else {
				enableTrade = true;
			}
		} else {
			enableTrade = false;
		}
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

		return new Conversation(conversationPartYouWontGetOut, gameObject, true);
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
