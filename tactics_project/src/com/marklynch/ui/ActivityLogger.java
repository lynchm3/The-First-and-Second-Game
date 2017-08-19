package com.marklynch.ui;

import java.util.Vector;

import com.marklynch.Game;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class ActivityLogger implements Draggable, Scrollable {
	private transient Vector<ActivityLog> logs = new Vector<ActivityLog>();
	public float width = 420;
	public float x = 0;
	public float textOffsetY = 0;
	// public float

	public void drawStaticUI() {

		// Log
		QuadUtils.drawQuad(Color.BLACK, x, x + width, 0, Game.windowHeight);

		// Log text
		for (int i = logs.size() - 1; i > -1; i--) {
			TextUtils.printTextWithImages(logs.get(i).contents, x + 20, textOffsetY + i * 20, Integer.MAX_VALUE, true);
		}

	}

	public boolean isMouseOver(int mouseX, int mouseY) {
		if (Game.level.showLog == false)
			return false;

		if (mouseX > x && mouseX < x + width && mouseY > 0 && mouseY < 0 + Game.windowHeight) {
			return true;
		}
		return false;
	}

	public void addActivityLog(ActivityLog activityLog) {
		this.logs.add(activityLog);
		scrollToBottom();
	}

	public void scrollToBottom() {

		textOffsetY = 0;
		int totalLogsHeight = logs.size() * 20;
		textOffsetY = Game.windowHeight - totalLogsHeight;
		if (textOffsetY > 0)
			textOffsetY = 0;

	}

	public void removeLastActivityLog() {
		logs.remove(logs.lastElement());
	}

	@Override
	public void drag(float dragX, float dragY) {
		int totalLogsHeight = logs.size() * 20;
		this.textOffsetY -= dragY;
		if (textOffsetY >= 0) {
			textOffsetY = 0;
		} else if (textOffsetY < Game.windowHeight - totalLogsHeight) {
			textOffsetY = Game.windowHeight - totalLogsHeight;
		}
	}

	@Override
	public void scroll(float dragX, float dragY) {
		drag(dragX, dragY);
	}

}
