package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionSmash extends Action {

	public static final String ACTION_NAME = "Smash";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Object attacker;
	GameObject target;

	// Default for hostiles
	public ActionSmash(Object attacker, GameObject target) {
		super(ACTION_NAME);
		this.attacker = attacker;
		this.target = target;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
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
			Game.level.logOnScreen(new ActivityLog(new Object[] { attacker, " smashed ", target }));

		if (attacker == Game.level.player)
			Game.level.endTurn();
	}

	@Override
	public boolean check() {

		if (attacker instanceof Actor) {
			Actor actor = (Actor) attacker;
			if (!actor.equippedWeapon.hasRange(actor.straightLineDistanceTo(target.squareGameObjectIsOn)))
				return false;
			if (!actor.squaresVisibleToThisCharacter.contains(target.squareGameObjectIsOn))
				return false;
		}

		return true;
	}

}
