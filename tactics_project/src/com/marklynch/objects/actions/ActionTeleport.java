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
	boolean endTurn;
	GameObject gameObjectInTheWay;
	Square squareToTeleportTo;

	public ActionTeleport(GameObject performer, GameObject target, Square squareToTeleportTo, boolean endTurn) {
		super(ACTION_NAME, textureTeleport, performer, target);
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

		if (target != gameObjectPerformer) {
			gameObjectPerformer.setPrimaryAnimation(new AnimationPush(gameObjectPerformer, squareToTeleportTo,
					gameObjectPerformer.getPrimaryAnimation(), null));
		}

		Square startSquare = target.squareGameObjectIsOn;

		target.setPrimaryAnimation(
				new AnimationTeleport(target, startSquare, squareToTeleportTo, new OnCompletionListener() {
					@Override
					public void animationComplete(GameObject gameObject) {
						postAnimation();
					}
				}));

	}

	public void postAnimation() {

		if (performer == Game.level.player)
			Game.level.levelMode = LevelMode.LEVEL_MODE_NORMAL;

		if (Game.level.shouldLog(gameObjectPerformer, target)) {
			if (gameObjectPerformer == target)
				Game.level.logOnScreen(
						new ActivityLog(new Object[] { gameObjectPerformer, " teleported to ", squareToTeleportTo }));
			else
				Game.level.logOnScreen(new ActivityLog(
						new Object[] { gameObjectPerformer, " teleported ", target, " to ", squareToTeleportTo }));
		}

		// Teleported big object on to big object... someone has to die.
		if (gameObjectInTheWay != null) {
			float damage = Math.min(target.remainingHealth, gameObjectInTheWay.remainingHealth);
			if (Game.level.shouldLog(target, gameObjectInTheWay))
				Game.level.logOnScreen(new ActivityLog(new Object[] { target, " teleported in to ", gameObjectInTheWay,
						", both took " + damage + " damage" }));

			target.changeHealth(-damage, gameObjectPerformer, this);
			gameObjectInTheWay.changeHealth(-damage, gameObjectPerformer, this);

			if (gameObjectPerformer == target) {
				target.changeHealth(-damage, gameObjectInTheWay, this);
				gameObjectInTheWay.changeHealth(-damage, target, this);
			} else {
				target.changeHealth(-damage, gameObjectPerformer, this);
				gameObjectInTheWay.changeHealth(-damage, gameObjectPerformer, this);
			}

		}

		gameObjectPerformer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (performer != null && !legal) {

			// GameObject gameObjectInTheWay = null;
			if (gameObjectInTheWay != null) {
				if (target != Game.level.player && target.owner != null && target.owner != Game.level.player) {
					Actor victim;
					Crime.TYPE severity;
					if (target instanceof Actor) {
						victim = (Actor) target;
						severity = Crime.TYPE.CRIME_ASSAULT;

					} else {
						victim = target.owner;
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
		} else if (performer != null) {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}

		if (endTurn && gameObjectPerformer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endPlayerTurn();

		if (performer != null)
			trespassingCheck(this, performer, target.squareGameObjectIsOn);

		Level.teleportee = null;
	}

	private void teleport() {
	}

	@Override
	public boolean check() {

		if (!target.moveable || target.isFloorObject)
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
		if (squareToTeleportTo.restricted() == true && !squareToTeleportTo.owners.contains(target)) {
			illegalReason = TRESPASSING;
			return false;
		}

		if (target != gameObjectPerformer) {
			boolean legalToPerformOnTeleportee = standardAttackLegalityCheck(gameObjectPerformer, target);
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
		ArrayList<GameObject> stampables = squareToTeleportTo.inventory.getGameObjectsOfClass(Stampable.class);
		if (!(target instanceof Blind) && stampables.size() > 0) {
			for (GameObject stampable : stampables) {
				return new Sound(target, stampable, squareToTeleportTo, 10, legal, this.getClass());
			}
		}
		return null;
	}
}
