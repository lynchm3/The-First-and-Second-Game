package com.marklynch.level.conversation;

import java.util.Vector;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.squares.Square;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;

import mdesl.graphics.Color;

public class WindowSelectConversationResponse {

	public float width;
	public Vector<LevelButton> buttons = new Vector<LevelButton>();
	public Level level;
	public Square square;
	public LevelButton selectSquareButton;
	public float drawPositionX, drawPositionY;
	public float marginBetweenButtons = 30;;

	Button highlightedButton;
	int highlightedButtonIndex = 0;

	ConversationResponse[] conversationResponses;

	float totalWidth = 0;

	public WindowSelectConversationResponse(float width, Level level, ConversationResponse[] conversationResponses) {

		this.conversationResponses = conversationResponses;
		updateObjectsButtons();

	}

	public void updateObjectsButtons() {
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
					"" + conversationResponses[i].text, true, false, Color.WHITE, Color.BLACK);

			responseButton.clickListener = new ClickListener() {

				@Override
				public void click() {

					conversationResponses[index].select();
				}
			};
			buttons.add(responseButton);
			widthSoFar += buttonWidth + marginBetweenButtons;

		}

		highlightedButton = buttons.get(highlightedButtonIndex);
		highlightedButton.highlight();

	}

	public void draw() {
		for (LevelButton button : buttons) {
			button.draw();
		}

	}
}
