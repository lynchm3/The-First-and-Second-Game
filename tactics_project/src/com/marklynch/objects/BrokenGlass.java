package com.marklynch.objects;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.ResourceUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class BrokenGlass extends Stampable {

	GlassShard[] glassShards;
	int glassShardsCount;

	public BrokenGlass() {
		super();








	}

	@Override
	public BrokenGlass makeCopy(Square square, Actor owner) {
		BrokenGlass brokenGlass = new BrokenGlass();
		super.setAttributesForCopy(brokenGlass, square, owner);
		brokenGlass.glassShardsCount = 10;// (int) (1d + Math.random() * 20d);
		brokenGlass.glassShards = new GlassShard[brokenGlass.glassShardsCount];
		for (int i = 0; i < brokenGlass.glassShardsCount; i++) {
			int randomShardNumber = (int) (Math.random() * 10d);
			brokenGlass.glassShards[i] = new GlassShard(
					ResourceUtils.getGlobalImage("glass_shard_" + randomShardNumber + ".png"), brokenGlass);
		}
		return brokenGlass;
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
			int actorPositionXInPixels = (int) (this.parent.squareGameObjectIsOn.xInGridPixels + drawOffsetX);
			int actorPositionYInPixels = (int) (this.parent.squareGameObjectIsOn.yInGridPixels + drawOffsetY);

			float alpha = 1.0f;

			// TextureUtils.skipNormals = true;

			if (!this.parent.squareGameObjectIsOn.visibleToPlayer)
				alpha = 0.5f;
			TextureUtils.drawTexture(imageTexture, alpha, actorPositionXInPixels, actorPositionYInPixels,
					actorPositionXInPixels + width, actorPositionYInPixels + height);
			// TextureUtils.skipNormals = false;
		}
	}

}
