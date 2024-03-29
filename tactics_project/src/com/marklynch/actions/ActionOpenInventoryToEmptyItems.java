package com.marklynch.actions;

import java.util.concurrent.CopyOnWriteArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Monster;
import com.marklynch.objects.actors.WildAnimal;

public class ActionOpenInventoryToEmptyItems extends Action {

	public static final String ACTION_NAME = "Empty";

	public ActionOpenInventoryToEmptyItems(Actor performer, Object target) {
		super(ACTION_NAME, textureEllipse, performer, target);
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

			for (Inventory inventory : (CopyOnWriteArrayList<Inventory>) Game.level.openInventories) {
				inventory.close();
			}
		} else {

			Game.level.player.inventory.setMode(Inventory.INVENTORY_MODE.MODE_SELECT_ITEM_TO_EMPTY);
			Game.level.player.inventory.open();
			Inventory.target = this.targetGameObjectOrSquare;
			Game.level.player.inventory.filter(Inventory.INVENTORY_FILTER_BY.FILTER_BY_FULL_JAR,
					true);
			Game.level.player.inventory.sort(Inventory.inventorySortBy, false, false);
		}
		Level.closeAllPopups();
	}

	@Override
	public boolean check() {
		return true;
	}

	@Override
	public boolean checkRange() {
		float maxDistance = 1;

		if (performer.straightLineDistanceTo(targetSquare) > maxDistance) {
			return false;
		}

		if (!performer.canSeeSquare(targetSquare)) {
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
		if (targetGameObject.owner != null && targetGameObject.owner != performer) {
			illegalReason = VANDALISM;
			return false;
		}

		// Is human
		if (targetGameObject instanceof Actor)
			if (!(targetGameObject instanceof Monster) && !(targetGameObject instanceof WildAnimal)) {
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