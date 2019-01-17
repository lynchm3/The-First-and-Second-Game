package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.constructs.animation.primary.AnimationIgnite;
import com.marklynch.level.constructs.effect.EffectBurning;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.constructs.power.PowerIgnite;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Matches;
import com.marklynch.objects.tools.FlammableLightSource;
import com.marklynch.ui.ActivityLog;

public class ActionIgnite extends Action {

	public static final String ACTION_NAME = "Ignite";

	Object igniteMethod;
	GameObject source;

	// Default for hostiles
	public ActionIgnite(Actor performer, GameObject target, GameObject source) {
		super(ACTION_NAME, textureBurn, performer, target);
		super.gameObjectPerformer = this.performer = performer;
		this.targetSquare = target.squareGameObjectIsOn;
		this.source = source;
		if (performer.equipped == target) {
			this.targetSquare = performer.squareGameObjectIsOn;
			this.actionName = ACTION_NAME + " " + target.name;
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

		if (performer.squareGameObjectIsOn.xInGrid > targetSquare.xInGrid) {
			performer.backwards = true;
		} else if (performer.squareGameObjectIsOn.xInGrid < targetSquare.xInGrid) {
			performer.backwards = false;
		}

		igniteMethod = getIgnitionMethod();

		GameObject igniteMethodGameObject = null;
		if (source != null) {
			igniteMethodGameObject = target;
		} else if (igniteMethod instanceof Power) {
			igniteMethod = ((Power) igniteMethod).name;
		} else if (igniteMethod instanceof GameObject) {
			igniteMethodGameObject = (GameObject) igniteMethod;
		}

		// Melee weapons
		performer.setPrimaryAnimation(
				new AnimationIgnite(performer, target, igniteMethodGameObject, new OnCompletionListener() {
					@Override
					public void animationComplete(GameObject gameObject) {
						postAnimation();
					}
				}));
	}

	public void postAnimation() {

		if (Game.level.shouldLog(target, performer)) {

			if (target != null) {
				Game.level.logOnScreen(
						new ActivityLog(new Object[] { performer, " ignited ", target, " with ", igniteMethod }));
			} else {
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " ignited with ", igniteMethod }));

			}
		}

//		for (GameObject gameObject : this.targetSquare.inventory.getGameObjects()) {
		target.addEffect(new EffectBurning(performer, target, 3));
//		}

		if (Game.level.openInventories.size() > 0)
			Game.level.openInventories.get(0).close();

		performer.distanceMovedThisTurn = performer.travelDistance;

		if (performer.faction == Game.level.factions.player) {
			Game.level.undoList.clear();
		}

		if (performer == Game.level.player)
			Game.level.endPlayerTurn();

		if (sound != null)
			sound.play();

		if (!legal) {

			Actor victim = null;

			if (target instanceof Actor)
				victim = (Actor) target;
			else if (target != null)
				victim = target.owner;
			if (victim != null) {
				Crime crime = new Crime(this.performer, victim, Crime.TYPE.CRIME_ARSON);
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

		if (targetSquare == null && target == null)
			return false;

		Object ignitionMethod = getIgnitionMethod();

		if (ignitionMethod != null)
			return true;

		disabledReason = NEED_MATCHES_OR_IGNITE_POWER;

		return false;
	}

	public Object getIgnitionMethod() {

		if (source != null) {
			return source;
		}

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
		boolean illegal = standardAttackLegalityCheck(performer, target);
		if (illegalReason == VANDALISM)
			illegalReason = ARSON;
		return illegal;
	}

	@Override
	public Sound createSound() {
		// Sound
		float loudness = 3;
		return new Sound(performer, target, performer.squareGameObjectIsOn, loudness, legal, this.getClass());
	}

}
