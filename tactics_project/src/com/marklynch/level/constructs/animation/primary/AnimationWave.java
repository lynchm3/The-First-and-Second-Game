package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.objects.GameObject;

public class AnimationWave extends Animation {

	GameObject target;

	public AnimationWave(GameObject performer, GameObject target) {
		super();
		this.target = target;
		durationToReach = 2000;

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
	public void update(double delta) {

		if (completed)
			return;

		durationSoFar += delta;

		float progress = durationSoFar / durationToReach;

		if (progress >= 1) {
			progress = 1;
		}

		if (progress < 0.2f) {

			rightShoulderAngle = -3.14f * progress * 5;
			rightElbowAngle = 0;

			leftShoulderAngle = 0;
			leftElbowAngle = 0;

		} else if (progress < 0.3f) {
			rightShoulderAngle = -3.14f - (progress - 0.2f) * 10;
			rightElbowAngle = 0;

			leftShoulderAngle = 0;
			leftElbowAngle = 0;
		} else if (progress < 0.4f) {
			rightShoulderAngle = -4.14f + (progress - 0.3f) * 10;
			rightElbowAngle = 0;

			leftShoulderAngle = 0;
			leftElbowAngle = 0;
		} else if (progress < 0.5f) {
			rightShoulderAngle = -3.14f + (progress - 0.4f) * 10;
			rightElbowAngle = 0;

			leftShoulderAngle = 0;
			leftElbowAngle = 0;
		} else if (progress < 0.6f) {
			rightShoulderAngle = -2.14f - (progress - 0.5f) * 10;
			rightElbowAngle = 0;

			leftShoulderAngle = 0;
			leftElbowAngle = 0;
		} else if (progress < 0.7f) {
			rightShoulderAngle = -3.14f - (progress - 0.6f) * 10;
			rightElbowAngle = 0;

			leftShoulderAngle = 0;
			leftElbowAngle = 0;
		} else if (progress < 0.8f) {
			rightShoulderAngle = -4.14f + (progress - 0.7f) * 10;
			rightElbowAngle = 0;

			leftShoulderAngle = 0;
			leftElbowAngle = 0;
		} else {
			rightShoulderAngle = -3.14f + 3.14f * (progress - 0.8f) * 5f;
			rightElbowAngle = 0;

			leftShoulderAngle = 0;
			leftElbowAngle = 0;

		}

		if (backwards) {
			reverseAnimtion();
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
