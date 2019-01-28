package com.marklynch.level.constructs.animation.primary;

import com.marklynch.actions.Action;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.level.constructs.animation.secondary.AnimationThrown;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.Arrow;
import com.marklynch.objects.inanimateobjects.GameObject;

public abstract class AnimationShootArrow extends Animation {

	// public Square startSquare;
	// public Square endSquare;
	// public float startOffsetX = 0;
	// public float startOffsetY = 0;

	float quarterDurationToReach;
	float halfDurationToReach;
	float threeQuarterDurationToReach;
	// for show only, walking actor, primary

	GameObject target;
	Action action;
	AnimationThrown animationThrown;
	GameObject weapon;

	KeyFrame kf2;

	public AnimationShootArrow(Actor performer, GameObject target, GameObject weapon, Action action,
			OnCompletionListener onCompletionListener) {
		super(performer, onCompletionListener, null, null, null, null, null, null, false, true, performer, target);
		if (!runAnimation)
			return;
		// this.performer []
		this.target = target;
		this.action = action;
		this.weapon = weapon;
		durationToReachMillis = 2000; // SLOWED IT DOWN HERE

		quarterDurationToReach = durationToReachMillis / 4;
		halfDurationToReach = quarterDurationToReach + quarterDurationToReach;
		threeQuarterDurationToReach = halfDurationToReach + quarterDurationToReach;

		backwards = performer.backwards;
		blockAI = true;
		drawArrowInOffHand = true;
		arrowHandleY = 24;

		KeyFrame kf0 = new KeyFrame(performer, this);
		kf0.setAllSpeeds(0.004f);
		kf0.rightShoulderAngle = -1.57f;
		kf0.rightElbowAngle = 0f;
		kf0.leftShoulderAngle = -1.57f;
		kf0.leftElbowAngle = 0f;
		kf0.bowStringHandleY = 0;
		keyFrames.add(kf0);

		KeyFrame kf1 = new KeyFrame(performer, this);
		kf1.setAllSpeeds(0.004f);
		kf1.bowStringHandleY = -8;
		kf1.arrowHandleY = 44;// 12 + 26;
		kf1.drawArrowInOffHand = false;
		kf1.drawArrowInMainHand = true;
		kf1.rightShoulderAngle = -1.57f;
		kf1.rightElbowAngle = 0f;
		kf1.leftShoulderAngle = -1.1775f;
		kf1.leftElbowAngle = -0.785f;
		keyFrames.add(kf1);

		kf2 = new KeyFrame(performer, this);
		kf2.setAllSpeeds(0.004f);
		kf2.bowStringHandleY = 0;
		kf2.drawArrowInOffHand = false;
		kf2.drawArrowInMainHand = false;
		kf2.rightShoulderAngle = -1.57f;
		kf2.rightElbowAngle = 0f;
		kf2.leftShoulderAngle = -1.1775f;
		kf2.leftElbowAngle = -0.785f;
		keyFrames.add(kf2);

		KeyFrame kf3 = new KeyFrame(performer, this);
		kf3.setAllSpeeds(0.004f);
		kf3.drawArrowInOffHand = false;
		kf3.drawArrowInMainHand = false;
		kf3.rightShoulderAngle = 0;
		kf3.rightElbowAngle = 0f;
		kf3.leftShoulderAngle = 0;
		kf3.leftElbowAngle = 0;
		keyFrames.add(kf3);

	}

	@Override
	public String toString() {
		return "AnimationShootArrow";
	}

	public Arrow arrow;
	boolean shotArrow;

	@Override
	public void update(double delta) {

		if (!shotArrow && keyFrames.get(phase) == kf2) {
			shootArrow();
			shotArrow = true;
		}

		// if (progress < 0.1) {
		// arrowHandleY = 64 - (52 * progress * 10);
		//
		// } else if (progress < 0.25f) {
		//
		// arrowHandleY = 12;
		// }

		super.keyFrameUpdate(delta);

	}

	public void shootArrow() {

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

	@Override
	protected void animationSubclassRunCompletionAlgorightm(boolean wait) {
		// TODO Auto-generated method stub

	}

}
