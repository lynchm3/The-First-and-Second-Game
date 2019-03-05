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

		blockAI = true;

		boundsY1 = 128;

		KeyFrame kf0 = new KeyFrame(performer, this);
		kf0.setAllSpeeds(0.01f);
		kf0.boundsY1 = 0;
		kf0.boundsY1Speed = 0.1f;

		keyFrames.add(kf0);

//		KeyFrame kf1 = new KeyFrame(performer, this);
////		kf1.offsetX = 64;
////		kf1.offsetY = 64;
//		kf1.setAllSpeeds(0.01f);
////		kf1.offsetXSpeed = 9999999;
////		kf1.offsetYSpeed = 9999999;
//		kf1.boundsY1 = -128;
//		kf1.boundsY1Speed = 1;
//		keyFrames.add(kf1);

//		ArrayList<Square> neighborSquares = targetSquare.getAllSquaresAtDistance(1);
//
//		Square squareToLeft = targetSquare.getSquareToLeftOf();
//		if (squareToLeft != null) {
//			if (squareToLeft.inventory.containsGameObjectWithTemplateId(performer.templateId)) {
//				fromLeft = true;
//			}
//		}
//		Square squareToRight = targetSquare.getSquareToRightOf();
//		if (squareToRight != null) {
//			if (squareToRight.inventory.containsGameObjectWithTemplateId(performer.templateId)) {
//				fromRight = true;
//			}
//		}
//		Square squareAbove = targetSquare.getSquareAbove();
//		if (squareAbove != null) {
//			if (squareAbove.inventory.containsGameObjectWithTemplateId(performer.templateId)) {
//				fromAbove = true;
//			}
//		}
//		Square squareBelow = targetSquare.getSquareBelow();
//		if (squareBelow != null) {
//			if (squareBelow.inventory.containsGameObjectWithTemplateId(performer.templateId)) {
//				fromBelow = true;
//			}
//		}

	}

	@Override
	public void update(double delta) {
		keyFrameUpdate(delta);
	}

	@Override
	public void draw1() {
//		if (fromLeft) {

//		}
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
