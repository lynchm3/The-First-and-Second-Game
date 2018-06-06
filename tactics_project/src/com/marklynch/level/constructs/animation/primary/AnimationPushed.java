package com.marklynch.level.constructs.animation.primary;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;

public class AnimationPushed extends Animation {

	public GameObject performer;
	public Square startSquare;
	public Square endSquare;
	public float startOffsetX = 0;
	public float startOffsetY = 0;

	float quarterDurationToReach;
	float halfDurationToReach;
	float threeQuarterDurationToReach;

	// for show only, walking actor, primary

	public AnimationPushed(GameObject performer, Square startSquare, Square endSquare) {
		super();
		durationToReach = 4000;

		quarterDurationToReach = durationToReach / 4;
		halfDurationToReach = quarterDurationToReach + quarterDurationToReach;
		threeQuarterDurationToReach = halfDurationToReach + quarterDurationToReach;

		this.startSquare = startSquare;
		this.endSquare = endSquare;

		startOffsetX = offsetX = (int) ((this.startSquare.xInGrid - this.endSquare.xInGrid) * Game.SQUARE_WIDTH);
		startOffsetY = offsetY = (int) ((this.startSquare.yInGrid - this.endSquare.yInGrid) * Game.SQUARE_HEIGHT);

		backwards = performer.backwards;

		float deltaX = endSquare.xInGrid - startSquare.xInGrid;
		float deltaY = endSquare.yInGrid - startSquare.yInGrid;
		double targetLimbRadians = Math.atan2(deltaY, deltaX);
		targetLimbDegrees = (float) Math.toDegrees(targetLimbRadians);
		targetLimbDegrees = (float) Math.atan2(endSquare.yInGrid - startSquare.yInGrid,
				endSquare.xInGrid - startSquare.xInGrid) + 1.5708f;
		// System.out.println("deltaX = " + deltaX);
		// System.out.println("deltaY = " + deltaY);
		System.out.println("targetLimbDegrees = " + targetLimbDegrees);

		blockAI = true;

		if (blockAI)
			Level.blockingAnimations.add(this);
	}

	float targetLimbDegrees = 0;

	@Override
	public void update(double delta) {

		if (completed)
			return;

		durationSoFar += delta;

		float progress = durationSoFar / durationToReach;

		if (progress >= 1) {
			progress = 1;
		}

		float angleChange = 1;

		leftShoulderAngle = targetLimbDegrees; // = moveTowardsTargetAngleInDegrees(leftShoulderAngle, angleChange,
												// targetLimbDegrees);
		rightShoulderAngle = targetLimbDegrees;// = moveTowardsTargetAngleInDegrees(rightShoulderAngle, angleChange,
												// targetLimbDegrees);
		leftElbowAngle = 0;// = moveTowardsTargetAngleInDegrees(leftElbowAngle, angleChange,
							// targetLimbDegrees);
		rightElbowAngle = 0;// = moveTowardsTargetAngleInDegrees(rightElbowAngle, angleChange,
							// targetLimbDegrees);

		leftHipAngle = targetLimbDegrees;

		rightHipAngle = targetLimbDegrees;

		leftKneeAngle = 0;

		rightKneeAngle = 0;

		if (progress >= 1) {
			completed = true;
			if (blockAI)
				Level.blockingAnimations.remove(this);
			offsetX = 0;
			offsetY = 0f;
		} else {
			offsetX = (int) (startOffsetX * (1 - progress));
			offsetY = (int) (startOffsetY * (1 - progress));
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
