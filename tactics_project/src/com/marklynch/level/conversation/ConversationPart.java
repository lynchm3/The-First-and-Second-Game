package com.marklynch.level.conversation;

import java.util.ArrayList;
import java.util.Arrays;

import com.marklynch.Game;
import com.marklynch.level.constructs.bounds.Area;
import com.marklynch.level.quest.Quest;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.button.Link;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

public class ConversationPart {
	public ArrayList<Link> links;

	protected ConversationResponse[] conversationResponses;
	public Object[] text;
	// int textWidth;
	int halfTextWidth;
	public ConversationResponseDisplay windowSelectConversationResponse;
	public GameObject talker;

	public LeaveConversationListener leaveConversationListener;

	public float height;

	public Quest quest;
	private int turn;
	private String turnString;
	private Area area;
	private Square square;
	public ArrayList<Object> squareAndText;

	public ConversationPart(Object[] text, ConversationResponse[] conversationResponses, GameObject talker,
			Quest quest) {
		super();
		this.conversationResponses = conversationResponses;
		this.text = text;
		this.talker = talker;
		this.quest = quest;
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

	public ConversationPart(Object[] text, ConversationResponse[] conversationResponses, GameObject talker) {
		this(text, conversationResponses, talker, null);
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

		if (quest != null)
			quest.addConversationPart(this);
	}

	public void leave() {
		if (leaveConversationListener != null)
			leaveConversationListener.leave();

		if (quest != null)
			quest.addConversationPart(this);

	}

	public void setTurn(int turn) {
		this.turn = turn;
		this.turnString = Game.level.timeString + " (Turn " + turn + ") ";
	}

	public String getTurnString() {
		return turnString;
	}

	public void setSquare(Square square) {
		this.square = square;
		squareAndText = new ArrayList<Object>();
		squareAndText.add(square);
		squareAndText.addAll(Arrays.asList(text));
	}

	public Square getSquare() {
		return square;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Area getArea() {
		return area;
	}

}
