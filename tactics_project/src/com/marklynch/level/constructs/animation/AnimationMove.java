package com.marklynch.level.constructs.animation;

import com.marklynch.level.squares.Square;

public class AnimationMove extends Animation {

	public Square startSquare;
	public Square endSquare;
	public float startOffsetX = 0;
	public float startOffsetY = 0;

	public AnimationMove() {
		super();
		completed = true;
	}

	public AnimationMove(Square startSquare, Square endSquare) {
		super();
		this.startSquare = startSquare;
		this.endSquare = endSquare;

		startOffsetX = offsetX = this.startSquare.xInGridPixels;
		startOffsetY = offsetY = this.startSquare.yInGridPixels;

	}

	@Override
	public void update(double delta) {

		if (completed)
			return;

		durationSoFar += delta;
		double progress = durationSoFar / durationToReach;
		if (progress >= 1) {
			completed = true;
			offsetX = 0;
			offsetY = 0;
		} else {
			offsetX = (int) (startOffsetX * (1 - progress));
			offsetY = (int) (startOffsetY * (1 - progress));
		}

	}

	@Override
	public void complete() {

		if (completed)
			return;

		completed = true;
		offsetX = 0;
		offsetY = 0;

	}

}
