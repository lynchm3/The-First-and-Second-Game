package com.marklynch.level.constructs.animation.primary;

import com.marklynch.Game;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;

public class AnimationTeleport extends Animation {

	public Square startSquare;
	public Square endSquare;
	public float startOffsetX = 0;
	public float startOffsetY = 0;
	public float endOffsetX = 0;
	public float endOffsetY = 0;

	float quarterDurationToReach;
	float halfDurationToReach;
	float threeQuarterDurationToReach;

	float offsetXFromLastAnimation;
	float offsetYFromLastAnimation;

	// for show only, walking actor, primary

	public AnimationTeleport(GameObject performer, Square startSquare, Square endSquare) {
		super(performer, startSquare, endSquare);
		if (!runAnimation)
			return;
		durationToReachMillis = 400;

		quarterDurationToReach = durationToReachMillis / 4;
		halfDurationToReach = quarterDurationToReach + quarterDurationToReach;
		threeQuarterDurationToReach = halfDurationToReach + quarterDurationToReach;

		this.startSquare = startSquare;
		this.endSquare = endSquare;
		startOffsetX = offsetX;// = offsetX;
		startOffsetY = offsetY;
		endOffsetX = (int) ((this.endSquare.xInGrid - this.startSquare.xInGrid) * Game.SQUARE_WIDTH);
		endOffsetY = (int) ((this.endSquare.yInGrid - this.startSquare.yInGrid) * Game.SQUARE_HEIGHT);
		backwards = performer.backwards;

		blockAI = true;
	}

	@Override
	public void update(double delta) {

		if (getCompleted())
			return;
		super.update(delta);

		durationSoFar += delta;

		float progress = durationSoFar / durationToReachMillis;

		if (progress >= 1) {
			progress = 1;
		}

		if (progress >= 1) {
			runCompletionAlgorightm(true);
			// if (performer.getPrimaryAnimation() == this)
			// performer.setPrimaryAnimation(new AnimationWait(performer));

			// offsetX = 0;
		} else if (progress >= 0.5f) {
			offsetX = endOffsetX;
			offsetY = endOffsetY;
			// boundsX1 = 0;
			boundsY1 = (int) ((1 - progress) * 2 * performer.height);
			// boundsX2 = 0;
			// boundsY2 = 0;
		} else {
			offsetX = startOffsetX;
			offsetY = startOffsetY;
			// boundsX1 = 0;
			boundsY1 = (int) (progress * 2 * performer.height);
			// boundsX2 = 0;
			// boundsY2 = 0;
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
