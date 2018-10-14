package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class ActionGiveItemsSelectedInInventory extends Action {

	public static final String ACTION_NAME = "Give";
	GameObject receiver;
	GameObject object;
	boolean logAsTake;
	InventorySquare inventorySquare;

	public ActionGiveItemsSelectedInInventory(GameObject performer, GameObject receiver, boolean logAsTake,
			GameObject object) {
		super(ACTION_NAME, textureGive);
		if (!(receiver instanceof Actor))
			this.actionName = "Put";
		super.gameObjectPerformer = this.gameObjectPerformer = performer;
		this.receiver = receiver;
		this.logAsTake = logAsTake;
		this.object = object;
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
		super.perform();

		if (!enabled)
			return;

		if (!checkRange())
			return;

		if (inventorySquare.stack.size() <= 5) {
			new ActionGiveItems(gameObjectPerformer, receiver, logAsTake, object).perform();
		} else {
			String qtyString = "Enter qty to give (have: " + inventorySquare.stack.size() + ")";
			if (!(receiver instanceof Actor)) {
				qtyString = "Enter qty to put (have: " + +inventorySquare.stack.size() + ")";
			}
			Game.level.player.inventory.showQTYDialog(
					new ActionGiveItems(gameObjectPerformer, receiver, logAsTake, object.inventorySquare.stack),
					inventorySquare.stack.size(), qtyString, 0);
		}
	}

	@Override
	public boolean check() {

		if (receiver == null)
			return false;

		return true;
	}

	@Override
	public boolean checkRange() {

		if (gameObjectPerformer instanceof Actor
				&& !((Actor) gameObjectPerformer).canSeeSquare(receiver.squareGameObjectIsOn)) {
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
