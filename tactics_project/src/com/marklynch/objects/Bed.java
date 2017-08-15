package com.marklynch.objects;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class Bed extends GameObject {

	String imagePathCovers;
	Texture imageTextureCovers;

	public Bed(String name, int health, String imagePath, String imagePathCovers, Square squareGameObjectIsOn,
			Inventory inventory, float widthRatio, float heightRatio, float drawOffsetX, float drawOffsetY,
			float soundWhenHit, float soundWhenHitting, float soundDampening, Color light, float lightHandleX,
			float lightHandlY, boolean stackable, float fireResistance, float waterResistance, float electricResistance,
			float poisonResistance, float slashResistance, float weight, Actor owner) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, widthRatio, heightRatio, drawOffsetX,
				drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
				stackable, fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance,
				weight, owner);
		this.imagePathCovers = imagePathCovers;
		loadCoverImage();
	}

	public void loadCoverImage() {
		this.imageTextureCovers = getGlobalImage(imagePathCovers);
	}

	public GameObject makeCopy(Square square) {
		return new Bed(new String(name), (int) totalHealth, imageTexturePath, imagePathCovers, square, new Inventory(),
				widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening,
				light, lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, slashResistance, weight, null);
	}

	@Override
	public GameObject makeCopy(Square square, Actor owner) {
		return new Bed(new String(name), (int) totalHealth, imageTexturePath, imagePathCovers, square, new Inventory(),
				widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening,
				light, lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, slashResistance, weight, owner);
	}

	@Override
	public void draw2() {

		super.draw2();

		if (!Game.fullVisiblity) {

			if (this.squareGameObjectIsOn.visibleToPlayer == false)
				return;
		}

		// Draw bed covers
		if (squareGameObjectIsOn != null) {
			int actorPositionXInPixels = this.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH;
			int actorPositionYInPixels = this.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT;

			// TextureUtils.skipNormals = true;
			TextureUtils.drawTexture(imageTextureCovers, 1f, actorPositionXInPixels, actorPositionYInPixels,
					actorPositionXInPixels + Game.SQUARE_WIDTH, actorPositionYInPixels + Game.SQUARE_HEIGHT);
			// TextureUtils.skipNormals = false;
		}

		super.draw2();

	}
}
