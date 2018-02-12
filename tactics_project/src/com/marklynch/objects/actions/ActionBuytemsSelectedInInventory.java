package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Trader;

public class ActionBuytemsSelectedInInventory extends Action {

	public static final String ACTION_NAME = "Buy";
	Actor performer;
	Actor seller;
	GameObject object;
	InventorySquare inventorySquare;

	public ActionBuytemsSelectedInInventory(Actor performer, Actor seller, GameObject object) {
		super(ACTION_NAME, "left.png");
		this.performer = performer;
		this.object = object;
		this.seller = seller;
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

		if (!checkRange())
			return;

		if (inventorySquare.stack.size() <= 5) {
			new ActionBuyItems(performer, seller, object).perform();
		} else {
			int maxCanAfford = Math.floorDiv(performer.getCarriedGoldValue(), object.value);
			int maxCanBuy = Math.min(maxCanAfford, inventorySquare.stack.size());

			Game.level.player.inventory.showQTYDialog(
					new ActionBuyItems(performer, seller, object.inventorySquare.stack), maxCanBuy,
					"Enter qty to buy (max " + maxCanBuy + ")", object.value);
		}
	}

	@Override
	public boolean check() {

		if (seller == null || object == null)
			return false;
		if (!(performer instanceof Trader) && performer.getCarriedGoldValue() < object.value)
			return false;
		return true;
	}

	@Override
	public boolean checkRange() {
		if (!performer.canSeeSquare(seller.squareGameObjectIsOn)) {
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
