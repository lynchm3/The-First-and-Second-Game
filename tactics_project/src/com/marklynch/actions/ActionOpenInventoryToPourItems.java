package com.marklynch.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.AggressiveWildAnimal;
import com.marklynch.objects.actors.Monster;

public class ActionOpenInventoryToPourItems extends Action {

	public static final String ACTION_NAME = "Pour";

	public ActionOpenInventoryToPourItems(Actor performer, Object target) {
		super(ACTION_NAME, textureEllipse, performer, target);
		System.out.println("ActionOpenInventoryToPourItems");
		super.gameObjectPerformer = this.performer = performer;
		if (!check()) {
			enabled = false;
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

		if (Game.level.openInventories.size() > 0) {

			for (Inventory inventory : (ArrayList<Inventory>) Game.level.openInventories.clone()) {
				inventory.close();
			}
		} else {

			Game.level.player.inventory.setMode(Inventory.INVENTORY_MODE.MODE_SELECT_ITEM_TO_POUR);
			Game.level.player.inventory.open();
			Inventory.target = this.targetGameObjectOrSquare;
			Game.level.player.inventory.filter(Inventory.INVENTORY_FILTER_BY.FILTER_BY_CONTAINER_FOR_LIQUIDS, true);
			Game.level.player.inventory.sort(Inventory.inventorySortBy, false, false);
		}
		Level.closeAllPopups();
	}

	@Override
	public boolean check() {
		System.out.println("CHECK TRUE");
		return true;
	}

	@Override
	public boolean checkRange() {
		float maxDistance = 1;
		System.out.println("CHECK RANGE");

		if (performer.straightLineDistanceTo(targetSquare) > maxDistance) {
			System.out.println("CHECK RANGE false a");
			return false;
		}

		if (!performer.canSeeSquare(targetSquare)) {
			System.out.println("CHECK RANGE false b");
			return false;
		}

		System.out.println("CHECK RANGE true");
		return true;
	}

	@Override
	public boolean checkLegality() {
		// Empty square, it's fine
		if (target == null)
			return true;

		// Something that belongs to some one else
		if (target.owner != null && target.owner != performer) {
			illegalReason = VANDALISM;
			return false;
		}

		// Is human
		if (target instanceof Actor)
			if (!(target instanceof Monster) && !(target instanceof AggressiveWildAnimal)) {
				illegalReason = ASSAULT;
				return false;
			}

		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}