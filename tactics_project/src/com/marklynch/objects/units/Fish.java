package com.marklynch.objects.units;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Faction;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.AnimationBubbles;
import com.marklynch.level.constructs.area.Area;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.TextureUtils;

public class Fish extends HerbivoreWildAnimal {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Fish() {
		super();
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public Fish makeCopy(String name, Square square, Faction faction, GameObject bed, GameObject[] mustHaves,
			GameObject[] mightHaves, Area area) {
		Fish actor = new Fish();
		setInstances(actor);
		super.setAttributesForCopy(name, actor, square, faction, bed, 0, mustHaves, mightHaves, area);

		return actor;
	}

	@Override
	public void update(int delta) {
		super.update(delta);

		if (squareGameObjectIsOn != null && delta % 3 == 0) {
			int x = (int) (squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH * drawOffsetRatioX);
			int y = (int) (squareGameObjectIsOn.yInGridPixels + Game.SQUARE_HEIGHT * drawOffsetRatioY);
			this.secondaryAnimations.add(new AnimationBubbles(this, x + width, y, 0.1f));
		}
	}

	@Override
	public void draw3() {

		if (this.remainingHealth <= 0)
			return;
		if (squareGameObjectIsOn == null)
			return;
		if (hiding)
			return;

		if (!Game.fullVisiblity) {

			if (this.squareGameObjectIsOn.visibleToPlayer == false && persistsWhenCantBeSeen == false)
				return;

			if (!this.squareGameObjectIsOn.seenByPlayer)
				return;
		}

		if (primaryAnimation != null && primaryAnimation.completed == false)
			primaryAnimation.draw1();

		for (Animation secondaryAnimation : secondaryAnimations)
			secondaryAnimation.draw1();

		// Draw object
		if (squareGameObjectIsOn != null) {

			int actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels
					+ Game.SQUARE_WIDTH * drawOffsetRatioX);
			int actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels
					+ Game.SQUARE_HEIGHT * drawOffsetRatioY);
			if (primaryAnimation != null) {
				actorPositionXInPixels += primaryAnimation.offsetX;
				actorPositionYInPixels += primaryAnimation.offsetY;
			}

			float alpha = 1.0f;
			if (hiding)
				alpha = 0.5f;

			float boundsX1 = (this.squareGameObjectIsOn.xInGridPixels + Game.SQUARE_WIDTH * drawOffsetRatioX);
			float boundsY1 = (this.squareGameObjectIsOn.yInGridPixels + Game.SQUARE_HEIGHT * drawOffsetRatioY);
			float boundsX2 = (boundsX1 + width);
			float boundsY2 = (boundsY1 + halfHeight);

			// GL11.glTexParameteri(target, pname, param);
			TextureUtils.drawTextureWithinBounds(imageTexture, alpha, actorPositionXInPixels, actorPositionYInPixels,
					actorPositionXInPixels + width, actorPositionYInPixels + height, boundsX1, boundsY1, boundsX2,
					boundsY2, backwards, false);

			// TextureUtils.drawTextureWithinBounds(gameObject.imageTexture,
			// alpha, actorPositionXInPixels,
			// actorPositionYInPixels, actorPositionXInPixels +
			// gameObject.width,
			// actorPositionYInPixels + gameObject.height, boundsX1, boundsY1,
			// boundsX2, boundsY2, false,
			// gameObject.flipYAxisInMirror);

			if (flash || this == Game.gameObjectMouseIsOver) {
				TextureUtils.drawTexture(imageTexture, 0.5f, actorPositionXInPixels, actorPositionYInPixels,
						actorPositionXInPixels + width, actorPositionYInPixels + height, 0, 0, 0, 0, backwards, false,
						flashColor, false);
			}
		}

		super.draw3();

	}

}