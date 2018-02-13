package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.InventorySquare;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class ActionDropItemsSelectedInInventory extends Action {

	public static final String ACTION_NAME = "Drop";
	GameObject performer;
	Square square;
	GameObject object;
	InventorySquare inventorySquare;

	public ActionDropItemsSelectedInInventory(GameObject performer, Square square, GameObject object) {
		super(ACTION_NAME, "right.png");
		this.performer = performer;
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

		if (!enabled)
			return;

		if (!checkRange())
			return;

		if (inventorySquare.stack.size() <= 5) {
			new ActionDropItems(performer, square, object).perform();
		} else {
			Game.level.player.inventory.showQTYDialog(
					new ActionDropItems(performer, square, object.inventorySquare.stack), inventorySquare.stack.size(),
					"Enter qty to drop", 0);
		}
	}

	@Override
	public boolean check() {

		if (square == null)
			return false;

		if (performer instanceof Actor) {
			Actor actor = (Actor) performer;
			if (!actor.inventory.contains(object)) {
				actionName = ACTION_NAME + " " + object.name + " (can't reach)";
				return false;
			}
		} else {
			if (!performer.inventory.contains(object)) {
				actionName = ACTION_NAME + " " + object.name + " (can't reach)";
				return false;
			}

		}

		System.out.println("square = " + square);
		System.out.println("square.inventory = " + square.inventory);
		System.out.println("object = " + object);
		if (!square.inventory.canShareSquare() && !object.canShareSquare) {
			actionName = ACTION_NAME + " " + object.name + " (no space)";
			return false;
		}

		return true;
	}

	@Override
	public boolean checkRange() {
		if (performer.straightLineDistanceTo(square) > 1) {
			actionName = ACTION_NAME + " " + object.name + " (can't reach)";
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
