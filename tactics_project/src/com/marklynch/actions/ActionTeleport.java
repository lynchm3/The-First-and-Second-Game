package com.marklynch.actions;

import java.util.concurrent.CopyOnWriteArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.Level.LevelMode;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.constructs.animation.primary.AnimationPush;
import com.marklynch.level.constructs.animation.primary.AnimationTeleport;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Blind;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Stampable;
import com.marklynch.ui.ActivityLog;

public class ActionTeleport extends Action {

	public static final String ACTION_NAME = "Teleport here";
	public static final String ACTION_NAME_FAST_TRAVEL = "Fast travel";
	boolean endTurn;
	GameObject gameObjectInTheWay;
	Square squareToTeleportTo;
	boolean log;
	boolean fastTravel;

	public ActionTeleport(GameObject performer, GameObject target, Square squareToTeleportTo, boolean endTurn,
			boolean log, boolean fastTravel) {
		super(ACTION_NAME, textureTeleport, performer, target);
		this.fastTravel = fastTravel;
		if (fastTravel) {
			this.actionName = ACTION_NAME_FAST_TRAVEL;
		}
		this.squareToTeleportTo = squareToTeleportTo;
		this.endTurn = endTurn;
		if (!check()) {
			enabled = false;
		}
		if (!target.canShareSquare)
			gameObjectInTheWay = this.squareToTeleportTo.inventory.gameObjectThatCantShareSquare;
		legal = checkLegality();
		sound = createSound();
		movement = true;
		this.log = log;

	}

	@Override
	public void perform() {
		super.perform();

		if (!enabled)
			return;

		if (!checkRange())
			return;

		if (performer != null && performer.peekSquare != null) {
			new ActionStopPeeking(performer).perform();
		}

		if (targetGameObject != gameObjectPerformer && performer instanceof Actor) {
			gameObjectPerformer.setPrimaryAnimation(new AnimationPush(gameObjectPerformer, squareToTeleportTo,
					gameObjectPerformer.getPrimaryAnimation(), null));
		}

		Square startSquare = targetGameObject.squareGameObjectIsOn;

		boolean straightenUp = performer == targetGameObject;
		targetGameObject.setPrimaryAnimation(new AnimationTeleport(targetGameObject, startSquare, squareToTeleportTo,
				straightenUp, new OnCompletionListener() {
					@Override
					public void animationComplete(GameObject gameObject) {
						postAnimation();
					}
				}));

	}

	public void postAnimation() {

		if (performer == Game.level.player)
			Game.level.levelMode = LevelMode.LEVEL_MODE_NORMAL;

		if (log && Game.level.shouldLog(gameObjectPerformer, targetGameObject)) {
			if (gameObjectPerformer == targetGameObject) {

				String actionString = " teleported to ";
				if (fastTravel) {
					actionString = " fast traveled to ";
				}

				Game.level.logOnScreen(
						new ActivityLog(new Object[] { gameObjectPerformer, actionString, squareToTeleportTo }));
			} else {
				Game.level.logOnScreen(new ActivityLog(new Object[] { gameObjectPerformer, " teleported ",
						targetGameObject, " to ", squareToTeleportTo }));
			}
		}

		// Teleported big object on to big object... someone has to die.
		if (gameObjectInTheWay != null) {
			float damage = Math.min(targetGameObject.remainingHealth, gameObjectInTheWay.remainingHealth);
			if (log && Game.level.shouldLog(targetGameObject, gameObjectInTheWay))
				Game.level.logOnScreen(new ActivityLog(new Object[] { targetGameObject, " teleported in to ",
						gameObjectInTheWay, ", both took " + damage + " damage" }));

			targetGameObject.changeHealth(-damage, gameObjectPerformer, this);
			gameObjectInTheWay.changeHealth(-damage, gameObjectPerformer, this);

			if (gameObjectPerformer == targetGameObject) {
				targetGameObject.changeHealth(-damage, gameObjectInTheWay, this);
				gameObjectInTheWay.changeHealth(-damage, targetGameObject, this);
			} else {
				targetGameObject.changeHealth(-damage, gameObjectPerformer, this);
				gameObjectInTheWay.changeHealth(-damage, gameObjectPerformer, this);
			}

		}

		if (sound != null)
			sound.play();

		if (performer != null && !legal) {

			// GameObject gameObjectInTheWay = null;
			if (gameObjectInTheWay != null) {
				if (targetGameObject != Game.level.player && targetGameObject.owner != null
						&& targetGameObject.owner != Game.level.player) {
					Actor victim;
					Crime.TYPE severity;
					if (targetGameObject instanceof Actor) {
						victim = (Actor) targetGameObject;
						severity = Crime.TYPE.CRIME_ASSAULT;

					} else {
						victim = targetGameObject.owner;
						severity = Crime.TYPE.CRIME_VANDALISM;
					}
					Crime crime = new Crime(this.performer, victim, severity);
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
					Crime crime = new Crime(this.performer, victim, severity);
					this.performer.crimesPerformedThisTurn.add(crime);
					this.performer.crimesPerformedInLifetime.add(crime);
					notifyWitnessesOfCrime(crime);
				}
			}
		} else if (performer != null) {
			trespassingCheck(performer, performer.squareGameObjectIsOn);
		}

		if (targetGameObject == Level.player && !squareToTeleportTo.onScreen()) {
			Game.level.centerToSquare = true;
			Game.level.squareToCenterTo = squareToTeleportTo;
		}
//		else {
//			Level.gameObjectsToFlash.add(gameObject);
//			Level.flashGameObjectCounters.put(gameObject, 0);
//		}

		if (endTurn && gameObjectPerformer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endPlayerTurn();

		if (performer != null)
			trespassingCheck(performer, targetGameObject.squareGameObjectIsOn);

		Level.teleportee = null;
	}

	private void teleport() {
	}

	@Override
	public boolean check() {

		if (!targetGameObject.moveable || targetGameObject.isFloorObject)
			return false;

		// if (squareToTeleportTo == target.squareGameObjectIsOn)
		// return false;

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
		if (squareToTeleportTo.restricted() == true && !squareToTeleportTo.owners.contains(targetGameObject)) {
			illegalReason = TRESPASSING;
			return false;
		}

		if (targetGameObject != gameObjectPerformer) {
			boolean legalToPerformOnTeleportee = standardAttackLegalityCheck(gameObjectPerformer, targetGameObject);
			if (!legalToPerformOnTeleportee) {
				return false;
			}
		}

		// If ur going to dmg something...
		if (gameObjectInTheWay != null) {
			return standardAttackLegalityCheck(gameObjectPerformer, gameObjectInTheWay);
		}

		return true;
	}

	@Override
	public Sound createSound() {

		// Sound of glass
		CopyOnWriteArrayList<GameObject> stampables = squareToTeleportTo.inventory.getGameObjectsOfClass(Stampable.class);
		if (!(targetGameObject instanceof Blind) && stampables.size() > 0) {
			for (GameObject stampable : stampables) {
				return new Sound(targetGameObject, stampable, squareToTeleportTo, 10, legal, this.getClass());
			}
		}
		return null;
	}
}
