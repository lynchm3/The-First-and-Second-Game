package com.marklynch.level.constructs.animation.secondary;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.marklynch.Game;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class AnimationSecondaryScale extends SecondaryAnimation {

	float startScale, endScale;
	Texture powTexture;

	public AnimationSecondaryScale(GameObject performer, float start, float end, float scaleDuration,
			float stayDuration, Texture powTexture, OnCompletionListener onCompletionListener) {

		super(performer, onCompletionListener, null, null, null, null, null, null, false, performer);
		if (!runAnimation)
			return;
		this.powTexture = powTexture;
		blockAI = false;

		scaleX = start;
		scaleY = start;

		KeyFrame kf0 = new KeyFrame(performer, this);
		kf0.scaleX = end;
		kf0.scaleY = end;
		kf0.keyFrameTimeMillis = scaleDuration;
		kf0.normaliseSpeeds = true;
		keyFrames.add(kf0);

		KeyFrame kf1 = new KeyFrame(performer, this);
		kf1.minTime = stayDuration;
		keyFrames.add(kf1);

	}

	@Override
	public void update(double delta) {
		keyFrameUpdate(delta);
	}

	@Override
	public void draw2() {

		if (performer.lastSquare == null)
			return;

		if (scaleX != 0 && scaleY != 0) {

			float powPositionXInPixels = performer.lastSquare.xInGridPixels;
			float powPositionYInPixels = performer.lastSquare.yInGridPixels;
			if (performer.getPrimaryAnimation() != null) {
				powPositionXInPixels += performer.getPrimaryAnimation().offsetX;
				powPositionYInPixels += performer.getPrimaryAnimation().offsetY;
			}

			Matrix4f view = Game.activeBatch.getViewMatrix();

			if (scaleX != 1 || scaleY != 1) {
				Game.flush();
				view.translate(new Vector2f(powPositionXInPixels + Game.HALF_SQUARE_WIDTH,
						powPositionYInPixels + Game.HALF_SQUARE_HEIGHT));
				view.scale(new Vector3f(scaleX, scaleY, 1f));
				view.translate(new Vector2f(-(powPositionXInPixels + Game.HALF_SQUARE_WIDTH),
						-(powPositionYInPixels + Game.HALF_SQUARE_HEIGHT)));
				Game.activeBatch.updateUniforms();
			}

			TextureUtils.drawTexture(powTexture, powPositionXInPixels, powPositionYInPixels,
					powPositionXInPixels + Game.SQUARE_WIDTH, powPositionYInPixels + Game.SQUARE_HEIGHT);

			if (scaleX != 1 || scaleY != 1) {
				Game.flush();
				// Matrix4f view = Game.activeBatch.getViewMatrix();
				view.translate(new Vector2f(powPositionXInPixels + Game.HALF_SQUARE_WIDTH,
						powPositionYInPixels + Game.HALF_SQUARE_HEIGHT));
				view.scale(new Vector3f(1f / scaleX, 1f / scaleY, 1f));
				view.translate(new Vector2f(-(powPositionXInPixels + Game.HALF_SQUARE_WIDTH),
						-(powPositionYInPixels + Game.HALF_SQUARE_HEIGHT)));
				Game.activeBatch.updateUniforms();
			}
			Game.flush();
		}
	}

	@Override
	public void draw1() {
	}

	@Override
	public void drawStaticUI() {
	}

	@Override
	public void draw3() {
	}

	@Override
	protected void animationSubclassRunCompletionAlgorightm(boolean wait) {
	}
}
