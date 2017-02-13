package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Projectile;
import com.marklynch.ui.ActivityLog;

public class ActionAttack extends Action {

	public static final String ACTION_NAME = "ATTACK";

	// Default for hostiles
	public ActionAttack(Actor performer, GameObject target) {
		super(ACTION_NAME, performer, target);
	}

	public void perform() {
		// performer.attack(target, false);

		performer.manageAttackerReferences(target);
		performer.manageAttackerReferencesForNearbyAllies(target);
		performer.manageAttackerReferencesForNearbyEnemies(target);

		target.remainingHealth -= performer.equippedWeapon.damage;
		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;
		String attackTypeString;
		attackTypeString = "attacked ";
		Game.level.logOnScreen(new ActivityLog(new Object[] {

				this, " " + attackTypeString + " ", target, " with ", performer.equippedWeapon.imageTexture,
				" for " + performer.equippedWeapon.damage + " damage" }));

		Actor actor = null;
		if (target instanceof Actor)
			actor = (Actor) target;

		if (target.checkIfDestroyed()) {
			if (target instanceof Actor) {
				Game.level.logOnScreen(new ActivityLog(new Object[] { this, " killed ", target }));
				((Actor) target).faction.checkIfDestroyed();
			} else {
				Game.level.logOnScreen(new ActivityLog(new Object[] { this, " destroyed a ", target }));
			}

		}

		// shoot projectile
		if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) > 1) {
			Game.level.projectiles.add(new Projectile(performer, target, 5f, true, "hunter.png"));
		} else {
			performer.showPow(target);
		}

		if (performer.faction == Game.level.factions.get(0)) {
			Game.level.undoList.clear();
			Game.level.undoButton.enabled = false;
		}
	}

}
