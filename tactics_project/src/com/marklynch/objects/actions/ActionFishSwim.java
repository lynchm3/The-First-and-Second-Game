package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.AnimationMove;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.WaterBody;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Fish;

public class ActionFishSwim extends Action {

	public static final String ACTION_NAME = "Swim";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	Fish performer;
	Square target;
	float targetOffsetX = 0;
	float targetOffsetY = 0;

	public ActionFishSwim(Fish performer, Square target, float targetOffsetX, float targetOffsetY) {
		super(ACTION_NAME, "action_move.png");
		this.performer = performer;
		this.target = target;
		this.targetOffsetX = targetOffsetX;
		this.targetOffsetY = targetOffsetY;
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

		System.out.println("FISH SWIM a");

		if (!enabled)
			return;
		System.out.println("FISH SWIM b");

		if (!checkRange())
			return;
		System.out.println("FISH SWIM c");

		moveTo(performer, target);

		// float startX = performer.squareGameObjectIsOn.xInGridPixels
		// + Game.SQUARE_WIDTH * this.performer.drawOffsetRatioX;
		// float startY = performer.squareGameObjectIsOn.yInGridPixels
		// + Game.SQUARE_HEIGHT * this.performer.drawOffsetRatioY;
		//
		// float endX = target.xInGridPixels + Game.SQUARE_WIDTH *
		// targetOffsetX;
		// float endY = target.yInGridPixels + Game.SQUARE_HEIGHT *
		// targetOffsetY;
		//
		// float distanceToCoverX = endX - startX;
		// float distanceToCoverY = endY - startY;
		// float totalDistanceToCover = Math.abs(distanceToCoverX) +
		// Math.abs(distanceToCoverY);
		//
		// float speed = 1f;
		//
		// float speedX = (distanceToCoverX / totalDistanceToCover) * speed;
		// float speedY = (distanceToCoverY / totalDistanceToCover) * speed;
		//
		// // NOW... to do this turn... FUCK... need the delta...
		//
		// float delta = 10f;
		//
		// float distanceX = speedX * delta;
		// float distanceY = speedY * delta;
		//
		// performer.drawOffsetRatioX += distanceX / Game.SQUARE_WIDTH;
		// performer.drawOffsetRatioY += distanceY / Game.SQUARE_HEIGHT;
		//
		// float newX = performer.squareGameObjectIsOn.xInGridPixels +
		// Game.SQUARE_WIDTH * this.performer.drawOffsetRatioX;
		// float newY = performer.squareGameObjectIsOn.yInGridPixels
		// + Game.SQUARE_HEIGHT * this.performer.drawOffsetRatioY;
		//
		// Square newSquare = Game.level.squares[(int) Math.floor(newX /
		// Game.SQUARE_WIDTH)][(int) Math
		// .floor(newY / Game.SQUARE_HEIGHT)];
		//
		// if (performer.squareGameObjectIsOn != newSquare) {
		// Square oldSquare = performer.squareGameObjectIsOn;
		// newSquare.inventory.add(performer);
		// performer.drawOffsetRatioX += (oldSquare.xInGrid -
		// newSquare.xInGrid);
		// performer.drawOffsetRatioY += (oldSquare.yInGrid -
		// newSquare.yInGrid);
		// }
		//
		// // IF WHOLLY IN NEW SQR, MOVE IT TO NEW SQUARE, offsets +=1 (or
		// mostly?)

	}

	public void moveTo(Actor actor, Square squareToMoveTo) {

		Square oldSquare = actor.squareGameObjectIsOn;
		GameObject gameObjectInTheWay = squareToMoveTo.inventory.gameObjectThatCantShareSquare;

		if (performer.squareGameObjectIsOn.onScreen() && performer.squareGameObjectIsOn.visibleToPlayer)
			performer.primaryAnimation = new AnimationMove(actor.drawOffsetRatioX * Game.SQUARE_WIDTH,
					actor.drawOffsetRatioY * Game.SQUARE_HEIGHT, this.targetOffsetX * Game.SQUARE_WIDTH,
					this.targetOffsetY * Game.SQUARE_HEIGHT);

		// move(actor, squareToMoveTo);
		actor.drawOffsetRatioX = this.targetOffsetX;
		actor.drawOffsetRatioY = this.targetOffsetY;

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
		System.out.println("FISH SWIM check a");
		if (target == null)
			return false;

		System.out.println("FISH SWIM check b");
		if (!target.inventory.contains(WaterBody.class)) {
			return false;
		}
		System.out.println("FISH SWIM check c");
		return true;
	}

	@Override
	public boolean checkRange() {
		if (performer.straightLineDistanceTo(target) > 1)
			return false;
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
