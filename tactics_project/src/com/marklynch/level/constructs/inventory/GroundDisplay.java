package com.marklynch.level.constructs.inventory;

import java.util.ArrayList;
import java.util.HashMap;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Gold;
import com.marklynch.objects.Openable;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.Draggable;
import com.marklynch.ui.Scrollable;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.StringWithColor;
import com.marklynch.utils.TextUtils;

import mdesl.graphics.Color;

public class GroundDisplay implements Draggable, Scrollable {

	public int squareGridWidthInSquares = 5;

	ArrayList<Square> squares = new ArrayList<Square>();
	ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	int squaresX;
	float squaresY;
	public transient ArrayList<GroundDisplaySquare> groundDisplaySquares = new ArrayList<GroundDisplaySquare>();
	transient private GroundDisplaySquare groundDisplaySquareMouseIsOver;

	public static final String stringEmpty = "Nothing nearby";
	public static final int lengthEmpty = Game.font.getWidth(stringEmpty);

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
		this.gameObjects.clear();
		for (Square square : squares) {
			for (GameObject gameObject : square.inventory.gameObjects) {
				if (gameObject.fitsInInventory) {
					this.gameObjects.add(gameObject);
				} else if (gameObject.canContainOtherObjects) {
					if (gameObject instanceof Actor) {

					} else if (gameObject instanceof Openable) {
						Openable openable = (Openable) gameObject;
						if (!openable.locked) {
							openable.open();
							for (GameObject gameObjectInContainer : gameObject.inventory.gameObjects) {
								this.gameObjects.add(gameObjectInContainer);
							}
						}
					} else {
						for (GameObject gameObjectInContainer : gameObject.inventory.gameObjects) {
							this.gameObjects.add(gameObjectInContainer);
						}
					}
				}
			}
		}
		matchGameObjectsToSquares();
	}

	public static HashMap<Integer, Integer> itemTypeCount = new HashMap<Integer, Integer>();
	public static HashMap<Integer, Integer> illegalItemTypeCount = new HashMap<Integer, Integer>();

	public void matchGameObjectsToSquares() {

		itemTypeCount.clear();
		illegalItemTypeCount.clear();
		groundDisplaySquares.clear();

		int xIndex = 0;
		int yIndex = 0;

		for (GameObject gameObject : gameObjects) {

			if (gameObject.value == 0 && gameObject instanceof Gold)
				continue;

			if (gameObject.value == 0 && gameObject instanceof Gold)
				continue;

			// Legal items
			if (gameObject.owner == null || gameObject.owner == Game.level.player) {
				if (itemTypeCount.containsKey(gameObject.templateId)) {
					itemTypeCount.put(gameObject.templateId, itemTypeCount.get(gameObject.templateId) + 1);
				} else {
					GroundDisplaySquare inventorySquare = new GroundDisplaySquare(xIndex, yIndex, null, this);
					inventorySquare.gameObject = gameObject;
					groundDisplaySquares.add(inventorySquare);
					itemTypeCount.put(gameObject.templateId, 1);
					xIndex++;
					if (xIndex == this.squareGridWidthInSquares) {
						xIndex = 0;
						yIndex++;
					}
				}

			} else {// Illegal items
				if (illegalItemTypeCount.containsKey(gameObject.templateId)) {
					illegalItemTypeCount.put(gameObject.templateId,
							illegalItemTypeCount.get(gameObject.templateId) + 1);
				} else {
					GroundDisplaySquare inventorySquare = new GroundDisplaySquare(xIndex, yIndex, null, this);
					inventorySquare.gameObject = gameObject;
					groundDisplaySquares.add(inventorySquare);
					illegalItemTypeCount.put(gameObject.templateId, 1);
					xIndex++;
					if (xIndex == this.squareGridWidthInSquares) {
						xIndex = 0;
						yIndex++;
					}
				}
			}
		}
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
		QuadUtils.drawQuad(Inventory.inventoryAreaColor, squaresX, squaresX + Inventory.squaresWidth, this.squaresY,
				this.squaresY + Inventory.squaresHeight);
	}

	public void drawText() {

		TextUtils.printTextWithImages(this.squaresX, Inventory.inventoryNamesY, 300f, true, false, null,
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
		int totalSquaresHeight = (int) ((gameObjects.size() / squareGridWidthInSquares) * Game.INVENTORY_SQUARE_HEIGHT);
		if (totalSquaresHeight < Game.windowHeight - Inventory.bottomBorderHeight - Inventory.topBorderHeight) {
			this.squaresY = Inventory.squaresBaseY;
		} else if (this.squaresY < -(totalSquaresHeight - (Game.windowHeight - Inventory.bottomBorderHeight))) {
			this.squaresY = -(totalSquaresHeight - (Game.windowHeight - Inventory.bottomBorderHeight));
		} else if (this.squaresY > Inventory.squaresBaseY) {
			this.squaresY = Inventory.squaresBaseY;
		}

	}

}
