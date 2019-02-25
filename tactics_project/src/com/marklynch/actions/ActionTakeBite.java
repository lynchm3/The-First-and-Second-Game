package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.ui.ActivityLog;

public class ActionTakeBite extends Action {

	public static final String ACTION_NAME = "Take bite";

	// Default for hostiles
	public ActionTakeBite(Actor performer, GameObject target) {
		super(ACTION_NAME, textureTakeBite, performer, target);
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

		if (Game.level.shouldLog(targetGameObject, performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " took a bite of ", targetGameObject }));

		if (performer.faction == Game.level.factions.player) {
			Game.level.undoList.clear();
		}

		if (performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endPlayerTurn();

		if (sound != null)
			sound.play();

		if (!legal) {
			Crime crime = new Crime(this.performer, targetGameObject.owner, Crime.TYPE.CRIME_THEFT);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}
	}

	@Override
	public boolean check() {
		return true;
	}

	@Override
	public boolean checkRange() {
		if (performer.straightLineDistanceTo(targetGameObject.squareGameObjectIsOn) > 1)
			return false;
		return true;
	}

	@Override
	public boolean checkLegality() {
		return standardAttackLegalityCheck(performer, targetGameObject);
	}

	@Override
	public Sound createSound() {
		return new Sound(performer, targetGameObject, targetGameObject.squareGameObjectIsOn, 1, legal, this.getClass());
	}

}
