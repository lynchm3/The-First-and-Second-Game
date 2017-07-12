package com.marklynch.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;
import java.util.Vector;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.ArrayUtils;
import com.marklynch.utils.TextureUtils;

public class InventorySquare extends Square {

	public transient GameObject gameObject;
	public transient Inventory inventoryThisBelongsTo;

	public int xInPixels = 0;
	public int yInPixels = 0;

	public InventorySquare(int x, int y, String imagePath, Inventory inventoryThisBelongsTo) {
		super(x, y, imagePath, 1, 1, null, false);

		this.showInventory = false;
		this.inventoryThisBelongsTo = inventoryThisBelongsTo;

		xInPixels = Math.round(inventoryThisBelongsTo.x + xInGrid * Game.SQUARE_WIDTH);
		yInPixels = Math.round(inventoryThisBelongsTo.y + yInGrid * Game.SQUARE_HEIGHT);
	}

	@Override
	public void loadImages() {
		this.imageTexture = getGlobalImage(imageTexturePath);

	}

	@Override
	public void postLoad1() {

	}

	@Override
	public void postLoad2() {

	}

	public void drawStaticUI() {

		// square texture

		TextureUtils.drawTexture(imageTexture, xInPixels, xInPixels + Game.SQUARE_WIDTH, yInPixels,
				yInPixels + Game.SQUARE_HEIGHT);
		if (gameObject != null) {

			float drawWidth = Game.SQUARE_WIDTH;
			float drawHeight = Game.SQUARE_HEIGHT;
			float realTextureWidth = gameObject.imageTexture.getWidth();
			float realTextureHeight = gameObject.imageTexture.getHeight();
			if (realTextureWidth >= realTextureHeight) {// knife
				drawHeight = 64 * realTextureHeight / realTextureWidth;
			} else {
				drawWidth = 64 * realTextureWidth / realTextureHeight;
			}
			// TextureUtils.skipNormals = false;
			TextureUtils.drawTexture(gameObject.imageTexture, xInPixels, xInPixels + drawWidth, yInPixels,
					yInPixels + drawHeight);

			for (Effect effect : gameObject.activeEffectsOnGameObject) {
				TextureUtils.drawTexture(effect.imageTexture, 0.75f, xInPixels, xInPixels + Game.SQUARE_WIDTH,
						yInPixels, yInPixels + Game.SQUARE_HEIGHT);
			}

		}

	}

	@Override
	public void drawHighlight() {

		TextureUtils.drawTexture(Game.level.gameCursor.imageTexture2, xInPixels, xInPixels + Game.SQUARE_WIDTH,
				yInPixels, yInPixels + Game.SQUARE_HEIGHT);

	}

	@Override
	public void drawCursor() {
		TextureUtils.drawTexture(Game.level.gameCursor.cursor, xInPixels, xInPixels + Game.SQUARE_WIDTH, yInPixels,
				yInPixels + Game.SQUARE_HEIGHT);
	}

	@Override
	public void drawDefaultAction() {
		Action defaultAction = this.getDefaultActionForTheSquareOrObject(Game.level.player);
		if (defaultAction != null && defaultAction.image != null) {
			TextureUtils.drawTexture(defaultAction.image, xInPixels, xInPixels + Game.SQUARE_WIDTH, yInPixels,
					yInPixels + Game.SQUARE_HEIGHT);
		}
	}

	@Override
	public String toString() {
		return "" + this.xInGrid + "," + this.yInGrid;

	}

	public boolean calculateIfPointInBoundsOfSquare(float mouseX, float mouseY) {
		if (mouseX > xInPixels && mouseX < xInPixels + (int) Game.SQUARE_WIDTH && mouseY > yInPixels
				&& mouseY < yInPixels + (int) Game.SQUARE_HEIGHT) {
			return true;
		}
		return false;
	}

	@Override
	public Action getDefaultActionForTheSquareOrObject(Actor performer) {
		GameObject targetGameObject = this.gameObject;
		if (targetGameObject != null) {
			return targetGameObject.getDefaultActionInInventory(performer);
		}
		return null;
	}

	public ArrayList<Action> getAllActionsForTheSquareOrObject(Actor performer) {
		GameObject targetGameObject = this.gameObject;
		if (targetGameObject != null) {
			return targetGameObject.getAllActionsInInventory(performer);
		}
		return new ArrayList<Action>();
	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
		return null;
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();
		return actions;
	}

	public Vector<InventorySquare> getAllInventorySquaresAtDistance(float distance) {
		Vector<InventorySquare> squares = new Vector<InventorySquare>();
		if (distance == 0) {
			squares.addElement(this);
			return squares;
		}

		boolean xGoingUp = true;
		boolean yGoingUp = true;
		for (float i = 0, x = -distance, y = 0; i < distance * 4; i++) {
			if (ArrayUtils.inBounds(inventoryThisBelongsTo.inventorySquares, this.xInGrid + x, this.yInGrid + y)) {
				squares.add(inventoryThisBelongsTo.inventorySquares[this.xInGrid + (int) x][this.yInGrid + (int) y]);
			}

			if (xGoingUp) {
				if (x == distance) {
					xGoingUp = false;
					x--;
				} else {
					x++;
				}
			} else {
				if (x == -distance) {
					xGoingUp = true;
					x++;
				} else {
					x--;
				}
			}

			if (yGoingUp) {
				if (y == distance) {
					yGoingUp = false;
					y--;
				} else {
					y++;
				}
			} else {
				if (y == -distance) {
					yGoingUp = true;
					y++;
				} else {
					y--;
				}
			}

		}
		return squares;
	}
}
