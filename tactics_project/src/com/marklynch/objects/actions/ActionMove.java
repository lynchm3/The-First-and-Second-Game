package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.objects.units.Actor;

public class ActionMove extends Action {

	public static final String ACTION_NAME = "Move here";
	Actor mover;
	Square target;

	public ActionMove(Actor mover, Square target) {
		super(ACTION_NAME);
		this.mover = mover;
		this.target = target;
	}

	@Override
	public void perform() {
		Square targetSquare = target;
		moveTo(mover, targetSquare);
	}

	public void moveTo(Actor actor, Square squareToMoveTo) {

		if (actor == Game.level.player) {
			System.out.println("actor.distanceMovedThisTurn = " + actor.distanceMovedThisTurn);
			System.out.println("actor.travelDistance = " + actor.travelDistance);
		}

		if (actor.travelDistance - actor.distanceMovedThisTurn <= 0)
			return;

		if (squareToMoveTo == actor.squareGameObjectIsOn || !squareToMoveTo.inventory.isPassable(actor))
			return;

		if (squareToMoveTo.walkingDistanceToSquare > actor.travelDistance - actor.distanceMovedThisTurn)
			return;

		if (actor != Game.level.player && actor.swapCooldown > 0) {
			actor.swapCooldown--;
			return;
		}

		Square oldSquare = actor.squareGameObjectIsOn;
		Actor actorInTheWay = (Actor) squareToMoveTo.inventory.getGameObjectThatCantShareSquare();

		if (actorInTheWay == Game.level.player) {
			return;
		}

		if (actorInTheWay == null) {
			move(actor, squareToMoveTo);

		} else if (actorInTheWay != null && actor.group != null && actor.group.getLeader() == actorInTheWay) {
			// don't try to swap with you group leader
			return;
		} else if (actorInTheWay != null && (actorInTheWay.travelDistance - actorInTheWay.distanceMovedThisTurn > 0)) {
			move(actorInTheWay, oldSquare);
			move(actor, squareToMoveTo);
			if (actorInTheWay.group != null && actorInTheWay.group.getLeader() == actor) {
				// No swap cooldown for group leaders moving a member of their
				// group
			} else {
				actor.swapCooldown = (int) (Math.random() * 3);
			}
		} else {
			// There's someone in the way, but they dont have the movement
			// points to swap with u, wait till next turn
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
}
