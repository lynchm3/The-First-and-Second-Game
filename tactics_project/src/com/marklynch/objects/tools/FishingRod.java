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
import com.marklynch.utils.TextUtils;
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

	public float progress = 0.5f;
	public float progressThisTurn = 0f;

	boolean fishingTargetInTheWater;

	public float fishingLineX1;
	public float fishingLineY1;

	public float fishCenterX;
	public float fishCenterY;

	public final float radius = 128;
	public float circleX1;
	public float circleY1;
	public float circleX2;
	public float circleY2;

	// Mouse circle
	public final float mouseCircleRadius = 32;
	public float mouseCircleCenterX;
	public float mouseCircleCenterY;
	public float mouseCircleX1;
	public float mouseCircleY1;
	public float mouseCircleX2;
	public float mouseCircleY2;

	// Directions of stuff
	public final static float maxDirectionChangeInRadiansPerSecond = 2f;
	public final static float maxDirectionChangeInRadiansPerMillisecond = maxDirectionChangeInRadiansPerSecond / 1000f;
	// degrees
	public float fishDirectionRadians;
	public float oppositOfFishDirectionRadians;
	public float approximatedFishRadians = -1f;
	public float targetDirectionRadians = -1f;
	public float mouseToFishAngleRadians;

	public void updateLine(Actor fisher, int weaponPositionXInPixels, int weaponPositionYInPixels, float delta) {

		fishingTargetInTheWater = fisher.fishingTarget.squareGameObjectIsOn != null;

		if (fishingTargetInTheWater && fisher.fishingTarget.primaryAnimation != null) {
			// Line
			fishingLineX1 = lineAnchorX + weaponPositionXInPixels;
			fishingLineY1 = lineAnchorY + weaponPositionYInPixels;
			fishCenterX = (int) (fisher.fishingTarget.squareGameObjectIsOn.xInGridPixels
					+ Game.SQUARE_WIDTH * fisher.fishingTarget.drawOffsetRatioX + fisher.fishingTarget.halfWidth)
					+ fisher.fishingTarget.primaryAnimation.offsetX;
			fishCenterY = (int) (fisher.fishingTarget.squareGameObjectIsOn.yInGridPixels
					+ Game.SQUARE_HEIGHT * fisher.fishingTarget.drawOffsetRatioY + fisher.fishingTarget.halfHeight)
					+ fisher.fishingTarget.primaryAnimation.offsetY;

			// Fish circle
			circleX1 = fishCenterX - radius;
			circleY1 = fishCenterY - radius;
			circleX2 = fishCenterX + radius;
			circleY2 = fishCenterY + radius;

			// Mouse circle
			List<Point> intersections = Utils.getCircleLineIntersectionPoint2(
					new Point(UserInputLevel.mouseXTransformed, UserInputLevel.mouseYTransformed),
					new Point(fishCenterX, fishCenterY), new Point(fishCenterX, fishCenterY), radius);

			if (intersections.size() != 0) {
				mouseCircleCenterX = intersections.get(0).x;
				mouseCircleCenterY = intersections.get(0).y;
				mouseCircleX1 = mouseCircleCenterX - mouseCircleRadius;
				mouseCircleY1 = mouseCircleCenterY - mouseCircleRadius;
				mouseCircleX2 = mouseCircleCenterX + mouseCircleRadius;
				mouseCircleY2 = mouseCircleCenterY + mouseCircleRadius;
			}

			// Direction on circle
			fishDirectionRadians = Utils.radianAngleFromLine(new Point(0, 0),
					new Point(fisher.fishingTarget.swimmingChangeX, fisher.fishingTarget.swimmingChangeY));
			oppositOfFishDirectionRadians = fishDirectionRadians - 3.14159f;
			if (oppositOfFishDirectionRadians < 0) {
				oppositOfFishDirectionRadians += 6.28319;
			} else if (oppositOfFishDirectionRadians > 6.28319) {
				oppositOfFishDirectionRadians -= 6.28319;
			}

			// targetDirectionRadians = fishDirectionRadians;
			// oppositOfTargetDirectionRadians = oppositOfFishDirectionRadians;
			// targetDirection
			if (approximatedFishRadians == -1) {
				approximatedFishRadians = fishDirectionRadians;
				// oppositOfTargetDirectionRadians =
				// oppositOfFishDirectionRadians;
			} else {
				float maxTargetChangeThisUpdate = maxDirectionChangeInRadiansPerMillisecond * delta;
				float differenceBetweenTargetAndFishAngle = Math.abs(approximatedFishRadians - fishDirectionRadians);
				if (differenceBetweenTargetAndFishAngle <= maxTargetChangeThisUpdate) {
					approximatedFishRadians = fishDirectionRadians;
					// oppositOfTargetDirectionRadians =
					// oppositOfFishDirectionRadians;
				} else {

					System.out.println("maxTargetChangeThisUpdate = " + maxTargetChangeThisUpdate);

					if (fishDirectionRadians > approximatedFishRadians) {
						if (differenceBetweenTargetAndFishAngle < 3.14) {

							approximatedFishRadians += maxTargetChangeThisUpdate;
						} else {
							approximatedFishRadians -= maxTargetChangeThisUpdate;

						}
					} else {
						if (differenceBetweenTargetAndFishAngle < 3.14) {

							approximatedFishRadians -= maxTargetChangeThisUpdate;
						} else {
							approximatedFishRadians += maxTargetChangeThisUpdate;

						}
					}

					if (approximatedFishRadians < 0) {
						approximatedFishRadians += 6.28319;
					} else if (approximatedFishRadians > 6.28319f) {
						approximatedFishRadians -= 6.28319;
					}
					targetDirectionRadians = approximatedFishRadians - 3.14f;
					if (targetDirectionRadians < 0) {
						targetDirectionRadians += 6.28319;
					} else if (targetDirectionRadians > 6.28319) {
						targetDirectionRadians -= 6.28319;
					}
				}
			}

			// Mouse angle
			mouseToFishAngleRadians = Utils.radianAngleFromLine(new Point(fishCenterX, fishCenterY),
					new Point(UserInputLevel.mouseXTransformed, UserInputLevel.mouseYTransformed));

			boolean withinLimit = false;

			if (Math.abs(mouseToFishAngleRadians - targetDirectionRadians) < 1f) {
				withinLimit = true;
			} else if ((Math.abs(mouseToFishAngleRadians + 6.28319) - targetDirectionRadians) < 1f) {
				withinLimit = true;
			} else if (Math.abs(mouseToFishAngleRadians - (targetDirectionRadians + 6.28319)) < 1f) {
				withinLimit = true;
			}

			if (withinLimit) {
				progress++;
				progressThisTurn++;
			} else {
				progress--;
				progressThisTurn--;
			}

		}

	}

	public void drawLine(Actor fisher, int weaponPositionXInPixels, int weaponPositionYInPixels) {

		boolean fishingTargetInTheWater = fisher.fishingTarget.squareGameObjectIsOn != null;

		if (fishingTargetInTheWater && fisher.fishingTarget.primaryAnimation != null) {

			// Fishing line
			LineUtils.drawLine(Color.BLACK, fishingLineX1, fishingLineY1, fishCenterX, fishCenterY, 2);

			// Fish circle
			TextureUtils.drawTexture(GameCursor.circle, 0.5f, circleX1, circleY1, circleX2, circleY2);

			// Mouse circle
			TextureUtils.drawTexture(GameCursor.circle, 0.5f, mouseCircleX1, mouseCircleY1, mouseCircleX2,
					mouseCircleY2);

			System.out.println("fishDirectionRadians = " + fishDirectionRadians);
			System.out.println("targetDirectionRadians = " + approximatedFishRadians);

			Matrix4f view = Game.activeBatch.getViewMatrix();

			// Fish direction
			Game.flush();
			view.translate(new Vector2f(fishCenterX, fishCenterY));
			view.rotate(fishDirectionRadians, new Vector3f(0f, 0f, 1f));
			view.translate(new Vector2f(-fishCenterX, -fishCenterY));
			Game.activeBatch.updateUniforms();

			TextureUtils.drawTexture(GameCursor.circleEdgeRed, 0.5f, circleX1, circleY1, circleX2, circleY2);

			Game.flush();
			view.translate(new Vector2f(fishCenterX, fishCenterY));
			view.rotate(-fishDirectionRadians, new Vector3f(0f, 0f, 1f));
			view.translate(new Vector2f(-fishCenterX, -fishCenterY));
			Game.activeBatch.updateUniforms();

			// Target direction
			Game.flush();
			view.translate(new Vector2f(fishCenterX, fishCenterY));
			view.rotate(targetDirectionRadians, new Vector3f(0f, 0f, 1f));
			view.translate(new Vector2f(-fishCenterX, -fishCenterY));
			Game.activeBatch.updateUniforms();

			TextureUtils.drawTexture(GameCursor.circleEdge, 0.5f, circleX1, circleY1, circleX2, circleY2);

			Game.flush();
			view.translate(new Vector2f(fishCenterX, fishCenterY));
			view.rotate(-targetDirectionRadians, new Vector3f(0f, 0f, 1f));
			view.translate(new Vector2f(-fishCenterX, -fishCenterY));
			Game.activeBatch.updateUniforms();

			// Mouse direction
			Game.flush();
			view.translate(new Vector2f(fishCenterX, fishCenterY));
			view.rotate(mouseToFishAngleRadians, new Vector3f(0f, 0f, 1f));
			view.translate(new Vector2f(-fishCenterX, -fishCenterY));
			Game.activeBatch.updateUniforms();

			TextureUtils.drawTexture(GameCursor.circleEdgeGreen, 0.5f, circleX1, circleY1, circleX2, circleY2);

			Game.flush();
			view.translate(new Vector2f(fishCenterX, fishCenterY));
			view.rotate(-mouseToFishAngleRadians, new Vector3f(0f, 0f, 1f));
			view.translate(new Vector2f(-fishCenterX, -fishCenterY));
			Game.activeBatch.updateUniforms();

			TextUtils.printTextWithImages(mouseCircleCenterX + 8, mouseCircleCenterY, Integer.MAX_VALUE, false, null,
					progress);

			// opposite of Target direction
			// Game.flush();
			// view.translate(new Vector2f(fishCenterX, fishCenterY));
			// view.rotate(oppositOfTargetDirectionRadians, new Vector3f(0f, 0f,
			// 1f));
			// view.translate(new Vector2f(-fishCenterX, -fishCenterY));
			// Game.activeBatch.updateUniforms();
			//
			// TextureUtils.drawTexture(GameCursor.circleEdge, 0.5f, circleX1,
			// circleY1, circleX2, circleY2);
			//
			// Game.flush();
			// view.translate(new Vector2f(fishCenterX, fishCenterY));
			// view.rotate(-oppositOfTargetDirectionRadians, new Vector3f(0f,
			// 0f, 1f));
			// view.translate(new Vector2f(-fishCenterX, -fishCenterY));
			// Game.activeBatch.updateUniforms();

		} else if (fisher.fishingAnimation != null) {
			float x2 = fisher.fishingAnimation.x + fisher.fishingTarget.halfWidth;
			float y2 = fisher.fishingAnimation.y + fisher.fishingTarget.halfHeight;

			LineUtils.drawLine(Color.BLACK, fishingLineX1, fishingLineY1, x2, y2, 2);
		}

	}

	public void reset() {
		approximatedFishRadians = -1f;
		targetDirectionRadians = -1f;
		progress = 0.5f;

	}

}
