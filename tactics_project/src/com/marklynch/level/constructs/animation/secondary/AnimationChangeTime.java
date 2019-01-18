package com.marklynch.level.constructs.animation.secondary;

import com.marklynch.Game;
import com.marklynch.objects.inanimateobjects.GameObject;

public class AnimationChangeTime extends SecondaryAnimation {

	float timeToChangeByInSeconds;
	float timeChangePerMillisecond;

	public AnimationChangeTime(GameObject targetGameObject, float timeToChangeByInSeconds,
			float durationToReachMillis) {

		super(null, null, null, null, null, null, null, null, false, targetGameObject);
		if (!runAnimation)
			return;

		this.timeToChangeByInSeconds = timeToChangeByInSeconds;
//		this.durationToReachMillis = durationMillis;
		this.timeChangePerMillisecond = timeToChangeByInSeconds * 1000 / durationToReachMillis;

//		int startTime = Level.time

		this.durationToReachMillis = durationToReachMillis;

		blockAI = true;

	}

	int totalChange = 0;

	@Override
	public void update(double delta) {

		if (getCompleted())
			return;

//		durationSoFar += delta;
//		double progress = durationSoFar / durationToReachMillis;
//		if (progress >= 1) {
//			runCompletionAlorightm(true);
//		} else {
		int intChange = (int) ((timeChangePerMillisecond * delta) / 1000f);
		int tempTotalChange = totalChange + intChange;
		if (tempTotalChange >= timeToChangeByInSeconds) {
			intChange = (int) (timeToChangeByInSeconds - totalChange);
			Game.level.changeTime(intChange);
			runCompletionAlorightm(true);
		} else {
			Game.level.changeTime(intChange);
		}
		totalChange += intChange;

//		}

	}

	@Override
	public void draw1() {
	}

	@Override
	public void draw2() {

	}

	@Override
	public void drawStaticUI() {
	}

	@Override
	public void draw3() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void childRunCompletionAlgorightm(boolean wait) {
		// TODO Auto-generated method stub

	}

}
