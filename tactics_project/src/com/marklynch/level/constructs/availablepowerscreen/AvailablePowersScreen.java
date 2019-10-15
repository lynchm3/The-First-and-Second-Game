package com.marklynch.level.constructs.availablepowerscreen;

import java.util.concurrent.CopyOnWriteArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.UserInputLevel;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.Scrollable;
import com.marklynch.ui.button.Button;
import com.marklynch.ui.button.LevelButton;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;

public class AvailablePowersScreen implements Draggable, Scrollable {

	static CopyOnWriteArrayList<PowerSquare> powerSquares = new CopyOnWriteArrayList<PowerSquare>();

	public boolean showing = false;

	// Close button
	public static CopyOnWriteArrayList<LevelButton> buttons = new CopyOnWriteArrayList<LevelButton>();
	static LevelButton buttonClose;

	public CopyOnWriteArrayList<PowerSquare> activateAtStart = new CopyOnWriteArrayList<PowerSquare>();

	public AvailablePowersScreen() {

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

//			if (power.passive == false) {
			PowerSquare powerSquare = new PowerSquare(power, offsetX, 64);
			powerSquares.add(powerSquare);
			offsetX += (128 + 64);
			buttons.add(powerSquare);
//			}
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

		for (PowerSquare powerSquare : powerSquares) {
			powerSquare.drawSquare();
		}

		Game.level.quickBar.drawStaticUI();

		if (UserInputLevel.draggableMouseIsOver instanceof PowerSquare) {
			((PowerSquare) UserInputLevel.draggableMouseIsOver).drawDragged();
		}

	}

	@Override
	public void scroll(float dragX, float dragY) {
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

	public Draggable getDraggable(float mouseX, float mouseY) {

		for (PowerSquare powerSquare : powerSquares) {
			if (powerSquare.calculateIfPointInBoundsOfButton(mouseX, mouseY)) {
				return powerSquare;
			}
		}
		return Level.skillTree;
	}

}
