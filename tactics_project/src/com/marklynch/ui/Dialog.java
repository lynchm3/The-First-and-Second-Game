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

		this.text = text;

		positiveButton = new LevelButton(0, 0, 100, 20, "end_turn_button.png", "end_turn_button.png",
				positiveButtonText, true, true, Color.WHITE, Color.BLACK, null);
		positiveButton.setClickListener(positiveClickListener);

		negativeButton = new LevelButton(0, 0, 100, 20, "end_turn_button.png", "end_turn_button.png",
				negativeButtonText, true, true, Color.WHITE, Color.BLACK, null);
		negativeButton.setClickListener(negativeClickListener);

		resize();

	}

	public void drawStaticUI() {

		QuadUtils.drawQuad(Color.BLACK, 0, 0, Game.windowWidth, Game.windowHeight);
		TextUtils.printTextWithImages(textX, textY, Integer.MAX_VALUE, false, null, Color.WHITE, 1f, text);

		positiveButton.draw();
		negativeButton.draw();

	}

	public void resize() {

		float[] textDimensions = TextUtils.getDimensions(Integer.MAX_VALUE, text);
		// textX = Game.halfWindowWidth - textDimensions[0] / 2;
		textX = Game.halfWindowWidth - 110;
		textY = Game.halfWindowHeight;

		positiveButton.x = Game.halfWindowWidth - 110;
		positiveButton.y = Game.halfWindowHeight + 100;

		negativeButton.x = Game.halfWindowWidth + 10;
		negativeButton.y = Game.halfWindowHeight + 100;

	}

}