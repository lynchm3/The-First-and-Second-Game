package com.marklynch.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Texture;

public class Wall extends GameObject {

	public boolean connectedTop;
	public boolean connectedTopRight;
	public boolean connectedRight;
	public boolean connectedBottomRight;
	public boolean connectedBottom;
	public boolean connectedBottomLeft;
	public boolean connectedLeft;
	public boolean connectedTopLeft;

	public Texture textureTop;
	public Texture textureTopRight;
	public Texture textureRight;
	public Texture textureBottomRight;
	public Texture textureBottom;
	public Texture textureBottomLeft;
	public Texture textureLeft;
	public Texture textureTopLeft;

	public Wall(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, float widthRatio, float heightRatio) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, widthRatio, heightRatio);
		loadImages();
	}

	@Override
	public void loadImages() {
		super.loadImages();
		textureTop = getGlobalImage("wall_top.png");
		textureTopRight = getGlobalImage("wall_top_right.png");
		textureRight = getGlobalImage("wall_right.png");
		textureBottomRight = getGlobalImage("wall_bottom_right.png");
		textureBottom = getGlobalImage("wall_bottom.png");
		textureBottomLeft = getGlobalImage("wall_bottom_left.png");
		textureLeft = getGlobalImage("wall_left.png");
		textureTopLeft = getGlobalImage("wall_top_left.png");
	}

	@Override
	public void draw1() {

		if (!this.squareGameObjectIsOn.seenByPlayer)
			return;

		// Draw object
		if (squareGameObjectIsOn != null) {
			int actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH
					+ drawOffsetX);
			int actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT
					+ drawOffsetY);

			float alpha = 1.0f;

			if (connectedTop)
				TextureUtils.drawTexture(textureTop, alpha, actorPositionXInPixels, actorPositionXInPixels + width,
						actorPositionYInPixels, actorPositionYInPixels + height);
			if (connectedTopRight)
				TextureUtils.drawTexture(textureTopRight, alpha, actorPositionXInPixels, actorPositionXInPixels + width,
						actorPositionYInPixels, actorPositionYInPixels + height);
			if (connectedRight)
				TextureUtils.drawTexture(textureRight, alpha, actorPositionXInPixels, actorPositionXInPixels + width,
						actorPositionYInPixels, actorPositionYInPixels + height);
			if (connectedBottomRight)
				TextureUtils.drawTexture(textureBottomRight, alpha, actorPositionXInPixels,
						actorPositionXInPixels + width, actorPositionYInPixels, actorPositionYInPixels + height);
			if (connectedBottom)
				TextureUtils.drawTexture(textureBottom, alpha, actorPositionXInPixels, actorPositionXInPixels + width,
						actorPositionYInPixels, actorPositionYInPixels + height);
			if (connectedBottomLeft)
				TextureUtils.drawTexture(textureBottomLeft, alpha, actorPositionXInPixels,
						actorPositionXInPixels + width, actorPositionYInPixels, actorPositionYInPixels + height);
			if (connectedLeft)
				TextureUtils.drawTexture(textureLeft, alpha, actorPositionXInPixels, actorPositionXInPixels + width,
						actorPositionYInPixels, actorPositionYInPixels + height);
			if (connectedTopLeft)
				TextureUtils.drawTexture(textureTopLeft, alpha, actorPositionXInPixels, actorPositionXInPixels + width,
						actorPositionYInPixels, actorPositionYInPixels + height);
		}
	}

	@Override
	public GameObject makeCopy(Square square) {
		return new Wall(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, blocksLineOfSight, widthRatio,
				heightRatio);
	}

}
