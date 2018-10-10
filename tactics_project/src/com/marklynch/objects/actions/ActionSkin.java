package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Junk;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.Knife;
import com.marklynch.objects.tools.Pickaxe;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionSkin extends Action {

	public static final String ACTION_NAME = "Skin";

	Actor performer;
	GameObject target;
	ActiontTakeAll actionLootAll;

	// Default for hostiles
	public ActionSkin(Actor performer, GameObject target) {
		super(ACTION_NAME, "action_skin.png");
		super.gameObjectPerformer = this.performer = performer;
		this.target = target;
		actionLootAll = new ActiontTakeAll(performer, target);
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

		actionLootAll.perform();

		Knife knife = (Knife) performer.inventory.getGameObjectOfClass(Knife.class);

		target.changeHealth(-target.remainingHealth, null, null);
		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		Actor oreOwner = performer;
		if (target.owner != null)
			oreOwner = target.owner;

		Junk fur = Templates.FUR.makeCopy(null, oreOwner);
		performer.inventory.add(fur);

		if (Game.level.shouldLog(target, performer))
			Game.level.logOnScreen(
					new ActivityLog(new Object[] { performer, " skinned ", fur, " from ", target, " with ", knife }));

		new ActiontTakeAll(performer, target).perform();

		target.checkIfDestroyed(performer, this);

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
			Crime crime = new Crime(this, this.performer, this.target.owner, Crime.TYPE.CRIME_THEFT, fur);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}
	}

	@Override
	public boolean check() {
		if (!performer.inventory.contains(Knife.class)) {
			disabledReason = "Need a knife";
			return false;
		}

		return true;
	}

	@Override
	public boolean checkRange() {

		if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) > 1) {
			actionName = ACTION_NAME_CANT_REACH;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkLegality() {

		if (!actionLootAll.legal)
			return false;

		if (target.owner != null && target.owner != performer)
			return false;

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

}
