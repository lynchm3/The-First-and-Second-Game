package com.marklynch.ui;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class ActivityLogger implements Draggable, Scrollable {

	public transient ArrayList<ActivityLog> logs = new ArrayList<ActivityLog>();

	public float totalHeight = 0;
	public final static float width = 300;
	public final static float negativeWidth = -width;
	public final static float leftBorder = 20;
	public final static float rightBorder = 20;
	public final static float textWidth = width - leftBorder - rightBorder;
	public float x = 0;
	public float textOffsetY = 0;
	// public float

	public static Texture fadeTop;
	public static Texture fadeBottom;

	public void drawStaticUI() {

		if (x <= negativeWidth)
			return;

		// Log
		QuadUtils.drawQuad(Color.BLACK, x, 0, x + width, Game.windowHeight);

		float heightSoFar = 0;

		// Log text
		// for (int i = logs.size() - 1; i > -1; i--) {

		float drawY;
		float drawX = x + leftBorder;
		for (ActivityLog log : logs) {

			drawY = textOffsetY + heightSoFar;

			if (drawY < -20) {
			} else if (drawY > Game.windowHeight) {
			} else {

				TextUtils.printTextWithImages(drawX, drawY, textWidth, true, log.links, log.contents);
			}
			heightSoFar += log.height;
		}

		if (textOffsetY < 0) {
			TextureUtils.drawTexture(fadeTop, x, 0, x + width, 64);
		}

		float totalHeight = heightSoFar;
		if (textOffsetY + totalHeight > Game.windowHeight) {
			TextureUtils.drawTexture(fadeBottom, x, Game.windowHeight - 64, x + width, Game.windowHeight);
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
		totalHeight += activityLog.height;
		this.logs.add(activityLog);
		scrollToBottom();
	}

	public void scrollToBottom() {

		textOffsetY = 0;
		textOffsetY = Game.windowHeight - totalHeight;
		if (textOffsetY > 0)
			textOffsetY = 0;

	}

	public void removeLastActivityLog() {
		totalHeight -= logs.get(logs.size() - 1).height;
		logs.remove(logs.get(logs.size() - 1));

	}

	@Override
	public void drag(float dragX, float dragY) {
		this.textOffsetY -= dragY;
		if (textOffsetY >= 0) {
			textOffsetY = 0;
		} else if (textOffsetY < Game.windowHeight - totalHeight) {
			textOffsetY = Game.windowHeight - totalHeight;
		}
	}

	@Override
	public void scroll(float dragX, float dragY) {
		drag(dragX, dragY);
	}

	public static void loadStaticImages() {
		fadeTop = ResourceUtils.getGlobalImage("fade_top.png", false);
		fadeBottom = ResourceUtils.getGlobalImage("fade_bottom.png", false);

	}

}
