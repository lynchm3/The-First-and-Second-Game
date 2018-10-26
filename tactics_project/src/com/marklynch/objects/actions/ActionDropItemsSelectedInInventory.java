package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class ActionDropItemsSelectedInInventory extends Action {

	public static final String ACTION_NAME = "Drop";
	GameObject objectToDrop;
	InventorySquare inventorySquare;

	public ActionDropItemsSelectedInInventory(GameObject performer, Square square, GameObject objectToDrop) {
		super(ACTION_NAME, textureDrop, null, performer, null, square);
		this.objectToDrop = objectToDrop;
		this.inventorySquare = objectToDrop.inventorySquare;

		if (!check()) {
			enabled = false;
		} else {
			actionName = ACTION_NAME + " " + objectToDrop.name;
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
			new ActionDropItems(gameObjectPerformer, targetSquare, objectToDrop).perform();
		} else {
			Game.level.player.inventory.showQTYDialog(
					new ActionDropItems(gameObjectPerformer, targetSquare, objectToDrop.inventorySquare.stack),
					inventorySquare.stack.size(), "Enter qty to drop (have: " + inventorySquare.stack.size() + ")", 0);
		}
	}

	@Override
	public boolean check() {

		if (targetSquare == null)
			return false;

		if (gameObjectPerformer instanceof Actor) {
			Actor actor = (Actor) gameObjectPerformer;
			if (!actor.inventory.contains(objectToDrop)) {
				return false;
			}
		} else {
			if (!gameObjectPerformer.inventory.contains(objectToDrop)) {
				return false;
			}

		}

		if (!targetSquare.inventory.canShareSquare && !objectToDrop.canShareSquare) {
			disabledReason = NO_SPACE;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkRange() {
		if (gameObjectPerformer.straightLineDistanceTo(targetSquare) > 1) {
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
