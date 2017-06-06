package com.marklynch.ui;

import java.util.Vector;

import com.marklynch.Game;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class ActivityLogger {
	private transient Vector<ActivityLog> logs = new Vector<ActivityLog>();
	public float width = 420;
	public float offsetY = 0;
	// public float

	public void drawStaticUI() {

		// Log
		QuadUtils.drawQuad(Color.BLACK, 0, width, 0, Game.windowHeight);

		// Log text
		for (int i = logs.size() - 1; i > -1; i--) {
			TextUtils.printTextWithImages(logs.get(i).contents, 20, offsetY + i * 20, Integer.MAX_VALUE, true);
		}

	}

	public boolean isMouseOver(int mouseX, int mouseY) {
		if (mouseX > 0 && mouseX < 0 + width && mouseY > 0 && mouseY < 0 + Game.windowHeight) {
			return true;
		}
		return false;
	}

	public void addActivityLog(ActivityLog activityLog) {
		this.logs.add(activityLog);
		scrollToBottom();
	}

	public void scrollToBottom() {

		offsetY = 0;
		int totalLogsHeight = logs.size() * 20;
		offsetY = Game.windowHeight - totalLogsHeight;
		if (offsetY > 0)
			offsetY = 0;

	}

	public void removeLastActivityLog() {
		logs.remove(logs.lastElement());
	}

	public void drag(float dragY) {
		int totalLogsHeight = logs.size() * 20;
		this.offsetY -= dragY;
		if (offsetY >= 0) {
			offsetY = 0;
		} else if (offsetY < Game.windowHeight - totalLogsHeight) {
			offsetY = Game.windowHeight - totalLogsHeight;
		}
	}

}
