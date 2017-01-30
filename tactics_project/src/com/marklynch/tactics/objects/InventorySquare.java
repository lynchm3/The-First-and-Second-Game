package com.marklynch.tactics.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import java.util.UUID;

import com.marklynch.Game;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Texture;

public class InventorySquare {

	public String guid = UUID.randomUUID().toString();

	public final int x;
	public final int y;
	public GameObject gameObject;
	public Inventory inventoryThisBelongsTo;

	// image
	public String imageTexturePath;
	public transient Texture imageTexture = null;

	public InventorySquare(int x, int y, String imagePath, Inventory inventoryThisBelongsTo) {
		super();
		this.x = x;
		this.y = y;
		this.imageTexturePath = imagePath;
		this.inventoryThisBelongsTo = inventoryThisBelongsTo;
	}

	public void loadImages() {
		this.imageTexture = getGlobalImage(imageTexturePath);

	}

	public void postLoad() {

	}

	public void drawStaticUI() {

		// square texture
		int squarePositionX = x * (int) Game.SQUARE_WIDTH;
		int squarePositionY = y * (int) Game.SQUARE_HEIGHT;
		TextureUtils.drawTexture(imageTexture, squarePositionX, squarePositionX + Game.SQUARE_WIDTH, squarePositionY,
				squarePositionY + Game.SQUARE_HEIGHT);

	}

	public void drawHighlight() {

		int squarePositionX = x * (int) Game.SQUARE_WIDTH;
		int squarePositionY = y * (int) Game.SQUARE_HEIGHT;
		TextureUtils.drawTexture(Game.level.gameCursor.imageTexture2, squarePositionX,
				squarePositionX + Game.SQUARE_WIDTH, squarePositionY, squarePositionY + Game.SQUARE_HEIGHT);

	}

	public void drawCursor() {
		// GL11.glPushMatrix();

		// GL11.glTranslatef(Game.windowWidth / 2, Game.windowHeight / 2, 0);
		// GL11.glScalef(Game.zoom, Game.zoom, 0);
		// GL11.glTranslatef(Game.dragX, Game.dragY, 0);
		// GL11.glTranslatef(-Game.windowWidth / 2, -Game.windowHeight / 2, 0);
		int squarePositionX = x * (int) Game.SQUARE_WIDTH;
		int squarePositionY = y * (int) Game.SQUARE_HEIGHT;

		TextureUtils.drawTexture(Game.level.gameCursor.cursor, squarePositionX, squarePositionX + Game.SQUARE_WIDTH,
				squarePositionY, squarePositionY + Game.SQUARE_HEIGHT);
		// GL11.glPopMatrix();
	}

	public String[] getDetails() {
		return new String[] { "" + x + " , " + y, "(Click again to dismiss)" };
	}

	@Override
	public String toString() {
		return "" + this.x + "," + this.y;

	}
}
