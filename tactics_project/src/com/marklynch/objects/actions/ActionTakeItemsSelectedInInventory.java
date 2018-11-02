package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class ActionTakeItemsSelectedInInventory extends Action {

	public static final String ACTION_NAME = "Take";
	public static final String ACTION_NAME_ILLEGAL = "Steal";
	GameObject objectToTake;
	InventorySquare inventorySquare;

	public ActionTakeItemsSelectedInInventory(Actor performer, Object target, GameObject objectToTake) {

		// public ActionTakeItems(Actor performer, Object target, GameObject
		// object) {
		super(ACTION_NAME, textureLeft, performer, target);
		super.gameObjectPerformer = this.performer = performer;
		this.objectToTake = objectToTake;
		this.inventorySquare = objectToTake.inventorySquare;
		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
		if (legal == false) {
			if (enabled) {
				actionName = ACTION_NAME_ILLEGAL;
			} else {
			}
		}
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
			new ActionTakeItems(performer, target, objectToTake).perform();
		} else {
			Game.level.player.inventory.showQTYDialog(
					new ActionTakeItems(performer, target, objectToTake.inventorySquare.stack),
					inventorySquare.stack.size(), "Enter qty to take (available: " + inventorySquare.stack.size() + ")",
					0);
		}
	}

	@Override
	public boolean check() {

		System.out.println("ActionTakeItemsSelectedInInventory a check()");

		if (targetSquare == null && target == null)
			return false;

		System.out.println("ActionTakeItemsSelectedInInventory b check()");

		return true;
	}

	@Override
	public boolean checkRange() {
		System.out.println("ActionTakeItemsSelectedInInventory a checkRange()");

		if (targetSquare == null && target == null)
			return false;

		System.out.println("ActionTakeItemsSelectedInInventory b checkRange()");
		if (targetSquare != null && performer.straightLineDistanceTo(targetSquare) < 2) {
			return true;
		}
		System.out.println("ActionTakeItemsSelectedInInventory c checkRange()");

		if (target != null && performer.straightLineDistanceTo(target.squareGameObjectIsOn) < 2) {
			return true;
		}
		System.out.println("ActionTakeItemsSelectedInInventory d checkRange()");

		return false;
	}

	@Override
	public boolean checkLegality() {
		if (objectToTake.owner != null && objectToTake.owner != performer) {
			illegalReason = THEFT;
			return false;
		}
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}