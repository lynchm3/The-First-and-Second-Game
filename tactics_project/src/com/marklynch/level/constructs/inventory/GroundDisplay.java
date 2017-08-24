package com.marklynch.level.constructs.inventory;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class GroundDisplay {

	public int squareGridWidthInSquares = 5;

	ArrayList<Square> squares = new ArrayList<Square>();
	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	int squaresX;
	int y;
	public transient ArrayList<GroundDisplaySquare> groundDisplaySquares = new ArrayList<GroundDisplaySquare>();
	transient private GroundDisplaySquare groundDisplaySquareMouseIsOver;

	public GroundDisplay(int x, int y) {
		this.squaresX = x;
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

	public void matchGameObjectsToSquares() {

		groundDisplaySquares.clear();

		int xIndex = 0;
		int yIndex = 0;

		for (GameObject gameObject : gameObjects) {
			GroundDisplaySquare groundDisplaySquare = new GroundDisplaySquare(xIndex, yIndex, null, this);
			groundDisplaySquare.gameObject = gameObject;
			groundDisplaySquares.add(groundDisplaySquare);
			xIndex++;
			if (xIndex == this.squareGridWidthInSquares) {
				xIndex = 0;
				yIndex++;
			}
		}
	}

	public void resize() {
		int xIndex = 0;
		int yIndex = 0;
		for (GroundDisplaySquare groundDisplaySquare : groundDisplaySquares) {
			groundDisplaySquare.xInGrid = xIndex;
			groundDisplaySquare.yInGrid = yIndex;
			xIndex++;
			if (xIndex == this.squareGridWidthInSquares) {
				xIndex = 0;
				yIndex++;
			}
		}
	}

	public void drawStaticUI() {

		TextUtils.printTextWithImages(900f, 8f, 300f, true,
				new Object[] { new StringWithColor("Items on the Ground", Color.WHITE) });

		for (GroundDisplaySquare groundDisplaySquare : groundDisplaySquares) {
			groundDisplaySquare.drawStaticUI();
		}

		// cursor
		if (this.groundDisplaySquareMouseIsOver != null && Game.buttonHoveringOver == null) {
			this.groundDisplaySquareMouseIsOver.drawCursor();
			this.groundDisplaySquareMouseIsOver.drawAction();
		}
	}

	public GroundDisplaySquare getGroundDisplaySquareMouseIsOver(float mouseXInPixels, float mouseYInPixels) {

		// Inventory sqr
		float offsetX = squaresX;
		float offsetY = y;
		float scroll = 0;

		int mouseXInSquares = (int) (((mouseXInPixels - offsetX) / Game.INVENTORY_SQUARE_WIDTH));
		int mouseYInSquares = (int) ((Game.windowHeight - mouseYInPixels - offsetY - scroll)
				/ Game.INVENTORY_SQUARE_HEIGHT);

		for (GroundDisplaySquare groundDisplaySquare : groundDisplaySquares) {
			if (groundDisplaySquare.xInGrid == mouseXInSquares && groundDisplaySquare.yInGrid == mouseYInSquares)
				return groundDisplaySquare;
		}

		return null;
	}

}
