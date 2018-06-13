package com.marklynch.ui.quickbar;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.UserInputLevel;
import com.marklynch.ui.button.LevelButton;

public class QuickBar {

	public QuickBarSquare[] quickBarSquares = new QuickBarSquare[] { new QuickBarSquare(0), new QuickBarSquare(1),
			new QuickBarSquare(2), new QuickBarSquare(3), new QuickBarSquare(4), new QuickBarSquare(5),
			new QuickBarSquare(6), new QuickBarSquare(7), new QuickBarSquare(8), new QuickBarSquare(9) };
	ArrayList<LevelButton> buttons = new ArrayList<LevelButton>();
	public static int positionX;
	public static int positionY;
	public static final int shortcutWidth = 64;

	public QuickBar() {
		resize();
	}

	public void drawStaticUI() {

		// QuadUtils.drawQuad(Color.WHITE, positionX, positionY, positionX +
		// Game.windowWidth, positionY + shortcutWidth);

		// for (int i = 0, j = 0; i < quickBarSquares.length && j <
		// Level.player.powers.size(); j++) {
		// quickBarSquares[i].setShortcut(null);
		// if (Level.player.powers.get(j).passive == false) {
		// quickBarSquares[i].setShortcut(Level.player.powers.get(j));
		// i++;
		// }
		// }

		for (QuickBarSquare quickBarSquare : quickBarSquares) {
			quickBarSquare.drawBackground();
		}

		for (QuickBarSquare quickBarSquare : quickBarSquares) {
			quickBarSquare.drawShortcut();
		}

		if (UserInputLevel.draggableMouseIsOver instanceof QuickBarSquare) {
			((QuickBarSquare) UserInputLevel.draggableMouseIsOver).drawShortcut();
		}
	}

	public void resize() {

		positionX = (int) (Game.halfWindowWidth - 5 * shortcutWidth);
		positionY = (int) (Game.windowHeight - 64 - shortcutWidth);
		for (QuickBarSquare quickBarSquare : quickBarSquares) {
			quickBarSquare.resetPosition();
		}

	}

	// int y = 0;
	// int width = 1920;
	// int height = 128;

	public boolean isMouseOver(int mouseX, int mouseY) {

		if (mouseX > positionX && mouseX < positionX + Game.windowWidth && mouseY > positionY
				&& mouseY < positionY + shortcutWidth) {
			return true;
		}

		return false;
	}

}
