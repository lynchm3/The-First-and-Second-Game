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

	Openable openable;

	// Default for hostiles
	public ActionLock(GameObject unlocker, Openable openable) {
		super(ACTION_NAME, "action_lock.png");
		super.gameObjectPerformer = this.gameObjectPerformer = unlocker;
		this.openable = openable;
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

		if (gameObjectPerformer instanceof Actor) {
			Actor actor = (Actor) gameObjectPerformer;
			Key key = actor.getKeyFor(openable);

			if (openable.isOpen())
				new ActionClose(gameObjectPerformer, openable).perform();

			openable.lock();

			if (Game.level.shouldLog(openable, gameObjectPerformer))
				Game.level
						.logOnScreen(new ActivityLog(new Object[] { gameObjectPerformer, " locked ", openable, " with ", key }));

			openable.showPow();

			if (actor.faction == Game.level.factions.player) {
				Game.level.undoList.clear();
			}

		} else {

			if (openable.isOpen())
				new ActionClose(gameObjectPerformer, openable).perform();

			openable.lock();

			if (gameObjectPerformer.squareGameObjectIsOn.visibleToPlayer)
				Game.level.logOnScreen(new ActivityLog(new Object[] { gameObjectPerformer, " locked ", openable }));

			openable.showPow();
		}
		gameObjectPerformer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		if (gameObjectPerformer instanceof Actor) {
			Actor actor = (Actor) gameObjectPerformer;

			if (!actor.hasKeyForDoor(openable)) {
				actionName = ACTION_NAME_NEED_KEY;
				disabledReason = "You need a key";
				return false;
			}

			if (openable instanceof Door && openable.squareGameObjectIsOn.inventory.canShareSquare == false) {
				actionName = ACTION_NAME_BLOCKED;
				disabledReason = "Doorway blocked";
			}

			if (openable instanceof Door && openable.squareGameObjectIsOn.inventory.contains(Actor.class)) {
				actionName = ACTION_NAME_BLOCKED;
				disabledReason = "Doorway blocked";
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean checkRange() {
		if (gameObjectPerformer instanceof Actor) {
			Actor actor = (Actor) gameObjectPerformer;
			if (!actor.canSeeGameObject(openable)) {
				actionName = ACTION_NAME_CANT_REACH;
				return false;
			}
			if (actor.straightLineDistanceTo(openable.squareGameObjectIsOn) != 1) {
				actionName = ACTION_NAME_CANT_REACH;
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
		return new Sound(gameObjectPerformer, openable, openable.squareGameObjectIsOn, 1, legal, this.getClass());
	}

}
