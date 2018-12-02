package com.marklynch.objects.actions;

import java.util.Random;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Fish;

public class ActionFishSwim extends Action {

	public static final String ACTION_NAME = "Swim";

	public ActionFishSwim(Fish performer) {
		super(ACTION_NAME, textureWalk, performer, null);
		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
		sound = createSound();
		movement = true;

	}

	@Override
	public void perform() {
		super.perform();

		if (!enabled)
			return;

		if (!checkRange())
			return;

		// float chanceToSwim = 0.2f;

		float maxChange = 0.05f;
		boolean dontOverlap = true;

		if (performer.swimmingChangeX > maxChange || performer.swimmingChangeX < -maxChange) {
			performer.swimmingChangeX = 0;
		}
		if (new Random().nextFloat() < 0.2f) {
			performer.swimmingChangeX = new Random().nextFloat() * maxChange;
			if (new Random().nextBoolean()) {
				performer.swimmingChangeX = -performer.swimmingChangeX;
			}
		}

		if (performer.swimmingChangeY > maxChange || performer.swimmingChangeY < -maxChange) {
			performer.swimmingChangeY = 0;
		}

		if (new Random().nextFloat() < 0.2f) {
			performer.swimmingChangeY = new Random().nextFloat() * maxChange;
			if (new Random().nextBoolean()) {
				performer.swimmingChangeY = -performer.swimmingChangeY;
			}
		}

		float halfWidthRatio = (performer.width / Game.SQUARE_WIDTH) / 2f;
		float halfHeightRatio = (performer.height / Game.SQUARE_HEIGHT) / 2f;

		// If we're moving out of water, cancel X
		if (performer.drawOffsetRatioX + performer.swimmingChangeX < 0
				&& (performer.squareGameObjectIsOn.getSquareToLeftOf() == null
						|| performer.squareGameObjectIsOn.getSquareToLeftOf().inventory.waterBody == null)) {

			performer.swimmingChangeX = 0;

		} else if (performer.drawOffsetRatioX + performer.swimmingChangeX >= 1 - performer.widthRatio
				&& (performer.squareGameObjectIsOn.getSquareToRightOf() == null
						|| performer.squareGameObjectIsOn.getSquareToRightOf().inventory.waterBody == null)) {

			performer.swimmingChangeX = 0;

		}

		// If we're moving out of water, cancel Y
		if (performer.drawOffsetRatioY + performer.swimmingChangeY < 0
				&& (performer.squareGameObjectIsOn.getSquareAbove() == null
						|| performer.squareGameObjectIsOn.getSquareAbove().inventory.waterBody == null)) {

			performer.swimmingChangeY = 0;

		} else if (performer.drawOffsetRatioY + performer.swimmingChangeY >= 1 - performer.heightRatio
				&& (performer.squareGameObjectIsOn.getSquareBelow() == null
						|| performer.squareGameObjectIsOn.getSquareBelow().inventory.waterBody == null)) {

			performer.swimmingChangeY = 0;

		}

		// Move over to other square if crossed over
		Square newSquare = performer.squareGameObjectIsOn;
		if (performer.drawOffsetRatioX + performer.swimmingChangeX < -halfWidthRatio) {

			Square potentialNewSquare = performer.squareGameObjectIsOn.getSquareToLeftOf();
			if (dontOverlap && potentialNewSquare.inventory.contains(Fish.class)) {
				performer.swimmingChangeX = 0;
			} else {
				newSquare = potentialNewSquare;
				performer.swimmingChangeX += 1;
			}
		} else if (performer.drawOffsetRatioX + performer.swimmingChangeX >= 1 - halfWidthRatio) {
			Square potentialNewSquare = performer.squareGameObjectIsOn.getSquareToRightOf();
			if (dontOverlap && potentialNewSquare.inventory.contains(Fish.class)) {
				performer.swimmingChangeX = 0;
			} else {
				newSquare = potentialNewSquare;
				performer.swimmingChangeX -= 1;
			}
		} else if (performer.drawOffsetRatioY + performer.swimmingChangeY < -halfHeightRatio) {
			Square potentialNewSquare = performer.squareGameObjectIsOn.getSquareAbove();
			if (dontOverlap && potentialNewSquare.inventory.contains(Fish.class)) {
				performer.swimmingChangeY = 0;
			} else {
				newSquare = potentialNewSquare;
				performer.swimmingChangeY += 1;
			}
		} else if (performer.drawOffsetRatioY + performer.swimmingChangeY >= 1 - halfHeightRatio) {
			Square potentialNewSquare = performer.squareGameObjectIsOn.getSquareBelow();
			if (dontOverlap && potentialNewSquare.inventory.contains(Fish.class)) {
				performer.swimmingChangeY = 0;
			} else {
				newSquare = potentialNewSquare;
				performer.swimmingChangeY -= 1;
			}
		}

		moveTo(performer, newSquare, performer.drawOffsetRatioX + performer.swimmingChangeX,
				performer.drawOffsetRatioY + performer.swimmingChangeY);

	}

	public void moveTo(Actor actor, Square target, float targetOffsetX, float targetOffsetY) {

		Square oldSquare = actor.squareGameObjectIsOn;
		GameObject gameObjectInTheWay = target.inventory.gameObjectThatCantShareSquare;

		// if (performer.squareGameObjectIsOn.onScreen() &&
		// performer.squareGameObjectIsOn.visibleToPlayer)
		// performer.setPrimaryAnimation(new AnimationWalk(
		// actor.squareGameObjectIsOn.xInGridPixels + actor.drawOffsetRatioX *
		// Game.SQUARE_WIDTH,
		// actor.squareGameObjectIsOn.yInGridPixels + actor.drawOffsetRatioY *
		// Game.SQUARE_HEIGHT,
		// target.xInGridPixels + targetOffsetX * Game.SQUARE_WIDTH,
		// target.yInGridPixels + targetOffsetY * Game.SQUARE_HEIGHT));

		if (actor.squareGameObjectIsOn != target)
			target.inventory.add(actor);

		// move(actor, squareToMoveTo);
		actor.drawOffsetRatioX = targetOffsetX;
		actor.drawOffsetRatioY = targetOffsetY;

		if (sound != null)
			sound.play();
	}

	private void move(Actor actor, Square square) {
		actor.lastSquare = actor.squareGameObjectIsOn;
		actor.squareGameObjectIsOn.inventory.remove(actor);
		actor.distanceMovedThisTurn += 1;
		actor.squareGameObjectIsOn = square;
		square.inventory.add(actor);

		// Actor.highlightSelectedCharactersSquares();
	}

	@Override
	public boolean check() {
		// if (target == null)
		// return false;

		// if (performer.beingFished == true)
		// return false;

		// if (target.inventory.waterBody == null) {
		// return false;
		// }
		return true;
	}

	@Override
	public boolean checkRange() {
		// if (performer.straightLineDistanceTo(target) > 1)
		// return false;
		return true;
	}

	@Override
	public boolean checkLegality() {
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}
}
