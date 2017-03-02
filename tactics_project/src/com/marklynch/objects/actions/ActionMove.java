package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Path;

public class ActionMove extends Action {

	public static final String ACTION_NAME = "Move here";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	Actor mover;
	Square target;

	public ActionMove(Actor mover, Square target) {
		super(ACTION_NAME);
		this.mover = mover;
		this.target = target;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}

	}

	@Override
	public void perform() {

		if (!enabled)
			return;
		moveTo(mover, target);
	}

	public void moveTo(Actor actor, Square squareToMoveTo) {

		Square oldSquare = actor.squareGameObjectIsOn;
		Actor actorInTheWay = (Actor) squareToMoveTo.inventory.getGameObjectThatCantShareSquare();

		if (actorInTheWay == Game.level.player) {
			return;
		}

		if (actorInTheWay == null) {
			move(actor, squareToMoveTo);

		} else {
			move(actorInTheWay, oldSquare);
			move(actor, squareToMoveTo);
			if (actorInTheWay.group != null && actorInTheWay.group.getLeader() == actor) {
				// No swap cooldown for group leaders moving a member of their
				// group
			} else {
				actor.swapCooldown = (int) (Math.random() * 3);
			}
		}

		if (mover == Game.level.player)
			Game.level.endTurn();
	}

	private void move(Actor actor, Square square) {
		actor.squareGameObjectIsOn.inventory.remove(actor);
		actor.distanceMovedThisTurn += 1;
		actor.squareGameObjectIsOn = square;
		square.inventory.add(actor);
		// Actor.highlightSelectedCharactersSquares();
	}

	@Override
	public boolean check() {

		System.out.println("check() a");

		if (mover.travelDistance - mover.distanceMovedThisTurn <= 0)
			return false;
		System.out.println("check() b");

		if (target == mover.squareGameObjectIsOn || !target.inventory.isPassable(mover))
			return false;
		System.out.println("check() c");

		Path path = mover.getPathTo(target);
		System.out.println("path = " + path);
		if (path != null)
			System.out.println("path.travelCost = " + path.travelCost);
		if (path == null || path.travelCost > mover.travelDistance - mover.distanceMovedThisTurn)
			return false;
		System.out.println("check() d");

		if (mover != Game.level.player && mover.swapCooldown > 0) {
			mover.swapCooldown--;
			return false;
		}
		System.out.println("check() e");

		Actor actorInTheWay = (Actor) target.inventory.getGameObjectThatCantShareSquare();

		if (actorInTheWay == Game.level.player) {
			return false;
		}
		System.out.println("check() f");

		if (mover.group != null && mover.group.getLeader() == actorInTheWay) {
			// don't try to swap with you group leader
			return false;
		}

		// if (actor.group != Game.level.activeActor.group) {
		//
		// }

		if (mover != Game.level.player && actorInTheWay != null
				&& (actorInTheWay.travelDistance - actorInTheWay.distanceMovedThisTurn <= 0)) {
			// If actorInTheWay has no moves left, doesn't count when player
			// tries to move
			return false;
		}
		System.out.println("check() h");

		return true;
	}
}
