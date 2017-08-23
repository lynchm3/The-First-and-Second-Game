package com.marklynch.level.constructs.inventory;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class GroundDisplay {

	public int widthInSquares = 1;
	public int heightInSquares = 6;

	ArrayList<Square> squares = new ArrayList<Square>();
	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	int x;
	int y;
	public transient GroundDisplaySquare[][] groundDisplaySquares = new GroundDisplaySquare[widthInSquares][heightInSquares];
	transient private GroundDisplaySquare groundDisplaySquareMouseIsOver;

	public GroundDisplay(int x, int y) {
		this.x = x;
		this.y = y;
		squares.add(Game.level.player.squareGameObjectIsOn);
		if (Game.level.player.squareGameObjectIsOn.getSquareAbove() != null)
			squares.add(Game.level.player.squareGameObjectIsOn.getSquareAbove());
		if (Game.level.player.squareGameObjectIsOn.getSquareToLeftOf() != null)
			squares.add(Game.level.player.squareGameObjectIsOn.getSquareToLeftOf());
		if (Game.level.player.squareGameObjectIsOn.getSquareToRightOf() != null)
			squares.add(Game.level.player.squareGameObjectIsOn.getSquareToRightOf());
		if (Game.level.player.squareGameObjectIsOn.getSquareBelow() != null)
			squares.add(Game.level.player.squareGameObjectIsOn.getSquareBelow());

		for (int i = 0; i < groundDisplaySquares[0].length; i++) {
			for (int j = 0; j < groundDisplaySquares.length; j++) {
				groundDisplaySquares[j][i] = new GroundDisplaySquare(j, i, null, this);
			}
		}

		refreshGameObjects();
	}

	public void refreshGameObjects() {
		this.gameObjects.clear();
		for (Square square : squares) {
			for (GameObject gameObject : square.inventory.gameObjects) {
				if (gameObject.fitsInInventory) {
					this.gameObjects.add(gameObject);
				}
			}
		}
		matchGameObjectsToSquares();
	}

	private void matchGameObjectsToSquares() {

		int index = 0;
		for (int i = 0; i < groundDisplaySquares[0].length; i++) {
			for (int j = 0; j < groundDisplaySquares.length; j++) {
				groundDisplaySquares[j][i].gameObject = null;
				if (index < gameObjects.size()) {
					groundDisplaySquares[j][i].gameObject = gameObjects.get(index);
					index++;
				}
			}
		}
	}

	public void drawStaticUI() {

		TextUtils.printTextWithImages(900f, 8f, 300f, true,
				new Object[] { new StringWithColor("Items on the Ground", Color.WHITE) });

		for (int i = 0; i < groundDisplaySquares[0].length; i++) {
			for (int j = 0; j < groundDisplaySquares.length; j++) {
				groundDisplaySquares[j][i].drawStaticUI();
			}
		}

		// cursor
		if (this.groundDisplaySquareMouseIsOver != null && Game.buttonHoveringOver == null) {
			this.groundDisplaySquareMouseIsOver.drawCursor();
			this.groundDisplaySquareMouseIsOver.drawAction();
		}
	}

	public GroundDisplaySquare getGroundDisplaySquareMouseIsOver(float mouseXInPixels, float mouseYInPixels) {

		// Inventory sqr
		float offsetX = x;
		float offsetY = y;
		float scroll = 0;

		float mouseXInSquares = (((mouseXInPixels - offsetX) / Game.INVENTORY_SQUARE_WIDTH));
		float mouseYInSquares = ((Game.windowHeight - mouseYInPixels - offsetY - scroll)
				/ Game.INVENTORY_SQUARE_HEIGHT);

		if (mouseXInSquares >= 0 && mouseXInSquares < groundDisplaySquares.length && mouseYInSquares >= 0
				&& mouseYInSquares < groundDisplaySquares[0].length) {

			return this.groundDisplaySquares[(int) mouseXInSquares][(int) mouseYInSquares];
		}

		return null;
	}

}
