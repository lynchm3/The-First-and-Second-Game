package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.tools.FishingRod;
import com.marklynch.objects.tools.Shovel;
import com.marklynch.ui.ActivityLog;

public class ActionFishingFailed extends Action {

	public static final String ACTION_NAME = "Fishing";

	// Default for hostiles
	public ActionFishingFailed(Actor attacker, GameObject target) {
		super(ACTION_NAME, textureFishing, attacker, target);
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

		performer.fishingTarget = targetGameObject;

		if (performer == Game.level.player) {
			Level.pausePlayer();
			targetGameObject.setPrimaryAnimation(null);
		} else {
		}

		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		if (Game.level.shouldLog(targetGameObject, performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " failed to catch ", targetGameObject }));

		FishingRod fishingRod = (FishingRod) performer.equipped;
		performer.fishingTarget.beingFishedBy = null;
		performer.fishingTarget = null;
		fishingRod.reset();

		if (performer == Game.level.player) {
			Level.pausePlayer();
			targetGameObject.setPrimaryAnimation(null);
			if (performer.equippedBeforePickingUpObject != null) {
				performer.equipped = performer.equippedBeforePickingUpObject;
				performer.equippedBeforePickingUpObject = null;
			}
		} else {
		}

		if (!legal) {
			Crime crime = new Crime(this.performer, this.targetGameObject.owner, Crime.TYPE.CRIME_THEFT, targetGameObject);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		} else {
			trespassingCheck(performer, performer.squareGameObjectIsOn);
		}

		if (performer.faction == Game.level.factions.player) {
			Game.level.undoList.clear();
		}

		if (performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endPlayerTurn();
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {

		if (!performer.inventory.containsGameObjectOfType(FishingRod.class)) {
			disabledReason = NEED_A_FISHING_ROD;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkRange() {
		return true;

		// CopyOnWriteArrayList<GameObject> fishingRods =
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
		return standardAttackLegalityCheck(performer, targetGameObject);
	}

	@Override
	public Sound createSound() {
		Shovel shovel = (Shovel) performer.inventory.getGameObjectOfClass(Shovel.class);
		if (shovel != null) {
			float loudness = Math.max(targetGameObject.soundWhenHit, shovel.soundWhenHitting);
			return new Sound(performer, shovel, targetGameObject.squareGameObjectIsOn, loudness, legal, this.getClass());
		}
		return null;
	}

}