package com.marklynch.level.constructs.animation.primary;

import com.marklynch.level.constructs.animation.Animation;
import com.marklynch.level.constructs.animation.secondary.AnimationThrown;
import com.marklynch.objects.Arrow;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;

public class AnimationShootArrow extends Animation {

	// public Square startSquare;
	// public Square endSquare;
	// public float startOffsetX = 0;
	// public float startOffsetY = 0;

	float quarterDurationToReach;
	float halfDurationToReach;
	float threeQuarterDurationToReach;
	// for show only, walking actor, primary

	GameObject target;
	Actor performer;
	Action action;
	AnimationThrown animationThrown;
	GameObject weapon;

	public AnimationShootArrow(Actor performer, GameObject target, GameObject weapon, Action action) {
		super();
		// this.performer []
		this.performer = performer;
		this.target = target;
		this.action = action;
		this.weapon = weapon;
		durationToReach = 2000; // SLOWED IT DOWN HERE

		quarterDurationToReach = durationToReach / 4;
		halfDurationToReach = quarterDurationToReach + quarterDurationToReach;
		threeQuarterDurationToReach = halfDurationToReach + quarterDurationToReach;

		// this.startSquare = startSquare;
		// this.endSquare = endSquare;

		// startOffsetX = offsetX = (int) ((this.startSquare.xInGrid -
		// this.endSquare.xInGrid) * Game.SQUARE_WIDTH);
		// startOffsetY = offsetY = (int) ((this.startSquare.yInGrid -
		// this.endSquare.yInGrid) * Game.SQUARE_HEIGHT);
		backwards = performer.backwards;
		blockAI = true;
		drawArrowInOffHand = true;
		arrowHandleY = 24;

	}

	@Override
	public void update(double delta) {

		if (getCompleted())
			return;

		durationSoFar += delta;

		float progress = durationSoFar / durationToReach;

		if (progress >= 1) {
			progress = 1;
		}

		if (progress < 0.1) {
			arrowHandleY = 64 - (52 * progress * 10);

		} else if (progress < 0.25f) {

			arrowHandleY = 12;
		}

		if (progress < 0.25f) {

			// arrowHandleY = 64 - (52 * progress * 4);

			bowStringHandleY = 0;
			rightShoulderAngle = -6.28f * progress;
			rightElbowAngle = 0f;

			leftShoulderAngle = -6.28f * progress;
			leftElbowAngle = 0f;

		} else if (progress < 0.5f) {

			bowStringHandleY = (progress - 0.25f) * -32;
			arrowHandleY = 36 + (progress - 0.25f) * 32;// 12 + 26;
			drawArrowInOffHand = false;
			drawArrowInMainHand = true;
			rightShoulderAngle = -1.57f;
			rightElbowAngle = 0f * progress;

			leftShoulderAngle = -1.57f + ((progress - 0.25f) * 1.57f);
			leftElbowAngle = -((progress - 0.25f) * 3.14f);

		} else if (progress < 0.75f) {

			if (animationThrown == null) {

				Arrow arrow = Templates.ARROW.makeCopy(null, null);
				arrow.drawOffsetRatioX = (float) (0.45f + Math.random() * 0.1f);
				arrow.drawOffsetRatioY = (float) (0.45f + Math.random() * 0.1f);
				animationThrown = new AnimationThrown("Arrow", performer, action, target, target.squareGameObjectIsOn,
						arrow, weapon, 2f, 0f, true);
				performer.secondaryAnimations.add(animationThrown);
			}

			bowStringHandleY = 0;
			drawArrowInOffHand = false;
			drawArrowInMainHand = false;
			rightShoulderAngle = -1.57f;
			rightElbowAngle = 0f;

			leftShoulderAngle = -1.1775f;
			leftElbowAngle = -0.785f;

		} else {

			drawArrowInOffHand = false;
			drawArrowInMainHand = false;
			rightShoulderAngle = -6.28f * (1f - progress);
			rightElbowAngle = 0f;

			leftShoulderAngle = -1.1775f * (4 * (1 - progress));
			leftElbowAngle = -0.785f * (4 * (1 - progress));
		}

		if (backwards) {
			float temp = rightShoulderAngle;
			rightShoulderAngle = -leftShoulderAngle;
			leftShoulderAngle = -temp;

			temp = rightElbowAngle;
			rightElbowAngle = -leftElbowAngle;
			leftElbowAngle = -temp;
		}

		if (progress >= 1) {
			// target.showPow();
			rightShoulderAngle = 0;
			rightElbowAngle = 0;
			leftShoulderAngle = 0;
			leftElbowAngle = 0;
			complete();
		} else {
		}

	}

	@Override
	public void draw2() {

	}

	@Override
	public void draw1() {

		// FUCKFKCKFUCK

	}

	@Override
	public void drawStaticUI() {
	}

}
