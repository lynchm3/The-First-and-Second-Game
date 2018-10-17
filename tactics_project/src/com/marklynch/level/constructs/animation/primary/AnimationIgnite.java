package com.marklynch.level.constructs.animation.primary;

import java.util.ArrayList;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.objects.GameObject;

public class AnimationIgnite extends Animation {

	int phase = 0;
	float phase1ShoulderAngleToReach = (float) Math.toRadians(-45);
	float phase2ShoulderAngleToReach = (float) Math.toRadians(0);

	GameObject target;
	GameObject fireSource;
	GameObject previouslyEquipped;

	// KEYFRAMEZ
	ArrayList<KeyFrame> keyFrames = new ArrayList<KeyFrame>();

	public AnimationIgnite(GameObject performer, GameObject target, GameObject fireSource) {
		super(performer, performer, target);
		if (!runAnimation)
			return;
		this.target = target;
		previouslyEquipped = performer.equipped;
		performer.equipped = fireSource;

		backwards = performer.backwards;

		blockAI = true;

		KeyFrame kf0 = new KeyFrame(performer, this);
		kf0.rightShoulderAngle = (float) Math.toRadians(-45);
		kf0.speed = 0.001d;
		keyFrames.add(kf0);

		KeyFrame kf1 = new KeyFrame(performer, this);
		kf1.rightShoulderAngle = (float) Math.toRadians(0);
		kf1.speed = 0.005d;
		keyFrames.add(kf1);

	}

	@Override
	public String toString() {
		return "AnimationIgnite";
	}

	@Override
	public void update(double delta) {

		if (getCompleted())
			return;

		super.update(delta);

		keyFrames.get(phase).animate(delta);
		if (keyFrames.get(phase).done)
			phase++;

		if (phase == keyFrames.size()) {
			performer.equipped = previouslyEquipped;
			runCompletionAlgorightm(true);
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

}
