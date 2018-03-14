package com.marklynch.objects.tools;

import java.util.ArrayList;

import com.marklynch.Game;
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

	public void drawLine(Actor fisher, int weaponPositionXInPixels, int weaponPositionYInPixels) {

		float x1 = lineAnchorX + weaponPositionXInPixels;
		float y1 = lineAnchorY + weaponPositionYInPixels;

		if (fisher.fishingTarget.squareGameObjectIsOn != null) {

			float x2 = (int) (fisher.fishingTarget.squareGameObjectIsOn.xInGridPixels
					+ Game.SQUARE_WIDTH * fisher.fishingTarget.drawOffsetRatioX + fisher.fishingTarget.halfWidth);
			float y2 = (int) (fisher.fishingTarget.squareGameObjectIsOn.yInGridPixels
					+ Game.SQUARE_HEIGHT * fisher.fishingTarget.drawOffsetRatioY + fisher.fishingTarget.halfHeight);
			if (primaryAnimation != null) {
				x2 += fisher.fishingTarget.primaryAnimation.offsetX;
				y2 += fisher.fishingTarget.primaryAnimation.offsetY;
			}
			LineUtils.drawLine(Color.BLACK, x1, y1, x2, y2, 2);
		} else {
			float x2 = fisher.fishingAnimation.x + fisher.fishingTarget.halfWidth;
			float y2 = fisher.fishingAnimation.y + fisher.fishingTarget.halfHeight;

			LineUtils.drawLine(Color.BLACK, x1, y1, x2, y2, 2);

		}

	}

}
