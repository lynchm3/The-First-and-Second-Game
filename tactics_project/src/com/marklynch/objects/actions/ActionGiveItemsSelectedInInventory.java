package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class ActionGiveItemsSelectedInInventory extends Action {

	public static final String ACTION_NAME = "Give";
	// GameObject receiver;
	GameObject objectToGive;
	boolean logAsTake;
	InventorySquare inventorySquare;

	public ActionGiveItemsSelectedInInventory(GameObject performer, GameObject receiver, boolean logAsTake,
			GameObject objectToGive) {
		super(ACTION_NAME, textureGive, performer, receiver);
		if (!(receiver instanceof Actor)) {
			this.actionName = "Put";
			this.image = texturePut;
		}
		this.logAsTake = logAsTake;
		this.inventorySquare = objectToGive.inventorySquare;
		this.objectToGive = objectToGive;

		if (!check()) {
			enabled = false;
		} else {
			actionName = ACTION_NAME + " " + objectToGive.name;
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
			new ActionGiveItems(gameObjectPerformer, target, logAsTake, objectToGive).perform();
		} else {
			String qtyString = "Enter qty to give (have: " + inventorySquare.stack.size() + ")";
			if (!(target instanceof Actor)) {
				qtyString = "Enter qty to put (have: " + +inventorySquare.stack.size() + ")";
			}
			Game.level.player.inventory.showQTYDialog(
					new ActionGiveItems(gameObjectPerformer, target, logAsTake, objectToGive.inventorySquare.stack),
					inventorySquare.stack.size(), qtyString, 0);
		}
	}

	@Override
	public boolean check() {

		if (target == null)
			return false;

		return true;
	}

	@Override
	public boolean checkRange() {

		if (gameObjectPerformer instanceof Actor
				&& !((Actor) gameObjectPerformer).canSeeSquare(target.squareGameObjectIsOn)) {
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
