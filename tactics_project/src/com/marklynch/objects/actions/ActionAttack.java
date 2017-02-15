package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Projectile;
import com.marklynch.ui.ActivityLog;

public class ActionAttack extends Action {

	public static final String ACTION_NAME = "Attack";

	Actor attacker;
	GameObject target;

	// Default for hostiles
	public ActionAttack(Actor attacker, GameObject target) {
		super(ACTION_NAME);
		this.attacker = attacker;
		this.target = target;
	}

	@Override
	public void perform() {
		// performer.attack(targetGameObject, false);

		GameObject targetGameObject = target;

		attacker.manageAttackerReferences(targetGameObject);
		attacker.manageAttackerReferencesForNearbyAllies(targetGameObject);
		attacker.manageAttackerReferencesForNearbyEnemies(targetGameObject);

		targetGameObject.remainingHealth -= attacker.equippedWeapon.damage;
		attacker.distanceMovedThisTurn = attacker.travelDistance;
		attacker.hasAttackedThisTurn = true;
		String attackTypeString;
		attackTypeString = "attacked ";
		Game.level.logOnScreen(new ActivityLog(new Object[] {

				attacker, " " + attackTypeString + " ", targetGameObject, " with ",
				attacker.equippedWeapon.imageTexture, " for " + attacker.equippedWeapon.damage + " damage" }));

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
		if (attacker.straightLineDistanceTo(targetGameObject.squareGameObjectIsOn) > 1) {
			Game.level.projectiles.add(new Projectile(attacker, targetGameObject, 5f, true, "hunter.png"));
		} else {
			attacker.showPow(targetGameObject);
		}

		if (attacker.faction == Game.level.factions.get(0)) {
			Game.level.undoList.clear();
			Game.level.undoButton.enabled = false;
		}
	}

}
