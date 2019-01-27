package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;

public class ActionTakeItemsSelectedInInventory extends Action {

	public static final String ACTION_NAME = "Take";
	public static final String ACTION_NAME_ILLEGAL = "Steal";
	GameObject objectToTake;
	Object objectToTakeFrom;
	InventorySquare inventorySquare;

	public ActionTakeItemsSelectedInInventory(Actor performer, Object objectToTakeFrom, GameObject objectToTake) {

		super(ACTION_NAME, textureLeft, performer, objectToTakeFrom);
		super.gameObjectPerformer = this.performer = performer;
		this.objectToTake = objectToTake;
		this.objectToTakeFrom = objectToTakeFrom;
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

			new ActionTakeItems(performer, objectToTakeFrom, objectToTake).perform();
		} else {
			Game.level.player.inventory.showQTYDialog(
					new ActionTakeItems(performer, objectToTakeFrom, objectToTake.inventorySquare.stack),
					inventorySquare.stack.size(), "Enter qty to take (available: " + inventorySquare.stack.size() + ")",
					0);
		}
	}

	@Override
	public boolean check() {

		if (targetSquare == null && target == null)
			return false;

		return true;
	}

	@Override
	public boolean checkRange() {

		if (targetSquare == null && target == null)
			return false;

		if (targetSquare != null && performer.straightLineDistanceTo(targetSquare) < 2) {
			return true;
		}

		if (target != null && performer.straightLineDistanceTo(target.squareGameObjectIsOn) < 2) {
			return true;
		}

		return false;
	}

	@Override
	public boolean checkLegality() {

		System.out.println("objectToTake.owner = " + objectToTake.owner);

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