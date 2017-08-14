package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.Door;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Key;
import com.marklynch.objects.Openable;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionLock extends Action {

	public static final String ACTION_NAME = "Lock";
	public static final String ACTION_NAME_CANT_REACH = ACTION_NAME + " (can't reach)";
	public static final String ACTION_NAME_NEED_KEY = ACTION_NAME + " (need key)";
	public static final String ACTION_NAME_BLOCKED = ACTION_NAME + " (blocked)";

	GameObject performer;
	Openable openable;

	// Default for hostiles
	public ActionLock(GameObject unlocker, Openable openable) {
		super(ACTION_NAME, "action_lock.png");
		this.performer = unlocker;
		this.openable = openable;
		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (performer instanceof Actor) {
			Actor actor = (Actor) performer;
			Key key = actor.getKeyFor(openable);

			if (openable.isOpen())
				new ActionClose(performer, openable).perform();

			openable.lock();

			if (Game.level.shouldLog(openable, performer))
				Game.level
						.logOnScreen(new ActivityLog(new Object[] { performer, " locked ", openable, " with ", key }));

			performer.showPow(openable);

			if (actor.faction == Game.level.factions.get(0)) {
				Game.level.undoList.clear();
			}

			actor.actionsPerformedThisTurn.add(this);
		} else {

			if (openable.isOpen())
				new ActionClose(performer, openable).perform();

			openable.lock();

			if (performer.squareGameObjectIsOn.visibleToPlayer)
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " locked ", openable }));

			performer.showPow(openable);
		}
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		if (performer instanceof Actor) {
			Actor actor = (Actor) performer;
			if (!actor.canSeeGameObject(openable)) {
				actionName = ACTION_NAME_CANT_REACH;
				return false;
			}
			if (actor.straightLineDistanceTo(openable.squareGameObjectIsOn) != 1) {
				actionName = ACTION_NAME_CANT_REACH;
				return false;
			}

			if (!actor.hasKeyForDoor(openable)) {
				actionName = ACTION_NAME_NEED_KEY;
				return false;
			}

			if (openable instanceof Door && openable.squareGameObjectIsOn.inventory.canShareSquare() == false) {
				actionName = ACTION_NAME_BLOCKED;
			}

			if (openable instanceof Door && openable.squareGameObjectIsOn.inventory.contains(Actor.class)) {
				actionName = ACTION_NAME_BLOCKED;
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		return true;
	}

	@Override
	public Sound createSound() {
		return new Sound(performer, openable, openable.squareGameObjectIsOn, 1, legal, this.getClass());
	}

}
