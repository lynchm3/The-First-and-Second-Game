package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionGiveSpecificItem extends Action {

	public static final String ACTION_NAME = "Give";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	Actor performer;
	GameObject receiver;
	GameObject object;

	public ActionGiveSpecificItem(Actor performer, GameObject receiver, GameObject object) {
		super(ACTION_NAME, "action_give.png");
		this.performer = performer;
		this.receiver = receiver;
		this.object = object;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		} else {
			actionName = ACTION_NAME + " " + object.name;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		if (Game.level.shouldLog(receiver, performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " gave ", object, " to ", receiver }));
		performer.inventory.remove(object);
		if (performer.equipped == object)
			performer.equip(null);
		receiver.inventory.add(object);
		if (receiver instanceof Actor) {
			object.owner = (Actor) receiver;
		}
		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		if (performer.straightLineDistanceTo(receiver.squareGameObjectIsOn) < 2) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkLegality() {
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}
}
