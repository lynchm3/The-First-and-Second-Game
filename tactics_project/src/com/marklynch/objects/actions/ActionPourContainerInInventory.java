package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.AggressiveWildAnimal;
import com.marklynch.objects.units.Monster;

public class ActionPourContainerInInventory extends Action {

	public static final String ACTION_NAME = "Pour";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	Object target;
	Square targetSquare;
	GameObject targetGameObject;

	public ActionPourContainerInInventory(Actor performer, Object target) {
		super(ACTION_NAME, "action_select_object.png");
		this.performer = performer;
		this.target = target;
		if (target instanceof Square) {
			targetSquare = (Square) target;
			targetGameObject = targetSquare.inventory.getGameObjectThatCantShareSquare();
		} else if (target instanceof GameObject) {
			targetGameObject = (GameObject) target;
			targetSquare = targetGameObject.squareGameObjectIsOn;
		}
		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		if (Game.level.openInventories.size() > 0) {

			for (Inventory inventory : (ArrayList<Inventory>) Game.level.openInventories.clone()) {
				inventory.close();
			}
		} else {

			Game.level.player.inventory.setMode(Inventory.INVENTORY_MODE.MODE_SELECT_ITEM_TO_POUR);
			Game.level.player.inventory.open();
			Inventory.target = this.target;
			Game.level.player.inventory.filter(Inventory.INVENTORY_FILTER_BY.FILTER_BY_CONTAINER_FOR_LIQUIDS, true);
			Game.level.player.inventory.sort(Inventory.inventorySortBy, false, false);
		}
		Level.closeAllPopups();
	}

	@Override
	public boolean check() {
		float maxDistance = 1;

		if (performer.straightLineDistanceTo(targetSquare) > maxDistance) {
			actionName = ACTION_NAME + " (can't reach)";
			return false;
		}

		if (!performer.canSeeSquare(targetSquare)) {
			actionName = ACTION_NAME + " (can't reach)";
			return false;
		}

		return true;
	}

	@Override
	public boolean checkLegality() {
		// Empty square, it's fine
		if (targetGameObject == null)
			return true;

		// Something that belongs to some one else
		if (targetGameObject.owner != null && targetGameObject.owner != performer)
			return false;

		// Is human
		if (targetGameObject instanceof Actor)
			if (!(targetGameObject instanceof Monster) && !(targetGameObject instanceof AggressiveWildAnimal))
				return false;

		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}