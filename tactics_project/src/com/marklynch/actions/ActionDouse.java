package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.constructs.animation.primary.AnimationIgnite;
import com.marklynch.level.constructs.effect.EffectWet;
import com.marklynch.level.constructs.power.Power;
import com.marklynch.level.constructs.power.PowerDouse;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.templates.Templates;
import com.marklynch.ui.ActivityLog;

public class ActionDouse extends Action {

	public static final String ACTION_NAME = "Douse";

	Object douseMethod;

	// Default for hostiles
	public ActionDouse(Actor performer, GameObject target) {
		super(ACTION_NAME, textureDouse, performer, target);
		super.gameObjectPerformer = this.performer = performer;
		this.targetSquare = target.squareGameObjectIsOn;
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

		douseMethod = getDouseMethod();

		if (douseMethod instanceof Power) {
			douseMethod = ((Power) douseMethod).name;
		}

		GameObject douseMethodGameObject = null;
		if (douseMethod instanceof GameObject) {
			douseMethodGameObject = (GameObject) douseMethod;
		}

		// Melee weapons
		performer.setPrimaryAnimation(
				new AnimationIgnite(performer, target, douseMethodGameObject, new OnCompletionListener() {
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
						new ActivityLog(new Object[] { performer, " doused ", target, " with ", douseMethod }));
			} else {
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " doused with ", douseMethod }));

			}
		}

//		for (GameObject gameObject : this.targetSquare.inventory.getGameObjects()) {
		target.addEffect(new EffectWet(performer, target, 3));
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
				Crime crime = new Crime(this.performer, victim, Crime.TYPE.CRIME_DOUSE);
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

		Object ignitionMethod = getDouseMethod();

		if (ignitionMethod != null)
			return true;

		disabledReason = NEED_WATER_OR_DOUSE_POWER;

		return false;
	}

	public Object getDouseMethod() {
		if (performer.hasPower(PowerDouse.class)) {
			return performer.getPower(PowerDouse.class);
		}

		if (performer.inventory.containsGameObjectWithTemplateId(Templates.JAR_OF_WATER.templateId)) {
			return performer.inventory.getGameObjectWithTemplateId(Templates.JAR_OF_WATER.templateId);
		}

//		for (GameObject flammableLightSource : performer.inventory.getGameObjectsOfClass(FlammableLightSource.class)) {
//			if (((FlammableLightSource) flammableLightSource).lit)
//				return flammableLightSource;
//		}

		return null;
	}

	@Override
	public boolean checkRange() {

		if (!performer.canSeeSquare(targetSquare)) {
			return false;
		}

		// Use magic?
		Power powerDouse = performer.getPower(PowerDouse.class);
		if (powerDouse != null) {
			if (performer.straightLineDistanceTo(targetSquare) <= powerDouse.range)
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

		float loudness = 3;
		return new Sound(performer, target, performer.squareGameObjectIsOn, loudness, legal, this.getClass());
	}

}
