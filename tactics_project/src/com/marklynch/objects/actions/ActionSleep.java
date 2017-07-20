package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIPath;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class ActionSleep extends Action {

	public static final String ACTION_NAME = "Sleep here";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	Actor performer;
	Square target;

	public ActionSleep(Actor mover, Square target) {
		super(ACTION_NAME, "action_sleep.png");
		this.performer = mover;
		this.target = target;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
		legal = checkLegality();
		sound = createSound();

	}

	@Override
	public void perform() {

		if (!enabled)
			return;
		moveTo(performer, target);

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();
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

		trespassingCheck(this, performer, performer.squareGameObjectIsOn);

		if (performer == Game.level.player && Game.level.activeActor == Game.level.player)
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

		if (performer.travelDistance - performer.distanceMovedThisTurn <= 0)
			return false;

		if (target == performer.squareGameObjectIsOn || !target.inventory.isPassable(performer))
			return false;

		AIPath path = performer.getPathTo(target);
		if (path.travelCost > performer.travelDistance - performer.distanceMovedThisTurn)
			return false;

		if (performer != Game.level.player && performer.swapCooldown > 0) {
			performer.swapCooldown--;
			return false;
		}

		Actor actorInTheWay = (Actor) target.inventory.getGameObjectThatCantShareSquare();

		if (actorInTheWay == Game.level.player) {
			return false;
		}

		if (performer.group != null && performer.group.getLeader() == actorInTheWay) {
			// don't try to swap with you group leader
			return false;
		}

		if (performer != Game.level.player && actorInTheWay != null
				&& (actorInTheWay.travelDistance - actorInTheWay.distanceMovedThisTurn <= 0)) {
			// If actorInTheWay has no moves left, doesn't count when player
			// tries to move
			return false;
		}

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
