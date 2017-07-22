package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.ui.ActivityLog;

public class ActionSmash extends Action {

	public static final String ACTION_NAME = "Smash";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	GameObject target;

	// Default for hostiles
	public ActionSmash(Actor attacker, GameObject target) {
		super(ACTION_NAME, "action_smash.png");
		this.performer = attacker;
		this.target = target;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;
		// performer.attack(targetGameObject, false);

		// GameObject targetGameObject;// = target;

		target.remainingHealth = 0;
		target.checkIfDestroyed(performer, this);

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();

		if (target.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " smashed ", target }));

		if (performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endTurn();

		if (!legal) {
			Crime crime = new Crime(this, this.performer, target.owner, 4);
			this.performer.crimesPerformedThisTurn.add(crime);
			this.performer.crimesPerformedInLifetime.add(crime);
			notifyWitnessesOfCrime(crime);
		} else {
			trespassingCheck(this, performer, performer.squareGameObjectIsOn);
		}
	}

	@Override
	public boolean check() {

		if (performer instanceof Actor) {
			Actor actor = (performer);

			Weapon weapon = null;
			if (actor.equipped instanceof Weapon) {
				weapon = (Weapon) actor.equipped;
			} else {
				return false;
			}
			if (weapon.hasRange(actor.straightLineDistanceTo(target.squareGameObjectIsOn)))
				return false;
			if (!actor.canSeeGameObject(target))
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

		if (performer instanceof Actor)
			return new Sound(performer, target, target.squareGameObjectIsOn, 20, legal, this.getClass());
		else
			return new Sound(null, target, target.squareGameObjectIsOn, 20, legal, this.getClass());

	}

}
