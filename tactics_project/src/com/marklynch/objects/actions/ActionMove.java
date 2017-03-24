package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.BrokenGlass;
import com.marklynch.objects.Door;
import com.marklynch.objects.GameObject;
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

		Door door = (Door) squareToMoveTo.inventory.getGameObectOfClass(Door.class);
		if (door != null && door.isOpen() == false) {
			new ActionOpen(actor, door).perform();
		}

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

		// Sound of glass
		BrokenGlass brokenGlass = (BrokenGlass) target.inventory.getGameObectOfClass(BrokenGlass.class);
		if (brokenGlass != null) {
			mover.sounds.add(new Sound(mover, brokenGlass, mover.squareGameObjectIsOn, 10));
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

		if (mover.travelDistance - mover.distanceMovedThisTurn <= 0)
			return false;

		if (target == mover.squareGameObjectIsOn || !target.inventory.isPassable(mover))
			return false;

		Path path = mover.getPathTo(target);
		if (path != null)
			if (path == null || path.travelCost > mover.travelDistance - mover.distanceMovedThisTurn)
				return false;

		if (mover != Game.level.player && mover.swapCooldown > 0) {
			mover.swapCooldown--;
			return false;
		}

		GameObject objectInTheWay = target.inventory.getGameObjectThatCantShareSquare();

		Actor actorInTheWay = null;
		if (objectInTheWay instanceof Actor) {
			actorInTheWay = (Actor) objectInTheWay;
		}

		if (actorInTheWay == Game.level.player) {
			return false;
		}

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

		return true;
	}
}
