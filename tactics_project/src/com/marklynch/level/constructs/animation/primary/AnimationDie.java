package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.objects.actors.Human;
import com.marklynch.objects.inanimateobjects.GameObject;

public class AnimationDie extends Animation {

	float targetOffsetY = 64f;
	float targetOffsetX = 32f;
	float targetTorsoAngle = 1.57f;// 3.14/2

	public AnimationDie(GameObject performer, OnCompletionListener onCompletionListener) {
		super(performer, onCompletionListener, null, null, null, null, null, null, true, true, performer);
		if (!runAnimation)
			return;
		// durationToReach = 400;
		blockAI = true;
		backwards = performer.backwards;

		if (!(performer instanceof Human)) {
			targetOffsetX = 0f;
			targetOffsetY = 0f;
			targetTorsoAngle = 1.57f;
		} else if (Math.random() >= 0.5) {
			targetOffsetX = (float) ((0f) - Math.random() * 16f);
			targetTorsoAngle = 1.57f;
		} else {
			targetOffsetX = (float) ((0f) + Math.random() * 16f);
			targetTorsoAngle = -1.57f;

		}
		targetOffsetY = (float) (32f - Math.random() * 16f);

		KeyFrame kf0 = new KeyFrame(performer, this);

		kf0.offsetX = targetOffsetX;
		kf0.offsetY = targetOffsetY;
		kf0.headToToeOffset = performer.width - 6 - performer.height; // the - 6 is a guess/fluff
		kf0.torsoAngle = targetTorsoAngle;
		kf0.leftElbowAngle = 0;
		kf0.rightElbowAngle = 0;
		kf0.leftShoulderAngle = 0;
		kf0.rightShoulderAngle = 0;
		kf0.leftHipAngle = 0;
		kf0.rightHipAngle = 0;
		kf0.leftKneeAngle = 0;
		kf0.rightKneeAngle = 0;
		// kf0.setAllSpeeds(0.004d);
		// kf0.offsetXSpeed = 0.5d;
		// kf0.offsetYSpeed = 1d;
		kf0.keyFrameTimeMillis = 100f;
		kf0.normaliseSpeeds = true;
		keyFrames.add(kf0);
	}

	@Override
	public void update(double delta) {
		keyFrameUpdate(delta);
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

	@Override
	protected void animationSubclassRunCompletionAlgorightm(boolean wait) {
//		System.out.println("postdie performer = " + performer);
//		System.out.println("postdie this = " + this);
//		System.out.println("postdie performer.primaryAnimation" + performer.primaryAnimation);
//		this.offsetX = targetOffsetX;
//		this.offsetY = targetOffsetY;
//		this.headToToeOffset = performer.width - 6 - performer.height; // the - 6 is a guess/fluff
//		this.torsoAngle = targetTorsoAngle;
//		this.leftElbowAngle = 0;
//		this.rightElbowAngle = 0;
//		this.leftShoulderAngle = 0;
//		this.rightShoulderAngle = 0;
//		this.leftHipAngle = 0;
//		this.rightHipAngle = 0;
//		this.leftKneeAngle = 0;
//		this.rightKneeAngle = 0;

	}
}
