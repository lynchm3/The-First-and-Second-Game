package com.marklynch.level.constructs.animation;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;

public class AnimationMove extends Animation {

	public Square startSquare;
	public Square endSquare;
	public double durationSoFar = 0;
	public double durationToReach = 200;
	public boolean completed = false;
	public int offsetX = 0;
	public int offsetY = 0;
	public int startOffsetX = 0;
	public int startOffsetY = 0;

	public AnimationMove() {
		super();
		completed = true;
	}

	public AnimationMove(Square startSquare, Square endSquare) {
		super();
		this.startSquare = startSquare;
		this.endSquare = endSquare;

		startOffsetX = offsetX = (int) ((this.startSquare.xInGrid - this.endSquare.xInGrid) * Game.SQUARE_WIDTH);
		startOffsetY = offsetY = (int) ((this.startSquare.yInGrid - this.endSquare.yInGrid) * Game.SQUARE_HEIGHT);

	}

	public void update(double delta) {

		if (Game.level.activeActor == Game.level.player)
			System.out.println("animation.update delta = " + delta);

		if (completed)
			return;

		durationSoFar += delta;

		if (Game.level.activeActor == Game.level.player)
			System.out.println("durationSoFar = " + durationSoFar);
		double progress = durationSoFar / durationToReach;
		if (Game.level.activeActor == Game.level.player)
			System.out.println("progress = " + progress);
		if (progress >= 1) {
			completed = true;
			offsetX = 0;
			offsetY = 0;
		} else {
			offsetX = (int) (startOffsetX * (1 - progress));
			offsetY = (int) (startOffsetY * (1 - progress));
			if (Game.level.activeActor == Game.level.player)
				System.out.println("offsetX = " + offsetX);
			if (Game.level.activeActor == Game.level.player)
				System.out.println("offsetY = " + offsetY);
		}

	}

}
