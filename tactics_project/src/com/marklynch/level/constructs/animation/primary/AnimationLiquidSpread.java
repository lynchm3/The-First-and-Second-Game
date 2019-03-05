package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.GameObject;

public class AnimationLiquidSpread extends Animation {

	float startScale, endScale;
	boolean fromLeft, fromRight, fromAbove, fromBelow;

	public AnimationLiquidSpread(GameObject performer, Square targetSquare, float durationToReachMillis,
			OnCompletionListener onCompletionListener) {

		super(performer, onCompletionListener, null, targetSquare, null, null, null, null, false, true, performer);
		if (!runAnimation)
			return;

		blockAI = false;

		backwards = performer.backwards;

		Square squareToLeft = targetSquare.getSquareToLeftOf();
		if (squareToLeft != null) {
			if (squareToLeft.inventory.containsGameObjectWithTemplateId(performer.templateId)) {
				fromLeft = true;
			}
		}
		Square squareToRight = targetSquare.getSquareToRightOf();
		if (squareToRight != null) {
			if (squareToRight.inventory.containsGameObjectWithTemplateId(performer.templateId)) {
				fromRight = true;
			}
		}
		Square squareAbove = targetSquare.getSquareAbove();
		if (squareAbove != null) {
			if (squareAbove.inventory.containsGameObjectWithTemplateId(performer.templateId)) {
				fromAbove = true;
			}
		}
		Square squareBelow = targetSquare.getSquareBelow();
		if (squareBelow != null) {
			if (squareBelow.inventory.containsGameObjectWithTemplateId(performer.templateId)) {
				fromBelow = true;
			}
		}

		KeyFrame kf0 = new KeyFrame(performer, this);
		boolean atLeastOneDirection = false;

		if (fromLeft) {
			// DOESNT WORK
			boundsX2 = (int) performer.width;
			kf0.boundsX2 = 0;
			kf0.boundsX2Speed = 0.1f;
			atLeastOneDirection = true;
		} else if (fromRight) {
			// WORKS
			boundsX1 = (int) performer.width;
			kf0.boundsX1 = 0;
			kf0.boundsX1Speed = 0.1f;
			atLeastOneDirection = true;
		}

		if (fromAbove) {
//			DOESNT WORK
			boundsY2 = 0;
			kf0.boundsX2 = (int) performer.height;
			kf0.boundsX2Speed = -0.1f;
			atLeastOneDirection = true;
		} else if (fromBelow) {
			// WORKS
			boundsY1 = (int) performer.height;
			kf0.boundsY1 = 0;
			kf0.boundsY1Speed = 0.1f;
			atLeastOneDirection = true;
		}

		if (!atLeastOneDirection) {
			scaleX = 0;
			scaleY = 0;
			kf0.scaleX = 1;
			kf0.scaleY = 1;
			kf0.scaleXSpeed = 0.001f;
			kf0.scaleYSpeed = 0.001f;
		}
		keyFrames.add(kf0);

	}

	@Override
	public void update(double delta) {
		keyFrameUpdate(delta);
	}

	@Override
	public void draw1() {
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
