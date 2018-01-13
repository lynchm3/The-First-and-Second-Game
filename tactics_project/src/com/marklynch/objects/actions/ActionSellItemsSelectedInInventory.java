package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Trader;

public class ActionSellItemsSelectedInInventory extends Action {

	public static final String ACTION_NAME = "Sell";
	Actor performer;
	Actor receiver;
	GameObject object;
	InventorySquare inventorySquare;

	public ActionSellItemsSelectedInInventory(Actor performer, Actor receiver, GameObject object) {
		super(ACTION_NAME, "right.png");
		this.performer = performer;
		this.object = object;
		this.receiver = receiver;
		this.inventorySquare = object.inventorySquare;

		if (!check()) {
			enabled = false;
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

		if (inventorySquare.stack.size() <= 5) {
			new ActionSellItems(performer, receiver, object).perform();
		} else {
			Game.level.player.inventory.showQTYDialog(
					new ActionSellItems(performer, receiver, object.inventorySquare.stack),
					inventorySquare.stack.size(), "Enter qty to sell", object.value);
		}
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
