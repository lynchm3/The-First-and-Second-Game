package com.marklynch.level.constructs.adventurelog;

import java.util.ArrayList;

import com.marklynch.level.Level;
import com.marklynch.level.quest.Quest;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.Scrollable;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.utils.TextUtils;

public class AdventureLog implements Draggable, Scrollable {

	public boolean showing = false;
	public Quest selectedQuest = null;

	int leftBarX;
	int leftBarWidth;

	int contentX;

	int listItemHeight = 30;
	public ArrayList<LevelButton> buttons = new ArrayList<LevelButton>();

	public AdventureLog() {
		resize();
	}

	public void resize() {
		leftBarX = 0;
		leftBarWidth = 300;

		contentX = 300;

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
		int questsDrawnInList = 0;
		for (Quest quest : Level.quests) {
			if (quest.started) {
				TextUtils.printTextWithImages(leftBarX, questsDrawnInList * listItemHeight, Integer.MAX_VALUE, true,
						new Object[] { quest.name });
				questsDrawnInList++;
			}
		}

		if (questsDrawnInList == 0) {
			TextUtils.printTextWithImages(0, 0, Integer.MAX_VALUE, true, new Object[] { "NO QUESTS" });
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
