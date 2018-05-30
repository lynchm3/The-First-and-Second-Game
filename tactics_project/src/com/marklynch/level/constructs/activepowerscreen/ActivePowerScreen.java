package com.marklynch.level.constructs.activepowerscreen;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.Scrollable;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;

public class ActivePowerScreen implements Draggable, Scrollable {

	static ArrayList<PowerSquare> powerSquares = new ArrayList<PowerSquare>();

	public boolean showing = false;

	// Close button
	public static ArrayList<LevelButton> buttons = new ArrayList<LevelButton>();
	static LevelButton buttonClose;

	public ArrayList<PowerSquare> activateAtStart = new ArrayList<PowerSquare>();

	public ActivePowerScreen() {

	}

	public static void loadStaticImages() {
		PowerSquare.loadStaticImages();
	}

	public void resize() {
	}

	public void open() {

		float offsetX = 64;
		powerSquares.clear();
		for (Power power : Level.player.powers) {
			if (power.passive == false) {
				PowerSquare powerSquare = new PowerSquare(power, offsetX, 64);
				powerSquares.add(powerSquare);
				offsetX += (128 + 64);
				buttons.add(powerSquare);
			}
		}

		resize();
		generateLinks();
		showing = true;

	}

	public static void generateLinks() {

	}

	public void close() {
		showing = false;
	}

	public static Color background = new Color(0f, 0f, 0f, 0.75f);

	public void drawStaticUI() {

		// links.clear();

		// Black cover
		QuadUtils.drawQuad(background, 0, 0, Game.windowWidth, Game.windowHeight);

		for (Button button : buttons) {
			button.draw();
		}

		drawPowerSquare(0, 0, false);

		Game.level.quickBar.drawStaticUI();

	}

	public static void drawPowerSquare(int x, int y, boolean smallVersion) {

		for (PowerSquare powerSquare : powerSquares) {
			powerSquare.drawSquare();
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
		for (PowerSquare powerSquare : powerSquares) {

			// powerSquare.updatePosition(powerSquare.x + dragX, powerSquare.y - dragY);
			powerSquare.updatePosition(powerSquare.x + dragX, powerSquare.y);
		}
	}

	@Override
	public void dragDropped() {
		// TODO Auto-generated method stub

	}

}
