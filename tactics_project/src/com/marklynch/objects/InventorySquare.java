package com.marklynch.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;
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
			TextureUtils.drawTexture(gameObject.imageTexture, xInPixels, xInPixels + Game.SQUARE_WIDTH, yInPixels,
					yInPixels + Game.SQUARE_HEIGHT);
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

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
		return null;
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();
		return actions;
	}
}
