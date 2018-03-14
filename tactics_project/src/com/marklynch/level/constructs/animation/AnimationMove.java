package com.marklynch.level.constructs.animation;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;

public class AnimationMove extends Animation {

	public Square startSquare;
	public Square endSquare;
	public float startOffsetX = 0;
	public float startOffsetY = 0;

	// for show only, walking actor, primary

	public AnimationMove(Square startSquare, Square endSquare) {
		super();
		durationToReach = 200;
		this.startSquare = startSquare;
		this.endSquare = endSquare;

		startOffsetX = offsetX = (int) ((this.startSquare.xInGrid - this.endSquare.xInGrid) * Game.SQUARE_WIDTH);
		startOffsetY = offsetY = (int) ((this.startSquare.yInGrid - this.endSquare.yInGrid) * Game.SQUARE_HEIGHT);
		blockAI = false;

	}

	public AnimationMove(int startX, int startY, int endX, int endY) {

	}

	public AnimationMove(float startX, float startY, float endX, float endY) {
		super();
		durationToReach = 200;
		// this.startSquare = startSquare;
		// this.endSquare = endSquare;

		startOffsetX = offsetX = startX - endX;
		startOffsetY = offsetY = startY - endY;
		blockAI = false;
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
	public void draw2() {

	}

	@Override
	public void draw1() {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawStaticUI() {
	}

}
