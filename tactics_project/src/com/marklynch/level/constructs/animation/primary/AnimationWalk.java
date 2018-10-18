package com.marklynch.level.constructs.animation.primary;

import com.marklynch.Game;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.Color;
import com.marklynch.utils.TextUtils;

public class AnimationWalk extends Animation {

	public Square startSquare;
	public Square endSquare;
	public float startOffsetX = 0;
	public float startOffsetY = 0;
	public float headBob = 0;

	float quarterDurationToReach;
	float halfDurationToReach;
	float threeQuarterDurationToReach;

	// 1
	static final float targetOffsetY0 = 0;
	static final float targetTorsoAngle0 = (float) Math.toRadians(7f);
	static final float targetLeftHip0 = (float) Math.toRadians(-10f);
	static final float targetLeftKnee0 = (float) Math.toRadians(7f);
	static final float targetRightHip0 = (float) Math.toRadians(-16f);
	static final float targetRightKnee0 = (float) Math.toRadians(37f);

	// 2
	static final float targetOffsetY1 = 1f;
	static final float targetTorsoAngle1 = (float) Math.toRadians(7f);
	static final float targetLeftHip1 = (float) Math.toRadians(-2f);
	static final float targetLeftKnee1 = (float) Math.toRadians(-1f);
	static final float targetRightHip1 = (float) Math.toRadians(-17f);
	static final float targetRightKnee1 = (float) Math.toRadians(28f);

	// 3
	static final float targetOffsetY2 = 2f;
	static final float targetTorsoAngle2 = (float) Math.toRadians(7f);
	static final float targetLeftHip2 = (float) Math.toRadians(-3f);
	static final float targetLeftKnee2 = (float) Math.toRadians(11f);
	static final float targetRightHip2 = (float) Math.toRadians(-18f);
	static final float targetRightKnee2 = (float) Math.toRadians(14f);

	// 4
	static final float targetOffsetY3 = 3f;
	static final float targetTorsoAngle3 = (float) Math.toRadians(7f);
	static final float targetLeftHip3 = (float) Math.toRadians(-2f);
	static final float targetLeftKnee3 = (float) Math.toRadians(21f);
	static final float targetRightHip3 = (float) Math.toRadians(-20f);
	static final float targetRightKnee3 = (float) Math.toRadians(13f);

	// 5
	static final float targetOffsetY4 = 3f;
	static final float targetTorsoAngle4 = (float) Math.toRadians(7f);
	static final float targetLeftHip4 = (float) Math.toRadians(7f);
	static final float targetLeftKnee4 = (float) Math.toRadians(11f);
	static final float targetRightHip4 = (float) Math.toRadians(-20f);
	static final float targetRightKnee4 = (float) Math.toRadians(0f);

	// 6
	static final float targetOffsetY5 = 2f;
	static final float targetTorsoAngle5 = (float) Math.toRadians(7f);
	static final float targetLeftHip5 = (float) Math.toRadians(8f);
	static final float targetLeftKnee5 = (float) Math.toRadians(20f);
	static final float targetRightHip5 = (float) Math.toRadians(-20f);
	static final float targetRightKnee5 = (float) Math.toRadians(7f);

	// 7
	static final float targetOffsetY6 = 1f;
	static final float targetTorsoAngle6 = (float) Math.toRadians(7f);
	static final float targetLeftHip6 = (float) Math.toRadians(5f);
	static final float targetLeftKnee6 = (float) Math.toRadians(20f);
	static final float targetRightHip6 = (float) Math.toRadians(-16f);
	static final float targetRightKnee6 = (float) Math.toRadians(8f);

	// 8
	static final float targetOffsetY7 = 0f;
	static final float targetTorsoAngle7 = (float) Math.toRadians(7f);
	static final float targetLeftHip7 = (float) Math.toRadians(4f);
	static final float targetLeftKnee7 = (float) Math.toRadians(26f);
	static final float targetRightHip7 = (float) Math.toRadians(-13f);
	static final float targetRightKnee7 = (float) Math.toRadians(9f);

	// float[] targetHeadBobYFrames = new float[] { targetOffsetY1, targetOffsetY2,
	// targetOffsetY3, targetOffsetY4,
	// targetOffsetY5, targetOffsetY6, targetOffsetY7, targetOffsetY8 };
	// float[] torsoKeyFrames = new float[] { targetTorsoAngle1, targetTorsoAngle2,
	// targetTorsoAngle3, targetTorsoAngle4,
	// targetTorsoAngle5, targetTorsoAngle6, targetTorsoAngle7, targetTorsoAngle8 };
	// float[] leftHipKeyFrames = new float[] { targetLeftHip1, targetLeftHip2,
	// targetLeftHip3, targetLeftHip4,
	// targetLeftHip5, targetLeftHip6, targetLeftHip7, targetLeftHip8 };
	// float[] leftKneeKeyFrames = new float[] { targetLeftKnee1, targetLeftKnee2,
	// targetLeftKnee3, targetLeftKnee4,
	// targetLeftKnee5, targetLeftKnee6, targetLeftKnee7, targetLeftKnee8 };
	// float[] rightHipKeyFrames = new float[] { targetRightHip1, targetRightHip2,
	// targetRightHip3, targetRightHip4,
	// targetRightHip5, targetRightHip6, targetRightHip7, targetRightHip8 };
	// float[] rightKneeKeyFrames = new float[] { targetRightKnee1,
	// targetRightKnee2, targetRightKnee3, targetRightKnee4,
	// targetRightKnee5, targetRightKnee6, targetRightKnee7, targetRightKnee8 };

	// for show only, walking actor, primary

	public AnimationWalk(GameObject performer, Square startSquare, Square endSquare, int phase) {
		super(performer, performer, endSquare);
		if (!runAnimation)
			return;
		durationToReachMillis = 400;

		quarterDurationToReach = durationToReachMillis / 4;
		halfDurationToReach = quarterDurationToReach + quarterDurationToReach;
		threeQuarterDurationToReach = halfDurationToReach + quarterDurationToReach;

		this.startSquare = startSquare;
		this.endSquare = endSquare;

		startOffsetX = offsetX = (int) ((this.startSquare.xInGrid - this.endSquare.xInGrid) * Game.SQUARE_WIDTH);
		startOffsetY = offsetY = (int) ((this.startSquare.yInGrid - this.endSquare.yInGrid) * Game.SQUARE_HEIGHT);

		// if (phase == 0 || phase == 2) {
		// offsetY += 0f;
		// } else {
		// offsetY += headBob;
		//
		// }

		if (phase == 0 || phase == 2) {
			lastKeyFrame = 0;
		} else {
			lastKeyFrame = 4;

		}

		backwards = performer.backwards;
		headBobLastKeyFrame = 0;
		if (performer.getPrimaryAnimation() instanceof AnimationWalk) {
			headBobLastKeyFrame = ((AnimationWalk) performer.getPrimaryAnimation()).headBob;
		}

		torsoAngleFromLastKeyFrame = performer.getPrimaryAnimation().torsoAngle;
		leftHipAngleFromLastKeyFrame = performer.getPrimaryAnimation().leftHipAngle;
		leftKneeAngleFromLastKeyFrame = performer.getPrimaryAnimation().leftKneeAngle;
		rightHipAngleFromLastKeyFrame = performer.getPrimaryAnimation().rightHipAngle;
		rightKneeAngleFromLastKeyFrame = performer.getPrimaryAnimation().rightKneeAngle;
		// if (backwards) {
		// torsoAngleFromLastKeyFrame = -torsoAngleFromLastKeyFrame;
		//
		// float temp = rightHipAngleFromLastKeyFrame;
		// rightHipAngleFromLastKeyFrame = -leftHipAngleFromLastKeyFrame;
		// leftHipAngleFromLastKeyFrame = -temp;
		//
		// temp = rightKneeAngleFromLastKeyFrame;
		// rightKneeAngleFromLastKeyFrame = -leftKneeAngleFromLastKeyFrame;
		// leftKneeAngleFromLastKeyFrame = -temp;
		// }

		blockAI = false;

		this.phase = phase;
		setAngles(0f);

		KeyFrame kf0 = new KeyFrame(performer, this);
		kf0.setAllSpeeds(1);
		kf0.torsoAngle = targetTorsoAngle0;
		kf0.rightHipAngle = targetRightHip0;
		kf0.rightKneeAngle = targetRightKnee0;
		kf0.leftHipAngle = targetLeftHip0;
		kf0.leftKneeAngle = targetLeftKnee0;
		keyFrames.add(kf0);

		KeyFrame kf1 = new KeyFrame(performer, this);
		kf1.setAllSpeeds(1);
		kf1.torsoAngle = targetTorsoAngle1;
		kf1.rightHipAngle = targetRightHip1;
		kf1.rightKneeAngle = targetRightKnee1;
		kf1.leftHipAngle = targetLeftHip1;
		kf1.leftKneeAngle = targetLeftKnee1;
		keyFrames.add(kf1);

		KeyFrame kf2 = new KeyFrame(performer, this);
		kf2.setAllSpeeds(1);
		kf2.torsoAngle = targetTorsoAngle2;
		kf2.rightHipAngle = targetRightHip2;
		kf2.rightKneeAngle = targetRightKnee2;
		kf2.leftHipAngle = targetLeftHip2;
		kf2.leftKneeAngle = targetLeftKnee2;
		keyFrames.add(kf2);

		KeyFrame kf3 = new KeyFrame(performer, this);
		kf3.setAllSpeeds(1);
		kf3.torsoAngle = targetTorsoAngle3;
		kf3.rightHipAngle = targetRightHip3;
		kf3.rightKneeAngle = targetRightKnee3;
		kf3.leftHipAngle = targetLeftHip3;
		kf3.leftKneeAngle = targetLeftKnee3;
		keyFrames.add(kf3);

		KeyFrame kf4 = new KeyFrame(performer, this);
		kf4.setAllSpeeds(1);
		kf4.torsoAngle = targetTorsoAngle4;
		kf4.rightHipAngle = targetRightHip4;
		kf4.rightKneeAngle = targetRightKnee4;
		kf4.leftHipAngle = targetLeftHip4;
		kf4.leftKneeAngle = targetLeftKnee4;
		keyFrames.add(kf4);

		KeyFrame kf5 = new KeyFrame(performer, this);
		kf5.setAllSpeeds(1);
		kf5.torsoAngle = targetTorsoAngle5;
		kf5.rightHipAngle = targetRightHip5;
		kf5.rightKneeAngle = targetRightKnee5;
		kf5.leftHipAngle = targetLeftHip5;
		kf5.leftKneeAngle = targetLeftKnee5;
		keyFrames.add(kf5);

		KeyFrame kf6 = new KeyFrame(performer, this);
		kf6.setAllSpeeds(1);
		kf6.torsoAngle = targetTorsoAngle6;
		kf6.rightHipAngle = targetRightHip6;
		kf6.rightKneeAngle = targetRightKnee6;
		kf6.leftHipAngle = targetLeftHip6;
		kf6.leftKneeAngle = targetLeftKnee6;
		keyFrames.add(kf6);

		KeyFrame kf7 = new KeyFrame(performer, this);
		kf7.setAllSpeeds(1);
		kf7.torsoAngle = targetTorsoAngle7;
		kf7.rightHipAngle = targetRightHip7;
		kf7.rightKneeAngle = targetRightKnee7;
		kf7.leftHipAngle = targetLeftHip7;
		kf7.leftKneeAngle = targetLeftKnee7;
		keyFrames.add(kf7);

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

		// float torsoAngleChange = (float) (0.05d * delta);
		// offsetY = moveTowardsTargetAngleInRadians(offsetY, offsetYChange, 0);
		// offsetY = moveTowardsTargetAngleInRadians(offsetY, offsetYChange, 0);
		// torsoAngle = moveTowardsTargetAngleInRadians(torsoAngleChange,
		// torsoAngleChange, 0);

		if (progress >= 1) {
			runCompletionAlgorightm(true);
			offsetX = 0;
		} else {
			offsetX = (int) (startOffsetX * (1 - progress));
			offsetY = (int) (startOffsetY * (1 - progress));
		}

		setAngles(progress);
	}

	// float frontLegBend = 0.35f;
	// float backLegBend = 0.10f;
	// float headBob = 0f;

	int lastKeyFrame = -1;
	float headBobLastKeyFrame = 0;
	float torsoAngleFromLastKeyFrame = 0;
	float leftHipAngleFromLastKeyFrame = 0;
	float leftKneeAngleFromLastKeyFrame = 0;
	float rightHipAngleFromLastKeyFrame = 0;
	float rightKneeAngleFromLastKeyFrame = 0;

	public void setAngles(float progress) {

		// arms
		float intermediateProgress = 0;
		if (phase == 0 || phase == 2) {
			// key frames 1,2,3,4
			if (progress < 0.25f) {
				// key frame 1
				keyFrame = 0;
				intermediateProgress = progress * 4;

			} else if (progress < 0.5f) {
				// key frame 2
				keyFrame = 1;
				intermediateProgress = (progress - 0.25f) * 4;
			} else if (progress < 0.75f) {
				// key frame 3
				keyFrame = 2;
				intermediateProgress = (progress - 0.5f) * 4;
			} else if (progress <= 1f) {
				// key frame 4
				keyFrame = 3;
				intermediateProgress = (progress - 0.75f) * 4;
			}

		} else if (phase == 1 || phase == 3) {
			// key frames 5,6,7,8
			if (progress < 0.25f) {
				// key frame 5
				keyFrame = 4;
				intermediateProgress = progress * 4;

			} else if (progress < 0.5f) {
				// key frame 6
				keyFrame = 5;
				intermediateProgress = (progress - 0.25f) * 4;
			} else if (progress < 0.75f) {
				// key frame 7
				keyFrame = 6;
				intermediateProgress = (progress - 0.5f) * 4;
			} else if (progress <= 1f) {
				// key frame 8
				keyFrame = 7;
				intermediateProgress = (progress - 0.75f) * 4;
			}

		}

		if (phase == 0) {

			leftShoulderAngle = 0.2f * progress;
			rightShoulderAngle = -leftShoulderAngle;

		}

		else if (phase == 1) {
			leftShoulderAngle = 0.2f * (1f - progress);
			rightShoulderAngle = -leftShoulderAngle;

		}

		else if (phase == 2) {
			leftShoulderAngle = 0.2f * -progress;
			rightShoulderAngle = -leftShoulderAngle;
		} else if (phase == 3) {
			leftShoulderAngle = 0.2f * (progress - 1f);
			rightShoulderAngle = -leftShoulderAngle;

		}

		if (lastKeyFrame != keyFrame) {
			lastKeyFrame = keyFrame;
			headBobLastKeyFrame = 0;
			if (performer.getPrimaryAnimation() instanceof AnimationWalk) {
				headBobLastKeyFrame = ((AnimationWalk) performer.getPrimaryAnimation()).headBob;
			}
			torsoAngleFromLastKeyFrame = performer.getPrimaryAnimation().torsoAngle;
			leftHipAngleFromLastKeyFrame = performer.getPrimaryAnimation().leftHipAngle;
			leftKneeAngleFromLastKeyFrame = performer.getPrimaryAnimation().leftKneeAngle;
			rightHipAngleFromLastKeyFrame = performer.getPrimaryAnimation().rightHipAngle;
			rightKneeAngleFromLastKeyFrame = performer.getPrimaryAnimation().rightKneeAngle;

			// if (backwards) {
			// torsoAngleFromLastKeyFrame = -torsoAngleFromLastKeyFrame;
			//
			// float temp = rightHipAngleFromLastKeyFrame;
			// rightHipAngleFromLastKeyFrame = -leftHipAngleFromLastKeyFrame;
			// leftHipAngleFromLastKeyFrame = -temp;
			//
			// temp = rightKneeAngleFromLastKeyFrame;
			// rightKneeAngleFromLastKeyFrame = -leftKneeAngleFromLastKeyFrame;
			// leftKneeAngleFromLastKeyFrame = -temp;
			// }
		}

		float headBobFromCurrentKeyFrame = targetHeadBobYFrames[keyFrame];
		float torsoAngleFormCurrentKeyFrame = torsoKeyFrames[keyFrame];
		float leftHipAngleFormCurrentKeyFrame = leftHipKeyFrames[keyFrame];
		float leftKneeAngleFormCurrentKeyFrame = leftKneeKeyFrames[keyFrame];
		float rightHipAngleFormCurrentKeyFrame = rightHipKeyFrames[keyFrame];
		float rightKneeAngleFormCurrentKeyFrame = rightKneeKeyFrames[keyFrame];

		// if (phase == 2 || phase == 4) {

		float temp = leftHipAngleFormCurrentKeyFrame;
		leftHipAngleFormCurrentKeyFrame = rightHipAngleFormCurrentKeyFrame;
		rightHipAngleFormCurrentKeyFrame = temp;

		temp = leftKneeAngleFormCurrentKeyFrame;
		leftKneeAngleFormCurrentKeyFrame = rightKneeAngleFormCurrentKeyFrame;
		rightKneeAngleFormCurrentKeyFrame = temp;

		// }

		if (backwards) {
			torsoAngleFormCurrentKeyFrame = -torsoAngleFormCurrentKeyFrame;

			temp = rightHipAngleFormCurrentKeyFrame;
			rightHipAngleFormCurrentKeyFrame = -leftHipAngleFormCurrentKeyFrame;
			leftHipAngleFormCurrentKeyFrame = -temp;

			temp = rightKneeAngleFormCurrentKeyFrame;
			rightKneeAngleFormCurrentKeyFrame = -leftKneeAngleFormCurrentKeyFrame;
			leftKneeAngleFormCurrentKeyFrame = -temp;
		}

		headBob = headBobLastKeyFrame + intermediateProgress * (headBobFromCurrentKeyFrame - headBobLastKeyFrame);
		offsetY += headBob;

		torsoAngle = torsoAngleFromLastKeyFrame
				+ intermediateProgress * (torsoAngleFormCurrentKeyFrame - torsoAngleFromLastKeyFrame);
		leftHipAngle = leftHipAngleFromLastKeyFrame
				+ intermediateProgress * (leftHipAngleFormCurrentKeyFrame - leftHipAngleFromLastKeyFrame);
		leftKneeAngle = leftKneeAngleFromLastKeyFrame
				+ intermediateProgress * (leftKneeAngleFormCurrentKeyFrame - leftKneeAngleFromLastKeyFrame);
		rightHipAngle = rightHipAngleFromLastKeyFrame
				+ intermediateProgress * (rightHipAngleFormCurrentKeyFrame - rightHipAngleFromLastKeyFrame);
		rightKneeAngle = rightKneeAngleFromLastKeyFrame
				+ intermediateProgress * (rightKneeAngleFormCurrentKeyFrame - rightKneeAngleFromLastKeyFrame);

		// if (phase == 2 || phase == 4) {
		//
		//
		// float temp = leftHipAngle;
		// leftHipAngle = rightHipAngle;
		// rightHipAngle = temp;
		//
		// temp = leftKneeAngle;
		// leftKneeAngle = rightKneeAngle;
		// rightKneeAngle = temp;
		//
		// }
		if (backwards) {

			leftElbowAngle = 0.1f;
			rightElbowAngle = 0.1f;
		} else {

			leftElbowAngle = -0.1f;
			rightElbowAngle = -0.1f;

		}

		// if (backwards) {
		//
		// reverseAnimation();
		// }

	}

	@Override
	public void draw2() {

	}

	int keyFrame = 0;

	@Override
	public void draw1() {
		TextUtils.printTextWithImages(performer.squareGameObjectIsOn.xInGridPixels,
				performer.squareGameObjectIsOn.yInGridPixels, Integer.MAX_VALUE, false, null, Color.WHITE,
				"" + keyFrame);
	}

	@Override
	public void drawStaticUI() {
	}

	@Override
	public void draw3() {
		// TODO Auto-generated method stub

	}

}
