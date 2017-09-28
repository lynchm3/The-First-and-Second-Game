package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Trader;
import com.marklynch.ui.ActivityLog;

public class ActionSellSpecificItem extends Action {

	public static final String ACTION_NAME = "Sell";
	public static final String ACTION_NAME_DISABLED = "(can't reach)";
	Actor performer;
	Actor receiver;
	GameObject object;

	public ActionSellSpecificItem(Actor performer, Actor receiver, GameObject object) {
		super(ACTION_NAME, "right.png");
		this.performer = performer;
		this.receiver = receiver;
		this.object = object;
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
			Game.level.logOnScreen(new ActivityLog(
					new Object[] { performer, " sold ", object, " to ", receiver, " for ", object.value, " gold" }));

		performer.addToCarriedGoldValue(object.value);
		receiver.removeFromCarriedGoldValue(object.value);
		object.owner = receiver;

		performer.inventory.remove(object);
		if (performer instanceof Actor) {
			Actor actor = performer;
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
			object.owner = receiver;
		}
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		if (!(receiver instanceof Trader) && receiver.getCarriedGoldValue() < object.value)
			return false;
		if (!performer.canSeeSquare(receiver.squareGameObjectIsOn)) {
			actionName = ACTION_NAME + " (can't reach)";
			return false;
		}
		return true;
		// if (performer.straightLineDistanceTo(receiver.squareGameObjectIsOn) <
		// 2) {
		// return true;
		// }
		// return false;
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
