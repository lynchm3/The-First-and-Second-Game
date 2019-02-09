package com.marklynch.level.constructs.availablepowerscreen;

import org.lwjgl.input.Mouse;

import com.marklynch.Game;
import com.marklynch.actions.ActionUsePower;
import com.marklynch.level.Level;
import com.marklynch.level.Level.LevelMode;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.squares.Square;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.Scrollable;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.ui.quickbar.QuickBarSquare;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

public class PowerSquare extends LevelButton implements Draggable, Scrollable {

	// public String name;
	// public String description;
	public float x1, y1, x2, y2;
	public float textX, textY;
	public static float width = 128;
	public float textWidth, textBarWidth, textBarX1, textBarY1, textBarX2, textBarY2;
	public float textBorderX = 4;
	public float textBarHeight = 24;
	private Power power;

	public float dragX = 0, dragY = 0;
	public static final float draggedPowerWidth = 64;
	public float draggedPowerHalfWidth = draggedPowerWidth / 2;

	public PowerSquare(Power power, float x, float y) {
		super(x, y, width, width, null, null, "", true, true, Color.TRANSPARENT, Color.WHITE, "BUTTON");
		this.power = power;
		setLocation(x, y);

		this.setClickListener(new ClickListener() {
			@Override
			public void click() {
				powerClicked(power);

			}
		});

	}

	public static void powerClicked(Power power) {
		if (power.passive) {
			power.toggledOn = !power.toggledOn;
		} else if (power.selectTarget) {
			Level.pausePlayer();
			if (Level.availablePowerScreen.showing)
				Level.availablePowerScreen.close();
			Level.levelMode = LevelMode.LEVEL_MODE_CAST;
			Game.level.selectedPower = power.makeCopy(Level.player);
		} else {
			Level.pausePlayer();
			if (Level.availablePowerScreen.showing)
				Level.availablePowerScreen.close();
			new ActionUsePower(Level.player, Game.gameObjectMouseIsOver, Level.player.squareGameObjectIsOn,
					power.makeCopy(Level.player), true).perform();
		}
		Game.level.popupMenuObjects.clear();
		Game.level.popupMenuActions.clear();
	}

	private void setLocation(float x, float y) {
		this.x1 = x;
		this.y1 = y;
		this.x2 = x + width;
		this.y2 = y + width;

		textWidth = Game.smallFont.getWidth(power.name);

		textX = x + (width / 2 - textWidth / 2);
		textY = y + width / 2 - 10;

		textBarWidth = Game.smallFont.getWidth(power.name) + textBorderX * 2;
		textBarX1 = textX - textBorderX;
		textBarY1 = textY;
		textBarX2 = textBarX1 + textBarWidth;
		textBarY2 = textBarY1 + textBarHeight;

	}

	public void init() {

		this.setClickListener(new ClickListener() {

			@Override
			public void click() {
				Level.pausePlayer();
				if (power.selectTarget) {
					Level.levelMode = LevelMode.LEVEL_MODE_CAST;
					Game.level.selectedPower = power.makeCopy(Level.player);
				} else {
					new ActionUsePower(Level.player, Game.gameObjectMouseIsOver, Level.player.squareGameObjectIsOn,
							power.makeCopy(Level.player), true).perform();
				}
				Game.level.popupMenuObjects.clear();
				Game.level.popupMenuActions.clear();
				Game.level.openCloseAvailablePowerScreen();

			}
		});
	}

	public void drawSquare() {
		TextureUtils.drawTexture(Square.WHITE_SQUARE, x1, y1, x2, y2);
		TextureUtils.drawTexture(power.image, x1, y1, x2, y2);
		QuadUtils.drawQuad(Color.BLACK, textBarX1, textBarY1, textBarX2, textBarY2);
		TextUtils.printTextWithImages(textX, textY, Integer.MAX_VALUE, false, null, Color.WHITE, power.name);
	}

	public void drawPower() {
		TextureUtils.drawTexture(this.power.image, x1, y1, x2, y2);
	}

	public void drawDragged() {

//		if (power.passive)
//			return;
		// TextureUtils.drawTexture(this.power.image, x1 + dragX, y1 + dragY, x2 +
		// dragX, y2 + dragY);
		TextureUtils.drawTexture(this.power.image, Mouse.getX() - draggedPowerHalfWidth,
				Game.windowHeight - Mouse.getY() - draggedPowerHalfWidth, Mouse.getX() + draggedPowerHalfWidth,
				Game.windowHeight - Mouse.getY() + draggedPowerHalfWidth);
	}

	@Override
	public void scroll(float dragX, float dragY) {

	}

	@Override
	public void drag(float drawOffsetX, float dragOffsetY) {

//		if (power.passive)
//			return;

		this.dragX = this.dragX + drawOffsetX;
		this.dragY = this.dragY - dragOffsetY;

		float centerX = this.x + this.dragX;
		float centerY = this.y + this.dragY;

		for (QuickBarSquare quickBarSquare : Game.level.quickBar.quickBarSquares) {
			if (quickBarSquare.calculateIfPointInBoundsOfButton(centerX, centerY)) {
				// quickBarSquare.tempSwap = this;
			} else {
				// quickBarSquare.tempSwap = null;
			}
		}
	}

	@Override
	public void dragDropped() {

//		if (power.passive)
//			return;

		float centerX = Mouse.getX();
		float centerY = Game.windowHeight - Mouse.getY();

		QuickBarSquare quickBarSquareToSwapWith = null;
		for (QuickBarSquare quickBarSquare : Game.level.quickBar.quickBarSquares) {
			if (quickBarSquare.calculateIfPointInBoundsOfButton(centerX, centerY)) {
				quickBarSquareToSwapWith = quickBarSquare;
			}
			quickBarSquare.tempSwap = null;
		}

		if (quickBarSquareToSwapWith == null) {

		} else {
			quickBarSquareToSwapWith.setShortcut(this.power);
		}

		this.dragX = 0;
		this.dragY = 0;
	}

	public static void loadStaticImages() {
		// TODO Auto-generated method stub

	}
}
