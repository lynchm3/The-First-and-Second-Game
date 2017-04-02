package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.quest.caveoftheblind.Blind;
import com.marklynch.objects.Door;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Stampable;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Path;

public class ActionMove extends Action {

	public static final String ACTION_NAME = "Move here";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	Actor performer;
	Square target;

	public ActionMove(Actor mover, Square target) {
		super(ACTION_NAME);
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
	}

	public void moveTo(Actor actor, Square squareToMoveTo) {

		Door door = (Door) squareToMoveTo.inventory.getGameObjectOfClass(Door.class);
		if (door != null && door.isOpen() == false) {
			new ActionOpen(actor, door).perform();
		}

		Square oldSquare = actor.squareGameObjectIsOn;
		GameObject gameObjectInTheWay = squareToMoveTo.inventory.getGameObjectThatCantShareSquare();

		Actor actorInTheWay = null;

		if (gameObjectInTheWay instanceof Actor)
			actorInTheWay = (Actor) squareToMoveTo.inventory.getGameObjectThatCantShareSquare();

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

		if (performer == Game.level.player)
			Game.level.endTurn();

		performer.actions.add(this);
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

		Path path = performer.getPathTo(target);
		if (path != null)
			if (path == null || path.travelCost > performer.travelDistance - performer.distanceMovedThisTurn)
				return false;

		if (performer != Game.level.player && performer.swapCooldown > 0) {
			performer.swapCooldown--;
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

		if (performer.group != null && performer.group.getLeader() == actorInTheWay) {
			// don't try to swap with you group leader
			return false;
		}

		// if (actor.group != Game.level.activeActor.group) {
		//
		// }

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

		// Sound of glass
		ArrayList<GameObject> stampables = target.inventory.getGameObjectsOfClass(Stampable.class);
		System.out.println("stampables.size() = " + stampables.size());
		if (!(performer instanceof Blind) && stampables.size() > 0) {
			for (GameObject stampable : stampables) {
				System.out.println("performer = " + performer);
				System.out.println("MAKING SOUND");
				return new Sound(performer, stampable, target, 10, legal, this.getClass());
			}
		}
		return null;
	}
}
