package com.marklynch.objects.actions;

import java.util.Random;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.primary.AnimationMove;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.tools.FishingRod;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Fish;
import com.marklynch.objects.units.Player;

public class ActionFishBeingFished extends Action {

	public static final String ACTION_NAME = "Swim";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	Fish performer;

	public ActionFishBeingFished(Fish performer) {
		super(ACTION_NAME, "action_move.png");
		super.gameObjectPerformer = this.performer = performer;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
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

		if (performer.beingFishedBy == null || performer.beingFishedBy.remainingHealth <= 0
				|| performer.beingFishedBy.fishingTarget != performer) {
			performer.beingFishedBy = null;
			return;
		}

		float maxChange = 0.2f;
		float maxShake = 0.05f;

		FishingRod fishingRod = (FishingRod) performer.beingFishedBy.equipped;
		performer.fightingFishingRod = false;

		float distanceToCoverX;
		float distanceToCoverY;
		float speedX;
		float speedY;
		float speed = 0.05f;

		distanceToCoverX = performer.beingFishedBy.squareGameObjectIsOn.xInGridPixels
				- performer.squareGameObjectIsOn.xInGridPixels;
		distanceToCoverY = performer.beingFishedBy.squareGameObjectIsOn.yInGridPixels
				- performer.squareGameObjectIsOn.yInGridPixels;
		float totalDistanceToCover = Math.abs(distanceToCoverX) + Math.abs(distanceToCoverY);

		speedX = (distanceToCoverX / totalDistanceToCover) * speed;
		speedY = (distanceToCoverY / totalDistanceToCover) * speed;

		boolean progressThisTurn = false;
		if (fishingRod.progressThisTurn > 0) {
			progressThisTurn = true;
		}

		if (progressThisTurn) {

		} else {
			performer.fightingFishingRod = true;
			speedX = -speedX;
			speedY = -speedY;
		}

		float shakeX = (float) (Math.random() * maxShake);
		if (new Random().nextBoolean()) {
			shakeX = -shakeX;
		}
		float shakeY = (float) (Math.random() * maxShake);
		if (new Random().nextBoolean()) {
			shakeY = -shakeY;
		}

		performer.swimmingChangeX = speedX + shakeX;
		performer.swimmingChangeY = speedY + shakeY;

		if (performer.swimmingChangeX > maxChange) {
			performer.swimmingChangeX = maxChange;
		} else if (performer.swimmingChangeX < -maxChange) {
			performer.swimmingChangeX = -maxChange;
		}

		if (new Random().nextFloat() < 0.1f) {
			performer.fightingFishingRod = true;
			performer.swimmingChangeX = new Random().nextFloat() * maxChange;
			if (new Random().nextBoolean()) {
				performer.swimmingChangeX = -performer.swimmingChangeX;
			}
		}

		if (performer.swimmingChangeY > maxChange || performer.swimmingChangeY < -maxChange) {
			performer.swimmingChangeY = maxChange;
		} else if (performer.swimmingChangeY < -maxChange) {
			performer.swimmingChangeY = -maxChange;
		}

		if (new Random().nextFloat() < 0.1f) {
			performer.fightingFishingRod = true;
			performer.swimmingChangeY = new Random().nextFloat() * maxChange;
			if (new Random().nextBoolean()) {
				performer.swimmingChangeY = -performer.swimmingChangeY;
			}
		}

		float halfWidthRatio = (performer.width / Game.SQUARE_WIDTH) / 2f;
		float halfHeightRatio = (performer.height / Game.SQUARE_HEIGHT) / 2f;

		boolean hitLand = false;

		// If we're moving out of water, cancel X
		if (performer.drawOffsetRatioX + performer.swimmingChangeX < 0
				&& (performer.squareGameObjectIsOn.getSquareToLeftOf() == null
						|| performer.squareGameObjectIsOn.getSquareToLeftOf().inventory.waterBody == null)) {

			performer.swimmingChangeX = -performer.swimmingChangeX;
			hitLand = true;

		} else if (performer.drawOffsetRatioX + performer.swimmingChangeX >= 1 - performer.widthRatio
				&& (performer.squareGameObjectIsOn.getSquareToRightOf() == null
						|| performer.squareGameObjectIsOn.getSquareToRightOf().inventory.waterBody == null)) {

			performer.swimmingChangeX = -performer.swimmingChangeX;
			hitLand = true;

		}

		// If we're moving out of water, cancel Y
		if (performer.drawOffsetRatioY + performer.swimmingChangeY < 0
				&& (performer.squareGameObjectIsOn.getSquareAbove() == null
						|| performer.squareGameObjectIsOn.getSquareAbove().inventory.waterBody == null)) {

			performer.swimmingChangeY = -performer.swimmingChangeY;
			hitLand = true;

		} else if (performer.drawOffsetRatioY + performer.swimmingChangeY >= 1 - performer.heightRatio
				&& (performer.squareGameObjectIsOn.getSquareBelow() == null
						|| performer.squareGameObjectIsOn.getSquareBelow().inventory.waterBody == null)) {

			performer.swimmingChangeY = -performer.swimmingChangeY;
			hitLand = true;

		}

		if (performer.beingFishedBy == Game.level.player) {
			if (fishingRod.lineDamage >= 1) {
				Player.playerTargetAction = new ActionFishingFailed(Level.player, performer);
				Player.playerTargetSquare = performer.squareGameObjectIsOn;
				Player.playerFirstMove = true;

			} else if (hitLand && fishingRod.progressThisTurn > 0 && performer.beingFishedBy == Game.level.player) {
				Player.playerTargetAction = new ActionFishingCompleted(Level.player, performer);
				Player.playerTargetSquare = performer.squareGameObjectIsOn;
				Player.playerFirstMove = true;
			}

			else if (totalDistanceToCover < Game.SQUARE_WIDTH + Game.HALF_SQUARE_WIDTH) {
				Player.playerTargetAction = new ActionFishingCompleted(Level.player, performer);
				Player.playerTargetSquare = performer.squareGameObjectIsOn;
				Player.playerFirstMove = true;

			}
		} else {
			if (hitLand && fishingRod.progressThisTurn > 0) {
				fishingRod.caught = true;
			} else if (totalDistanceToCover < Game.SQUARE_WIDTH + Game.HALF_SQUARE_WIDTH) {
				fishingRod.caught = true;
			}
		}

		// Move over to other square if crossed over
		Square newSquare = performer.squareGameObjectIsOn;
		float targetOffsetX = performer.swimmingChangeX;
		float targetOffsetY = performer.swimmingChangeY;

		if (performer.drawOffsetRatioX + performer.swimmingChangeX < -halfWidthRatio) {

			Square potentialNewSquare = performer.squareGameObjectIsOn.getSquareToLeftOf();

			newSquare = potentialNewSquare;
			targetOffsetX += 1;
		} else if (performer.drawOffsetRatioX + performer.swimmingChangeX >= 1 - halfWidthRatio) {
			Square potentialNewSquare = performer.squareGameObjectIsOn.getSquareToRightOf();

			newSquare = potentialNewSquare;
			targetOffsetX -= 1;
		} else if (performer.drawOffsetRatioY + performer.swimmingChangeY < -halfHeightRatio) {
			Square potentialNewSquare = performer.squareGameObjectIsOn.getSquareAbove();

			newSquare = potentialNewSquare;
			targetOffsetY += 1;
		} else if (performer.drawOffsetRatioY + performer.swimmingChangeY >= 1 - halfHeightRatio) {
			Square potentialNewSquare = performer.squareGameObjectIsOn.getSquareBelow();

			newSquare = potentialNewSquare;
			targetOffsetY -= 1;
		}

		moveTo(performer, newSquare, performer.drawOffsetRatioX + targetOffsetX,
				performer.drawOffsetRatioY + targetOffsetY);

		fishingRod.progressThisTurn = 0;
	}

	public void moveTo(Actor actor, Square target, float targetOffsetX, float targetOffsetY) {

		Square oldSquare = actor.squareGameObjectIsOn;
		GameObject gameObjectInTheWay = target.inventory.gameObjectThatCantShareSquare;

		if (performer.squareGameObjectIsOn.onScreen() && performer.squareGameObjectIsOn.visibleToPlayer)
			performer.primaryAnimation = new AnimationMove(
					actor.squareGameObjectIsOn.xInGridPixels + actor.drawOffsetRatioX * Game.SQUARE_WIDTH,
					actor.squareGameObjectIsOn.yInGridPixels + actor.drawOffsetRatioY * Game.SQUARE_HEIGHT,
					target.xInGridPixels + targetOffsetX * Game.SQUARE_WIDTH,
					target.yInGridPixels + targetOffsetY * Game.SQUARE_HEIGHT);

		if (actor.squareGameObjectIsOn != target)
			target.inventory.add(actor);

		// move(actor, squareToMoveTo);
		actor.drawOffsetRatioX = targetOffsetX;
		actor.drawOffsetRatioY = targetOffsetY;

		performer.actionsPerformedThisTurn.add(this);

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
