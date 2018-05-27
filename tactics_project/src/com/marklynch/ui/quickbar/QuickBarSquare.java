package com.marklynch.ui.quickbar;

import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextureUtils;

public class QuickBarSquare extends LevelButton {

	Object object;
	int index;
	int x1, y1, x2, y2;

	public QuickBarSquare(int index) {
		super(QuickBar.positionX + index * QuickBar.shortcutWidth, QuickBar.positionY, QuickBar.shortcutWidth,
				QuickBar.shortcutWidth, null, null, "", true, true, Color.TRANSPARENT, Color.WHITE, "BUTTON");
		this.index = index;
		x1 = QuickBar.positionX + index * QuickBar.shortcutWidth;
		y1 = QuickBar.positionY;
		x2 = QuickBar.positionX + (index + 1) * QuickBar.shortcutWidth;
		y2 = QuickBar.positionY + QuickBar.shortcutWidth;
	}

	public void drawStaticUI() {

		QuadUtils.drawQuad(Color.BLACK, x1, y1, x2, y2);
		TextureUtils.drawTexture(Square.WHITE_SQUARE, x1, y1, x2, y2);

		if (object == null) {

		} else if (object instanceof Power) {
			drawPower((Power) object);
		} else if (object instanceof GameObject) {
			drawGameObject((GameObject) object);
		}
	}

	public void drawPower(Power power) {
		TextureUtils.drawTexture(power.image, x1, y1, x2, y2);
	}

	public void drawGameObject(GameObject gameObject) {
		TextureUtils.drawTexture(gameObject.imageTexture, x1, y1, x2, y2);
	}

}
