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

	float quarterDurationToReach;
	float halfDurationToReach;
	float threeQuarterDurationToReach;

	float offsetXFromLastAnimation;
	float offsetYFromLastAnimation;

	// for show only, walking actor, primary

	public AnimationTeleport(GameObject performer, Square startSquare, Square endSquare) {
		super(performer);
		durationToReach = 400;

		quarterDurationToReach = durationToReach / 4;
		halfDurationToReach = quarterDurationToReach + quarterDurationToReach;
		threeQuarterDurationToReach = halfDurationToReach + quarterDurationToReach;

		this.startSquare = startSquare;
		this.endSquare = endSquare;
		offsetXFromLastAnimation = offsetX;
		offsetYFromLastAnimation = offsetY;
		startOffsetX = offsetX = (int) ((this.startSquare.xInGrid - this.endSquare.xInGrid) * Game.SQUARE_WIDTH)
				+ offsetXFromLastAnimation;
		startOffsetY = offsetY = (int) ((this.startSquare.yInGrid - this.endSquare.yInGrid) * Game.SQUARE_HEIGHT)
				+ offsetYFromLastAnimation;

		backwards = performer.backwards;

		blockAI = true;
	}

	@Override
	public void update(double delta) {

		if (getCompleted())
			return;
		super.update(delta);

		durationSoFar += delta;

		float progress = durationSoFar / durationToReach;

		if (progress >= 1) {
			progress = 1;
		}

		if (progress >= 1) {
			complete();
			// if (performer.getPrimaryAnimation() == this)
			// performer.setPrimaryAnimation(new AnimationWait(performer));

			// offsetX = 0;
		} else if (progress >= 0.5f) {
			offsetX = offsetXFromLastAnimation;
			offsetY = offsetYFromLastAnimation;
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

}
