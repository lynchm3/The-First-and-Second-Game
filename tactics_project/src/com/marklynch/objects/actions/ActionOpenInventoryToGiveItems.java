package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

public class ActionOpenInventoryToGiveItems extends Action {

	public static final String ACTION_NAME = "Give";

	public ActionOpenInventoryToGiveItems(Actor performer, GameObject target) {
		super(ACTION_NAME, textureEllipse, performer, target);

		if (!(target instanceof Actor)) {
			this.actionName = "Put";
		}

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

			Game.level.player.inventory.setMode(Inventory.INVENTORY_MODE.MODE_SELECT_ITEM_TO_GIVE);
			// Game.level.player.inventory.otherInventory = target.inventory;
			Game.level.player.inventory.open();
			Inventory.target = this.target;
			Game.level.player.inventory.filter(Inventory.inventoryFilterBy, true);
			Game.level.player.inventory.sort(Inventory.inventorySortBy, false, false);
			// Game.level.openInventories.add(Game.level.player.inventory);
			// Game.level.player.inventory.open();

			// Game.level.player.inventory.setActionOnSelect(new
			// ActionFillEquippedContainer());
		}
		Level.closeAllPopups();

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

		return true;
	}

	@Override
	public boolean checkRange() {

		if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) > 1) {
			return false;
		}

		if (!performer.canSeeSquare(target.squareGameObjectIsOn)) {
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