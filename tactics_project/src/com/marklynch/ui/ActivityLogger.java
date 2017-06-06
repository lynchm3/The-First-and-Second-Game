package com.marklynch.ui;

import java.util.Vector;

import com.marklynch.Game;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class ActivityLogger {
	public transient Vector<ActivityLog> logs = new Vector<ActivityLog>();
	public float width = 420;
	public float offset = 0;
	// public float

	public void drawStaticUI() {

		offset = 20;
		int totalLogsHeight = logs.size() * 20;
		offset = Game.windowHeight - totalLogsHeight;
		if (offset > 20)
			offset = 20;

		// Log
		QuadUtils.drawQuad(Color.BLACK, 0, width, 0, Game.windowHeight);

		// Log text
		for (int i = logs.size() - 1; i > -1; i--) {
			TextUtils.printTextWithImages(logs.get(i).contents, 20, offset + i * 20, Integer.MAX_VALUE, true);
		}

	}

	public boolean isMouseOver(int mouseX, int mouseY) {
		if (mouseX > 0 && mouseX < 0 + width && mouseY > 0 && mouseY < 0 + Game.windowHeight) {
			System.out.println("mouse over log!");
			return true;
		}
		return false;
	}

}
