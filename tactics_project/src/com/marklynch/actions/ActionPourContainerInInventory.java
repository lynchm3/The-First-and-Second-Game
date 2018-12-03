package com.marklynch.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.AggressiveWildAnimal;
import com.marklynch.objects.actors.Monster;
import com.marklynch.objects.inanimateobjects.GameObject;

public class ActionPourContainerInInventory extends Action {

	public static final String ACTION_NAME = "Pour";

	public ActionPourContainerInInventory(Actor performer, GameObject target, Square targetSquare) {
		super(ACTION_NAME, textureEllipse, performer, target);
		super.gameObjectPerformer = this.performer = performer;
		this.targetSquare = targetSquare;
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
			Inventory.target = this.target;
			Game.level.player.inventory.filter(Inventory.INVENTORY_FILTER_BY.FILTER_BY_CONTAINER_FOR_LIQUIDS, true);
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