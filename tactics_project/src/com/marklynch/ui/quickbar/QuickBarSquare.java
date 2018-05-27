package com.marklynch.ui.quickbar;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.Level.LevelMode;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.ActionUsePower;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.Scrollable;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextureUtils;

public class QuickBarSquare extends LevelButton implements Draggable, Scrollable {

	private Object shortcut;
	public int index;
	public int x1, y1, x2, y2;

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

		if (shortcut == null) {

		} else if (shortcut instanceof Power) {
			drawPower((Power) shortcut);
		} else if (shortcut instanceof GameObject) {
			drawGameObject((GameObject) shortcut);
		}
	}

	public void setShortcut(Object shortcut) {
		this.shortcut = shortcut;
		if (shortcut == null) {
			this.setClickListener(null);
		} else if (shortcut instanceof Power) {
			this.setClickListener(new ClickListener() {
				@Override
				public void click() {
					Power power = (Power) QuickBarSquare.this.shortcut;
					Level.pausePlayer();
					if (power.selectTarget) {
						Level.levelMode = LevelMode.LEVEL_MODE_CAST;
						Game.level.selectedPower = power.makeCopy(Level.player);
					} else {
						new ActionUsePower(Level.player, Level.player.squareGameObjectIsOn,
								power.makeCopy(Level.player)).perform();
					}
					Game.level.popupMenuObjects.clear();
					Game.level.popupMenuActions.clear();
				}
			});
		} else if (shortcut instanceof GameObject) {
			this.setClickListener(null);
		}
	}

	public void drawPower(Power power) {
		TextureUtils.drawTexture(power.image, x1, y1, x2, y2);
	}

	public void drawGameObject(GameObject gameObject) {
		TextureUtils.drawTexture(gameObject.imageTexture, x1, y1, x2, y2);
	}

	public Object getShortcut() {
		return shortcut;
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

}
