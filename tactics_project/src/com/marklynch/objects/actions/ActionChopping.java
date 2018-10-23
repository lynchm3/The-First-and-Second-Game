package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.primary.AnimationShake;
import com.marklynch.level.constructs.animation.primary.AnimationSlash;
import com.marklynch.level.constructs.animation.secondary.AnimationTake;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Junk;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.Axe;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Player;
import com.marklynch.ui.ActivityLog;

public class ActionChopping extends Action {

	public static final String ACTION_NAME = "Chop";

	Actor performer;
	GameObject target;
	Axe axe;

	// Default for hostiles
	public ActionChopping(Actor attacker, GameObject vein) {
		super(ACTION_NAME, textureChop);
		super.gameObjectPerformer = this.performer = attacker;
		this.target = vein;
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

		if (performer.squareGameObjectIsOn.xInGrid > target.squareGameObjectIsOn.xInGrid) {
			performer.backwards = true;
		} else if (performer.squareGameObjectIsOn.xInGrid < target.squareGameObjectIsOn.xInGrid) {
			performer.backwards = false;
		}

		axe = (Axe) performer.inventory.getGameObjectOfClass(Axe.class);
		if (performer.equipped != axe)
			performer.equippedBeforePickingUpObject = performer.equipped;
		performer.equipped = axe;

		performer.setPrimaryAnimation(new AnimationSlash(performer, target) {
			@Override
			public void runCompletionAlgorightm(boolean wait) {
				super.runCompletionAlgorightm(wait);
				postMeleeAnimation();
			}
		});
	}

	public void postMeleeAnimation() {

		float damage = target.totalHealth / 4f;
		target.changeHealth(-damage, null, null);
		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		Actor oreOwner = performer;
		if (target.owner != null)
			oreOwner = target.owner;

		if (Game.level.shouldLog(target, performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " chopped at ", target, " with ", axe }));

		if (Game.level.shouldLog(target, performer)) {
			target.setPrimaryAnimation(new AnimationShake(target));
		}

		boolean destroyed = target.checkIfDestroyed(performer, this);

		Junk wood = null;
		if (destroyed) {
			wood = Templates.WOOD.makeCopy(target.squareGameObjectIsOn, oreOwner);
			if (Game.level.openInventories.size() > 0) {
			} else if (performer.squareGameObjectIsOn.onScreen() && performer.squareGameObjectIsOn.visibleToPlayer) {
				performer.addSecondaryAnimation(new AnimationTake(wood, performer, 0, 0, 1f));
			}
			performer.inventory.add(wood);
			if (Game.level.shouldLog(target, performer)) {
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " received ", wood }));
			}

		} else {

		}

		// if (performer == Game.level.player) {
		// if (destroyed) {
		// target.setPrimaryAnimation(null);
		// if (performer.equippedBeforePickingUpObject != null) {
		// performer.equipped = performer.equippedBeforePickingUpObject;
		// performer.equippedBeforePickingUpObject = null;
		// }
		// } else {
		//// Player.playerTargetSquare = performer.squareGameObjectIsOn;
		//// Player.playerFirstMove = true;
		//
		// }
		// } else {
		//
		// }

		target.showPow();

		if (performer.faction == Game.level.factions.player) {
			Game.level.undoList.clear();
		}

		if (performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endPlayerTurn();
		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (!legal) {
			Crime crime = new Crime(this, this.performer, this.target.owner, Crime.TYPE.CRIME_VANDALISM, wood);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}

	}

	@Override
	public boolean check() {

		if (!performer.inventory.contains(Axe.class)) {
			disabledReason = NEED_AN_AXE;
			return false;
		}

		if (target.remainingHealth <= 0) {
			disabledReason = null;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkRange() {

		if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) > 1) {
			return false;
		}

		if (!performer.canSeeGameObject(target))
			return false;

		return true;
	}

	@Override
	public boolean checkLegality() {
		return standardAttackLegalityCheck(performer, target);
	}

	@Override
	public Sound createSound() {
		Axe axe = (Axe) performer.inventory.getGameObjectOfClass(Axe.class);
		if (axe != null) {
			float loudness = Math.max(target.soundWhenHit, axe.soundWhenHitting);
			return new Sound(performer, axe, target.squareGameObjectIsOn, loudness, legal, this.getClass());
		}
		return null;
	}

	@Override
	public boolean shouldContinue() {

		if (performed && Player.inFight()) {
			return false;
		}
		if (target.remainingHealth <= 0) {
			disabledReason = null;
			return false;
		}

		return true;
	}

}
