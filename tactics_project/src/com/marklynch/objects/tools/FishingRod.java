package com.marklynch.objects.tools;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.animation.AnimationTake;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.Color;
import com.marklynch.utils.LineUtils;

public class FishingRod extends Tool {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	public float lineAnchorX = 93;
	public float lineAnchorY = 0;
	// public float lineX2;
	// public float lineY2;

	public FishingRod() {
		super();
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public FishingRod makeCopy(Square square, Actor owner) {
		FishingRod weapon = new FishingRod();
		setInstances(weapon);
		setAttributesForCopy(weapon, square, owner);
		return weapon;
	}

	public void drawLine(Actor fisher, GameObject fishingTarget, int weaponPositionXInPixels,
			int weaponPositionYInPixels) {

		float x1 = lineAnchorX + weaponPositionXInPixels;
		float y1 = lineAnchorY + weaponPositionYInPixels;

		if (fishingTarget.squareGameObjectIsOn != null) {

			float x2 = (int) (fishingTarget.squareGameObjectIsOn.xInGridPixels
					+ Game.SQUARE_WIDTH * fishingTarget.drawOffsetRatioX + fishingTarget.halfWidth);
			float y2 = (int) (fishingTarget.squareGameObjectIsOn.yInGridPixels
					+ Game.SQUARE_HEIGHT * fishingTarget.drawOffsetRatioY + fishingTarget.halfHeight);
			if (primaryAnimation != null) {
				x2 += fishingTarget.primaryAnimation.offsetX;
				y2 += fishingTarget.primaryAnimation.offsetY;
			}
			LineUtils.drawLine(Color.BLACK, x1, y1, x2, y2, 2);
		} else {

			// System.out.println("fishingTarget.secondaryAnimations.size() = "
			// + fishingTarget.secondaryAnimations.size());

			System.out.println("fisher.secondaryAnimations.size() = " + fisher.secondaryAnimations.size());
			if (fisher.secondaryAnimations.size() > 0)
				System.out.println("fisher.secondaryAnimations.get(0) = " + fisher.secondaryAnimations.get(0));

			if (fisher.secondaryAnimations.size() > 0 && fisher.secondaryAnimations.get(0) instanceof AnimationTake) {
				AnimationTake animationTake = (AnimationTake) fisher.secondaryAnimations.get(0);
				float x2 = animationTake.x + fishingTarget.halfWidth;
				float y2 = animationTake.y + fishingTarget.halfHeight;

				LineUtils.drawLine(Color.BLACK, x1, y1, x2, y2, 2);

			}

		}

	}

}
