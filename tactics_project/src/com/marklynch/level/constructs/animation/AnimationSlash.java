package com.marklynch.level.constructs.animation;

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

	public AnimationSlash(GameObject target) {
		super();
		this.target = target;
		durationToReach = 400;

		quarterDurationToReach = durationToReach / 4;
		halfDurationToReach = quarterDurationToReach + quarterDurationToReach;
		threeQuarterDurationToReach = halfDurationToReach + quarterDurationToReach;

		// this.startSquare = startSquare;
		// this.endSquare = endSquare;

		// startOffsetX = offsetX = (int) ((this.startSquare.xInGrid -
		// this.endSquare.xInGrid) * Game.SQUARE_WIDTH);
		// startOffsetY = offsetY = (int) ((this.startSquare.yInGrid -
		// this.endSquare.yInGrid) * Game.SQUARE_HEIGHT);
		blockAI = true;

	}

	@Override
	public void update(double delta) {

		if (completed)
			return;

		durationSoFar += delta;

		float progress = durationSoFar / durationToReach;

		if (progress < 0.75f) {

			// leftShoulderAngle = 0.2f * progress;
			rightShoulderAngle = -3f * progress;
			rightElbowAngle = -3f * progress;

			// leftShoulderAngle = -3f * progress;
			// leftElbowAngle = -4f * progress;

		} else {
			rightShoulderAngle = -9f * (1f - progress);
			rightElbowAngle = -9f * (1f - progress);

			// leftShoulderAngle = -9f * (1f - progress);
			// leftElbowAngle = -12f * (1f - progress);
		}

		if (progress >= 1) {
			target.showPow();
			rightShoulderAngle = 0;
			rightElbowAngle = 0;
			completed = true;
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

}
