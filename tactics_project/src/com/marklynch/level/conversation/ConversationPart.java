package com.marklynch.level.conversation;

import java.util.ArrayList;
import java.util.Arrays;
//import mdesl.graphics.Color;

import com.marklynch.Game;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.button.Link;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;

public class ConversationPart {
	public ArrayList<Link> links;
	public ArrayList<Link> linksForJournal;

	protected ConversationResponse[] conversationResponses;
	public Object[] text;
	// int textWidth;
	int halfTextWidth;
	public ConversationResponseDisplay windowSelectConversationResponse;
	public GameObject talker;

	public LeaveConversationListener leaveConversationListener;

	public float height;

	public ArrayList<Quest> quests;
	private int turn;
	private String turnString;
	private Area area;
	private Square square;
	public ArrayList<Object> squareAndText;

	public ConversationPart(Object[] text, ConversationResponse[] conversationResponses, GameObject talker,
			Quest... quests) {
		super();
		this.conversationResponses = conversationResponses;
		this.text = text;
		this.talker = talker;
		this.quests = new ArrayList<Quest>(Arrays.asList(quests));
		// textWidth = Game.font.getWidth(text);
		// halfTextWidth = textWidth / 2;

		// if (conversationResponses.length > 0)
		windowSelectConversationResponse = new ConversationResponseDisplay(100, Game.level, conversationResponses,
				talker, this);

		if (text != null) {
			links = TextUtils.getLinks(text);
			height = TextUtils.getDimensions(text, Integer.MAX_VALUE)[1];

		}

	}

	public void drawStaticUI1() {

		if (talker.group != null) {
			for (Actor actor : talker.group.getMembers()) {

				if (actor.remainingHealth <= 0)
					continue;

				if (actor == talker)
					continue;

				float offsetX = -(System.identityHashCode(actor) % Game.halfWindowHeight);// -
																							// 128f;//
																							// -64;
				float offsetY = 0;

				if (actor == Game.level.conversation.originalConversationTarget) {

				} else {
					TextureUtils.drawTexture(actor.imageTexture, 1.0f, 0 + offsetX, Game.halfWindowHeight + offsetY,
							Game.halfWindowHeight + offsetX, Game.windowHeight + offsetY);
				}

			}

			if (talker != Game.level.conversation.originalConversationTarget) {
				float offsetX = -(System.identityHashCode(Game.level.conversation.originalConversationTarget)
						% Game.halfWindowHeight); // -64;
				float offsetY = 0;
				TextureUtils.drawTexture(Game.level.conversation.originalConversationTarget.imageTexture, 0 + offsetX,
						Game.halfWindowHeight + offsetY, Game.halfWindowHeight + offsetX, Game.windowHeight + offsetY,
						Color.RED);
				// (Texture texture, float x1, float y1, float x2, float y2,
				// Color color)
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

		TextUtils.printTextWithImages(x1, y1, maxWidth, true, links, text);

	}

	public void setConversationResponses(ConversationResponse[] conversationResponses) {
		this.conversationResponses = conversationResponses;
		windowSelectConversationResponse = new ConversationResponseDisplay(100, Game.level, conversationResponses,
				talker, this);
	}

	public void resize() {
		windowSelectConversationResponse.resize();

	}

	public void selectDialogueOption(char character) {
		windowSelectConversationResponse.selectDialogueOption(character);

		for (Quest quest : quests)
			quest.addConversationPart(this);
	}

	public void leave() {
		if (leaveConversationListener != null)
			leaveConversationListener.leave();

		for (Quest quest : quests)
			quest.addConversationPart(this);

	}

	public void setTurnAndSquareAndArea(int turn, Square square, Area area) {
		this.turn = turn;
		this.turnString = Game.level.timeString + " (Turn " + turn + ") ";

		this.square = square;
		squareAndText = new ArrayList<Object>();
		squareAndText.add(square);
		squareAndText.addAll(Arrays.asList(text));

		this.area = area;

		linksForJournal = new ArrayList<Link>();
		linksForJournal.addAll(TextUtils.getLinks(true, talker, square));
		linksForJournal.addAll(links);

	}

	public Square getSquare() {
		return square;
	}

	public String getTurnString() {
		return turnString;
	}

	public Area getArea() {
		return area;
	}

}
