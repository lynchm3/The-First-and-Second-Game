package com.marklynch.ui.quickbar;

import com.marklynch.Game;

public class QuickBar {

	QuickBarSquare[] quickBarSquareItems = new QuickBarSquare[] { new QuickBarSquare(0), new QuickBarSquare(1),
			new QuickBarSquare(2), new QuickBarSquare(3), new QuickBarSquare(4), new QuickBarSquare(5),
			new QuickBarSquare(6), new QuickBarSquare(7), new QuickBarSquare(8), new QuickBarSquare(9) };
	public static int positionX = 428;
	public static int positionY = 8;
	public static int shortcutWidth = 32;

	public void drawStaticUI() {

		for (int i = 0; i < quickBarSquareItems.length && i < Game.level.player.powers.size(); i++) {
			quickBarSquareItems[i].object = null;
			quickBarSquareItems[i].object = Game.level.player.powers.get(i);
		}

		for (QuickBarSquare quickBarSquare : quickBarSquareItems) {
			quickBarSquare.drawStaticUI();
		}
	}

}
