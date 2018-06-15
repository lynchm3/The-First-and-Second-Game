package com.marklynch.level.constructs.animation.primary;

import com.marklynch.Game;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;

public class AnimationWalk extends Animation {

	public Square startSquare;
	public Square endSquare;
	public float startOffsetX = 0;
	public float startOffsetY = 0;

	float quarterDurationToReach;
	float halfDurationToReach;
	float threeQuarterDurationToReach;

	// 1
	float targetTorsoAngle1 = (float) Math.toRadians(11.4f);
	float targetLeftHip1 = (float) Math.toRadians(-19.13f);
	float targetLeftKnee1 = (float) Math.toRadians(13.86f);
	float targetRightHip1 = (float) Math.toRadians(-33.01f);
	float targetRightKnee1 = (float) Math.toRadians(75.21f);

	// 2
	float targetTorsoAngle2 = (float) Math.toRadians(8.9f);
	float targetLeftHip2 = (float) Math.toRadians(-2f);
	float targetLeftKnee2 = (float) Math.toRadians(-1f);
	float targetRightHip2 = (float) Math.toRadians(-34f);
	float targetRightKnee2 = (float) Math.toRadians(57f);

	// 3
	float targetTorsoAngle3 = (float) Math.toRadians(13.35f);
	float targetLeftHip3 = (float) Math.toRadians(-3f);
	float targetLeftKnee3 = (float) Math.toRadians(22f);
	float targetRightHip3 = (float) Math.toRadians(-36f);
	float targetRightKnee3 = (float) Math.toRadians(27f);

	// 4
	float targetTorsoAngle4 = (float) Math.toRadians(7.27f);
	float targetLeftHip4 = (float) Math.toRadians(-2f);
	float targetLeftKnee4 = (float) Math.toRadians(42f);
	float targetRightHip4 = (float) Math.toRadians(-40f);
	float targetRightKnee4 = (float) Math.toRadians(27f);

	// 5
	float targetTorsoAngle5 = (float) Math.toRadians(10.20f);
	float targetLeftHip5 = (float) Math.toRadians(15f);
	float targetLeftKnee5 = (float) Math.toRadians(22f);
	float targetRightHip5 = (float) Math.toRadians(-40f);
	float targetRightKnee5 = (float) Math.toRadians(0f);

	// 6
	float targetTorsoAngle6 = (float) Math.toRadians(8f);
	float targetLeftHip6 = (float) Math.toRadians(16f);
	float targetLeftKnee6 = (float) Math.toRadians(40f);
	float targetRightHip6 = (float) Math.toRadians(-40f);
	float targetRightKnee6 = (float) Math.toRadians(14f);

	// 7
	float targetTorsoAngle7 = (float) Math.toRadians(8.6f);
	float targetLeftHip7 = (float) Math.toRadians(5f);
	float targetLeftKnee7 = (float) Math.toRadians(40f);
	float targetRightHip7 = (float) Math.toRadians(-32f);
	float targetRightKnee7 = (float) Math.toRadians(17f);

	// 8
	float targetTorsoAngle8 = (float) Math.toRadians(10f);
	float targetLeftHip8 = (float) Math.toRadians(7f);
	float targetLeftKnee8 = (float) Math.toRadians(52f);
	float targetRightHip8 = (float) Math.toRadians(-26f);
	float targetRightKnee8 = (float) Math.toRadians(18f);

	float[] torsoKeyFrames = new float[] { targetTorsoAngle1, targetTorsoAngle2, targetTorsoAngle3, targetTorsoAngle4,
			targetTorsoAngle5, targetTorsoAngle6, targetTorsoAngle7, targetTorsoAngle8 };
	float[] leftHipKeyFrames = new float[] { targetLeftHip1, targetLeftHip2, targetLeftHip3, targetLeftHip4,
			targetLeftHip5, targetLeftHip6, targetLeftHip7, targetLeftHip8 };
	float[] leftKneeKeyFrames = new float[] { targetLeftKnee1, targetLeftKnee2, targetLeftKnee3, targetLeftKnee4,
			targetLeftKnee5, targetLeftKnee6, targetLeftKnee7, targetLeftKnee8 };
	float[] rightHipKeyFrames = new float[] { targetRightHip1, targetRightHip2, targetRightHip3, targetRightHip4,
			targetRightHip5, targetRightHip6, targetRightHip7, targetRightHip8 };
	float[] rightKneeKeyFrames = new float[] { targetRightKnee1, targetRightKnee2, targetRightKnee3, targetRightKnee4,
			targetRightKnee5, targetRightKnee6, targetRightKnee7, targetRightKnee8 };

	// for show only, walking actor, primary

	public AnimationWalk(GameObject performer, Square startSquare, Square endSquare, int phase) {
		super(performer);
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

	public AnimationWalk(int startX, int startY, int endX, int endY) {
		super(null);

		setAngles(0f);
	}

	public AnimationWalk(float startX, float startY, float endX, float endY) {
		super(null);
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

		if (getCompleted())
			return;
		super.update(delta);

		durationSoFar += delta;

		float progress = durationSoFar / durationToReach;

		if (progress >= 1) {
			progress = 1;
		}

		float torsoAngleChange = (float) (0.05d * delta);
		// offsetY = moveTowardsTargetAngleInRadians(offsetY, offsetYChange, 0);
		// offsetY = moveTowardsTargetAngleInRadians(offsetY, offsetYChange, 0);
		torsoAngle = moveTowardsTargetAngleInRadians(torsoAngleChange, torsoAngleChange, 0);

		setAngles(progress);

		if (progress >= 1) {
			runCompletionAlgorightm();
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
	float headBob = 0f;

	public void setAngles(float progress) {

		// arms
		int oldKeyFrame = 0;
		int newKeyFrame = 0;
		float intermediateProgress = 0;
		if (phase == 0) {
			// key frames 1,2,3,4
			if (progress < 0.25f) {
				// key frame 1
				oldKeyFrame = 7;
				newKeyFrame = 0;
				intermediateProgress = progress * 4;

			} else if (progress < 0.5f) {
				// key frame 2
				oldKeyFrame = 0;
				newKeyFrame = 1;
				intermediateProgress = (progress - 0.25f) * 4;
			} else if (progress < 0.75f) {
				// key frame 3
				oldKeyFrame = 1;
				newKeyFrame = 2;
				intermediateProgress = (progress - 0.5f) * 4;
			} else if (progress <= 1f) {
				// key frame 4
				oldKeyFrame = 2;
				newKeyFrame = 3;
				intermediateProgress = (progress - 0.75f) * 4;
			}

			leftShoulderAngle = 0.2f * progress;
			rightShoulderAngle = -leftShoulderAngle;

		} else if (phase == 1) {
			// key frames 4,5,6,7
			if (progress < 0.25f) {
				// key frame 1
				oldKeyFrame = 3;
				newKeyFrame = 4;
				intermediateProgress = progress * 4;

			} else if (progress < 0.5f) {
				// key frame 2
				oldKeyFrame = 4;
				newKeyFrame = 5;
				intermediateProgress = (progress - 0.25f) * 4;
			} else if (progress < 0.75f) {
				// key frame 3
				oldKeyFrame = 5;
				newKeyFrame = 6;
				intermediateProgress = (progress - 0.5f) * 4;
			} else if (progress <= 1f) {
				// key frame 4
				oldKeyFrame = 6;
				newKeyFrame = 7;
				intermediateProgress = (progress - 0.75f) * 4;
			}
			leftShoulderAngle = 0.2f * (1f - progress);
			rightShoulderAngle = -leftShoulderAngle;

		} else if (phase == 2) {
			leftShoulderAngle = 0.2f * -progress;
			rightShoulderAngle = -leftShoulderAngle;
		} else if (phase == 3) {
			leftShoulderAngle = 0.2f * (progress - 1f);
			rightShoulderAngle = -leftShoulderAngle;

		}
		torsoAngle = torsoKeyFrames[oldKeyFrame]
				+ intermediateProgress * (torsoKeyFrames[newKeyFrame] - torsoKeyFrames[oldKeyFrame]);
		leftHipAngle = leftHipKeyFrames[oldKeyFrame]
				+ intermediateProgress * (leftHipKeyFrames[newKeyFrame] - leftHipKeyFrames[oldKeyFrame]);
		leftKneeAngle = leftKneeKeyFrames[oldKeyFrame]
				+ intermediateProgress * (leftKneeKeyFrames[newKeyFrame] - leftKneeKeyFrames[oldKeyFrame]);
		rightHipAngle = rightHipKeyFrames[oldKeyFrame]
				+ intermediateProgress * (rightHipKeyFrames[newKeyFrame] - rightHipKeyFrames[oldKeyFrame]);
		rightKneeAngle = rightKneeKeyFrames[oldKeyFrame]
				+ intermediateProgress * (rightKneeKeyFrames[newKeyFrame] - rightKneeKeyFrames[oldKeyFrame]);

		leftElbowAngle = -0.1f;
		rightElbowAngle = -0.1f;

		if (backwards) {

			reverseAnimation();
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
