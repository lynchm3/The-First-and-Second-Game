package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.tools.Knife;
import com.marklynch.objects.tools.Pickaxe;
import com.marklynch.ui.ActivityLog;

public class ActionSkin extends Action {

	public static final String ACTION_NAME = "Skin";

	ActiontTakeAll actionLootAll;

	// Default for hostiles
	public ActionSkin(Actor performer, GameObject target) {
		super(ACTION_NAME, textureSkin, performer, target);
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

		targetGameObject.changeHealth(-targetGameObject.remainingHealth, performer, this);
		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;

		Actor oreOwner = performer;
		if (targetGameObject.owner != null)
			oreOwner = targetGameObject.owner;

		GameObject fur = Templates.FUR.makeCopy(null, oreOwner);
		performer.inventory.add(fur);

		if (Game.level.shouldLog(targetGameObject, performer))
			Game.level.logOnScreen(
					new ActivityLog(new Object[] { performer, " skinned ", fur, " from ", targetGameObject, " with ", knife }));

		new ActiontTakeAll(performer, targetGameObject).perform();

		targetGameObject.checkIfDestroyed(performer, this);

		targetGameObject.showPow();

		if (performer.faction == Game.level.factions.player) {
			Game.level.undoList.clear();
		}

		if (performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endPlayerTurn();
		if (sound != null)
			sound.play();

		if (!legal) {
			Crime crime = new Crime(this.performer, this.targetGameObject.owner, Crime.TYPE.CRIME_THEFT, fur);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}
	}

	@Override
	public boolean check() {
		if (!performer.inventory.containsGameObjectOfType(Knife.class)) {
			disabledReason = NEED_A_KNIFE;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkRange() {

		if (performer.straightLineDistanceTo(targetGameObject.squareGameObjectIsOn) > 1) {
			return false;
		}

		return true;
	}

	@Override
	public boolean checkLegality() {

		if (!actionLootAll.legal) {
			illegalReason = actionLootAll.illegalReason;
			return false;
		}

		if (targetGameObject.owner != null && targetGameObject.owner != performer) {
			illegalReason = THEFT;
			return false;
		}

		return true;
	}

	@Override
	public Sound createSound() {
		Pickaxe pickaxe = (Pickaxe) performer.inventory.getGameObjectOfClass(Pickaxe.class);
		if (pickaxe != null) {
			float loudness = Math.max(targetGameObject.soundWhenHit, pickaxe.soundWhenHitting);
			return new Sound(performer, pickaxe, targetGameObject.squareGameObjectIsOn, loudness, legal, this.getClass());
		}
		return null;
	}

}
