package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.primary.AnimationSlash;
import com.marklynch.level.constructs.animation.secondary.AnimationTake;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Vein;
import com.marklynch.objects.tools.Pickaxe;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionMining extends Action {

	public static final String ACTION_NAME = "Mine";

	Actor performer;
	Vein target;
	Pickaxe pickaxe;

	// Default for hostiles
	public ActionMining(Actor attacker, Vein vein) {
		super(ACTION_NAME, textureMine);
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

		pickaxe = (Pickaxe) performer.inventory.getGameObjectOfClass(Pickaxe.class);
		if (performer.equipped != pickaxe)
			performer.equippedBeforePickingUpObject = performer.equipped;
		performer.equipped = pickaxe;

		performer.setPrimaryAnimation(new AnimationSlash(performer, target) {
			@Override
			public void runCompletionAlgorightm(boolean wait) {
				super.runCompletionAlgorightm(wait);
				postMeleeAnimation();
			}
		});
	}

	public void postMeleeAnimation() {

		if (Game.level.shouldLog(target, performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " mined ", target, " with ", pickaxe }));

		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		boolean destroyed = target.checkIfDestroyed(performer, this);

		GameObject ore = null;
		// Actor oreOwner = performer;
		// if (target.owner != null)
		// oreOwner = target.owner;

		if (Math.random() < target.dropChance) {

			if (!target.infinite) {
				float damage = target.totalHealth / Vein.totalOresForExhaustableVeins;
				if (target.inventory.gameObjects.size() > 0) {
					ore = target.inventory.get(0);
					performer.inventory.add(ore);
				}
				target.changeHealth(-damage, null, null);
			} else {
				ore = target.oreTemplate.makeCopy(target.squareGameObjectIsOn, target.owner);
				performer.inventory.add(ore);
			}

			if (ore != null) {

				if (Game.level.openInventories.size() > 0) {

				} else if (performer.squareGameObjectIsOn.onScreen()
						&& performer.squareGameObjectIsOn.visibleToPlayer) {
					performer.addSecondaryAnimation(new AnimationTake(ore, performer, 0, 0, 1f));

				}
				if (Game.level.shouldLog(target, performer))
					Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " received ", ore }));
			}
		} else {

			if (Game.level.shouldLog(target, performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " received no ore" }));
		}

		if (!target.infinite && target.checkIfDestroyed(performer, this)) {

			if (Game.level.shouldLog(target, performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " depleted ", target }));
		}

		// if (performer == Game.level.player) {
		// if (destroyed) {
		// target.setPrimaryAnimation(null);
		// if (performer.equippedBeforePickingUpObject != null) {
		// performer.equipped = performer.equippedBeforePickingUpObject;
		// performer.equippedBeforePickingUpObject = null;
		// }
		// } else {
		// Player.playerTargetSquare = performer.squareGameObjectIsOn;
		// Player.playerFirstMove = true;
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

			// if(ore !=)
			if (ore != null) {
				Crime crime = new Crime(this, this.performer, this.target.owner, Crime.TYPE.CRIME_THEFT, ore);
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

		if (!performer.inventory.contains(Pickaxe.class)) {
			disabledReason = NEED_A_PICKAXE;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkRange() {

		if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) > 1) {
			return false;
		}

		if (target.remainingHealth <= 0) {
			disabledReason = null;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		if (target.owner != null && target.owner != performer) {
			illegalReason = THEFT;
			return false;
		}
		return true;
	}

	@Override
	public Sound createSound() {
		Pickaxe pickaxe = (Pickaxe) performer.inventory.getGameObjectOfClass(Pickaxe.class);
		if (pickaxe != null) {
			float loudness = Math.max(target.soundWhenHit, pickaxe.soundWhenHitting);
			return new Sound(performer, pickaxe, target.squareGameObjectIsOn, loudness, legal, this.getClass());
		}
		return null;
	}

	@Override
	public boolean shouldContinue() {
		if (target.remainingHealth <= 0) {
			disabledReason = null;
			return false;
		}

		return true;
	}

}
