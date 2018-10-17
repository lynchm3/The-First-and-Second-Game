package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.objects.GameObject;

public class AnimationIgnite extends Animation {

	int phase = 1;
	float phase1ShoulderAngleToReach = (float) Math.toRadians(-45);
	float phase2ShoulderAngleToReach = (float) Math.toRadians(0);

	GameObject target;

	public AnimationIgnite(GameObject performer, GameObject target, GameObject fireSource) {
		super(performer, performer, target);
		if (!runAnimation)
			return;
		this.target = target;
		durationToReachMillis = 400;

		backwards = performer.backwards;

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
		super.update(delta);

		if (phase == 1) {
			float angleChange = (float) (0.001d * delta);
			rightShoulderAngle = moveTowardsTargetAngleInRadians(rightShoulderAngle, angleChange,
					phase1ShoulderAngleToReach);
			if (rightShoulderAngle == phase1ShoulderAngleToReach)
				phase++;
		} else if (phase == 2) {
			float angleChange = (float) (0.005d * delta);
			rightShoulderAngle = moveTowardsTargetAngleInRadians(rightShoulderAngle, angleChange,
					phase2ShoulderAngleToReach);
			if (rightShoulderAngle == phase2ShoulderAngleToReach)
				phase++;
		} else {
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
