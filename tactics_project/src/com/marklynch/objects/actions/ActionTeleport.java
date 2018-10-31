package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.Level.LevelMode;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.constructs.animation.primary.AnimationPush;
import com.marklynch.level.constructs.animation.primary.AnimationTeleport;
import com.marklynch.level.quest.caveoftheblind.Blind;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Stampable;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionTeleport extends Action {

	public static final String ACTION_NAME = "Teleport here";
	Actor actorPerformer;
	GameObject gameObjectPerformer;
	GameObject teleportee;
	Square targetSquare;
	boolean endTurn;
	GameObject gameObjectInTheWay;

	public ActionTeleport(GameObject performer, GameObject teleportee, Square target, boolean endTurn) {
		super(ACTION_NAME, textureTeleport, performer, target, targetSquare);
		super.gameObjectPerformer = this.gameObjectPerformer = performer;
		if (performer instanceof Actor)
			this.actorPerformer = (Actor) performer;
		this.teleportee = teleportee;
		this.targetSquare = target;
		this.endTurn = endTurn;
		if (!check()) {
			enabled = false;
		}
		if (!teleportee.canShareSquare)
			gameObjectInTheWay = target.inventory.gameObjectThatCantShareSquare;
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

		if (actorPerformer != null && actorPerformer.peekSquare != null) {
			new ActionStopPeeking(actorPerformer).perform();
		}

		if (teleportee != gameObjectPerformer) {
			gameObjectPerformer.setPrimaryAnimation(new AnimationPush(gameObjectPerformer, targetSquare,
					gameObjectPerformer.getPrimaryAnimation(), null));
		}

		Square startSquare = teleportee.squareGameObjectIsOn;

		if (teleportee == Level.player) {
			Level.pausePlayer();
		}

		teleportee.setPrimaryAnimation(
				new AnimationTeleport(teleportee, startSquare, targetSquare, new OnCompletionListener() {
					@Override
					public void animationComplete(GameObject gameObject) {
						postAnimation();
					}
				}));

	}

	public void postAnimation() {

		teleportee.lastSquare = teleportee.squareGameObjectIsOn;
		Game.level.levelMode = LevelMode.LEVEL_MODE_NORMAL;

		teleport(teleportee, targetSquare);

		gameObjectPerformer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (actorPerformer != null && !legal) {

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
					Crime crime = new Crime(this, this.actorPerformer, victim, severity);
					this.actorPerformer.crimesPerformedThisTurn.add(crime);
					this.actorPerformer.crimesPerformedInLifetime.add(crime);
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
					Crime crime = new Crime(this, this.actorPerformer, victim, severity);
					this.actorPerformer.crimesPerformedThisTurn.add(crime);
					this.actorPerformer.crimesPerformedInLifetime.add(crime);
					notifyWitnessesOfCrime(crime);
				}
			}
		} else if (actorPerformer != null) {
			trespassingCheck(this, actorPerformer, actorPerformer.squareGameObjectIsOn);
		}

		if (endTurn && gameObjectPerformer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endPlayerTurn();

		if (actorPerformer != null)
			trespassingCheck(this, actorPerformer, teleportee.squareGameObjectIsOn);

		Level.teleportee = null;
	}

	private void teleport(GameObject gameObject, Square square) {

		gameObject.squareGameObjectIsOn.inventory.remove(gameObject);
		gameObject.squareGameObjectIsOn = square;
		square.inventory.add(gameObject);

		if (Game.level.shouldLog(gameObjectPerformer, teleportee)) {
			if (gameObjectPerformer == teleportee)
				Game.level.logOnScreen(
						new ActivityLog(new Object[] { gameObjectPerformer, " teleported to ", targetSquare }));
			else
				Game.level.logOnScreen(new ActivityLog(
						new Object[] { gameObjectPerformer, " teleported ", teleportee, " to ", targetSquare }));
		}

		// Teleported big object on to big object... someone has to die.
		if (gameObjectInTheWay != null) {
			float damage = Math.min(gameObject.remainingHealth, gameObjectInTheWay.remainingHealth);
			if (Game.level.shouldLog(gameObject, gameObjectInTheWay))
				Game.level.logOnScreen(new ActivityLog(new Object[] { gameObject, " teleported in to ",
						gameObjectInTheWay, ", both took " + damage + " damage" }));

			gameObject.changeHealth(-damage, null, null);
			gameObjectInTheWay.changeHealth(-damage, null, null);

			if (gameObjectPerformer == teleportee) {
				gameObject.changeHealth(-damage, gameObjectInTheWay, this);
				gameObjectInTheWay.changeHealth(-damage, gameObject, this);
			} else {
				gameObject.changeHealth(-damage, gameObjectPerformer, this);
				gameObjectInTheWay.changeHealth(-damage, gameObjectPerformer, this);
			}

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
		if (targetSquare.restricted() == true && !targetSquare.owners.contains(teleportee)) {
			illegalReason = TRESPASSING;
			return false;
		}

		if (teleportee != gameObjectPerformer) {
			boolean legalToPerformOnTeleportee = standardAttackLegalityCheck(gameObjectPerformer, teleportee);
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
		ArrayList<GameObject> stampables = targetSquare.inventory.getGameObjectsOfClass(Stampable.class);
		if (!(teleportee instanceof Blind) && stampables.size() > 0) {
			for (GameObject stampable : stampables) {
				return new Sound(teleportee, stampable, targetSquare, 10, legal, this.getClass());
			}
		}
		return null;
	}
}
