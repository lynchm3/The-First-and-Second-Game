package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.ai.utils.AIPath;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.AnimationMove;
import com.marklynch.level.quest.caveoftheblind.Blind;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Door;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.Stampable;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.RockGolem;
import com.marklynch.objects.units.TinyNeutralWildAnimal;

public class ActionMove extends Action {

	public static final String ACTION_NAME = "Move here";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	Actor performer;
	Square target;
	boolean endTurn;

	public ActionMove(Actor performer, Square target, boolean endTurn) {
		super(ACTION_NAME, "action_move.png");
		this.performer = performer;
		this.target = target;
		this.endTurn = endTurn;
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

		if (!enabled)
			return;
		moveTo(performer, target);
	}

	public void moveTo(Actor actor, Square squareToMoveTo) {

		if (performer.peekSquare != null) {
			new ActionStopPeeking(performer).perform();
		}

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

		if (performer.squareGameObjectIsOn.onScreen() && performer.squareGameObjectIsOn.visibleToPlayer)
			performer.primaryAnimation = new AnimationMove(actor.squareGameObjectIsOn, squareToMoveTo);

		if (actorInTheWay == null) {
			move(actor, squareToMoveTo);

		} else if (performer == Game.level.player && actorInTheWay instanceof TinyNeutralWildAnimal) {

			move(actor, squareToMoveTo);
			new ActionSquash(performer, actorInTheWay, true).perform();

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

		if (performer == Game.level.player) {
			if (performer.onScreen()) {
				if (Game.level.player.playerTargetSquare == null)
					Game.level.cameraFollow = true;
				if (Game.level.cameraFollow)
					Game.level.dragToFollowPlayer();
			}

		}

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (performer.hiding) {

			HidingPlace hidingPlaceAtDestination = (HidingPlace) target.inventory
					.getGameObjectOfClass(HidingPlace.class);

			if (hidingPlaceAtDestination == null || hidingPlaceAtDestination.remainingHealth <= 0) {
				new ActionStopHiding(performer, performer.hidingPlace).perform();
				// performer.hiding = false;
				// performer.hidingPlace.actorsHidingHere.remove(performer);
				// performer.hidingPlace = null;
			} else {
				performer.hidingPlace.actorsHidingHere.remove(performer);
				performer.hidingPlace = (HidingPlace) target.inventory.getGameObjectOfClass(HidingPlace.class);
				performer.hidingPlace.actorsHidingHere.add(performer);
			}
		}

		if (endTurn && performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endTurn();

		trespassingCheck(this, performer, performer.squareGameObjectIsOn);

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

		if (performer.travelDistance - performer.distanceMovedThisTurn <= 0)
			return false;

		if (target == null) {
			System.out.println("performer = " + performer);
			System.out.println("target = " + target);
			System.out.println("target.inventory = " + target.inventory);
			for (StackTraceElement s : Thread.currentThread().getStackTrace()) {
				System.out.println(s);
			}
		}

		if (target == performer.squareGameObjectIsOn || !target.inventory.isPassable(performer))
			return false;

		AIPath path = performer.getPathTo(target);
		if (path == null)
			return false;

		if (!path.complete)
			return false;

		if (performer.straightLineDistanceTo(target) > performer.travelDistance - performer.distanceMovedThisTurn)
			return false;

		if (path.travelCost > performer.travelDistance - performer.distanceMovedThisTurn)
			return false;

		if (performer != Game.level.player && performer.swapCooldown > 0) {
			performer.swapCooldown--;
			return false;
		}

		GameObject objectInTheWay = target.inventory.getGameObjectThatCantShareSquare();

		Actor actorInTheWay = null;
		if (objectInTheWay instanceof Actor) {
			actorInTheWay = (Actor) objectInTheWay;

			if (actorInTheWay == Game.level.player) {
				return false;
			}

			if (actorInTheWay instanceof RockGolem) {
				if (((RockGolem) actorInTheWay).awake == false)
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
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		if (target.restricted == true && !target.owners.contains(performer)) {
			return false;
		}

		Door door = (Door) target.inventory.getGameObjectOfClass(Door.class);
		if (door != null && door.isOpen() == false) {
			Action open = new ActionOpen(performer, door);
			if (!open.legal) {
				return false;
			}
		}

		return true;
	}

	@Override
	public Sound createSound() {

		// Sound of glass
		ArrayList<GameObject> stampables = target.inventory.getGameObjectsOfClass(Stampable.class);
		if (!(performer instanceof Blind) && stampables.size() > 0) {
			for (GameObject stampable : stampables) {
				return new Sound(performer, stampable, target, 10, legal, this.getClass());
			}
		}
		return null;
	}
}
