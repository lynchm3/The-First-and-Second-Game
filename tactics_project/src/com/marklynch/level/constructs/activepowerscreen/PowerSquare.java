package com.marklynch.level.constructs.activepowerscreen;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.Level.LevelMode;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.ActionUsePower;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.TextUtils;
import com.marklynch.utils.TextureUtils;

public class PowerSquare extends LevelButton {

	// public String name;
	// public String description;
	public float x1, y1, x2, y2;
	public float textX, textY;
	public static float width = 128;
	public float textWidth, textBarWidth, textBarX1, textBarY1, textBarX2, textBarY2;
	public float textBorderX = 4;
	public float textBarHeight = 24;
	private Power power;

	public PowerSquare(Power power, float x, float y) {
		super(x, y, width, width, null, null, "", true, true, Color.TRANSPARENT, Color.WHITE, "BUTTON");
		this.x1 = x;
		this.y1 = y;
		this.x2 = x + width;
		this.y2 = y + width;
		this.power = power;

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
					new ActionUsePower(Level.player, Level.player.squareGameObjectIsOn, power.makeCopy(Level.player))
							.perform();
				}
				Game.level.popupMenuObjects.clear();
				Game.level.popupMenuActions.clear();
				Game.level.openCloseActivePowerScreen();

			}
		});
	}

	public void drawSquare() {
		TextureUtils.drawTexture(Square.WHITE_SQUARE, x1, y1, x2, y2);
		TextureUtils.drawTexture(power.image, x1, y1, x2, y2);
		QuadUtils.drawQuad(Color.BLACK, textBarX1, textBarY1, textBarX2, textBarY2);
		TextUtils.printTextWithImages(textX, textY, Integer.MAX_VALUE, false, null, Color.WHITE, power.name);
	}

	public static void loadStaticImages() {

	}
}
