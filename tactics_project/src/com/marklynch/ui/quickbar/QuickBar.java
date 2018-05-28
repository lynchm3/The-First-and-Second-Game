package com.marklynch.ui.quickbar;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.UserInputLevel;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;

public class QuickBar {

	public QuickBarSquare[] quickBarSquares = new QuickBarSquare[] { new QuickBarSquare(0), new QuickBarSquare(1),
			new QuickBarSquare(2), new QuickBarSquare(3), new QuickBarSquare(4), new QuickBarSquare(5),
			new QuickBarSquare(6), new QuickBarSquare(7), new QuickBarSquare(8), new QuickBarSquare(9) };
	ArrayList<LevelButton> buttons = new ArrayList<LevelButton>();
	public static int positionX = 428;
	public static int positionY = 8;
	public static int shortcutWidth = 32;

	public void drawStaticUI() {

		QuadUtils.drawQuad(Color.WHITE, positionX, positionY, positionX + Game.windowWidth, positionY + shortcutWidth);

		// for (int i = 0, j = 0; i < quickBarSquares.length && j <
		// Level.player.powers.size(); j++) {
		// quickBarSquares[i].setShortcut(null);
		// if (Level.player.powers.get(j).passive == false) {
		// quickBarSquares[i].setShortcut(Level.player.powers.get(j));
		// i++;
		// }
		// }

		for (QuickBarSquare quickBarSquare : quickBarSquares) {
			quickBarSquare.drawStaticUI();
		}

		if (UserInputLevel.draggableMouseIsOver instanceof QuickBarSquare) {
			((QuickBarSquare) UserInputLevel.draggableMouseIsOver).drawStaticUI();
		}
	}
	//
	// @Override
	// public void scroll(float dragX, float dragY) {
	// // System.out.println("SKILL TREE . SCROLL");
	// // drag(dragX, dragY);
	//
	// // zooming buttons? fuck...
	// }
	//
	// @Override
	// public void drag(float dragX, float dragY) {
	//
	// // this.offsetX -= dragX;
	// // this.offsetY -= dragY;
	//
	// // System.out.println("SKILL TREE . DRAG");
	//
	// // for (SkillTreeNode skillTreeNode : skillTreeNodes) {
	// //
	// // skillTreeNode.updatePosition(skillTreeNode.x + dragX, skillTreeNode.y -
	// // dragY);
	// // }
	//
	// // fixScroll();
	// // resize2();
	// }

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
