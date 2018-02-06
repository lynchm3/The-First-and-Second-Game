package com.marklynch.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.TextureUtils;

import com.marklynch.utils.Texture;

public class Bed extends GameObject {

	String imagePathCovers;
	public Texture imageTextureCovers;

	public Bed() {
		super();
		loadCoverImage();

		canBePickedUp = false;

		fitsInInventory = false;





	}

	public void loadCoverImage() {
		this.imageTextureCovers = getGlobalImage(imagePathCovers);
	}

	@Override
	public Bed makeCopy(Square square, Actor owner) {
		Bed bed = new Bed();
		super.setAttributesForCopy(bed, square, owner);
		bed.imageTextureCovers = imageTextureCovers;
		return bed;
	}

	@Override
	public void draw2() {

		if (!Game.fullVisiblity) {

			if (this.squareGameObjectIsOn.visibleToPlayer == false)
				return;
		}

		// Draw bed covers
		if (squareGameObjectIsOn != null) {
			float actorPositionXInPixels = this.squareGameObjectIsOn.xInGridPixels;
			float actorPositionYInPixels = this.squareGameObjectIsOn.yInGridPixels;

			// TextureUtils.skipNormals = true;
			TextureUtils.drawTexture(imageTextureCovers, 1f, actorPositionXInPixels, actorPositionYInPixels,
					actorPositionXInPixels + Game.SQUARE_WIDTH, actorPositionYInPixels + Game.SQUARE_HEIGHT);
			// TextureUtils.skipNormals = false;
		}

		super.draw2();

	}
}
