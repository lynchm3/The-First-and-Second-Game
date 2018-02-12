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
	Actor buyer;
	GameObject object;
	InventorySquare inventorySquare;

	public ActionSellItemsSelectedInInventory(Actor performer, Actor buyer, GameObject object) {
		super(ACTION_NAME, "right.png");
		this.performer = performer;
		this.object = object;
		this.buyer = buyer;
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
			new ActionSellItems(performer, buyer, object).perform();
		} else {
			int maxCanAfford = Math.floorDiv(buyer.getCarriedGoldValue(), object.value);
			int maxCanSell = Math.min(maxCanAfford, inventorySquare.stack.size());
			Game.level.player.inventory.showQTYDialog(
					new ActionSellItems(performer, buyer, object.inventorySquare.stack), maxCanSell,
					"Enter qty to sell (max " + maxCanSell + ")", object.value);
		}
	}

	@Override
	public boolean check() {

		if (buyer == null || object == null)
			return false;

		if (!(buyer instanceof Trader) && buyer.getCarriedGoldValue() < object.value)
			return false;

		return true;
	}

	@Override
	public boolean checkRange() {
		if (!performer.canSeeSquare(buyer.squareGameObjectIsOn)) {
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
