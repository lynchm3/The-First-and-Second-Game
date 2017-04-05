package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.weapons.Projectile;
import com.marklynch.ui.ActivityLog;

public class ActionThrow extends Action {

	public static final String ACTION_NAME = "Attack";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	GameObject target;
	GameObject object;

	// Default for hostiles
	public ActionThrow(Actor performer, GameObject target, GameObject object) {
		super(ACTION_NAME);
		this.performer = performer;
		this.target = target;
		this.object = object;
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

		float damage = 5;
		target.remainingHealth -= damage;
		// target.attacked(performer);

		performer.distanceMovedThisTurn = performer.travelDistance;
		performer.hasAttackedThisTurn = true;
		String attackTypeString;
		attackTypeString = "attacked ";

		if (performer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(
					new Object[] { performer, " threw a ", object, " at ", target, " for " + damage + " damage" }));

		// shoot projectile
		Game.level.projectiles.add(new Projectile(object.name, performer, target, 5f, true, "hunter.png"));

		if (performer.faction == Game.level.factions.get(0)) {
			Game.level.undoList.clear();
			Game.level.undoButton.enabled = false;
		}

		if (performer == Game.level.player)
			Game.level.endTurn();

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {

		if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) > 10)
			return false;

		if (!performer.visibleFrom(target.squareGameObjectIsOn))
			return false;

		return true;
	}

	@Override
	public boolean checkLegality() {
		// Something that belongs to some one else
		if (target.owner != null && target.owner != Game.level.player)
			return false;
		return true;
	}

	@Override
	public Sound createSound() {

		// Sound
		// float loudness = target.soundWhenHit *
		// performer.equipped.soundWhenHitting;
		// if (performer.equipped != null)
		// return new Sound(performer, performer.equipped,
		// target.squareGameObjectIsOn, loudness, legal,
		// this.getClass());
		return null;
	}

}
