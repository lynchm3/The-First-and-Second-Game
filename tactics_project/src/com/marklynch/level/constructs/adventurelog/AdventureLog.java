package com.marklynch.level.constructs.adventurelog;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.quest.Quest;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.Scrollable;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class AdventureLog implements Draggable, Scrollable {

	public boolean showing = false;

	public Quest questToDisplayInAdventureLog = null;
	public Quest activeQuest = null;

	int listX;
	int listY;
	int listBorder;
	int listWidth;

	int contentX;
	int contentY;
	int contentBorder;

	int listItemHeight;

	transient static int bottomBorderHeight;

	public static ArrayList<LevelButton> buttons = new ArrayList<LevelButton>();
	public static ArrayList<LevelButton> buttonsToMakeQuestAcive = new ArrayList<LevelButton>();
	public static ArrayList<LevelButton> buttonsToDisplayQuest = new ArrayList<LevelButton>();
	public static ArrayList<LevelButton> links = new ArrayList<LevelButton>();
	// Close button
	static LevelButton buttonClose;

	public AdventureLog() {
		resize();

		buttonClose = new LevelButton(Game.halfWindowWidth - 25f, bottomBorderHeight, 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", "CLOSE [N]", true, false, Color.BLACK, Color.WHITE);
		buttonClose.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.level.openCloseAdventureLog();
			}
		});
		buttons.add(buttonClose);
	}

	public void resize() {
		listX = 0;
		listY = 0;
		listBorder = 16;
		listWidth = 300;

		contentX = listX + listWidth + listBorder * 2;
		contentY = 0;
		contentBorder = 16;

		listItemHeight = 30;

		bottomBorderHeight = 384;

	}

	public void open() {
		// Sort quests by active, keep selectedQuest at top
		// And also sort by completed...
		resize();
		showing = true;

		buttons.clear();
		buttonsToMakeQuestAcive.clear();
		buttonsToDisplayQuest.clear();
		buttons.add(buttonClose);

		int questsDrawnInList = 0;
		for (final Quest quest : Level.quests) {
			if (quest.started) {
				if (questToDisplayInAdventureLog == null) {
					questToDisplayInAdventureLog = quest;
				}
				if (activeQuest == null) {
					activeQuest = quest;
				}

				// buttons to make quest the active one
				final LevelButton buttonToMakeQuestActive = new LevelButton(
						listX + listBorder + listWidth - listItemHeight,
						listY + listBorder + questsDrawnInList * listItemHeight, listItemHeight, listItemHeight,
						"end_turn_button.png", "end_turn_button.png", "", true, true, Color.BLACK, Color.WHITE);
				buttonToMakeQuestActive.setClickListener(new ClickListener() {

					@Override
					public void click() {
						for (LevelButton button : buttonsToMakeQuestAcive) {
							// change colors to off
							button.buttonColor = Color.BLACK;
							button.setTextColor(Color.WHITE);
						}
						buttonToMakeQuestActive.buttonColor = Color.WHITE;
						buttonToMakeQuestActive.setTextColor(Color.BLACK);
						activeQuest = quest;

					}
				});

				if (questToDisplayInAdventureLog == quest) {
					buttonToMakeQuestActive.buttonColor = Color.WHITE;
					buttonToMakeQuestActive.setTextColor(Color.BLACK);
				}

				buttons.add(buttonToMakeQuestActive);
				buttonsToMakeQuestAcive.add(buttonToMakeQuestActive);

				// Button w/ the name of the quest -

				final LevelButton buttonToShowQuestDetails = new LevelButton(listX + listBorder,
						listY + listBorder + questsDrawnInList * listItemHeight, listWidth - listItemHeight,
						listItemHeight, "end_turn_button.png", "end_turn_button.png", quest.name, true, true,
						Color.BLACK, Color.WHITE);
				buttonToShowQuestDetails.setClickListener(new ClickListener() {

					@Override
					public void click() {
						for (LevelButton button : buttonsToDisplayQuest) {
							// change colors to off
							button.buttonColor = Color.BLACK;
							button.setTextColor(Color.WHITE);
						}
						buttonToShowQuestDetails.buttonColor = Color.WHITE;
						buttonToShowQuestDetails.setTextColor(Color.BLACK);
						questToDisplayInAdventureLog = quest;

					}
				});

				if (questToDisplayInAdventureLog == quest) {
					buttonToShowQuestDetails.buttonColor = Color.WHITE;
					buttonToShowQuestDetails.setTextColor(Color.BLACK);
				}

				buttons.add(buttonToShowQuestDetails);
				buttonsToDisplayQuest.add(buttonToShowQuestDetails);
				questsDrawnInList++;

			}
		}

	}

	public void close() {
		showing = false;
	}

	public void drawStaticUI() {

		links.clear();

		// Black cover
		QuadUtils.drawQuad(Color.BLACK, 0, Game.windowWidth, 0, Game.windowHeight);

		// List of quests
		// int questsDrawnInList = 0;
		// for (Quest quest : Level.quests) {
		// if (quest.started) {
		// if (selectedQuest == null)
		// selectedQuest = quest;
		// if (quest == selectedQuest) {
		// // HIGHLIGHT
		// }
		//
		//
		// TextUtils.printTextWithImages(listX + listBorder,
		// listY + listBorder + questsDrawnInList * listItemHeight,
		// Integer.MAX_VALUE, true, false, links,
		// new Object[] { quest.name });
		// questsDrawnInList++;
		//
		// }
		// }

		// Content
		int questTextsDrawn = 0;
		if (questToDisplayInAdventureLog != null) {
			for (Object pieceOfInfo : questToDisplayInAdventureLog.info) {
				TextUtils.printTextWithImages(contentX + contentBorder,
						contentY + contentBorder + questTextsDrawn * listItemHeight, Integer.MAX_VALUE, true, true,
						links, new Object[] { pieceOfInfo });
				questTextsDrawn++;
			}
		}

		if (buttons.size() == 1) {
			TextUtils.printTextWithImages(0, 0, Integer.MAX_VALUE, true, false, links, new Object[] { "NO QUESTS" });
		} else {
		}

		// Buttons
		for (Button button : buttons) {
			button.draw();
		}
	}

	@Override
	public void scroll(float dragX, float dragY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drag(float dragX, float dragY) {
		// TODO Auto-generated method stub

	}

}
