package com.marklynch.objects.tools;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.marklynch.Game;
import com.marklynch.GameCursor;
import com.marklynch.level.UserInputLevel;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.Color;
import com.marklynch.utils.LineUtils;
import com.marklynch.utils.TextureUtils;
import com.marklynch.utils.Utils;
import com.marklynch.utils.Utils.Point;

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

		float fishingLineX1 = lineAnchorX + weaponPositionXInPixels;
		float fishingLineY1 = lineAnchorY + weaponPositionYInPixels;

		boolean fishingTargetInTheWater = fisher.fishingTarget.squareGameObjectIsOn != null;

		if (fishingTargetInTheWater && fisher.fishingTarget.primaryAnimation != null) {

			// Fishing line
			float fishCenterX = (int) (fisher.fishingTarget.squareGameObjectIsOn.xInGridPixels
					+ Game.SQUARE_WIDTH * fisher.fishingTarget.drawOffsetRatioX + fisher.fishingTarget.halfWidth)
					+ fisher.fishingTarget.primaryAnimation.offsetX;
			float fishCenterY = (int) (fisher.fishingTarget.squareGameObjectIsOn.yInGridPixels
					+ Game.SQUARE_HEIGHT * fisher.fishingTarget.drawOffsetRatioY + fisher.fishingTarget.halfHeight)
					+ fisher.fishingTarget.primaryAnimation.offsetY;

			LineUtils.drawLine(Color.BLACK, fishingLineX1, fishingLineY1, fishCenterX, fishCenterY, 2);

			// Fish circle
			float radius = 128;
			float circleCenterX = fishCenterX;
			float circleCenterY = fishCenterY;
			float circleX1 = circleCenterX - radius;
			float circleY1 = circleCenterY - radius;
			float circleX2 = circleCenterX + radius;
			float circleY2 = circleCenterY + radius;
			TextureUtils.drawTexture(GameCursor.circle, 0.5f, circleX1, circleY1, circleX2, circleY2);

			// Line mouse - to - fish
			// LineUtils.drawLine(Color.RED, fishX, fishY,
			// UserInputLevel.mouseXTransformed,
			// UserInputLevel.mouseYTransformed, 5);

			// Mouse circle
			float mouseCircleRadius = 32;
			List<Point> intersections = Utils.getCircleLineIntersectionPoint2(
					new Point(UserInputLevel.mouseXTransformed, UserInputLevel.mouseYTransformed),
					new Point(fishCenterX, fishCenterY), new Point(fishCenterX, fishCenterY), radius);
			if (intersections.size() != 0) {
				float mouseCircleCenterX = intersections.get(0).x;
				float mouseCircleCenterY = intersections.get(0).y;
				float mouseCircleX1 = mouseCircleCenterX - mouseCircleRadius;
				float mouseCircleY1 = mouseCircleCenterY - mouseCircleRadius;
				float mouseCircleX2 = mouseCircleCenterX + mouseCircleRadius;
				float mouseCircleY2 = mouseCircleCenterY + mouseCircleRadius;
				TextureUtils.drawTexture(GameCursor.circle, 0.5f, mouseCircleX1, mouseCircleY1, mouseCircleX2,
						mouseCircleY2);
			}

			// Direction on circle
			float fishDirectionRadians = Utils.radianAngleFromLine(new Point(0, 0),
					new Point(fisher.fishingTarget.swimmingChangeX, fisher.fishingTarget.swimmingChangeY));

			System.out.println("Angle = " + fishDirectionRadians);

			Game.flush();
			Matrix4f view = Game.activeBatch.getViewMatrix();
			view.translate(new Vector2f(circleCenterX, circleCenterY));
			view.rotate(fishDirectionRadians, new Vector3f(0f, 0f, 1f));
			view.translate(new Vector2f(-circleCenterX, -circleCenterY));
			Game.activeBatch.updateUniforms();

			TextureUtils.drawTexture(GameCursor.circleEdge, 0.5f, circleX1, circleY1, circleX2, circleY2);

			Game.flush();
			view.translate(new Vector2f(circleCenterX, circleCenterY));
			view.rotate(-fishDirectionRadians, new Vector3f(0f, 0f, 1f));
			view.translate(new Vector2f(-circleCenterX, -circleCenterY));
			Game.activeBatch.updateUniforms();

		} else if (fisher.fishingAnimation != null) {
			float x2 = fisher.fishingAnimation.x + fisher.fishingTarget.halfWidth;
			float y2 = fisher.fishingAnimation.y + fisher.fishingTarget.halfHeight;

			LineUtils.drawLine(Color.BLACK, fishingLineX1, fishingLineY1, x2, y2, 2);
		}

	}

}
