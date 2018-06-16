package com.marklynch.level.constructs.animation.secondary;

import com.marklynch.Game;
import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.TextureUtils;

public class AnimationMovementFade1 extends Animation {

	Square square;
	GameObject gameObject;
	float alpha = 0.5f;

	public AnimationMovementFade1(Square square, GameObject gameObject) {
		super(null);
		this.square = square;
		this.gameObject = gameObject;
		this.durationToReach = 1000f;
		this.durationSoFar = 0;

	}

	@Override
	public void update(double delta) {

		if (getCompleted())
			return;

		durationSoFar += delta;
		float progress = durationSoFar / durationToReach;
		if (progress >= 1) {
			runCompletionAlgorightm();
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

			int actorPositionXInPixels = (int) (this.square.xInGridPixels + Game.SQUARE_WIDTH * actor.drawOffsetRatioX);
			int actorPositionYInPixels = (int) (this.square.yInGridPixels
					+ Game.SQUARE_HEIGHT * actor.drawOffsetRatioY);
			actor.drawActor(actorPositionXInPixels, actorPositionYInPixels, alpha, true, 1f, 1f, 0f,
					Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, TextureUtils.neutralColor, true, false, actor.backwards, false);
		} else {

			int actorPositionXInPixels = (int) (square.xInGridPixels + Game.SQUARE_WIDTH * gameObject.drawOffsetRatioX);
			int actorPositionYInPixels = (int) (square.yInGridPixels
					+ Game.SQUARE_HEIGHT * gameObject.drawOffsetRatioY);
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

}
