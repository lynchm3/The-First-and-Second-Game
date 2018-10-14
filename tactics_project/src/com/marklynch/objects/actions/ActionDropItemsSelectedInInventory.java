package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class ActionDropItemsSelectedInInventory extends Action {

	public static final String ACTION_NAME = "Drop";
	Square square;
	GameObject object;
	InventorySquare inventorySquare;

	public ActionDropItemsSelectedInInventory(GameObject performer, Square square, GameObject object) {
		super(ACTION_NAME, textureDrop);
		super.gameObjectPerformer = this.gameObjectPerformer = performer;
		this.square = square;
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
			new ActionDropItems(gameObjectPerformer, square, object).perform();
		} else {
			Game.level.player.inventory.showQTYDialog(
					new ActionDropItems(gameObjectPerformer, square, object.inventorySquare.stack),
					inventorySquare.stack.size(), "Enter qty to drop (have: " + inventorySquare.stack.size() + ")", 0);
		}
	}

	@Override
	public boolean check() {

		if (square == null)
			return false;

		if (gameObjectPerformer instanceof Actor) {
			Actor actor = (Actor) gameObjectPerformer;
			if (!actor.inventory.contains(object)) {
				return false;
			}
		} else {
			if (!gameObjectPerformer.inventory.contains(object)) {
				return false;
			}

		}

		if (!square.inventory.canShareSquare && !object.canShareSquare) {
			disabledReason = NO_SPACE;
			return false;
		}

		return true;
	}

	@Override
	public boolean checkRange() {
		if (gameObjectPerformer.straightLineDistanceTo(square) > 1) {
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
