package com.marklynch.level.constructs.animation.primary;

import com.marklynch.Game;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;

public class AnimationTeleport extends Animation {

	public GameObject performer;
	public Square startSquare;
	public Square endSquare;
	public float startOffsetX = 0;
	public float startOffsetY = 0;

	float quarterDurationToReach;
	float halfDurationToReach;
	float threeQuarterDurationToReach;

	// for show only, walking actor, primary

	public AnimationTeleport(GameObject performer, Square startSquare, Square endSquare) {
		super();
		durationToReach = 400;

		quarterDurationToReach = durationToReach / 4;
		halfDurationToReach = quarterDurationToReach + quarterDurationToReach;
		threeQuarterDurationToReach = halfDurationToReach + quarterDurationToReach;

		this.startSquare = startSquare;
		this.endSquare = endSquare;

		startOffsetX = offsetX = (int) ((this.startSquare.xInGrid - this.endSquare.xInGrid) * Game.SQUARE_WIDTH);
		startOffsetY = offsetY = (int) ((this.startSquare.yInGrid - this.endSquare.yInGrid) * Game.SQUARE_HEIGHT);

		this.performer = performer;

		backwards = performer.backwards;

		blockAI = false;
	}

	@Override
	public void update(double delta) {

		if (completed)
			return;

		durationSoFar += delta;

		float progress = durationSoFar / durationToReach;

		if (progress >= 1) {
			progress = 1;
		}

		if (progress >= 1) {
			completed = true;
			offsetX = 0;
		} else if (progress >= 0.5f) {
			offsetX = 0;
			offsetY = 0;
			boundsX1 = 0;
			boundsY1 = (int) ((1 - progress) * 2 * performer.height);
			boundsX2 = 0;
			boundsY2 = 0;
		} else {
			offsetX = startOffsetX;
			offsetY = startOffsetY;
			boundsX1 = 0;
			boundsY1 = (int) (progress * 2 * performer.height);
			boundsX2 = 0;
			boundsY2 = 0;
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
