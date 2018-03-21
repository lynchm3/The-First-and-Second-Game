package com.marklynch.objects.tools;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.GameCursor;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.Color;
import com.marklynch.utils.LineUtils;
import com.marklynch.utils.TextureUtils;

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

		float lineX1 = lineAnchorX + weaponPositionXInPixels;
		float lineY1 = lineAnchorY + weaponPositionYInPixels;

		boolean fishingTargetInTheWater = fisher.fishingTarget.squareGameObjectIsOn != null;

		if (fishingTargetInTheWater && fisher.fishingTarget.primaryAnimation != null) {

			float lineX2 = (int) (fisher.fishingTarget.squareGameObjectIsOn.xInGridPixels
					+ Game.SQUARE_WIDTH * fisher.fishingTarget.drawOffsetRatioX + fisher.fishingTarget.halfWidth)
					+ fisher.fishingTarget.primaryAnimation.offsetX;
			float lineY2 = (int) (fisher.fishingTarget.squareGameObjectIsOn.yInGridPixels
					+ Game.SQUARE_HEIGHT * fisher.fishingTarget.drawOffsetRatioY + fisher.fishingTarget.halfHeight)
					+ fisher.fishingTarget.primaryAnimation.offsetY;

			LineUtils.drawLine(Color.BLACK, lineX1, lineY1, lineX2, lineY2, 2);

			float circleX1 = (int) (fisher.fishingTarget.squareGameObjectIsOn.xInGridPixels
					+ Game.SQUARE_WIDTH * fisher.fishingTarget.drawOffsetRatioX + fisher.fishingTarget.halfWidth)
					+ fisher.fishingTarget.primaryAnimation.offsetX - 128;
			float circleY1 = (int) (fisher.fishingTarget.squareGameObjectIsOn.yInGridPixels
					+ Game.SQUARE_HEIGHT * fisher.fishingTarget.drawOffsetRatioY + fisher.fishingTarget.halfHeight)
					+ fisher.fishingTarget.primaryAnimation.offsetY - 128;

			float circleX2 = circleX1 + 256;
			float circleY2 = circleY1 + 256;

			TextureUtils.drawTexture(GameCursor.circle, 0.5f, circleX1, circleY1, circleX2, circleY2);

		} else if (fisher.fishingAnimation != null) {
			float x2 = fisher.fishingAnimation.x + fisher.fishingTarget.halfWidth;
			float y2 = fisher.fishingAnimation.y + fisher.fishingTarget.halfHeight;

			LineUtils.drawLine(Color.BLACK, lineX1, lineY1, x2, y2, 2);
		}

	}

}
