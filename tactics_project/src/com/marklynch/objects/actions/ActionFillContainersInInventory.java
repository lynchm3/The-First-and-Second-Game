package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.UserInputLevel;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.WaterSource;
import com.marklynch.objects.units.Actor;

public class ActionFillContainersInInventory extends Action {

	public static final String ACTION_NAME = "Fill Container(s)";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	WaterSource waterSource;

	public ActionFillContainersInInventory(Actor performer, WaterSource waterSource) {
		super(ACTION_NAME, "action_select_object.png");
		this.performer = performer;
		this.waterSource = waterSource;
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
			Game.level.player.inventory.open();
			Inventory.waterSource = this.waterSource;
			Game.level.player.inventory.filter(Inventory.INVENTORY_FILTER_BY.FILTER_BY_CONTAINER_FOR_LIQUIDS, true);
			Game.level.player.inventory.sort(Inventory.inventorySortBy, false);
			Game.level.player.inventory.setMode(Inventory.INVENTORY_MODE.MODE_SELECT_ITEM_TO_FILL);
			// Game.level.openInventories.add(Game.level.player.inventory);

			// Game.level.player.inventory.setActionOnSelect(new
			// ActionFillEquippedContainer());
		}
		UserInputLevel.closeAllPopups();

		// if (performer.squareGameObjectIsOn.visibleToPlayer)
		// Game.level.logOnScreen(new ActivityLog(new Object[] { performer, "
		// picked up ", object }));
		// if (performer.inventory.contains(performer.equipped))
		// performer.equippedBeforePickingUpObject = performer.equipped;
		// object.squareGameObjectIsOn.inventory.remove(object);
		// if (object.fitsInInventory)
		// performer.inventory.add(object);
		// performer.equip(object);
		// if (object.owner == null)
		// object.owner = performer;
		// performer.actionsPerformedThisTurn.add(this);
		// if (sound != null)
		// sound.play();
		//
		// if (!legal) {
		// Crime crime = new Crime(this, this.performer, object.owner, 4,
		// object);
		// this.performer.crimesPerformedThisTurn.add(crime);
		// this.performer.crimesPerformedInLifetime.add(crime);
		// notifyWitnessesOfCrime(crime);
		// }
	}

	@Override
	public boolean check() {
		if (performer.straightLineDistanceTo(waterSource.squareGameObjectIsOn) > 1) {
			actionName = ACTION_NAME_DISABLED;
			return false;
		}

		if (!performer.canSeeSquare(waterSource.squareGameObjectIsOn)) {
			actionName = ACTION_NAME + " (can't reach)";
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