package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.constructs.animation.primary.AnimationEmpty;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Liquid;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.Jar;
import com.marklynch.ui.ActivityLog;

public class ActionEmptyItem extends Action {

	public static final String ACTION_NAME = "Empty";
	Jar jar;
	private GameObject previouslyEquipped;

	// Default for hostiles
	public ActionEmptyItem(GameObject performer, Object target, Jar container) {
		super(ACTION_NAME, textureEmpty, performer, target);

		this.jar = container;
		if (!check()) {
			enabled = false;
		} else {
			actionName = ACTION_NAME + " " + container.name;
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

		previouslyEquipped = performer.equipped;
		performer.equipped = jar;

//		if (targetGameObject != gameObjectPerformer) {
		gameObjectPerformer.setPrimaryAnimation(new AnimationEmpty(gameObjectPerformer, targetSquare,
				gameObjectPerformer.getPrimaryAnimation(), new OnCompletionListener() {
					@Override
					public void animationComplete(GameObject gameObject) {
						postAnimation();
					}
				}));
//		}
	}

	public void postAnimation() {

		// Logging
		if (Game.level.shouldLog(targetGameObject, performer)) {
			if (targetGameObject != null) {
				Game.level.logOnScreen(
						new ActivityLog(new Object[] { performer, " emptied ", jar, " on ", targetGameObject }));
			} else {
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " emptied out ", jar }));

			}
		}

		// Add effect from liquid to all objects on the sqr
		if (jar.contents instanceof Liquid) {
			for (GameObject gameObject : this.targetSquare.inventory.getGameObjects()) {
				for (Effect effect : jar.contents.touchEffects) {
					gameObject.addEffect(effect.makeCopy(performer, gameObject));
				}
			}
		}

		// Spread liquid or just drop object on ground if not liquid
		if (jar.contents instanceof Liquid) {
			targetSquare.liquidSpread((Liquid) jar.contents);
		} else {
			targetSquare.inventory.add(jar.contents);
		}

		// Put stuff in actor's hand
		performer.equip(previouslyEquipped);
		GameObject newJar = Templates.JAR.makeCopy(null, jar.owner);
		performer.inventory.add(newJar);
		if (performer.equipped == jar) {
			if (performer.inventory.contains(performer.equippedBeforePickingUpObject)) {
				performer.equip(performer.equippedBeforePickingUpObject);
			} else if (performer.inventory.containsDuplicateOf(jar)) {
				performer.equip(performer.inventory.getDuplicateOf(jar));
			} else {
				performer.equip(newJar);
			}
			performer.equippedBeforePickingUpObject = null;
		}

		performer.inventory.remove(jar);

		if (Game.level.openInventories.size() > 0)
			Game.level.openInventories.get(0).close();

		performer.distanceMovedThisTurn = performer.travelDistance;

		if (performer == Game.level.player)
			Game.level.endPlayerTurn();

		if (sound != null)
			sound.play();

		if (!legal) {

			Actor victim = null;

			if (targetGameObject instanceof Actor)
				victim = (Actor) targetGameObject;
			else if (targetGameObject != null)
				victim = targetGameObject.owner;
			if (victim != null) {
				Crime crime = new Crime(this.performer, victim, Crime.TYPE.CRIME_DOUSE);
				this.performer.crimesPerformedThisTurn.add(crime);
				this.performer.crimesPerformedInLifetime.add(crime);
				notifyWitnessesOfCrime(crime);
			}
		} else {
			trespassingCheck(performer, performer.squareGameObjectIsOn);
		}
	}

	@Override
	public boolean check() {

		if (targetSquare == null && targetGameObject == null) {
			return false;
		}

		if (jar.contents == null) {
			disabledReason = CONTAINER_IS_EMPTY;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkRange() {

		if (performer.straightLineDistanceTo(targetSquare) > 1) {
			return false;
		}

		if (!performer.canSeeSquare(targetSquare)) {
			return false;
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		return standardAttackLegalityCheck(performer, targetGameObject);
	}

	@Override
	public Sound createSound() {

		// Sound
		float loudness = 3;
		return new Sound(performer, jar, targetSquare, loudness, legal, this.getClass());
	}

}
