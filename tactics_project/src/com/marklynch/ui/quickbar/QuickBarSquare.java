package com.marklynch.ui.quickbar;

import com.marklynch.level.constructs.power.Power;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.TextureUtils;

public class QuickBarSquare {

	Object object;
	int index;

	public QuickBarSquare(int index) {
		this.index = index;
	}

	public void drawStaticUI() {
		if (object == null) {

		} else if (object instanceof Power) {
			drawPower((Power) object);
		} else if (object instanceof GameObject) {
			drawGameObject((GameObject) object);
		}
	}

	public void drawPower(Power power) {
		TextureUtils.drawTexture(power.image, QuickBar.positionX + index * QuickBar.shortcutWidth, QuickBar.positionY,
				QuickBar.positionX + index * QuickBar.shortcutWidth + QuickBar.shortcutWidth,
				QuickBar.positionY + QuickBar.shortcutWidth);
	}

	public void drawGameObject(GameObject gameObject) {
		TextureUtils.drawTexture(gameObject.imageTexture, QuickBar.positionX + index * QuickBar.shortcutWidth,
				QuickBar.positionY, QuickBar.positionX + index * QuickBar.shortcutWidth + QuickBar.shortcutWidth,
				QuickBar.positionY + QuickBar.shortcutWidth);
	}

}
