package com.marklynch.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.primary.AnimationWalk;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Blind;
import com.marklynch.objects.actors.RockGolem;
import com.marklynch.objects.actors.TinyNeutralWildAnimal;
import com.marklynch.objects.inanimateobjects.Door;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.RemoteDoor;
import com.marklynch.objects.inanimateobjects.Stampable;

public class ActionMove extends Action {

	public static final String ACTION_NAME = "Move here";
	boolean endTurn;
	boolean animate = true;

	public ActionMove(GameObject performer, Square target, boolean endTurn, boolean animate) {
		super(ACTION_NAME, textureWalk, performer, target);
		super.gameObjectPerformer = performer;
		if (performer instanceof Actor)
			this.performer = (Actor) performer;
		this.targetSquare = target;
		this.endTurn = endTurn;
		this.animate = animate;
		if (!check()) {
			enabled = false;
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

		if (performer.squareGameObjectIsOn.xInGrid > targetSquare.xInGrid) {
			performer.backwards = true;
		} else if (performer.squareGameObjectIsOn.xInGrid < targetSquare.xInGrid) {
			performer.backwards = false;
		}

		moveTo(performer, targetSquare);
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

		if (animate && Game.level.shouldLog(actor, targetSquare)) {
			performer.setPrimaryAnimation(new AnimationWalk(performer, actor.squareGameObjectIsOn, squareToMoveTo,
					performer.walkPhase, null));
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
			if (actorInTheWay.groupOfActors != null && actorInTheWay.groupOfActors.getLeader() == actor) {
				// No swap cooldown for group leaders moving a member of their
				// group
			} else {
				actor.swapCooldown = (int) (Math.random() * 3);
			}
		}

		if (performer == Game.level.player) {
			if (Game.level.settingFollowPlayer) {
				if (performer.onScreen()) {
					if (Game.level.player.playerTargetAction != null
							&& Game.level.player.playerTargetAction.targetSquare == null)
						Game.level.cameraFollow = true;
					if (Game.level.cameraFollow)
						Game.level.dragToFollowPlayer();
				}
			}
		}

		if (sound != null)
			sound.play();

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

		if (!performer.canSeeSquare(targetSquare)) {
			return true;
		}

		if (targetSquare.inventory.door != null) {
			if (!targetSquare.inventory.door.open
					&& (!performer.canOpenDoors || targetSquare.inventory.door instanceof RemoteDoor)) {
				return false;
			} else if (targetSquare.inventory.door.locked && !performer.hasKeyForDoor(targetSquare.inventory.door)) {
				return false;
			}
		}

		GameObject objectInTheWay = targetSquare.inventory.gameObjectThatCantShareSquare;

		if (objectInTheWay instanceof Actor) {
			Actor actorInTheWay = (Actor) objectInTheWay;

			if (actorInTheWay == Game.level.player) {
				return false;
			}

			if (actorInTheWay instanceof RockGolem) {
				if (((RockGolem) actorInTheWay).awake == false)
					return false;
			}

			if (performer.groupOfActors != null && performer.groupOfActors.getLeader() == actorInTheWay) {
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
		if (performer.straightLineDistanceTo(targetSquare) > 1)
			return false;
		return true;
	}

	@Override
	public boolean checkLegality() {
		if (targetSquare.restricted() == true && !targetSquare.owners.contains(performer)) {
			illegalReason = TRESPASSING;
			return false;
		}

		Door door = (Door) targetSquare.inventory.getGameObjectOfClass(Door.class);
		if (door != null && door.isOpen() == false && !(door instanceof RemoteDoor)) {
			Action open = new ActionOpen(performer, door);
			if (!open.legal) {
				illegalReason = TRESPASSING;
				return false;
			}
		}

		return true;
	}

	@Override
	public Sound createSound() {

		// Sound of glass
		ArrayList<GameObject> stampables = targetSquare.inventory.getGameObjectsOfClass(Stampable.class);
		if (!(performer instanceof Blind) && stampables.size() > 0) {
			for (GameObject stampable : stampables) {
				return new Sound(performer, stampable, targetSquare, 10, legal, this.getClass());
			}
		}
		return null;
	}
}
