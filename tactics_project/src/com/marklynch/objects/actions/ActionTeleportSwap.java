package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.quest.caveoftheblind.Blind;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Door;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.RemoteDoor;
import com.marklynch.objects.Stampable;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionTeleportSwap extends Action {

	public static final String ACTION_NAME = "Swap here";
	GameObject teleportee;
	boolean endTurn;

	public ActionTeleportSwap(Actor performer, GameObject teleportee, Square targetSquare, boolean endTurn) {
		super(ACTION_NAME, textureTeleport, performer, targetSquare);
		super.gameObjectPerformer = this.performer = performer;
		this.teleportee = teleportee;
		this.endTurn = endTurn;
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

		if (performer.peekSquare != null) {
			new ActionStopPeeking(performer).perform();
		}

		Door door = (Door) targetSquare.inventory.getGameObjectOfClass(Door.class);
		if (door != null && door.isOpen() == false && !(door instanceof RemoteDoor)) {
			new ActionOpen(teleportee, door).perform();
		}

		// Actor actorInTheWay = null;
		//
		// if (gameObjectInTheWay instanceof Actor)
		// actorInTheWay = (Actor)
		// target.inventory.getGameObjectThatCantShareSquare();

		// if (actorInTheWay == Game.level.player) {
		// return;
		// }

		// if (actorInTheWay == null) {
		teleportSwap(teleportee, targetSquare);

		// } else {
		// move(actorInTheWay, oldSquare);
		// move(teleportee, target);
		// }
		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (endTurn && performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endPlayerTurn();

		trespassingCheck(this, performer, teleportee.squareGameObjectIsOn);

		Level.teleportee = null;

	}

	private void teleportSwap(GameObject gameObject, Square square) {

		Square originalSquare = gameObject.squareGameObjectIsOn;
		GameObject gameObjectInTheWay = null;
		if (!gameObject.canShareSquare)
			gameObjectInTheWay = square.inventory.gameObjectThatCantShareSquare;

		gameObject.squareGameObjectIsOn.inventory.remove(gameObject);
		gameObject.squareGameObjectIsOn = square;
		square.inventory.add(gameObject);

		if (Game.level.shouldLog(performer, teleportee)) {
			if (performer == teleportee)
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " teleported to ", targetSquare }));
			else
				Game.level.logOnScreen(
						new ActivityLog(new Object[] { performer, " teleported ", teleportee, " to ", targetSquare }));
		}

		// Teleported big object on to big object... someone has to die.
		if (gameObjectInTheWay != null) {
			if (Game.level.shouldLog(gameObject, gameObjectInTheWay))
				Game.level.logOnScreen(
						new ActivityLog(new Object[] { gameObject, " swapped with ", gameObjectInTheWay }));

			gameObjectInTheWay.squareGameObjectIsOn.inventory.remove(gameObjectInTheWay);
			gameObjectInTheWay.squareGameObjectIsOn = originalSquare;
			originalSquare.inventory.add(gameObjectInTheWay);
		}
	}

	@Override
	public boolean check() {

		if (targetSquare == teleportee.squareGameObjectIsOn)
			return false;

		// if (target.inventory.isPassable(teleportee))
		// return false;
		//
		// GameObject objectInTheWay =
		// target.inventory.getGameObjectThatCantShareSquare();
		// if (objectInTheWay != null)
		// return false;

		return true;
	}

	@Override
	public boolean checkRange() {

		return true;
	}

	@Override
	public boolean checkLegality() {
		if (teleportee != gameObjectPerformer) {
			boolean illegalToPerformOnTeleportee = standardAttackLegalityCheck(gameObjectPerformer, teleportee);
			if (illegalToPerformOnTeleportee) {
				return false;
			}
		}

		// if (teleportee != gameO) {
		// boolean illegalToPerformOnTeleportee =
		// standardAttackLegalityCheck(gameObjectPerformer, teleportee);
		// if (illegalToPerformOnTeleportee) {
		// return false;
		// }
		// }

		if (targetSquare.restricted() == true && !targetSquare.owners.contains(teleportee)) {
			illegalReason = TRESPASSING;
			return false;
		}
		return true;
	}

	@Override
	public Sound createSound() {

		// Sound of glass
		ArrayList<GameObject> stampables = targetSquare.inventory.getGameObjectsOfClass(Stampable.class);
		if (!(teleportee instanceof Blind) && stampables.size() > 0) {
			for (GameObject stampable : stampables) {
				return new Sound(teleportee, stampable, targetSquare, 10, legal, this.getClass());
			}
		}
		return null;
	}
}
