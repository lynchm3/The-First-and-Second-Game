package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.Level.LevelMode;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.tools.FishingRod;
import com.marklynch.objects.tools.Shovel;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Player;
import com.marklynch.ui.ActivityLog;

public class ActionFishingStart extends Action {

	public static final String ACTION_NAME = "Fishing";
	public static final String ACTION_NAME_CANT_REACH = ACTION_NAME + " (can't reach)";
	public static final String ACTION_NAME_NEED_FISHING_ROD = ACTION_NAME + " (need fishing rod)";

	Actor performer;
	GameObject target;

	// Default for hostiles
	public ActionFishingStart(Actor attacker, GameObject target) {
		super(ACTION_NAME, "action_fishing.png");
		this.performer = attacker;
		this.target = target;
		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

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
			if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) <= f.maxRange) {
				fishingRod = (FishingRod) f;
			}
		}
		fishingRod.reset();

		if (performer.equipped != fishingRod)
			performer.equippedBeforePickingUpObject = performer.equipped;
		performer.equipped = fishingRod;

		if (performer == Game.level.player) {
			Level.levelMode = LevelMode.LEVEL_MODE_FISHING;
			if (Game.level.shouldLog(target, performer)) {
				Game.level.logOnScreen(new ActivityLog(
						new Object[] { performer, " went fishing for ", target, " with ", fishingRod }));
				// target.primaryAnimation = new AnimationShake();
			}
			Player.playerTargetAction = new ActionFishingInProgress(performer, target);
			Player.playerTargetSquare = performer.squareGameObjectIsOn;
			Player.playerFirstMove = true;
		} else {
			if (Math.random() < 2) {
				if (Game.level.shouldLog(target, performer))
					Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " went fishing for ", target,
							" with ", fishingRod, " but failed!" }));
			}
		}

		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

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
			actionName = ACTION_NAME_NEED_FISHING_ROD;
			disabledReason = "You need a fishing rod";
			return false;
		}

		return true;
	}

	@Override
	public boolean checkRange() {

		ArrayList<GameObject> fishingRods = performer.inventory.getGameObjectsOfClass(FishingRod.class);

		for (GameObject fishingRod : fishingRods) {
			if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) <= fishingRod.maxRange) {
				return true;
			}
		}
		actionName = ACTION_NAME_CANT_REACH;
		return false;
	}

	@Override
	public boolean checkLegality() {
		if (target.owner != null && target.owner != performer)
			return false;
		return true;
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