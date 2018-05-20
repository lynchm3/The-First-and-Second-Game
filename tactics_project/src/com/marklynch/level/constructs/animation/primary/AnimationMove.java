package com.marklynch.level.constructs.animation.primary;

import com.marklynch.Game;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;

public class AnimationMove extends Animation {

	public Square startSquare;
	public Square endSquare;
	public float startOffsetX = 0;
	public float startOffsetY = 0;

	float quarterDurationToReach;
	float halfDurationToReach;
	float threeQuarterDurationToReach;

	// for show only, walking actor, primary

	public AnimationMove(GameObject performer, Square startSquare, Square endSquare, int phase) {
		super();
		durationToReach = 400;

		quarterDurationToReach = durationToReach / 4;
		halfDurationToReach = quarterDurationToReach + quarterDurationToReach;
		threeQuarterDurationToReach = halfDurationToReach + quarterDurationToReach;

		this.startSquare = startSquare;
		this.endSquare = endSquare;

		startOffsetX = offsetX = (int) ((this.startSquare.xInGrid - this.endSquare.xInGrid) * Game.SQUARE_WIDTH);
		startOffsetY = offsetY = (int) ((this.startSquare.yInGrid - this.endSquare.yInGrid) * Game.SQUARE_HEIGHT);

		if (phase == 0 || phase == 2) {
			offsetY += 0f;
		} else {
			offsetY += headBob;

		}

		backwards = performer.backwards;

		blockAI = false;

		this.phase = phase;
		setAngles(0f);
	}

	public AnimationMove(int startX, int startY, int endX, int endY) {

		setAngles(0f);
	}

	public AnimationMove(float startX, float startY, float endX, float endY) {
		super();
		durationToReach = 400;
		startOffsetX = offsetX = startX - endX;
		startOffsetY = offsetY = startY - endY;
		if (phase == 0 || phase == 2) {
			offsetY += 0f;
		} else {
			offsetY += headBob;

		}
		blockAI = false;
		setAngles(0f);

	}

	@Override
	public void update(double delta) {

		if (completed)
			return;

		durationSoFar += delta;

		// if (durationSoFar < quarterDurationToReach) {
		// rightShoulderAngle = -leftShoulderAngle;
		// } else if (durationSoFar < halfDurationToReach) {
		// leftShoulderAngle = 0.001f * (halfDurationToReach - durationSoFar);
		// rightShoulderAngle = -leftShoulderAngle;
		// } else if (durationSoFar < threeQuarterDurationToReach) {
		// leftShoulderAngle = 0.001f * (halfDurationToReach - durationSoFar);
		// rightShoulderAngle = -leftShoulderAngle;
		// } else {
		// leftShoulderAngle = 0.001f * (durationSoFar - durationToReach);
		// rightShoulderAngle = -leftShoulderAngle;
		// }

		float progress = durationSoFar / durationToReach;

		if (progress >= 1) {
			progress = 1;
		}

		setAngles(progress);

		if (progress >= 1) {
			completed = true;
			offsetX = 0;
			// offsetY = 0;
			if (phase == 0 || phase == 2) {
				offsetY = headBob;
			} else {
				offsetY = 0f;

			}
		} else {
			offsetX = (int) (startOffsetX * (1 - progress));
			offsetY = (int) (startOffsetY * (1 - progress));
			if (phase == 0 || phase == 2) {
				offsetY += headBob * progress;
			} else {
				offsetY += headBob * (1f - progress);

			}
		}
	}

	float frontLegBend = 0.35f;
	float backLegBend = 0.10f;
	float headBob = 2f;

	public void setAngles(float progress) {

		if (phase == 0) {
			leftShoulderAngle = 0.2f * progress;
			rightShoulderAngle = -leftShoulderAngle;

			leftHipAngle = backLegBend * progress;
			leftKneeAngle = backLegBend * progress;

			rightHipAngle = -frontLegBend * progress;
			rightKneeAngle = frontLegBend * progress;

		} else if (phase == 1) {
			leftShoulderAngle = 0.2f * (1f - progress);
			rightShoulderAngle = -leftShoulderAngle;

			leftHipAngle = backLegBend * (1f - progress);
			leftKneeAngle = backLegBend * (1f - progress);

			rightHipAngle = -frontLegBend * (1f - progress);
			rightKneeAngle = frontLegBend * (1f - progress);

		} else if (phase == 2) {
			leftShoulderAngle = 0.2f * -progress;
			rightShoulderAngle = -leftShoulderAngle;

			leftHipAngle = -frontLegBend * progress;
			leftKneeAngle = frontLegBend * progress;

			rightHipAngle = backLegBend * progress;
			rightKneeAngle = backLegBend * progress;

		} else if (phase == 3) {
			leftShoulderAngle = 0.2f * (progress - 1f);
			rightShoulderAngle = -leftShoulderAngle;

			leftHipAngle = -frontLegBend * (1f - progress);
			leftKneeAngle = frontLegBend * (1f - progress);

			rightHipAngle = backLegBend * (1f - progress);
			rightKneeAngle = backLegBend * (1f - progress);

		}

		leftElbowAngle = -0.1f;
		rightElbowAngle = -0.1f;

		if (backwards) {

			reverseAnimtion();
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
