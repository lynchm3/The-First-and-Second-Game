package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.constructs.animation.primary.AnimationIgnite;
import com.marklynch.level.constructs.effect.EffectBurning;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.constructs.power.PowerIgnite;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Matches;
import com.marklynch.objects.tools.FlammableLightSource;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionIgnite extends Action {

	public static final String ACTION_NAME = "Ignite";

	Actor performer;
	Square targetSquare;
	GameObject targetGameObject;
	Object igniteMethod;

	// Default for hostiles
	public ActionIgnite(Actor performer, Object target) {
		super(ACTION_NAME, textureBurn, performer, target, targetSquare);
		super.gameObjectPerformer = this.performer = performer;
		if (target instanceof Square) {
			targetSquare = (Square) target;
			targetGameObject = targetSquare.inventory.gameObjectThatCantShareSquare;
		} else if (target instanceof GameObject) {
			targetGameObject = (GameObject) target;
			targetSquare = targetGameObject.squareGameObjectIsOn;
		}
		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {
		super.perform();

		if (!enabled)
			return;

		if (!checkRange())
			return;

		if (performer.squareGameObjectIsOn.xInGrid > targetGameObject.squareGameObjectIsOn.xInGrid) {
			performer.backwards = true;
		} else if (performer.squareGameObjectIsOn.xInGrid < targetGameObject.squareGameObjectIsOn.xInGrid) {
			performer.backwards = false;
		}

		igniteMethod = getIgnitionMethod();

		if (igniteMethod instanceof Power) {
			igniteMethod = ((Power) igniteMethod).name;
		}

		GameObject igniteMethodGameObject = null;
		if (igniteMethod instanceof GameObject) {
			igniteMethodGameObject = (GameObject) igniteMethod;
		}

		// Melee weapons
		performer.setPrimaryAnimation(
				new AnimationIgnite(performer, targetGameObject, igniteMethodGameObject, new OnCompletionListener() {
					@Override
					public void animationComplete(GameObject gameObject) {
						postAnimation();
					}
				}));
	}

	public void postAnimation() {

		if (Game.level.shouldLog(targetGameObject, performer)) {

			if (targetGameObject != null) {
				Game.level.logOnScreen(new ActivityLog(
						new Object[] { performer, " ignited ", targetGameObject, " with ", igniteMethod }));
			} else {
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " ignited with ", igniteMethod }));

			}
		}

		for (GameObject gameObject : this.targetSquare.inventory.getGameObjects()) {
			gameObject.addEffect(new EffectBurning(performer, gameObject, 3));
		}

		if (Game.level.openInventories.size() > 0)
			Game.level.openInventories.get(0).close();

		performer.distanceMovedThisTurn = performer.travelDistance;

		if (performer.faction == Game.level.factions.player) {
			Game.level.undoList.clear();
		}

		if (performer == Game.level.player)
			Game.level.endPlayerTurn();

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (!legal) {

			Actor victim = null;

			if (targetGameObject instanceof Actor)
				victim = (Actor) targetGameObject;
			else if (targetGameObject != null)
				victim = targetGameObject.owner;
			if (victim != null) {
				Crime crime = new Crime(this, this.performer, victim, Crime.TYPE.CRIME_ARSON);
				this.performer.crimesPerformedThisTurn.add(crime);
				this.performer.crimesPerformedInLifetime.add(crime);
				notifyWitnessesOfCrime(crime);
			}
		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}

	}

	@Override
	public boolean check() {

		if (targetSquare == null && targetGameObject == null)
			return false;

		Object ignitionMethod = getIgnitionMethod();

		if (ignitionMethod != null)
			return true;

		disabledReason = NEED_MATCHES_OR_IGNITE_POWER;

		return false;
	}

	public Object getIgnitionMethod() {
		if (performer.hasPower(PowerIgnite.class)) {
			return performer.getPower(PowerIgnite.class);
		}

		if (performer.inventory.contains(Matches.class)) {
			return performer.inventory.getGameObjectOfClass(Matches.class);
		}

		for (GameObject flammableLightSource : performer.inventory.getGameObjectsOfClass(FlammableLightSource.class)) {
			if (((FlammableLightSource) flammableLightSource).lit)
				return flammableLightSource;
		}

		return null;
	}

	@Override
	public boolean checkRange() {

		if (!performer.canSeeSquare(targetSquare)) {
			return false;
		}

		// Use magic?
		Power powerIgnite = performer.getPower(PowerIgnite.class);
		if (powerIgnite != null) {
			if (performer.straightLineDistanceTo(targetSquare) <= powerIgnite.range)
				return true;
		}

		// No magic, just matches
		if (performer.straightLineDistanceTo(targetSquare) > 1) {
			return false;
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		boolean illegal = standardAttackLegalityCheck(performer, targetGameObject);
		if (illegalReason == VANDALISM)
			illegalReason = ARSON;
		return illegal;
	}

	@Override
	public Sound createSound() {

		// Sound
		float loudness = 3;
		return new Sound(performer, performer, targetSquare, loudness, legal, this.getClass());
	}

}
