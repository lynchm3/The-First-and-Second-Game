package com.marklynch.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.utils.TextureUtils;

public class InventorySquare extends Square {

	public transient GameObject gameObject;
	public transient Inventory inventoryThisBelongsTo;

	public int xInPixels = 0;
	public int yInPixels = 0;

	public InventorySquare(int x, int y, String imagePath, Inventory inventoryThisBelongsTo) {
		super(x, y, imagePath, 1, 1, null);

		this.showInventory = false;
		this.inventoryThisBelongsTo = inventoryThisBelongsTo;

		xInPixels = (int) inventoryThisBelongsTo.x + xInGrid * (int) Game.SQUARE_WIDTH;
		yInPixels = (int) inventoryThisBelongsTo.y + yInGrid * (int) Game.SQUARE_HEIGHT;
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
}
