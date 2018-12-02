package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.constructs.animation.primary.AnimationShake;
import com.marklynch.level.constructs.animation.primary.AnimationSlash;
import com.marklynch.level.constructs.animation.secondary.AnimationTake;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Player;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.Axe;
import com.marklynch.ui.ActivityLog;

public class ActionChopping extends Action {

	public static final String ACTION_NAME = "Chop";
	Axe axe;

	// Default for hostiles
	public ActionChopping(Actor attacker, GameObject target) {
		super(ACTION_NAME, textureChop, attacker, target);
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

		performer.setPrimaryAnimation(new AnimationSlash(performer, target, new OnCompletionListener() {
			@Override
			public void animationComplete(GameObject gameObject) {
				postMeleeAnimation();
			}
		}));
	}

	public void postMeleeAnimation() {

		float damage = target.totalHealth / 4f;
		target.changeHealth(-damage, performer, this);
		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		Actor treeOwner = performer;
		if (target.owner != null)
			treeOwner = target.owner;

		if (Game.level.shouldLog(target, performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " chopped at ", target, " with ", axe }));

		target.setPrimaryAnimation(new AnimationShake(target, null, false, 200));

		if (target.remainingHealth <= 0) {

			GameObject wood = Templates.WOOD.makeCopy(target.squareGameObjectIsOn, treeOwner);
			if (Game.level.openInventories.size() > 0) {
			} else if (performer.squareGameObjectIsOn.onScreen() && performer.squareGameObjectIsOn.visibleToPlayer) {
				Level.addSecondaryAnimation(new AnimationTake(wood, performer, 0, 0, 1f, null));
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
		if (sound != null)
			sound.play();

		if (!legal) {
			Crime crime = new Crime(this, this.performer, this.target.owner, Crime.TYPE.CRIME_VANDALISM, target);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}
		performer.equipped = performer.equippedBeforePickingUpObject;

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
