package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.constructs.animation.primary.AnimationShake;
import com.marklynch.level.constructs.animation.primary.AnimationSlash;
import com.marklynch.level.constructs.animation.secondary.AnimationTake;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Player;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Vein;
import com.marklynch.objects.tools.Pickaxe;
import com.marklynch.ui.ActivityLog;
import com.marklynch.ui.popups.Toast;

public class ActionMining extends Action {

	public static final String ACTION_NAME = "Mine";

	Vein vein;
	Pickaxe pickaxe;

	// Default for hostiles
	public ActionMining(Actor attacker, Vein target) {
		super(ACTION_NAME, textureMine, attacker, target);
		// super.gameObjectPerformer = this.performer = attacker;
		this.vein = target;
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

		if (performer.squareGameObjectIsOn.xInGrid > vein.squareGameObjectIsOn.xInGrid) {
			performer.backwards = true;
		} else if (performer.squareGameObjectIsOn.xInGrid < vein.squareGameObjectIsOn.xInGrid) {
			performer.backwards = false;
		}

		pickaxe = (Pickaxe) performer.inventory.getGameObjectOfClass(Pickaxe.class);
		if (performer.equipped != pickaxe)
			performer.equippedBeforePickingUpObject = performer.equipped;
		performer.equipped = pickaxe;

		performer.setPrimaryAnimation(new AnimationSlash(performer, vein, new OnCompletionListener() {
			@Override
			public void animationComplete(GameObject gameObject) {
				postMeleeAnimation();
			}
		}));
	}

	public void postMeleeAnimation() {

		if (Game.level.shouldLog(vein, performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " mined ", vein, " with ", pickaxe }));

		vein.setPrimaryAnimation(new AnimationShake(vein, null, false, 200));

		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		boolean destroyed = vein.checkIfDestroyed(performer, this);

		GameObject ore = null;
		// Actor oreOwner = performer;
		// if (vein.owner != null)
		// oreOwner = vein.owner;

		if (Math.random() < vein.dropChance) {

			if (!vein.infinite) {
				float damage = vein.totalHealth / Vein.totalOresForExhaustableVeins;
				if (vein.inventory.gameObjects.size() > 0) {
					ore = vein.inventory.get(0);
					performer.inventory.add(ore);
					if (performer == Level.player)
						Level.addToast(new Toast(new Object[] { this.image, " ", targetGameObject }));
				}
				vein.changeHealth(-damage, performer, this);
			} else {
				ore = vein.oreTemplate.makeCopy(vein.squareGameObjectIsOn, vein.owner);
				performer.inventory.add(ore);
				if (performer == Level.player)
					Level.addToast(new Toast(new Object[] { this.image, " ", ore }));
			}

			if (ore != null) {

				if (Game.level.openInventories.size() > 0) {

				} else if (performer.squareGameObjectIsOn.onScreen()
						&& performer.squareGameObjectIsOn.visibleToPlayer) {
					Level.addSecondaryAnimation(new AnimationTake(ore, performer, 0, 0, 1f, null));

				}
				if (Game.level.shouldLog(vein, performer))
					Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " received ", ore }));
			}
		} else {

			if (Game.level.shouldLog(vein, performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " received no ore" }));
		}

		if (!vein.infinite && vein.checkIfDestroyed(performer, this)) {

			if (Game.level.shouldLog(vein, performer))
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " depleted ", vein }));
		}

		// if (performer == Game.level.player) {
		// if (destroyed) {
		// vein.setPrimaryAnimation(null);
		// if (performer.equippedBeforePickingUpObject != null) {
		// performer.equipped = performer.equippedBeforePickingUpObject;
		// performer.equippedBeforePickingUpObject = null;
		// }
		// } else {
		// Player.playerveinSquare = performer.squareGameObjectIsOn;
		// Player.playerFirstMove = true;
		// }
		// } else {
		//
		// }

		vein.showPow();

		if (performer.faction == Game.level.factions.player) {
			Game.level.undoList.clear();
		}

		if (performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endPlayerTurn();
		if (sound != null)
			sound.play();

		if (!legal) {

			// if(ore !=)
			if (ore != null) {
				Crime crime = new Crime(this.performer, this.vein.owner, Crime.TYPE.CRIME_ILLEGAL_MINING, ore);
				this.performer.crimesPerformedThisTurn.add(crime);
				this.performer.crimesPerformedInLifetime.add(crime);
				notifyWitnessesOfCrime(crime);
			}
		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}

		if (performer == Level.player) {
			if (!(Player.playerTargetAction instanceof ActionMining) || !shouldContinue())
				performer.equipped = performer.equippedBeforePickingUpObject;
		}

	}

	@Override
	public boolean check() {

		if (!performer.inventory.containsGameObjectOfType(Pickaxe.class)) {
			disabledReason = NEED_A_PICKAXE;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkRange() {

		if (performer.straightLineDistanceTo(vein.squareGameObjectIsOn) > 1) {
			return false;
		}

		if (vein.remainingHealth <= 0) {
			disabledReason = null;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		if (vein.owner != null && vein.owner != performer) {
			illegalReason = THEFT;
			return false;
		}
		return true;
	}

	@Override
	public Sound createSound() {
		Pickaxe pickaxe = (Pickaxe) performer.inventory.getGameObjectOfClass(Pickaxe.class);
		if (pickaxe != null) {
			float loudness = Math.max(vein.soundWhenHit, pickaxe.soundWhenHitting);
			return new Sound(performer, pickaxe, vein.squareGameObjectIsOn, loudness, legal, this.getClass());
		}
		return null;
	}

	@Override
	public boolean shouldContinue() {

		if (performed && Player.inFight()) {
			return false;
		}
		if (vein.remainingHealth <= 0) {
			disabledReason = null;
			return false;
		}

		return true;
	}

}
