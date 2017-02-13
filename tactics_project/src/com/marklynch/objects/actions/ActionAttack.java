package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Projectile;
import com.marklynch.ui.ActivityLog;

public class ActionAttack extends Action {

	public static final String ACTION_NAME = "Attack";

	// Default for hostiles
	public ActionAttack(Actor performer, GameObject targetGameObject) {
		super(ACTION_NAME, performer, targetGameObject);
	}

	@Override
	public void perform() {
		// performer.attack(targetGameObject, false);

		GameObject targetGameObject = (GameObject) target;

		performer.manageAttackerReferences(targetGameObject);
		performer.manageAttackerReferencesForNearbyAllies(targetGameObject);
		performer.manageAttackerReferencesForNearbyEnemies(targetGameObject);

		targetGameObject.remainingHealth -= performer.equippedWeapon.damage;
		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;
		String attackTypeString;
		attackTypeString = "attacked ";
		Game.level.logOnScreen(new ActivityLog(new Object[] {

				this, " " + attackTypeString + " ", targetGameObject, " with ", performer.equippedWeapon.imageTexture,
				" for " + performer.equippedWeapon.damage + " damage" }));

		Actor actor = null;
		if (targetGameObject instanceof Actor)
			actor = (Actor) targetGameObject;

		if (targetGameObject.checkIfDestroyed()) {
			if (targetGameObject instanceof Actor) {
				Game.level.logOnScreen(new ActivityLog(new Object[] { this, " killed ", targetGameObject }));
				((Actor) targetGameObject).faction.checkIfDestroyed();
			} else {
				Game.level.logOnScreen(new ActivityLog(new Object[] { this, " destroyed a ", targetGameObject }));
			}

		}

		// shoot projectile
		if (performer.straightLineDistanceTo(targetGameObject.squareGameObjectIsOn) > 1) {
			Game.level.projectiles.add(new Projectile(performer, targetGameObject, 5f, true, "hunter.png"));
		} else {
			performer.showPow(targetGameObject);
		}

		if (performer.faction == Game.level.factions.get(0)) {
			Game.level.undoList.clear();
			Game.level.undoButton.enabled = false;
		}
	}

}
