package com.marklynch.level.constructs.animation.secondary;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.TextureUtils;

public class AnimationMovementFade1 extends SecondaryAnimation {

	Square square;
	GameObject gameObject;
	float alpha = 0.5f;

	public AnimationMovementFade1(Square square, GameObject gameObject, OnCompletionListener onCompletionListener) {
		super(null, onCompletionListener, null, null, null, null, null, null, false, square);
		if (!runAnimation)
			return;
		this.square = square;
		this.gameObject = gameObject;
		this.durationToReachMillis = 1000f;
		this.durationSoFar = 0;

	}

	@Override
	public void update(double delta) {

		if (getCompleted())
			return;

		durationSoFar += delta;
		float progress = durationSoFar / durationToReachMillis;
		if (progress >= 1) {
			runCompletionAlorightm(true);
		} else {
			alpha = (1f - progress) * 0.5f;
		}
	}

	@Override
	public void draw1() {

		if (getCompleted())
			return;

		if (gameObject instanceof Actor) {
			Actor actor = (Actor) gameObject;

			int actorPositionXInPixels = (int) (this.square.xInGridPixels + actor.drawOffsetX);
			int actorPositionYInPixels = (int) (this.square.yInGridPixels + actor.drawOffsetY);
			actor.drawActor(actorPositionXInPixels, actorPositionYInPixels, alpha, true, 1f, 1f, 0f, Integer.MIN_VALUE,
					Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, TextureUtils.neutralColor, true, false,
					actor.backwards, false, true);
		} else {

			int actorPositionXInPixels = (int) (square.xInGridPixels + gameObject.drawOffsetX);
			int actorPositionYInPixels = (int) (square.yInGridPixels + gameObject.drawOffsetY);
			TextureUtils.drawTexture(gameObject.imageTexture, alpha, actorPositionXInPixels, actorPositionYInPixels,
					actorPositionXInPixels + gameObject.width, actorPositionYInPixels + gameObject.height,
					gameObject.backwards);
		}
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
	protected void animationSubclassRunCompletionAlgorightm(boolean wait) {
		// TODO Auto-generated method stub

	}

}
