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
	Actor target;
	GameObject object;
	InventorySquare inventorySquare;

	public ActionBuytemsSelectedInInventory(Actor performer, Actor target, GameObject object) {
		super(ACTION_NAME, "left.png");
		this.performer = performer;
		this.object = object;
		this.target = target;
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
			new ActionBuyItems(performer, target, object).perform();
		} else {
			Game.level.player.inventory.showQTYDialog(
					new ActionBuyItems(performer, target, object.inventorySquare.stack), inventorySquare.stack.size());
		}
	}

	@Override
	public boolean check() {
		if (!(performer instanceof Trader) && performer.getCarriedGoldValue() < object.value)
			return false;
		if (!performer.canSeeSquare(target.squareGameObjectIsOn)) {
			actionName = ACTION_NAME + " (can't reach)";
			return false;
		}
		return true;
		// if (target != null &&
		// performer.straightLineDistanceTo(target.squareGameObjectIsOn) < 2) {
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
