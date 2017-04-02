package com.marklynch.objects;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.TextureUtils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class BrokenGlass extends Stampable {

	GlassShard[] glassShards;
	int glassShardsCount;

	public BrokenGlass(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio, float heightRatio,
			float soundHandleX, float soundHandleY, float soundWhenHit, float soundWhenHitting, float soundDampening,
			Color light, float lightHandleX, float lightHandlY, boolean stackable, float fireResistance,
			float iceResistance, float electricResistance, float poisonResistance, Actor owner) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio, heightRatio,
				soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, iceResistance, electricResistance, poisonResistance, owner);

		glassShardsCount = 10;// (int) (1d + Math.random() * 20d);
		glassShards = new GlassShard[glassShardsCount];
		for (int i = 0; i < glassShardsCount; i++) {
			int randomShardNumber = (int) (Math.random() * 10d);
			glassShards[i] = new GlassShard(ResourceUtils.getGlobalImage("glass_shard_" + randomShardNumber + ".png"),
					this);
		}

	}

	@Override
	public BrokenGlass makeCopy(Square square, Actor owner) {
		return new BrokenGlass(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, blocksLineOfSight,
				persistsWhenCantBeSeen, widthRatio, heightRatio, soundHandleX, soundHandleY, soundWhenHit,
				soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY, stackable, fireResistance,
				iceResistance, electricResistance, poisonResistance, owner);
	}

	@Override
	public void draw1() {

		if (this.remainingHealth <= 0)
			return;

		// if (this.squareGameObjectIsOn.visibleToPlayer == false &&
		// persistsWhenCantBeSeen == false)
		// return;
		//
		// if (!this.squareGameObjectIsOn.seenByPlayer)
		// return;

		// Draw object
		if (squareGameObjectIsOn != null) {
			for (GlassShard glassShard : glassShards) {
				glassShard.draw1();
			}
		}
	}

	public static class GlassShard {
		BrokenGlass parent;
		Texture imageTexture;
		float width;
		float height;
		float drawOffsetX;
		float drawOffsetY;

		public GlassShard(Texture imageTexture, BrokenGlass parent) {
			this.imageTexture = imageTexture;
			this.parent = parent;
			width = imageTexture.getWidth();
			height = imageTexture.getHeight();
			float drawOffsetXMax = Game.SQUARE_WIDTH - width;
			float drawOffsetYMax = Game.SQUARE_HEIGHT - height;
			drawOffsetX = (float) (Math.random() * drawOffsetXMax);
			drawOffsetY = (float) (Math.random() * drawOffsetYMax);
		}

		public void draw1() {

			if (!Game.fullVisiblity) {
				if (parent.squareGameObjectIsOn.visibleToPlayer == false && parent.persistsWhenCantBeSeen == false)
					return;

				if (!parent.squareGameObjectIsOn.seenByPlayer)
					return;
			}

			// Draw object
			int actorPositionXInPixels = (int) (this.parent.squareGameObjectIsOn.xInGrid * (int) Game.SQUARE_WIDTH
					+ drawOffsetX);
			int actorPositionYInPixels = (int) (this.parent.squareGameObjectIsOn.yInGrid * (int) Game.SQUARE_HEIGHT
					+ drawOffsetY);

			float alpha = 1.0f;

			// TextureUtils.skipNormals = true;

			if (!this.parent.squareGameObjectIsOn.visibleToPlayer)
				alpha = 0.5f;
			TextureUtils.drawTexture(imageTexture, alpha, actorPositionXInPixels, actorPositionXInPixels + width,
					actorPositionYInPixels, actorPositionYInPixels + height);
			// TextureUtils.skipNormals = false;
		}
	}

}
