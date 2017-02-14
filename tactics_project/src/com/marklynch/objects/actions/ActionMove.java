package com.marklynch.objects.actions;

import com.marklynch.level.Square;
import com.marklynch.objects.units.Actor;

public class ActionMove extends Action {

	public static final String ACTION_NAME = "Move";
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

		if (actor.travelDistance - actor.distanceMovedThisTurn <= 0)
			return;

		if (squareToMoveTo == actor.squareGameObjectIsOn || !squareToMoveTo.inventory.isPassable())
			return;

		Square oldSquare = actor.squareGameObjectIsOn;
		Actor actorInTheWay = (Actor) squareToMoveTo.inventory.getGameObjectThatCantShareSquare();

		if (actorInTheWay == null) {
			move(actor, squareToMoveTo);
		} else if (actorInTheWay != null && (actorInTheWay.travelDistance - actorInTheWay.distanceMovedThisTurn > 0)) {
			move(actorInTheWay, oldSquare);
			move(actor, squareToMoveTo);
		} else {
			// There's someone in the way, but they dont have the movement
			// points to swap with u, wait till next turn
		}
	}

	private void move(Actor actor, Square square) {
		actor.squareGameObjectIsOn.inventory.remove(actor);
		actor.distanceMovedThisTurn += 1;
		actor.squareGameObjectIsOn = square;
		square.inventory.add(actor);
		// Actor.highlightSelectedCharactersSquares();
	}
}
