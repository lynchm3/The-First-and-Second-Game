package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Projectile;
import com.marklynch.ui.ActivityLog;

public class ActionAttack extends Action {

	public static final String ACTION_NAME = "Attack";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	GameObject target;

	// Default for hostiles
	public ActionAttack(Actor attacker, GameObject target) {
		super(ACTION_NAME);
		this.performer = attacker;
		this.target = target;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
		legal = checkLegality();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;
		// performer.attack(targetGameObject, false);

		// GameObject targetGameObject;// = target;

		if (target instanceof Actor) {
			performer.addAttackerForThisAndGroupMembers((Actor) target);
			performer.addAttackerForNearbyFactionMembersIfVisible((Actor) target);
			((Actor) target).addAttackerForNearbyFactionMembersIfVisible(performer);
		}
		target.remainingHealth -= performer.equippedWeapon.getEffectiveSlashDamage();
		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;
		String attackTypeString;
		attackTypeString = "attacked ";

		if (performer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] {

					performer, " " + attackTypeString + " ", target, " with ", performer.equippedWeapon.imageTexture,
					" for " + performer.equippedWeapon.getEffectiveSlashDamage() + " damage" }));

		Actor actor = null;
		if (target instanceof Actor)
			actor = (Actor) target;

		if (target.checkIfDestroyed()) {
			if (target instanceof Actor) {
				if (performer.squareGameObjectIsOn.visibleToPlayer)
					Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " killed ", target }));
				((Actor) target).faction.checkIfDestroyed();
			} else {
				if (performer.squareGameObjectIsOn.visibleToPlayer)
					Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " destroyed a ", target }));
			}

		}

		// shoot projectile
		if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) > 1) {
			Game.level.projectiles.add(new Projectile("Arrow", performer, target, 5f, true, "hunter.png"));
		} else {
			performer.showPow(target);
		}

		// Sound
		float loudness = target.soundWhenHit * performer.equippedWeapon.soundWhenHitting;
		if (performer.equippedWeapon != null)
			sound = new Sound(performer, performer.equippedWeapon, performer.squareGameObjectIsOn, loudness, legal,
					this.getClass());

		if (performer.faction == Game.level.factions.get(0)) {
			Game.level.undoList.clear();
			Game.level.undoButton.enabled = false;
		}

		if (performer == Game.level.player)
			Game.level.endTurn();

		performer.actions.add(this);
	}

	@Override
	public boolean check() {
		if (!performer.visibleFrom(target.squareGameObjectIsOn))
			return false;

		if (!performer.equippedWeapon.hasRange(performer.straightLineDistanceTo(target.squareGameObjectIsOn)))
			return false;

		return true;
	}

	@Override
	public boolean checkLegality() {
		return true;
	}

}
