package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class ActionTakeItemsSelectedInInventory extends VariableQtyAction {

	public static final String ACTION_NAME = "Take";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	Object target;
	Square targetSquare;
	GameObject targetGameObject;
	GameObject object;
	InventorySquare inventorySquare;

	public ActionTakeItemsSelectedInInventory(Actor performer, Object target, GameObject object) {

		// public ActionTakeItems(Actor performer, Object target, GameObject
		// object) {
		super(ACTION_NAME, "left.png");
		this.performer = performer;
		this.target = target;
		if (this.target instanceof Square)
			targetSquare = (Square) target;
		if (this.target instanceof GameObject)
			targetGameObject = (GameObject) target;
		this.object = object;
		this.inventorySquare = object.inventorySquare;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		if (inventorySquare.stack.size() <= 5) {
			new ActionTakeItems(performer, target, object).perform();
		} else {
			Game.level.player.inventory.showQTYDialog(
					new ActionTakeItems(performer, target, object.inventorySquare.stack), inventorySquare.stack.size());
		}
	}

	@Override
	public boolean check() {

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
		if (object.owner != null && object.owner != performer)
			return false;
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}