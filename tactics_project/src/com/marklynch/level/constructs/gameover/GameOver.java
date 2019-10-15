package com.marklynch.level.constructs.gameover;

import java.util.concurrent.CopyOnWriteArrayList;

import com.marklynch.Game;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.Scrollable;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.ClickListener;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.ui.button.Link;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class GameOver implements Draggable, Scrollable {

	public boolean showing = false;

	int contentX;
	int contentY;

	transient static int bottomBorderHeight;

	public static CopyOnWriteArrayList<LevelButton> buttons = new CopyOnWriteArrayList<LevelButton>();

	public CopyOnWriteArrayList<Link> logLinks = new CopyOnWriteArrayList<Link>();
	public CopyOnWriteArrayList<Link> conversationLinks = new CopyOnWriteArrayList<Link>();
	public CopyOnWriteArrayList<Link> objectiveLinks = new CopyOnWriteArrayList<Link>();

	static LevelButton buttonClose;

	public static Texture youDied;

	Color translucentBlack = new Color(0f, 0f, 0f, 0.5f);

	public GameOver() {

		buttonClose = new LevelButton(Game.halfWindowWidth - 35f, bottomBorderHeight, 70f, 30f, "end_turn_button.png",
				"end_turn_button.png", "GO!", true, false, Color.BLACK, Color.WHITE, null);
		buttonClose.setClickListener(new ClickListener() {
			@Override
			public void click() {
				Game.level.player.remainingHealth = Game.level.player.totalHealth;
				Game.level.openCloseGameOver();
			}
		});
		buttons.add(buttonClose);
		resize();
	}

	public static void loadStaticImages() {
		youDied = ResourceUtils.getGlobalImage("you_died.png", false);
	}

	public void resize() {

		contentX = (int) (Game.windowWidth / 2 - 640 / 2);
		contentY = (int) (Game.windowHeight / 2 - 128 / 2);
		bottomBorderHeight = 384;

		buttonClose.updatePosition(Game.halfWindowWidth - 35f, bottomBorderHeight);

	}

	public void open() {
		resize();
		showing = true;

		buttons.clear();
		buttons.add(buttonClose);

	}

	public void close() {
		showing = false;
	}

	public void drawStaticUI() {

		// links.clear();

		// Black cover
		QuadUtils.drawQuad(translucentBlack, 0, 0, Game.windowWidth, Game.windowHeight);

		TextureUtils.drawTexture(youDied, contentX, contentY, contentX + 640, contentY + 128);

		// Buttons
		for (Button button : buttons) {
			button.draw();
		}
	}

	@Override
	public void scroll(float dragX, float dragY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drag(float dragX, float dragY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragDropped() {
		// TODO Auto-generated method stub
		
	}

}
