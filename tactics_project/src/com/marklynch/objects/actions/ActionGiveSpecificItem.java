package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Openable;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionGiveSpecificItem extends Action {

	public static final String ACTION_NAME = "Give";
	public static final String ACTION_NAME_DISABLED = "(can't reach)";
	GameObject performer;
	GameObject receiver;
	GameObject object;
	boolean logAsTake;

	public ActionGiveSpecificItem(GameObject performer, GameObject receiver, GameObject object, boolean logAsTake) {
		super(ACTION_NAME, "right.png");
		if (!(receiver instanceof Actor))
			this.actionName = "Put";
		this.performer = performer;
		this.receiver = receiver;
		this.object = object;
		this.logAsTake = logAsTake;
		if (!check()) {
			enabled = false;
			actionName = actionName + ACTION_NAME_DISABLED;
		} else {
			actionName = actionName + " " + object.name;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		if (Game.level.shouldLog(receiver, performer))
			if (logAsTake)
				Game.level
						.logOnScreen(new ActivityLog(new Object[] { receiver, " took ", object, " from ", performer }));
			else if (receiver instanceof Actor)
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " gave ", object, " to ", receiver }));
			else
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " put ", object, " in ", receiver }));
		performer.inventory.remove(object);

		if (performer instanceof Actor) {
			Actor actor = (Actor) performer;
			if (actor.equipped == object) {
				if (actor.inventory.contains(actor.equippedBeforePickingUpObject)) {
					actor.equip(actor.equippedBeforePickingUpObject);
				} else {
					actor.equip(null);
				}
				actor.equippedBeforePickingUpObject = null;
			}
			if (actor.helmet == object)
				actor.helmet = null;
			if (actor.bodyArmor == object)
				actor.bodyArmor = null;
			if (actor.legArmor == object)
				actor.legArmor = null;
		}

		receiver.inventory.add(object);
		if (receiver instanceof Actor) {
			object.owner = (Actor) receiver;
		}

		if (receiver instanceof Openable) {
			((Openable) receiver).open();
		}

		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		// if (performer.straightLineDistanceTo(receiver.squareGameObjectIsOn) <
		// 2) {
		// return true;
		// }

		if (performer instanceof Actor && !((Actor) performer).canSeeSquare(receiver.squareGameObjectIsOn)) {
			return false;
		}

		return true;
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
