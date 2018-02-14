package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.AnimationTake;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.WaterSource;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.FishingRod;
import com.marklynch.objects.tools.Shovel;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionFishing extends Action {

	public static final String ACTION_NAME = "Fishing";
	public static final String ACTION_NAME_CANT_REACH = ACTION_NAME + " (can't reach)";
	public static final String ACTION_NAME_NEED_FISHING_ROD = ACTION_NAME + " (need fishing rod)";

	Actor performer;
	WaterSource target;

	// Default for hostiles
	public ActionFishing(Actor attacker, WaterSource target) {
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

		FishingRod fishingRod = (FishingRod) performer.inventory.getGameObjectOfClass(FishingRod.class);

		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		// target.squareGameObjectIsOn.imageTexture = Square.MUD_TEXTURE;

		if (Game.level.shouldLog(target, performer))
			Game.level.logOnScreen(
					new ActivityLog(new Object[] { performer, " went fishing in ", target, " with ", fishingRod }));

		GameObject fish;
		if (target.owner == null) {
			fish = Templates.FISH.makeCopy(target.squareGameObjectIsOn, performer);
		} else {
			fish = Templates.FISH.makeCopy(target.squareGameObjectIsOn, target.owner);
		}

		if (Game.level.openInventories.size() > 0) {
		} else if (performer.squareGameObjectIsOn.onScreen() && performer.squareGameObjectIsOn.visibleToPlayer) {
			performer.secondaryAnimations.add(new AnimationTake(fish, performer, 0, 0, 0.25f));
		}
		performer.inventory.add(fish);
		if (Game.level.shouldLog(target, performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " received ", fish }));
		if (!legal) {
			Crime crime = new Crime(this, this.performer, this.target.owner, Crime.TYPE.CRIME_THEFT, fish);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}

		target.showPow();

		if (performer.faction == Game.level.factions.player) {
			Game.level.undoList.clear();
		}

		if (performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endTurn();
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

		if (!performer.canSeeGameObject(target)) {
			actionName = ACTION_NAME_CANT_REACH;
			return false;
		}

		if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) > 1) {
			actionName = ACTION_NAME_CANT_REACH;
			return false;
		}

		return true;
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