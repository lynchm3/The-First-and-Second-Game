package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.quest.caveoftheblind.Blind;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Stampable;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionTeleport extends Action {

	public static final String ACTION_NAME = "Teleport here";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	Actor performer;
	GameObject teleportee;
	Square target;
	boolean endTurn;
	GameObject gameObjectInTheWay;

	public ActionTeleport(Actor performer, GameObject teleportee, Square target, boolean endTurn) {
		super(ACTION_NAME, "action_teleport.png");
		this.performer = performer;
		this.teleportee = teleportee;
		this.target = target;
		this.endTurn = endTurn;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
		if (!teleportee.canShareSquare)
			gameObjectInTheWay = target.inventory.getGameObjectThatCantShareSquare();
		legal = checkLegality();
		sound = createSound();
		movement = true;

	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		if (performer.peekSquare != null) {
			new ActionStopPeeking(performer).perform();
		}

		teleport(teleportee, target);

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (!legal) {

			// GameObject gameObjectInTheWay = null;
			if (gameObjectInTheWay != null) {
				if (teleportee != Game.level.player && teleportee.owner != null
						&& teleportee.owner != Game.level.player) {
					Actor victim;
					Crime.TYPE severity;
					if (teleportee instanceof Actor) {
						victim = (Actor) teleportee;
						severity = Crime.TYPE.CRIME_ASSAULT;

					} else {
						victim = teleportee.owner;
						severity = Crime.TYPE.CRIME_VANDALISM;
					}
					Crime crime = new Crime(this, this.performer, victim, severity);
					this.performer.crimesPerformedThisTurn.add(crime);
					this.performer.crimesPerformedInLifetime.add(crime);
					notifyWitnessesOfCrime(crime);

				}
				if (gameObjectInTheWay != Game.level.player && gameObjectInTheWay.owner != null
						&& gameObjectInTheWay.owner != Game.level.player) {
					Actor victim;
					Crime.TYPE severity;
					if (gameObjectInTheWay instanceof Actor) {
						victim = (Actor) gameObjectInTheWay;
						severity = Crime.TYPE.CRIME_ASSAULT;

					} else {
						victim = gameObjectInTheWay.owner;
						severity = Crime.TYPE.CRIME_VANDALISM;
					}
					Crime crime = new Crime(this, this.performer, victim, severity);
					this.performer.crimesPerformedThisTurn.add(crime);
					this.performer.crimesPerformedInLifetime.add(crime);
					notifyWitnessesOfCrime(crime);
				}
			}
		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}

		if (endTurn && performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endTurn();

		trespassingCheck(this, performer, teleportee.squareGameObjectIsOn);

		Level.teleportee = null;

	}

	private void teleport(GameObject gameObject, Square square) {

		gameObject.squareGameObjectIsOn.inventory.remove(gameObject);
		gameObject.squareGameObjectIsOn = square;
		square.inventory.add(gameObject);

		if (Game.level.shouldLog(performer, teleportee)) {
			if (performer == teleportee)
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " teleported to ", target }));
			else
				Game.level.logOnScreen(
						new ActivityLog(new Object[] { performer, " teleported ", teleportee, " to ", target }));
		}

		// Teleported big object on to big object... someone has to die.
		if (gameObjectInTheWay != null) {
			float damage = Math.min(gameObject.remainingHealth, gameObjectInTheWay.remainingHealth);
			if (Game.level.shouldLog(gameObject, gameObjectInTheWay))
				Game.level.logOnScreen(new ActivityLog(new Object[] { gameObject, " teleported in to ",
						gameObjectInTheWay, ", both took " + damage + " damage" }));

			gameObject.remainingHealth -= damage;
			gameObjectInTheWay.remainingHealth -= damage;

			if (performer == teleportee) {
				gameObjectInTheWay.attackedBy(gameObject, this);
				gameObject.attackedBy(gameObjectInTheWay, this);
			} else {
				gameObjectInTheWay.attackedBy(performer, this);
				gameObject.attackedBy(performer, this);
			}

		}
	}

	@Override
	public boolean check() {

		if (target == teleportee.squareGameObjectIsOn)
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
	public boolean checkLegality() {
		if (target.restricted == true && !target.owners.contains(teleportee)) {
			return false;
		}

		// If ur going to dmg something...
		if (gameObjectInTheWay != null) {
			if (teleportee != Game.level.player && teleportee.owner != null && teleportee.owner != Game.level.player)
				return false;
			if (gameObjectInTheWay != Game.level.player && gameObjectInTheWay.owner != null
					&& gameObjectInTheWay.owner != Game.level.player)
				return false;

		}

		return true;
	}

	@Override
	public Sound createSound() {

		// Sound of glass
		ArrayList<GameObject> stampables = target.inventory.getGameObjectsOfClass(Stampable.class);
		if (!(teleportee instanceof Blind) && stampables.size() > 0) {
			for (GameObject stampable : stampables) {
				return new Sound(teleportee, stampable, target, 10, legal, this.getClass());
			}
		}
		return null;
	}
}
