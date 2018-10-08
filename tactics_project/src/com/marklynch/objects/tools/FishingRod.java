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
	public int fishingRange = 1;

	public FishingRod() {
		super();
		type = "Fishing Rod";
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
		weapon.fishingRange = this.fishingRange;
		return weapon;
	}

	public float lineDamage = 0f;
	public float progressThisTurn = 0f;
	public boolean caught = false;

	boolean fishingTargetInTheWater;

	public float fishingLineX1;
	public float fishingLineY1;

	public float fishCenterX;
	public float fishCenterY;

	public final float fishCircleRadius = 128;
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
	public final static float maxDirectionChangeInRadiansPerSecond = 1f;
	public final static float maxDirectionChangeInRadiansPerMillisecond = maxDirectionChangeInRadiansPerSecond / 1000f;
	// degrees
	public float fishDirectionRadians;
	public float oppositOfFishDirectionRadians;
	public float gradualTarget = -1f;
	public float targetDirectionRadians = -1f;
	public float mouseDistanceToTargetRadians = 0f;
	public float mouseToFishAngleRadians;

	public void updateFishingMinigame(Actor fisher, int weaponPositionXInPixels, int weaponPositionYInPixels,
			float delta) {
		fishingTargetInTheWater = fisher.fishingTarget.squareGameObjectIsOn != null;

		if (fishingTargetInTheWater && fisher.fishingTarget.getPrimaryAnimation() != null) {
			// Line
			fishingLineX1 = lineAnchorX + weaponPositionXInPixels;

			// Fish circle
			circleX1 = fishCenterX - fishCircleRadius;
			circleY1 = fishCenterY - fishCircleRadius;
			circleX2 = fishCenterX + fishCircleRadius;
			circleY2 = fishCenterY + fishCircleRadius;

			// Mouse circle
			List<Point> intersections = Utils.getCircleLineIntersectionPoint2(
					new Point(UserInputLevel.mouseXTransformed, UserInputLevel.mouseYTransformed),
					new Point(fishCenterX, fishCenterY), new Point(fishCenterX, fishCenterY), fishCircleRadius);

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

			float absoluteTarget = 0f;
			if (fisher.fishingTarget.fightingFishingRod) {
				absoluteTarget = oppositOfFishDirectionRadians;
			} else {
				absoluteTarget = fishDirectionRadians;

			}

			// targetDirectionRadians = fishDirectionRadians;
			// oppositOfTargetDirectionRadians = oppositOfFishDirectionRadians;
			// targetDirection
			if (gradualTarget == -1) {
				gradualTarget = absoluteTarget;
				// oppositOfTargetDirectionRadians =
				// oppositOfFishDirectionRadians;
			} else {
				float maxTargetChangeThisUpdate = maxDirectionChangeInRadiansPerMillisecond * delta;
				float differenceBetweenTargetAndFishAngle = Math.abs(gradualTarget - absoluteTarget);
				if (differenceBetweenTargetAndFishAngle <= maxTargetChangeThisUpdate) {
					gradualTarget = absoluteTarget;
					// oppositOfTargetDirectionRadians =
					// oppositOfFishDirectionRadians;
				} else {

					if (absoluteTarget > gradualTarget) {
						if (differenceBetweenTargetAndFishAngle < 3.14) {

							gradualTarget += maxTargetChangeThisUpdate;
						} else {
							gradualTarget -= maxTargetChangeThisUpdate;

						}
					} else {
						if (differenceBetweenTargetAndFishAngle < 3.14) {

							gradualTarget -= maxTargetChangeThisUpdate;
						} else {
							gradualTarget += maxTargetChangeThisUpdate;

						}
					}

					if (gradualTarget < 0) {
						gradualTarget += 6.28319;
					} else if (gradualTarget > 6.28319f) {
						gradualTarget -= 6.28319;
					}

					targetDirectionRadians = gradualTarget;
				}
			}

			// Mouse angle
			mouseToFishAngleRadians = Utils.radianAngleFromLine(new Point(fishCenterX, fishCenterY),
					new Point(UserInputLevel.mouseXTransformed, UserInputLevel.mouseYTransformed));
			mouseDistanceToTargetRadians = Math.abs(mouseToFishAngleRadians - targetDirectionRadians);
			boolean withinLimit = false;

			if (mouseDistanceToTargetRadians < 1f) {
				withinLimit = true;
			} else if ((Math.abs(mouseToFishAngleRadians + 6.28319) - targetDirectionRadians) < 1f) {
				withinLimit = true;
			} else if (Math.abs(mouseToFishAngleRadians - (targetDirectionRadians + 6.28319)) < 1f) {
				withinLimit = true;
			}

			if (withinLimit) {
				progressThisTurn++;
			} else {
				lineDamage += delta / 10000f;
				progressThisTurn--;
			}

		}
	}

	public void updateLine(Actor fisher, int weaponPositionXInPixels, int weaponPositionYInPixels, float delta) {

		fishingTargetInTheWater = fisher.fishingTarget.squareGameObjectIsOn != null;

		if (fishingTargetInTheWater && fisher.fishingTarget.getPrimaryAnimation() != null) {
			// Line
			fishingLineX1 = lineAnchorX + weaponPositionXInPixels;
			fishingLineY1 = lineAnchorY + weaponPositionYInPixels;
			fishCenterX = (int) (fisher.fishingTarget.squareGameObjectIsOn.xInGridPixels
					+ Game.SQUARE_WIDTH * fisher.fishingTarget.drawOffsetRatioX + fisher.fishingTarget.halfWidth)
					+ fisher.fishingTarget.getPrimaryAnimation().offsetX;
			fishCenterY = (int) (fisher.fishingTarget.squareGameObjectIsOn.yInGridPixels
					+ Game.SQUARE_HEIGHT * fisher.fishingTarget.drawOffsetRatioY + fisher.fishingTarget.halfHeight)
					+ fisher.fishingTarget.getPrimaryAnimation().offsetY;

			if (fisher != Game.level.player) {
				float progressThisUpdate = (float) (Math.random() - 0.5);
				progressThisTurn += progressThisUpdate;
				if (progressThisUpdate < 0) {
					lineDamage += delta / 10000f;
				}

			}
		}

	}

	public void drawFishingMinigame(Actor fisher, int weaponPositionXInPixels, int weaponPositionYInPixels) {

		boolean fishingTargetInTheWater = fisher.fishingTarget.squareGameObjectIsOn != null;

		if (fishingTargetInTheWater && fisher.fishingTarget.getPrimaryAnimation() != null) {

			// Fish circle
			TextureUtils.drawTexture(GameCursor.circleBlack, 0.25f, circleX1, circleY1, circleX2, circleY2);

			Matrix4f view = Game.activeBatch.getViewMatrix();

			// Fishing line
			Color lineColor = new Color(lineDamage, 0, 0);
			LineUtils.drawLine(lineColor, fishingLineX1, fishingLineY1, fishCenterX, fishCenterY, 2);

			// Target direction (circle edge)
			Game.flush();
			view.translate(new Vector2f(fishCenterX, fishCenterY));
			view.rotate(targetDirectionRadians, new Vector3f(0f, 0f, 1f));
			view.translate(new Vector2f(-fishCenterX, -fishCenterY));
			Game.activeBatch.updateUniforms();

			Color edgeColor = Color.RED;
			if (mouseDistanceToTargetRadians < 1f) {
				edgeColor = new Color(1f, 1f - mouseDistanceToTargetRadians, 1f - mouseDistanceToTargetRadians);
			}

			TextureUtils.drawTexture(GameCursor.circleEdge, 0.75f, circleX1, circleY1, circleX2, circleY2, edgeColor);

			Game.flush();
			view.translate(new Vector2f(fishCenterX, fishCenterY));
			view.rotate(-targetDirectionRadians, new Vector3f(0f, 0f, 1f));
			view.translate(new Vector2f(-fishCenterX, -fishCenterY));
			Game.activeBatch.updateUniforms();

			// Mouse circle

			Game.flush();
			view.translate(new Vector2f(mouseCircleCenterX, mouseCircleCenterY));
			view.rotate(mouseToFishAngleRadians, new Vector3f(0f, 0f, 1f));
			view.translate(new Vector2f(-mouseCircleCenterX, -mouseCircleCenterY));
			Game.activeBatch.updateUniforms();

			TextureUtils.drawTexture(GameCursor.circleMouseFishing, 0.75f, mouseCircleX1, mouseCircleY1, mouseCircleX2,
					mouseCircleY2, edgeColor);

			Game.flush();
			view.translate(new Vector2f(mouseCircleCenterX, mouseCircleCenterY));
			view.rotate(-mouseToFishAngleRadians, new Vector3f(0f, 0f, 1f));
			view.translate(new Vector2f(-mouseCircleCenterX, -mouseCircleCenterY));
			Game.activeBatch.updateUniforms();

		} else if (fisher.fishingAnimation != null) {
			float x2 = fisher.fishingAnimation.x + fisher.fishingTarget.halfWidth;
			float y2 = fisher.fishingAnimation.y + fisher.fishingTarget.halfHeight;

			LineUtils.drawLine(Color.BLACK, fishingLineX1, fishingLineY1, x2, y2, 2);
		}

	}

	public void drawLine(Actor fisher, float weaponPositionXInPixels, float weaponPositionYInPixels, Color color) {
		// Fishing line
		// Color lineColor = new Color(lineDamage, 0, 0);
		// color

		LineUtils.drawLine(color, fishingLineX1, fishingLineY1, fishCenterX, fishCenterY, 2);
	}

	public void reset() {
		gradualTarget = -1f;
		targetDirectionRadians = -1f;
		progressThisTurn = 0f;
		lineDamage = 0f;
		caught = false;
	}

}
