package com.marklynch.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.tools.FishingRod;
import com.marklynch.objects.tools.Shovel;
import com.marklynch.ui.ActivityLog;

public class ActionFishingStart extends Action {

	public static final String ACTION_NAME = "Fishing";

	// Default for hostiles
	public ActionFishingStart(Actor attacker, GameObject target) {
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

		performer.fishingTarget = target;
		performer.fishingProgress = 0;
		target.beingFishedBy = performer;

		FishingRod fishingRod = null;
		ArrayList<GameObject> fishingRods = performer.inventory.getGameObjectsOfClass(FishingRod.class);
		for (GameObject f : fishingRods) {
			if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) <= ((FishingRod) f).fishingRange) {
				fishingRod = (FishingRod) f;
			}
		}
		fishingRod.reset();

		if (performer.equipped != fishingRod)
			performer.equippedBeforePickingUpObject = performer.equipped;
		performer.equipped = fishingRod;

		if (Game.level.shouldLog(target, performer)) {
			Game.level.logOnScreen(
					new ActivityLog(new Object[] { performer, " went fishing for ", target, " with ", fishingRod }));
		}

		// if (performer == Game.level.player) {
		// Level.levelMode = LevelMode.LEVEL_MODE_FISHING;
		// Player.playerTargetAction = new ActionFishingInProgress(performer, target);
		// Player.playerTargetSquare = performer.squareGameObjectIsOn;
		// Player.playerFirstMove = true;
		// } else {
		// // if (Math.random() < 2) {
		// // if (Game.level.shouldLog(target, performer))
		// // Game.level.logOnScreen(new ActivityLog(new Object[] { performer,
		// // " went fishing for ", target,
		// // " with ", fishingRod, " but failed!" }));
		// // }
		// }

		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		if (!legal) {
			Crime crime = new Crime(this.performer, this.target.owner, Crime.TYPE.CRIME_THEFT, target);
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
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {

		if (!performer.inventory.contains(FishingRod.class)) {
			disabledReason = NEED_A_FISHING_ROD;
			return false;
		}

		if (target.beingFishedBy != null) {
			disabledReason = ALREADY_BEING_FISHED;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkRange() {

		ArrayList<GameObject> fishingRods = performer.inventory.getGameObjectsOfClass(FishingRod.class);

		for (GameObject fishingRod : fishingRods) {
			if (performer
					.straightLineDistanceTo(target.squareGameObjectIsOn) <= ((FishingRod) fishingRod).fishingRange) {
				return true;
			}
		}
		return false;
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