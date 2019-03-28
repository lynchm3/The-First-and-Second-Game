package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.objects.inanimateobjects.GameObject;

public class AnimationIgnite extends Animation {

	float phase1ShoulderAngleToReach = (float) Math.toRadians(-45);
	float phase2ShoulderAngleToReach = (float) Math.toRadians(0);

	GameObject target;
	GameObject fireSource;
	GameObject previouslyEquipped;

	public AnimationIgnite(GameObject performer, GameObject target, GameObject fireSource,
			OnCompletionListener onCompletionListener) {
		super(performer, onCompletionListener, null, null, null, null, null, null, false, true, performer, target);
		if (!runAnimation)
			return;
		this.target = target;
		previouslyEquipped = performer.equipped;
		performer.equipped = fireSource;

		backwards = performer.backwards;

		blockAI = true;

		KeyFrame kf0 = new KeyFrame(performer, this);
		kf0.rightShoulderAngle = (float) Math.toRadians(-45);
		kf0.setAllSpeeds(0.001f);
		keyFrames.add(kf0);

		KeyFrame kf1 = new KeyFrame(performer, this);
		kf1.rightShoulderAngle = (float) Math.toRadians(0);
		kf1.setAllSpeeds(0.005f);
		keyFrames.add(kf1);

		blockAI = true;

	}

	@Override
	public String toString() {
		return "AnimationIgnite";
	}

	@Override
	public void update(double delta) {
		if (getCompleted())
			return;
		keyFrameUpdate(delta);
		if (keyFrameIndex == keyFrames.size()) {
			performer.equipped = previouslyEquipped;
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

	@Override
	protected void animationSubclassRunCompletionAlgorightm(boolean wait) {
		// TODO Auto-generated method stub

	}

}
