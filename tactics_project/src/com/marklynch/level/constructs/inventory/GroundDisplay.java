package com.marklynch.level.constructs.inventory;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Openable;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.Scrollable;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;

public class GroundDisplay implements Draggable, Scrollable {

	int x, y;

	ArrayList<ArrayList<GameObject>> stacks = new ArrayList<ArrayList<GameObject>>(GameObject.class);

	public int squareGridWidthInSquares = 5;

	ArrayList<Square> squares = new ArrayList<Square>(Square.class);
	int squaresX;
	float squaresY;
	public transient ArrayList<GroundDisplaySquare> groundDisplaySquares = new ArrayList<GroundDisplaySquare>(
			GroundDisplaySquare.class);
	transient private GroundDisplaySquare groundDisplaySquareMouseIsOver;

	public static final String stringEmpty = "Nothing nearby";
	public static final int lengthEmpty = Game.smallFont.getWidth(stringEmpty);

	public GroundDisplay(int x, int y) {
		this.squaresX = x;
		this.squaresY = y;
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
		matchStacksToSquares();
		resize2();
	}

	public void matchStacksToSquares() {

		x = 0;
		y = 0;
		groundDisplaySquares.clear();
		for (Square square : squares) {
			matchStacksToSquaresForInventory(square.inventory);
			for (GameObject gameObject : square.inventory.gameObjects) {
				if (gameObject.fitsInInventory) {

				} else if (gameObject.canContainOtherObjects && gameObject.showInventoryInGroundDisplay) {
					if (gameObject instanceof Actor) {

					} else if (gameObject instanceof Openable) {
						Openable openable = (Openable) gameObject;
						if (!openable.locked) {
							openable.open();
							matchStacksToSquaresForInventory(gameObject.inventory);
						}
					} else {
						matchStacksToSquaresForInventory(gameObject.inventory);
					}
				}
			}
		}
	}

	public void matchStacksToSquaresForInventory(Inventory inventory) {

		for (ArrayList<GameObject> stack : inventory.allStacks) {
			matchStackToSquare(stack);
		}
	}

	public void matchStackToSquare(ArrayList<GameObject> stack) {

		GroundDisplaySquare inventorySquare = new GroundDisplaySquare(0, 0, null, this);
		inventorySquare.stack = stack;
		groundDisplaySquares.add(inventorySquare);

		x++;
	}

	public void resize2() {
		int xIndex = 0;
		int yIndex = 0;
		for (InventorySquare inventorySquare : groundDisplaySquares) {
			inventorySquare.xInGrid = xIndex;
			inventorySquare.yInGrid = yIndex;
			inventorySquare.xInPixels = Math
					.round(this.squaresX + inventorySquare.xInGrid * Game.INVENTORY_SQUARE_WIDTH);
			inventorySquare.yInPixels = Math
					.round(this.squaresY + inventorySquare.yInGrid * Game.INVENTORY_SQUARE_HEIGHT);
			xIndex++;
			if (xIndex == this.squareGridWidthInSquares) {
				xIndex = 0;
				yIndex++;
			}
		}
	}

	public void drawStaticUI() {

		drawBorder();
	}

	public void drawSquares() {

		for (GroundDisplaySquare groundDisplaySquare : groundDisplaySquares) {
			groundDisplaySquare.drawStaticUI();
		}

	}

	public void drawBorder() {
		QuadUtils.drawQuad(Inventory.inventoryAreaColor, squaresX, Inventory.topBorderHeight,
				squaresX + Inventory.squaresAreaWidth, Inventory.topBorderHeight + Inventory.squaresAreaHeight);
	}

	public void drawText() {

		TextUtils.printTextWithImages(this.squaresX, Inventory.inventoryNamesY, 300f, true, null, Color.WHITE,
				new Object[] { new StringWithColor("Items Nearby", Color.WHITE) });

	}

	public GroundDisplaySquare getGroundDisplaySquareMouseIsOver(float mouseXInPixels, float mouseYInPixels) {

		// Inventory sqr
		float offsetX = squaresX;
		float offsetY = squaresY;
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

	@Override
	public void scroll(float dragX, float dragY) {
		drag(dragX, dragY);
	}

	@Override
	public void drag(float dragX, float dragY) {
		this.squaresY -= dragY;
		fixScroll();
		resize2();
	}

	public void fixScroll() {

		// int stackCount =

		int totalSquaresHeight = (int) ((groundDisplaySquares.size() / squareGridWidthInSquares)
				* Game.INVENTORY_SQUARE_HEIGHT);
		if (totalSquaresHeight < Game.windowHeight - Inventory.bottomBorderHeight - Inventory.topBorderHeight) {
			this.squaresY = Inventory.squaresBaseY;
		} else if (this.squaresY < -(totalSquaresHeight - (Game.windowHeight - Inventory.bottomBorderHeight))) {
			this.squaresY = -(totalSquaresHeight - (Game.windowHeight - Inventory.bottomBorderHeight));
		} else if (this.squaresY > Inventory.squaresBaseY) {
			this.squaresY = Inventory.squaresBaseY;
		}

	}

	@Override
	public void dragDropped() {

	}

}
