package com.marklynch.ui.quickbar;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.TextureUtils;

public class QuickBar {

	ArrayList<Object> shortcuts = new ArrayList<Object>();
	int positionX = 428;
	int positionY = 8;
	int shortcutWidth = 32;

	public void drawStaticUI() {

		int shortcutsDrawn = 0;
		shortcuts.clear();
		shortcuts.addAll(Game.level.player.powers);
		for (Object shortcut : shortcuts) {
			if (shortcut instanceof Power) {
				drawPower((Power) shortcut, shortcutsDrawn);
			} else if (shortcut instanceof GameObject) {
				drawGameObject((GameObject) shortcut, shortcutsDrawn);
			}
			shortcutsDrawn++;
		}
	}

	public void drawPower(Power power, int index) {
		TextureUtils.drawTexture(power.image, positionX + index * shortcutWidth, positionY,
				positionX + index * shortcutWidth + shortcutWidth, positionY + shortcutWidth);
	}

	public void drawGameObject(GameObject gameObject, int index) {
		TextureUtils.drawTexture(gameObject.imageTexture, positionX + index * shortcutWidth, positionY,
				positionX + index * shortcutWidth + shortcutWidth, positionY + shortcutWidth);
	}

}
