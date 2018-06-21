package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.objects.GameObject;

public class AnimationSlash extends Animation {

	// public Square startSquare;
	// public Square endSquare;
	// public float startOffsetX = 0;
	// public float startOffsetY = 0;

	float quarterDurationToReach;
	float halfDurationToReach;
	float threeQuarterDurationToReach;

	// for show only, walking actor, primary

	GameObject target;

	public AnimationSlash(GameObject performer, GameObject target) {
		super(performer);
		this.target = target;
		durationToReachMillis = 400;

		quarterDurationToReach = durationToReachMillis / 4;
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

	}

	@Override
	public String toString() {
		return "AnimationSlash";
	}

	@Override
	public void update(double delta) {

		if (getCompleted())
			return;
		super.update(delta);

		durationSoFar += delta;

		float progress = durationSoFar / durationToReachMillis;

		if (progress >= 1) {
			progress = 1;
		}

		if (progress < 0.75f) {

			// leftShoulderAngle = 0.2f * progress;
			rightShoulderAngle = -3f * progress;
			rightElbowAngle = -3f * progress;

			leftShoulderAngle = 0;
			leftElbowAngle = 0;

		} else {
			rightShoulderAngle = -9f * (1f - progress);
			rightElbowAngle = -9f * (1f - progress);

			leftShoulderAngle = 0;
			leftElbowAngle = 0;
		}

		if (backwards) {
			reverseAnimation();
		}

		if (progress >= 1) {
			target.showPow();
			rightShoulderAngle = 0;
			rightElbowAngle = 0;
			runCompletionAlgorightm();
		} else {
		}

	}

	@Override
	public void draw2() {

	}

	@Override
	public void draw1() {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawStaticUI() {
	}

	@Override
	public void draw3() {
		// TODO Auto-generated method stub
		
	}

}
