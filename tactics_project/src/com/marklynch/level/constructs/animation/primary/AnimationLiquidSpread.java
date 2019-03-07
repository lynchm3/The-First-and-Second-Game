package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.KeyFrame;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.GameObject;

public class AnimationLiquidSpread extends Animation {

	float startScale, endScale;
	boolean spreadFromLeft, spreadFromRight, spreadFromAbove, spreadFromBelow;

	public AnimationLiquidSpread(GameObject performer, Square targetSquare, OnCompletionListener onCompletionListener) {

		super(performer, onCompletionListener, null, targetSquare, null, null, null, null, false, true, performer,
				targetSquare);


		if (!runAnimation)
			return;

		blockAI = false;

		backwards = performer.backwards;

		Square squareToLeft = targetSquare.getSquareToLeftOf();
		if (squareToLeft != null) {
			if (squareToLeft.inventory.containsGameObjectWithTemplateId(performer.templateId)) {
				spreadFromLeft = true;
			}
		}
		Square squareToRight = targetSquare.getSquareToRightOf();
		if (squareToRight != null) {
			if (squareToRight.inventory.containsGameObjectWithTemplateId(performer.templateId)) {
				spreadFromRight = true;
			}
		}
		Square squareAbove = targetSquare.getSquareAbove();
		if (squareAbove != null) {
			if (squareAbove.inventory.containsGameObjectWithTemplateId(performer.templateId)) {
				spreadFromAbove = true;
			}
		}
		Square squareBelow = targetSquare.getSquareBelow();
		if (squareBelow != null) {
			if (squareBelow.inventory.containsGameObjectWithTemplateId(performer.templateId)) {
				spreadFromBelow = true;
			}
		}

		KeyFrame kf0 = new KeyFrame(performer, this);
		boolean atLeastOneDirection = false;

		if (spreadFromLeft) {
			// DOESNT WORK
			boundsX2 = 0;
			kf0.boundsX2 = (int) performer.width;
			kf0.boundsX2Speed = 0.1f;
			atLeastOneDirection = true;
		} else if (spreadFromRight) {
			// WORKS
			boundsX1 = (int) performer.width;
			kf0.boundsX1 = 0;
			kf0.boundsX1Speed = 0.1f;
			atLeastOneDirection = true;
		}

		if (spreadFromAbove) {
//			DOESNT WORK
			boundsY2 = 0;
			kf0.boundsY2 = (int) performer.height;
			kf0.boundsY2Speed = 0.1f;
			atLeastOneDirection = true;
		} else if (spreadFromBelow) {
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
