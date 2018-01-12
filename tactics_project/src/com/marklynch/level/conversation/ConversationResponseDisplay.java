package com.marklynch.level.conversation;

import java.util.Vector;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.ActionInitiateTrade;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;

import mdesl.graphics.Color;

public class ConversationResponseDisplay {

	public Vector<LevelButton> buttons = new Vector<LevelButton>();
	public LevelButton buttonTrade;
	public final static String stringTrade = "TRADE [A]";
	final static float tradeButtonWidth = Game.font.getWidth(stringTrade);
	public LevelButton buttonLeave;
	public final static String stringLeave = "LEAVE [ESC}";
	final static float leaveButtonWidth = Game.font.getWidth(stringLeave);
	// conversationReponseEnd = new ConversationResponse("Leave", null);
	public Level level;
	public Square square;
	public LevelButton selectSquareButton;
	public float drawPositionX, drawPositionY;
	public float marginBetweenButtons = 30;

	Button highlightedButton;
	int highlightedButtonIndex = 0;

	ConversationResponse[] conversationResponses;

	float totalWidth = 0;

	GameObject talker;
	private ConversationPart conversationPart;

	public ConversationResponseDisplay(float width, Level level, ConversationResponse[] conversationResponses,
			GameObject talker, ConversationPart conversationPart) {

		this.conversationResponses = conversationResponses;
		this.conversationPart = conversationPart;
		for (int i = 0; i < this.conversationResponses.length; i++) {
			conversationResponses[i].text = (i + 1) + ". " + conversationResponses[i].text;
		}
		this.talker = talker;
		updateObjectsButtons();

	}

	public void updateObjectsButtons() {

		totalWidth = 0;
		for (int i = 0; i < conversationResponses.length; i++) {
			totalWidth += Game.font.getWidth(conversationResponses[i].text);
			totalWidth += marginBetweenButtons;
		}
		totalWidth -= 30;

		float positionX = Game.halfWindowWidth - totalWidth / 2;
		float widthSoFar = 0;

		buttons.clear();

		float buttonHeight = 30;

		for (int i = 0; i < conversationResponses.length; i++) {
			final int index = i;

			// The line and the highlight are drawn in relation to zoom and
			// position...

			// BUT... I dont want the buttons to zoom :P
			float buttonWidth = Game.font.getWidth(conversationResponses[i].text);

			final LevelButton responseButton = new LevelButton(positionX + widthSoFar,
					Conversation.bottomMargin + buttonHeight + 10, buttonWidth, buttonHeight, null, null,
					"" + conversationResponses[i].text, true, false, Color.WHITE, Color.BLACK, null);

			responseButton.clickListener = new ClickListener() {

				@Override
				public void click() {
					conversationResponses[index].select();
				}
			};
			buttons.add(responseButton);
			widthSoFar += buttonWidth + marginBetweenButtons;

		}

		buttonTrade = new LevelButton(leaveButtonWidth + 30 + tradeButtonWidth + 30, buttonHeight + 10,
				tradeButtonWidth, buttonHeight, null, null, stringTrade, false, false, Color.WHITE, Color.BLACK, null);
		buttonTrade.clickListener = new ClickListener() {
			@Override
			public void click() {
				new ActionInitiateTrade(Game.level.player, talker).perform();
			}
		};
		buttons.add(buttonTrade);

		buttonLeave = new LevelButton(leaveButtonWidth + 30, buttonHeight + 10, leaveButtonWidth, buttonHeight, null,
				null, stringLeave, false, false, Color.WHITE, Color.BLACK, null);
		buttonLeave.clickListener = new ClickListener() {
			@Override
			public void click() {
				leave();
				Game.level.conversation = null;
			}
		};
		buttons.add(buttonLeave);

		highlightedButton = buttons.get(highlightedButtonIndex);
		highlightedButton.highlight();

	}

	public void draw() {
		for (LevelButton button : buttons) {
			button.draw();
		}
	}

	public void resize() {
		updateObjectsButtons();
	}

	public void leave() {
		conversationPart.leave();
	}

	public void selectDialogueOption(char character) {
		int index = Character.getNumericValue(character) - 1;
		if (index >= 0 && index < conversationResponses.length) {
			conversationResponses[index].select();
		}
	}
}
