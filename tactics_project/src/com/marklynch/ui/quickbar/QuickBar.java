package com.marklynch.ui.quickbar;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.Scrollable;
import com.marklynch.ui.button.LevelButton;

public class QuickBar implements Draggable, Scrollable {

	QuickBarSquare[] quickBarSquareItems = new QuickBarSquare[] { new QuickBarSquare(0), new QuickBarSquare(1),
			new QuickBarSquare(2), new QuickBarSquare(3), new QuickBarSquare(4), new QuickBarSquare(5),
			new QuickBarSquare(6), new QuickBarSquare(7), new QuickBarSquare(8), new QuickBarSquare(9) };
	ArrayList<LevelButton> buttons = new ArrayList<LevelButton>();
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

	@Override
	public void scroll(float dragX, float dragY) {
		// System.out.println("SKILL TREE . SCROLL");
		// drag(dragX, dragY);

		// zooming buttons? fuck...
	}

	@Override
	public void drag(float dragX, float dragY) {
		// this.offsetX -= dragX;
		// this.offsetY -= dragY;

		// System.out.println("SKILL TREE . DRAG");

		// for (SkillTreeNode skillTreeNode : skillTreeNodes) {
		//
		// skillTreeNode.updatePosition(skillTreeNode.x + dragX, skillTreeNode.y -
		// dragY);
		// }

		// fixScroll();
		// resize2();
	}

	int x = 0;
	int y = 0;
	int width = 1920;
	int height = 128;

	public boolean isMouseOver(int mouseX, int mouseY) {
		if (Game.level.showLog == false)
			return false;

		if (mouseX > x && mouseX < x + width && mouseY > 0 && mouseY < 0 + Game.windowHeight) {
			return true;
		}

		return false;
	}

}
