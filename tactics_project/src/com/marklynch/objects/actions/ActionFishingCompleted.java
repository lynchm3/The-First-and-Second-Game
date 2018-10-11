package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.primary.AnimationWalk;
import com.marklynch.level.constructs.animation.secondary.AnimationTake;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.tools.FishingRod;
import com.marklynch.objects.tools.Shovel;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionFishingCompleted extends Action {

	public static final String ACTION_NAME = "Fishing";

	Actor performer;
	GameObject target;

	// Default for hostiles
	public ActionFishingCompleted(Actor attacker, GameObject target) {
		super(ACTION_NAME, "action_fishing.png");
		super.gameObjectPerformer = this.performer = attacker;
		this.target = target;
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

		performer.fishingTarget = target;

		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		if (target.fitsInInventory) {

			if (Game.level.openInventories.size() == 0 && performer.squareGameObjectIsOn.onScreen()
					&& performer.squareGameObjectIsOn.visibleToPlayer) {
				performer.fishingAnimation = new AnimationTake(target, performer, 0, 0, 1f);
				performer.addSecondaryAnimation(performer.fishingAnimation);
			}
			performer.inventory.add(target);
		} else {
			Square oldSquare = target.squareGameObjectIsOn;
			performer.squareGameObjectIsOn.inventory.add(target);
			if (Game.level.openInventories.size() == 0 && performer.squareGameObjectIsOn.onScreen()
					&& performer.squareGameObjectIsOn.visibleToPlayer) {
				target.setPrimaryAnimation(new AnimationWalk(target, oldSquare, performer.squareGameObjectIsOn, 0));
			}
		}

		if (Game.level.shouldLog(target, performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " caught ", target }));

		FishingRod fishingRod = (FishingRod) performer.equipped;
		performer.fishingTarget.beingFishedBy = null;
		performer.fishingTarget = null;
		fishingRod.reset();

		if (performer == Game.level.player) {
			Level.pausePlayer();
			target.setPrimaryAnimation(null);
			if (performer.equippedBeforePickingUpObject != null) {
				performer.equipped = performer.equippedBeforePickingUpObject;
				performer.equippedBeforePickingUpObject = null;
			}
		} else {
		}

		if (!legal) {
			Crime crime = new Crime(this, this.performer, this.target.owner, Crime.TYPE.CRIME_THEFT, target);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}

		if (performer.faction == Game.level.factions.player) {
			Game.level.undoList.clear();
		}

		if (performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endPlayerTurn();
		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {

		if (!performer.inventory.contains(FishingRod.class)) {
			disabledReason = NEED_A_FISHING_ROD;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkRange() {
		return true;

		// ArrayList<GameObject> fishingRods =
		// performer.inventory.getGameObjectsOfClass(FishingRod.class);
		//
		// for (GameObject fishingRod : fishingRods) {
		// if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) <=
		// fishingRod.maxRange) {
		// return true;
		// }
		// }
		// actionName = ACTION_NAME_CANT_REACH;
		// return false;
	}

	@Override
	public boolean checkLegality() {
		return standardAttackLegalityCheck(performer, target);
	}

	@Override
	public Sound createSound() {
		Shovel shovel = (Shovel) performer.inventory.getGameObjectOfClass(Shovel.class);
		if (shovel != null) {
			float loudness = Math.max(target.soundWhenHit, shovel.soundWhenHitting);
			return new Sound(performer, shovel, target.squareGameObjectIsOn, loudness, legal, this.getClass());
		}
		return null;
	}

}