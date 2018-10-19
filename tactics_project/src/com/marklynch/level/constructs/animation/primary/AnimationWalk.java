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

	public AnimationWalk(GameObject performer, Square startSquare, Square endSquare, int walkingPhase) {
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

		if (walkingPhase == 0 || walkingPhase == 2) {

		} else {

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

		// this.phase = walkingPhase;
		// setAngles(0f);

		if (walkingPhase == 0) {
			setUpWalkPart0();

		} else if (walkingPhase == 1) {// 1 or 3
			setUpWalkPart1();

		} else if (walkingPhase == 2) {// 1 or 3
			setUpWalkPart0();
		} else {// 1 or 3
			setUpWalkPart1();
		}

	}

	@Override
	public void update(double delta) {
		keyFrameUpdate(delta);
	}

	public void setUpWalkPart0() {

		KeyFrame kf0 = new KeyFrame(performer, this);
		kf0.setAllSpeeds(0.001);
		kf0.offsetXSpeed = 0.01;
		kf0.offsetYSpeed = 0.1;
		kf0.offsetX = startOffsetX * 3 / 4;
		kf0.offsetY = startOffsetY * 3 / 4;
		kf0.torsoAngle = targetTorsoAngle0;
		kf0.rightHipAngle = targetRightHip0;
		kf0.rightKneeAngle = targetRightKnee0;
		kf0.leftHipAngle = targetLeftHip0;
		kf0.leftKneeAngle = targetLeftKnee0;
		kf0.leftElbowAngle = 0.1f;
		kf0.rightElbowAngle = 0.1f;
		kf0.normaliseSpeeds = true;
		keyFrames.add(kf0);

		KeyFrame kf1 = new KeyFrame(performer, this);
		kf1.setAllSpeeds(0.001);
		kf1.offsetXSpeed = 0.01;
		kf1.offsetYSpeed = 0.1;
		kf1.offsetX = startOffsetX * 2 / 4;
		kf1.offsetY = startOffsetY * 2 / 4;
		kf1.torsoAngle = targetTorsoAngle1;
		kf1.rightHipAngle = targetRightHip1;
		kf1.rightKneeAngle = targetRightKnee1;
		kf1.leftHipAngle = targetLeftHip1;
		kf1.leftKneeAngle = targetLeftKnee1;
		kf1.leftElbowAngle = 0.1f;
		kf1.rightElbowAngle = 0.1f;
		kf1.normaliseSpeeds = true;
		keyFrames.add(kf1);

		KeyFrame kf2 = new KeyFrame(performer, this);
		kf2.setAllSpeeds(0.001);
		kf2.offsetXSpeed = 0.01;
		kf2.offsetYSpeed = 0.1;
		kf2.offsetX = startOffsetX * 1 / 4;
		kf2.offsetY = startOffsetY * 1 / 4;
		kf2.torsoAngle = targetTorsoAngle2;
		kf2.rightHipAngle = targetRightHip2;
		kf2.rightKneeAngle = targetRightKnee2;
		kf2.leftHipAngle = targetLeftHip2;
		kf2.leftKneeAngle = targetLeftKnee2;
		kf2.leftElbowAngle = 0.1f;
		kf2.rightElbowAngle = 0.1f;
		kf2.normaliseSpeeds = true;
		keyFrames.add(kf2);

		KeyFrame kf3 = new KeyFrame(performer, this);
		kf3.setAllSpeeds(0.001);
		kf3.offsetXSpeed = 0.01;
		kf3.offsetYSpeed = 0.1;
		kf3.offsetX = startOffsetX * 0 / 4;
		kf3.offsetY = startOffsetY * 0 / 4;
		kf3.torsoAngle = targetTorsoAngle3;
		kf3.rightHipAngle = targetRightHip3;
		kf3.rightKneeAngle = targetRightKnee3;
		kf3.leftHipAngle = targetLeftHip3;
		kf3.leftKneeAngle = targetLeftKnee3;
		kf3.leftElbowAngle = 0.1f;
		kf3.rightElbowAngle = 0.1f;
		kf3.normaliseSpeeds = true;
		keyFrames.add(kf3);

	}

	public void setUpWalkPart1() {

		KeyFrame kf4 = new KeyFrame(performer, this);
		kf4.setAllSpeeds(0.001);
		kf4.offsetXSpeed = 0.01;
		kf4.offsetYSpeed = 0.1;
		kf4.offsetX = startOffsetX * 3 / 4;
		kf4.offsetY = startOffsetY * 3 / 4;
		kf4.torsoAngle = targetTorsoAngle4;
		kf4.rightHipAngle = targetRightHip4;
		kf4.rightKneeAngle = targetRightKnee4;
		kf4.leftHipAngle = targetLeftHip4;
		kf4.leftKneeAngle = targetLeftKnee4;
		kf4.leftElbowAngle = 0.1f;
		kf4.rightElbowAngle = 0.1f;
		kf4.normaliseSpeeds = true;
		keyFrames.add(kf4);

		KeyFrame kf5 = new KeyFrame(performer, this);
		kf5.setAllSpeeds(0.001);
		kf5.offsetXSpeed = 0.01;
		kf5.offsetYSpeed = 0.1;
		kf5.offsetX = startOffsetX * 2 / 4;
		kf5.offsetY = startOffsetY * 2 / 4;
		kf5.torsoAngle = targetTorsoAngle5;
		kf5.rightHipAngle = targetRightHip5;
		kf5.rightKneeAngle = targetRightKnee5;
		kf5.leftHipAngle = targetLeftHip5;
		kf5.leftKneeAngle = targetLeftKnee5;
		kf5.leftElbowAngle = 0.1f;
		kf5.rightElbowAngle = 0.1f;
		kf5.normaliseSpeeds = true;
		keyFrames.add(kf5);

		KeyFrame kf6 = new KeyFrame(performer, this);
		kf6.setAllSpeeds(0.001);
		kf6.offsetXSpeed = 0.01;
		kf6.offsetYSpeed = 0.1;
		kf6.offsetX = startOffsetX * 1 / 4;
		kf6.offsetY = startOffsetY * 1 / 4;
		kf6.torsoAngle = targetTorsoAngle6;
		kf6.rightHipAngle = targetRightHip6;
		kf6.rightKneeAngle = targetRightKnee6;
		kf6.leftHipAngle = targetLeftHip6;
		kf6.leftKneeAngle = targetLeftKnee6;
		kf6.leftElbowAngle = 0.1f;
		kf6.rightElbowAngle = 0.1f;
		kf6.normaliseSpeeds = true;
		keyFrames.add(kf6);

		KeyFrame kf7 = new KeyFrame(performer, this);
		kf7.setAllSpeeds(0.001);
		kf7.offsetXSpeed = 0.01;
		kf7.offsetYSpeed = 0.1;
		kf7.offsetX = startOffsetX * 0 / 4;
		kf7.offsetY = startOffsetY * 0 / 4;
		kf7.torsoAngle = targetTorsoAngle7;
		kf7.rightHipAngle = targetRightHip7;
		kf7.rightKneeAngle = targetRightKnee7;
		kf7.leftHipAngle = targetLeftHip7;
		kf7.leftKneeAngle = targetLeftKnee7;
		kf7.leftElbowAngle = 0.1f;
		kf7.rightElbowAngle = 0.1f;
		kf7.normaliseSpeeds = true;
		keyFrames.add(kf7);

	}

	// float frontLegBend = 0.35f;
	// float backLegBend = 0.10f;
	// float headBob = 0f;

	// int lastKeyFrame = -1;
	float headBobLastKeyFrame = 0;
	float torsoAngleFromLastKeyFrame = 0;
	float leftHipAngleFromLastKeyFrame = 0;
	float leftKneeAngleFromLastKeyFrame = 0;
	float rightHipAngleFromLastKeyFrame = 0;
	float rightKneeAngleFromLastKeyFrame = 0;

	@Override
	public void draw2() {

	}

	int keyFrame = 0;

	@Override
	public void draw1() {
		TextUtils.printTextWithImages(performer.squareGameObjectIsOn.xInGridPixels,
				performer.squareGameObjectIsOn.yInGridPixels, Integer.MAX_VALUE, false, null, Color.WHITE, "" + phase);
	}

	@Override
	public void drawStaticUI() {
	}

	@Override
	public void draw3() {
		// TODO Auto-generated method stub

	}

}
