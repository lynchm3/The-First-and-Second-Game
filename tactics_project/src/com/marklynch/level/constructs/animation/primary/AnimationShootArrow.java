package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class AnimationShootArrow extends Animation {

	// public Square startSquare;
	// public Square endSquare;
	// public float startOffsetX = 0;
	// public float startOffsetY = 0;

	float quarterDurationToReach;
	float halfDurationToReach;
	float threeQuarterDurationToReach;
	// for show only, walking actor, primary

	GameObject target;

	public AnimationShootArrow(Actor performer, GameObject target) {
		super();
		this.target = target;
		durationToReach = 2000; // SLOWED IT DOWN HERE

		quarterDurationToReach = durationToReach / 4;
		halfDurationToReach = quarterDurationToReach + quarterDurationToReach;
		threeQuarterDurationToReach = halfDurationToReach + quarterDurationToReach;

		// this.startSquare = startSquare;
		// this.endSquare = endSquare;

		// startOffsetX = offsetX = (int) ((this.startSquare.xInGrid -
		// this.endSquare.xInGrid) * Game.SQUARE_WIDTH);
		// startOffsetY = offsetY = (int) ((this.startSquare.yInGrid -
		// this.endSquare.yInGrid) * Game.SQUARE_HEIGHT);
		backwards = performer.backwards;
		blockAI = true;
		drawArrowInOffHand = true;
		arrowHandleY = 12;

	}

	@Override
	public void update(double delta) {

		if (completed)
			return;

		durationSoFar += delta;

		float progress = durationSoFar / durationToReach;

		if (progress >= 1) {
			progress = 1;
		}

		if (progress < 0.25f) {

			rightShoulderAngle = -6.28f * progress;
			rightElbowAngle = 0f;

			leftShoulderAngle = -6.28f * progress;
			leftElbowAngle = 0f;

		} else if (progress < 0.5f) {

			arrowHandleY = 36 + (progress - 0.25f) * 32;// 12 + 26;
			drawArrowInOffHand = false;
			drawArrowInMainHand = true;
			rightShoulderAngle = -1.57f;
			rightElbowAngle = 0f * progress;

			leftShoulderAngle = -1.57f + ((progress - 0.25f) * 1.57f);
			leftElbowAngle = -((progress - 0.25f) * 3.14f);

		} else if (progress < 0.75f) {

			drawArrowInOffHand = false;
			drawArrowInMainHand = false;
			rightShoulderAngle = -1.57f;
			rightElbowAngle = 0f;

			leftShoulderAngle = -1.1775f;
			leftElbowAngle = -0.785f;

		} else {

			drawArrowInOffHand = false;
			drawArrowInMainHand = false;
			rightShoulderAngle = -6.28f * (1f - progress);
			rightElbowAngle = 0f;

			leftShoulderAngle = -1.1775f * (4 * (1 - progress));
			leftElbowAngle = -0.785f * (4 * (1 - progress));
		}

		if (backwards) {
			float temp = rightShoulderAngle;
			rightShoulderAngle = -leftShoulderAngle;
			leftShoulderAngle = -temp;

			temp = rightElbowAngle;
			rightElbowAngle = -leftElbowAngle;
			leftElbowAngle = -temp;
		}

		if (progress >= 1) {
			target.showPow();
			rightShoulderAngle = 0;
			rightElbowAngle = 0;
			leftShoulderAngle = 0;
			leftElbowAngle = 0;
			completed = true;
		} else {
		}

	}

	@Override
	public void draw2() {

	}

	@Override
	public void draw1() {

		// FUCKFKCKFUCK

	}

	@Override
	public void drawStaticUI() {
	}

}
