package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class ActionEatItemsSelectedInInventory extends Action {

	public static final String ACTION_NAME = "Eat";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	GameObject object;
	InventorySquare inventorySquare;

	public ActionEatItemsSelectedInInventory(Actor performer, GameObject object) {

		// public ActionTakeItems(Actor performer, Object target, GameObject
		// object) {
		super(ACTION_NAME, "action_eat.png");
		this.performer = performer;
		this.object = object;
		this.inventorySquare = object.inventorySquare;
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

		if (inventorySquare.stack.size() <= 5) {
			new ActionEatItems(performer, object).perform();
		} else {
			Game.level.player.inventory.showQTYDialog(new ActionEatItems(performer, object.inventorySquare.stack),
					inventorySquare.stack.size());
		}
	}

	@Override
	public boolean check() {
		if (performer.straightLineDistanceTo(object.squareGameObjectIsOn) < 2) {
			return true;
		}
		if (performer.inventory == object.inventoryThatHoldsThisObject)
			return true;

		return false;
	}

	@Override
	public boolean checkLegality() {
		if (object.owner != null && object.owner != performer)
			return false;
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}