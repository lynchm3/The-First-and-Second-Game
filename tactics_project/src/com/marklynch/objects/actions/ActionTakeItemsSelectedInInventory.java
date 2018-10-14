package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class ActionTakeItemsSelectedInInventory extends Action {

	public static final String ACTION_NAME = "Take";
	public static final String ACTION_NAME_ILLEGAL = "Steal";

	Actor performer;
	Object target;
	Square targetSquare;
	GameObject targetGameObject;
	GameObject object;
	InventorySquare inventorySquare;

	public ActionTakeItemsSelectedInInventory(Actor performer, Object target, GameObject object) {

		// public ActionTakeItems(Actor performer, Object target, GameObject
		// object) {
		super(ACTION_NAME, textureLeft);
		super.gameObjectPerformer = this.performer = performer;
		this.target = target;
		if (this.target instanceof Square)
			targetSquare = (Square) target;
		if (this.target instanceof GameObject)
			targetGameObject = (GameObject) target;
		this.object = object;
		this.inventorySquare = object.inventorySquare;
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
			new ActionTakeItems(performer, target, object).perform();
		} else {
			Game.level.player.inventory.showQTYDialog(
					new ActionTakeItems(performer, target, object.inventorySquare.stack), inventorySquare.stack.size(),
					"Enter qty to take (available: " + inventorySquare.stack.size() + ")", 0);
		}
	}

	@Override
	public boolean check() {

		if (targetSquare == null && targetGameObject == null)
			return false;

		return true;
	}

	@Override
	public boolean checkRange() {

		if (targetSquare == null && targetGameObject == null)
			return false;

		if (targetSquare != null && performer.straightLineDistanceTo(targetSquare) < 2) {
			return true;
		}

		if (targetGameObject != null && performer.straightLineDistanceTo(targetGameObject.squareGameObjectIsOn) < 2) {
			return true;
		}

		return false;
	}

	@Override
	public boolean checkLegality() {
		if (object.owner != null && object.owner != performer) {
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