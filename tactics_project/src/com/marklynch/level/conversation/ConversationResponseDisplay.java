package com.marklynch.level.conversation;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.ActionInitiateTrade;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Human;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;

import com.marklynch.utils.Color;

public class ConversationResponseDisplay {

	public ArrayList<LevelButton> responseButtons = new ArrayList<LevelButton>();
	public static ArrayList<LevelButton> standardButtons = new ArrayList<LevelButton>();
	public static LevelButton buttonTrade;
	public final static String stringTrade = "TRADE [A]";
	final static float tradeButtonWidth = Game.smallFont.getWidth(stringTrade);
	public static LevelButton buttonLeave;
	public final static String stringLeave = "LEAVE [ESC}";
	final static float leaveButtonWidth = Game.smallFont.getWidth(stringLeave);
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
		updateResponseButtons();

	}

	public void updateResponseButtons() {

		totalWidth = 0;
		for (int i = 0; i < conversationResponses.length; i++) {
			totalWidth += Game.smallFont.getWidth(conversationResponses[i].text);
			totalWidth += marginBetweenButtons;
		}
		totalWidth -= 30;

		float positionX = Game.halfWindowWidth - totalWidth / 2;
		float widthSoFar = 0;

		responseButtons.clear();
		buttonTrade = null;
		buttonLeave = null;

		float buttonHeight = 30;

		for (int i = 0; i < conversationResponses.length; i++) {
			final int index = i;

			// The line and the highlight are drawn in relation to zoom and
			// position...

			// BUT... I dont want the buttons to zoom :P
			float buttonWidth = Game.smallFont.getWidth(conversationResponses[i].text);

			final LevelButton responseButton = new LevelButton(positionX + widthSoFar,
					Conversation.bottomMargin + buttonHeight + 10, buttonWidth, buttonHeight, null, null,
					"" + conversationResponses[i].text, true, false, Color.WHITE, Color.BLACK, null);

			responseButton.clickListener = new ClickListener() {

				@Override
				public void click() {
					conversationResponses[index].select();
				}
			};
			responseButtons.add(responseButton);
			widthSoFar += buttonWidth + marginBetweenButtons;

		}
		// if (Game.level.conversation != null) {
		// if (Game.level.conversation.enableEsc) {
		// buttonLeave.enabled = true;
		// } else {
		// buttonLeave.enabled = false;
		// }
		// }

		// if()
		// highlightedButton = buttons.get(highlightedButtonIndex);
		// highlightedButton.highlight();

	}

	public static void updateStandardButtons() {

		standardButtons.clear();
		buttonTrade = null;
		buttonLeave = null;
		float buttonHeight = 30;

		if (Game.level.conversation != null && Game.level.conversation.originalConversationTarget instanceof Human) {
			String tooltipText;

			if (Game.level.conversation.enableTrade) {
				tooltipText = "Open trade";
			} else {
				tooltipText = "Won't trade, recently witnessed you committing a crime";
			}

			buttonTrade = new LevelButton(leaveButtonWidth + 30 + tradeButtonWidth + 30, buttonHeight + 10,
					tradeButtonWidth, buttonHeight, null, null, stringTrade, false, false, Color.WHITE, Color.BLACK,
					tooltipText);
			buttonTrade.clickListener = new ClickListener() {
				@Override
				public void click() {
					new ActionInitiateTrade(Game.level.player,
							(Actor) Game.level.conversation.originalConversationTarget).perform();
				}
			};
			standardButtons.add(buttonTrade);
			buttonTrade.enabled = Game.level.conversation.enableTrade;
		}

		if (Game.level.conversation != null) {
			String tooltipText;
			if (Game.level.conversation.enableEsc) {
				tooltipText = "Leave conversation";
			} else {
				tooltipText = "Response required";
			}

			buttonLeave = new LevelButton(leaveButtonWidth + 30, buttonHeight + 10, leaveButtonWidth, buttonHeight,
					null, null, stringLeave, false, false, Color.WHITE, Color.BLACK, tooltipText);
			buttonLeave.clickListener = new ClickListener() {
				@Override
				public void click() {
					leave();
					Game.level.conversation = null;
				}
			};
			standardButtons.add(buttonLeave);
			buttonLeave.enabled = Game.level.conversation.enableEsc;
		}

	}

	public void draw() {
		for (LevelButton button : responseButtons) {
			button.draw();
		}
		for (LevelButton button : standardButtons) {
			button.draw();
		}
	}

	public void resize() {
		updateResponseButtons();
		updateStandardButtons();
	}

	public static void leave() {
		Game.level.conversation.currentConversationPart.leave();
	}

	public void selectDialogueOption(char character) {
		int index = Character.getNumericValue(character) - 1;
		if (index >= 0 && index < conversationResponses.length) {
			conversationResponses[index].select();
		}
	}
}
