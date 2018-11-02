package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Trader;

public class ActionSellItemsSelectedInInventory extends Action {

	public static final String ACTION_NAME = "Sell";
	Actor buyer;
	GameObject sellObject;
	InventorySquare inventorySquare;

	public ActionSellItemsSelectedInInventory(Actor performer, Actor target, GameObject sellObject) {
		super(ACTION_NAME, textureSell, performer, target);
		this.sellObject = sellObject;
		this.buyer = target;
		this.inventorySquare = sellObject.inventorySquare;

		if (!check()) {
			enabled = false;
		} else {
			actionName = ACTION_NAME + " " + sellObject.name;
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

		if (inventorySquare.stack.size() <= 5) {
			new ActionSellItems(performer, buyer, sellObject).perform();
		} else {
			int maxCanAfford = Math.floorDiv(buyer.getCarriedGoldValue(), sellObject.value);
			int maxCanSell = Math.min(maxCanAfford, inventorySquare.stack.size());
			Game.level.player.inventory.showQTYDialog(
					new ActionSellItems(performer, buyer, sellObject.inventorySquare.stack), maxCanSell,
					"Enter qty to sell (max " + maxCanSell + ")", sellObject.value);
		}
	}

	@Override
	public boolean check() {

		if (buyer == null || sellObject == null)
			return false;

		if (!(buyer instanceof Trader) && buyer.getCarriedGoldValue() < sellObject.value) {
			disabledReason = NOT_ENOUGH_GOLD;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkRange() {
		if (!performer.canSeeSquare(buyer.squareGameObjectIsOn)) {
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
