package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.primary.AnimationWalk;
import com.marklynch.level.quest.caveoftheblind.Blind;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Door;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.RemoteDoor;
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
		super.gameObjectPerformer = this.performer = performer;
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
		super.perform();

		if (!enabled)
			return;

		if (!checkRange())
			return;

		if (performer.squareGameObjectIsOn.xInGrid > target.xInGrid) {
			performer.backwards = true;
		} else if (performer.squareGameObjectIsOn.xInGrid < target.xInGrid) {
			performer.backwards = false;
		}

		moveTo(performer, target);
	}

	public void moveTo(Actor actor, Square squareToMoveTo) {

		if (performer.peekSquare != null) {
			new ActionStopPeeking(performer).perform();
		}

		Door door = (Door) squareToMoveTo.inventory.getGameObjectOfClass(Door.class);
		if (door != null && door.isOpen() == false && !(door instanceof RemoteDoor)) {
			new ActionOpen(actor, door).perform();
		}

		Square oldSquare = actor.squareGameObjectIsOn;
		GameObject gameObjectInTheWay = squareToMoveTo.inventory.gameObjectThatCantShareSquare;

		Actor actorInTheWay = null;

		if (gameObjectInTheWay instanceof Actor)
			actorInTheWay = (Actor) squareToMoveTo.inventory.gameObjectThatCantShareSquare;

		if (actorInTheWay == Game.level.player) {
			return;
		}

		if (performer.squareGameObjectIsOn.onScreen() && performer.squareGameObjectIsOn.visibleToPlayer) {
			performer.setPrimaryAnimation(
					new AnimationWalk(performer, actor.squareGameObjectIsOn, squareToMoveTo, performer.walkPhase));
			// performer.primaryAnimation.phase = performer.walkPhase;
			performer.walkPhase++;
			if (performer.walkPhase >= 4) {
				performer.walkPhase = 0;
			}
		}

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
			if (Game.level.settingFollowPlayer) {
				if (performer.onScreen()) {
					if (Game.level.player.playerTargetSquare == null)
						Game.level.cameraFollow = true;
					if (Game.level.cameraFollow)
						Game.level.dragToFollowPlayer();
				}
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
			Game.level.endPlayerTurn();

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

		// if (performer.travelDistance - performer.distanceMovedThisTurn <= 0)
		// return false;
		//
		// if (target == performer.squareGameObjectIsOn ||
		// !target.inventory.isPassable(performer))
		// return false;
		//
		// AIPath path = performer.getPathTo(target);
		// if (path == null)
		// return false;
		//
		// if (!path.complete)
		// return false;
		//
		// if (performer.straightLineDistanceTo(target) >
		// performer.travelDistance - performer.distanceMovedThisTurn)
		// return false;
		//
		// if (path.travelCost > performer.travelDistance -
		// performer.distanceMovedThisTurn)
		// return false;
		//
		// if (performer != Game.level.player && performer.swapCooldown > 0) {
		// performer.swapCooldown--;
		// return false;
		// }

		if (!performer.canSeeSquare(target)) {
			return true;
		}

		if (target.inventory.door != null) {
			if (!target.inventory.door.open
					&& (!performer.canOpenDoors || target.inventory.door instanceof RemoteDoor)) {
				return false;
			} else if (target.inventory.door.locked && !performer.hasKeyForDoor(target.inventory.door)) {
				return false;
			}
		}

		GameObject objectInTheWay = target.inventory.gameObjectThatCantShareSquare;

		if (objectInTheWay instanceof Actor) {
			Actor actorInTheWay = (Actor) objectInTheWay;

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
		} else if (objectInTheWay != null) {
			return false;
		}

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
		if (target.restricted == true && !target.owners.contains(performer)) {
			return false;
		}

		Door door = (Door) target.inventory.getGameObjectOfClass(Door.class);
		if (door != null && door.isOpen() == false && !(door instanceof RemoteDoor)) {
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
