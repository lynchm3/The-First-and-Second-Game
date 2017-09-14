package com.marklynch.level.constructs.adventurelog;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.quest.Quest;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.Scrollable;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class AdventureLog implements Draggable, Scrollable {

	public boolean showing = false;
	public Quest selectedQuest = null;

	int listX;
	int listY;
	int listBorder;
	int listWidth;

	int contentX;
	int contentY;
	int contentBorder;

	int listItemHeight = 30;
	public ArrayList<LevelButton> buttons = new ArrayList<LevelButton>();

	public AdventureLog() {
		resize();
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

	}

	public void open() {
		// Sort quests by active, keep selectedQuest at top
		// And also sort by completed...
		resize();
		showing = true;
	}

	public void close() {
		showing = false;
	}

	public void drawStaticUI() {

		// Black cover
		QuadUtils.drawQuad(Color.BLACK, 0, Game.windowWidth, 0, Game.windowHeight);

		int questsDrawnInList = 0;
		for (Quest quest : Level.quests) {
			if (quest.started) {
				if (selectedQuest == null)
					selectedQuest = quest;
				if (quest == selectedQuest) {
					// HIGHLIGHT
				}
				TextUtils.printTextWithImages(listX + listBorder,
						listY + listBorder + questsDrawnInList * listItemHeight, Integer.MAX_VALUE, true,
						new Object[] { quest.name });
				questsDrawnInList++;

			}
		}

		int questTextsDrawn = 0;
		if (selectedQuest != null) {
			for (String textPart : selectedQuest.text) {
				TextUtils.printTextWithImages(contentX + contentBorder,
						contentY + contentBorder + questTextsDrawn * listItemHeight, Integer.MAX_VALUE, true,
						new Object[] { textPart });
			}
			questTextsDrawn++;
		}

		if (questsDrawnInList == 0) {
			TextUtils.printTextWithImages(0, 0, Integer.MAX_VALUE, true, new Object[] { "NO QUESTS" });
		} else {
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
