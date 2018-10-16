package com.marklynch.ui;

import com.marklynch.Game;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;

public class Dialog {

	public String text;
	public float textX, textY;
	public LevelButton positiveButton;
	public LevelButton negativeButton;

	public Dialog(String text, String positiveButtonText, String negativeButtonText,
			ClickListener positiveClickListener, ClickListener negativeClickListener) {

		resize();

	}

	public void drawStaticUI() {

		QuadUtils.drawQuad(Color.BLACK, 0, 0, Game.windowWidth, Game.windowHeight);
		TextUtils.printTextWithImages(textX, textY, Integer.MAX_VALUE, false, null, Color.WHITE, text);

		positiveButton.draw();
		negativeButton.draw();

	}

	public void resize() {

		float[] textDimensions = TextUtils.getDimensions(Integer.MAX_VALUE, text);
		textX = Game.halfWindowWidth - textDimensions[0] / 2;
		textY = Game.halfWindowHeight;

	}

}
