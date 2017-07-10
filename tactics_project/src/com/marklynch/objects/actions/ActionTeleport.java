package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.quest.caveoftheblind.Blind;
import com.marklynch.objects.Door;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.Stampable;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionTeleport extends Action {

	public static final String ACTION_NAME = "Teleport here";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	Actor performer;
	Square target;
	boolean endTurn;

	public ActionTeleport(Actor mover, Square target, boolean endTurn) {
		super(ACTION_NAME, "action_teleport.png");
		this.performer = mover;
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

		Door door = (Door) target.inventory.getGameObjectOfClass(Door.class);
		if (door != null && door.isOpen() == false) {
			new ActionOpen(performer, door).perform();
		}

		Square oldSquare = performer.squareGameObjectIsOn;
		GameObject gameObjectInTheWay = target.inventory.getGameObjectThatCantShareSquare();

		Actor actorInTheWay = null;

		if (gameObjectInTheWay instanceof Actor)
			actorInTheWay = (Actor) target.inventory.getGameObjectThatCantShareSquare();

		if (actorInTheWay == Game.level.player) {
			return;
		}

		if (actorInTheWay == null) {
			move(performer, target);

		} else {
			move(actorInTheWay, oldSquare);
			move(performer, target);
			if (actorInTheWay.group != null && actorInTheWay.group.getLeader() == performer) {
				// No swap cooldown for group leaders moving a member of their
				// group
			} else {
				performer.swapCooldown = (int) (Math.random() * 3);
			}
		}

		Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " teleported to ", target }));

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

		// if (performer.travelDistance - performer.distanceMovedThisTurn <= 0)
		// return false;

		if (target == performer.squareGameObjectIsOn || !target.inventory.isPassable(performer))
			return false;

		// Path path = performer.getPathTo(target);
		// if (path == null)
		// return false;

		// if (path.travelCost > performer.travelDistance -
		// performer.distanceMovedThisTurn)
		// return false;

		// if (performer != Game.level.player && performer.swapCooldown > 0) {
		// performer.swapCooldown--;
		// return false;
		// }

		GameObject objectInTheWay = target.inventory.getGameObjectThatCantShareSquare();
		if (objectInTheWay != null)
			return false;

		return true;
	}

	@Override
	public boolean checkLegality() {
		if (target.restricted == true && !target.owners.contains(performer)) {
			return false;
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
