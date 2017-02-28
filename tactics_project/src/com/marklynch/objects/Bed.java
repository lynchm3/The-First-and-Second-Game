package com.marklynch.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Texture;

public class Bed extends GameObject {

	String imagePathCovers;
	Texture imageTextureCovers;

	public Bed(String name, int health, String imagePath, String imagePathCovers, Square squareGameObjectIsOn,
			Inventory inventory, boolean showInventory, boolean canShareSquare, boolean fitsInInventory,
			boolean canContainOtherObjects, boolean blocksLineOfSight, float widthRatio, float heightRatio) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, widthRatio, heightRatio);
		this.imagePathCovers = imagePathCovers;
		loadCoverImage();
	}

	public void loadCoverImage() {
		this.imageTextureCovers = getGlobalImage(imagePathCovers);
	}

	@Override
	public GameObject makeCopy(Square square) {
		return new Bed(new String(name), (int) totalHealth, imageTexturePath, imagePathCovers, square,
				inventory.makeCopy(), showInventory, canShareSquare, fitsInInventory, canContainOtherObjects,
				blocksLineOfSight, widthRatio, heightRatio);
	}

	@Override
	public void draw2() {

		super.draw2();

		if (this.squareGameObjectIsOn.visibleToPlayer == false)
			return;

		// Draw bed covers
		if (squareGameObjectIsOn != null) {
			int actorPositionXInPixels = this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH;
			int actorPositionYInPixels = this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT;

			// TextureUtils.skipNormals = true;
			TextureUtils.drawTexture(imageTextureCovers, 1f, actorPositionXInPixels,
					actorPositionXInPixels + Game.SQUARE_WIDTH, actorPositionYInPixels,
					actorPositionYInPixels + Game.SQUARE_HEIGHT);
			// TextureUtils.skipNormals = false;
		}

	}
}
