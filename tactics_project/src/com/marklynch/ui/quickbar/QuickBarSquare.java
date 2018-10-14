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
import com.marklynch.ui.button.Tooltip;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

public class QuickBarSquare extends LevelButton implements Draggable, Scrollable {

	private Object shortcut;
	public int index;
	public float x1, y1, x2, y2;
	public float dragX = 0, dragY = 0;
	public QuickBarSquare tempSwap;

	public QuickBarSquare(int index) {
		super(QuickBar.positionX + index * QuickBar.shortcutWidth, QuickBar.positionY, QuickBar.shortcutWidth,
				QuickBar.shortcutWidth, null, null, "", true, true, Color.TRANSPARENT, Color.WHITE, "BUTTON");
		this.index = index;
		resetPosition();
	}

	public void resetPosition() {
		updatePosition(QuickBar.positionX + index * QuickBar.shortcutWidth, QuickBar.positionY);
	}

	public void drawBackground() {

		// if (shortcut == null) {
		// return;
		// }

		QuadUtils.drawQuad(Color.BLACK, x1, y1, x2, y2);
		TextureUtils.drawTexture(Square.WHITE_SQUARE, x1, y1, x2, y2);
	}

	public void drawShortcut(String hotkey) {

		if (shortcut instanceof Power) {
			drawPower((Power) shortcut);
		} else if (shortcut instanceof GameObject) {
			drawGameObject((GameObject) shortcut);
		}

		if (hotkey != null) {
			QuadUtils.drawQuad(Color.WHITE, x1 + 8, y1 + 4, x1 + 20, y1 + 24);
			TextUtils.printTextWithImages(x1 + 8, y1 + 4, Integer.MAX_VALUE, false, null, Color.BLACK, hotkey);
		}
		// (float posX, float posY, float maxWidth, boolean wrap, ArrayList<Link> links,
		// Color defaultColor, Object... contents)
	}

	public void setShortcut(Object shortcut) {
		this.shortcut = shortcut;
		// this.setTooltipText(this.shortcut);
		this.tooltips.clear();
		this.tooltips.add(new Tooltip(false, Tooltip.WHITE, this.shortcut));
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
						new ActionUsePower(Level.player, Game.gameObjectMouseIsOver, Level.player.squareGameObjectIsOn,
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

		if (tempSwap != null) {
			TextureUtils.drawTexture(power.image, tempSwap.x1, tempSwap.y1, tempSwap.x2, tempSwap.y2);

		} else {
			TextureUtils.drawTexture(power.image, x1 + dragX, y1 + dragY, x2 + dragX, y2 + dragY);
		}
	}

	public void drawGameObject(GameObject gameObject) {
		TextureUtils.drawTexture(gameObject.imageTexture, x1 + dragX, y1 + dragY, x2 + dragX, y2 + dragY);
	}

	public Object getShortcut() {
		return shortcut;
	}

	@Override
	public void scroll(float dragX, float dragY) {
	}

	@Override
	public void drag(float drawOffsetX, float dragOffsetY) {

		this.dragX = this.dragX + drawOffsetX;
		this.dragY = this.dragY - dragOffsetY;

		///

		float centerX = x1 + this.dragX + QuickBar.shortcutWidth / 2f;
		float centerY = y1 + this.dragY + QuickBar.shortcutWidth / 2f;

		for (QuickBarSquare quickBarSquare : Game.level.quickBar.quickBarSquares) {
			if (quickBarSquare != this && quickBarSquare.calculateIfPointInBoundsOfButton(centerX, centerY)) {
				quickBarSquare.tempSwap = this;
			} else {
				quickBarSquare.tempSwap = null;
			}
		}
	}

	@Override
	public void updatePosition(float x, float y) {

		this.x = x;
		this.y = y;

		realX = x;
		if (this.xFromLeft == false)
			realX = Game.windowWidth - x;

		realY = y;
		if (this.yFromTop == false)
			realY = Game.windowHeight - y;
		x1 = x;
		y1 = y;
		x2 = x + QuickBar.shortcutWidth;
		y2 = y + QuickBar.shortcutWidth;

		dragX = 0;
		dragY = 0;

	}

	@Override
	public void dragDropped() {

		float centerX = x1 + dragX + QuickBar.shortcutWidth / 2f;
		float centerY = y1 + dragY + QuickBar.shortcutWidth / 2f;

		QuickBarSquare quickBarSquareToSwapWith = null;
		for (QuickBarSquare quickBarSquare : Game.level.quickBar.quickBarSquares) {
			if (quickBarSquare != this && quickBarSquare.calculateIfPointInBoundsOfButton(centerX, centerY)) {

				quickBarSquareToSwapWith = quickBarSquare;
			}

			quickBarSquare.tempSwap = null;
		}

		if (quickBarSquareToSwapWith == null) {
			if (Game.level.quickBar.isMouseOver((int) centerX, (int) centerY)) {

			} else {
				setShortcut(null);
			}
		} else {
			Object tempShortcut = this.shortcut;
			setShortcut(quickBarSquareToSwapWith.shortcut);
			quickBarSquareToSwapWith.setShortcut(tempShortcut);
		}
		resetPosition();
	}

}
