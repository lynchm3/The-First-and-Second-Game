package com.marklynch.level.constructs.animation;

public class AnimationShake extends Animation {

	public float baseOffsetX = 0;
	public float baseOffsetY = 0;

	// for show only, walking actor, primary

	public AnimationShake() {
		super();
		// durationToReach = 200;
		this.baseOffsetX = baseOffsetX;
		this.baseOffsetX = baseOffsetX;
		blockAI = false;

	}

	// public AnimationShake(int startX, int startY, int endX, int endY) {
	//
	// }
	//
	// public AnimationShake(float startX, float startY, float endX, float endY)
	// {
	// super();
	// durationToReach = 200;
	// // this.startSquare = startSquare;
	// // this.endSquare = endSquare;
	//
	// startOffsetX = offsetX = startX - endX;
	// startOffsetY = offsetY = startY - endY;
	// blockAI = false;
	// }

	@Override
	public void update(double delta) {

		if (completed)
			return;

		offsetX = (float) (Math.random() * 16) - 8f;
		offsetY = (float) (Math.random() * 16) - 8f;

		// durationSoFar += delta;
		// double progress = durationSoFar / durationToReach;
		// if (progress >= 1) {
		// completed = true;
		// offsetX = 0;
		// offsetY = 0;
		// } else {
		// offsetX = (int) (startOffsetX * (1 - progress));
		// offsetY = (int) (startOffsetY * (1 - progress));
		// }

	}

	@Override
	public void draw2() {

	}

	@Override
	public void draw1() {

	}

	@Override
	public void drawStaticUI() {
	}

}
