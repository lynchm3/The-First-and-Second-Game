package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Weapon;
import com.marklynch.ui.ActivityLog;

public class ActionSmash extends Action {

	public static final String ACTION_NAME = "Smash";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	GameObject performer;
	GameObject target;

	// Default for hostiles
	public ActionSmash(GameObject attacker, GameObject target) {
		super(ACTION_NAME);
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
		target.checkIfDestroyed();

		if (target.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " smashed ", target }));

		if (performer == Game.level.player)
			Game.level.endTurn();

		if (performer instanceof Actor)
			((Actor) performer).actions.add(this);
	}

	@Override
	public boolean check() {

		if (performer instanceof Actor) {
			Actor actor = ((Actor) performer);

			Weapon weapon = null;
			if (actor.equipped instanceof Weapon) {
				weapon = (Weapon) actor.equipped;
			} else {
				return false;
			}
			if (weapon.hasRange(actor.straightLineDistanceTo(target.squareGameObjectIsOn)))
				return false;
			if (!actor.visibleFrom(target.squareGameObjectIsOn))
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
			return new Sound((Actor) performer, target, target.squareGameObjectIsOn, 20, legal, this.getClass());
		else
			return new Sound(null, target, target.squareGameObjectIsOn, 20, legal, this.getClass());

	}

}
