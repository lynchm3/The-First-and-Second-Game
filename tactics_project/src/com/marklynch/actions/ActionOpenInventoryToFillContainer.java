package com.marklynch.actions;

import java.util.concurrent.CopyOnWriteArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;

public class ActionOpenInventoryToFillContainer extends Action {

	public static final String ACTION_NAME = "Fill Container(s)";

	public ActionOpenInventoryToFillContainer(Actor performer, GameObject target) {
		super(ACTION_NAME, textureFillContainer, performer, target);
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
			Game.level.player.inventory.setMode(Inventory.INVENTORY_MODE.MODE_SELECT_ITEM_TO_FILL);
			Game.level.player.inventory.open();
			Inventory.target = this.targetGameObject;
			Game.level.player.inventory.filter(Inventory.INVENTORY_FILTER_BY.FILTER_BY_EMPTY_CONTAINER_FOR_LIQUIDS,
					true);
			Game.level.player.inventory.sort(Inventory.inventorySortBy, false, false);
			// Game.level.openInventories.add(Game.level.player.inventory);

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
		if (performer.straightLineDistanceTo(targetGameObject.squareGameObjectIsOn) > 1) {
			return false;
		}

		if (!performer.canSeeSquare(targetGameObject.squareGameObjectIsOn)) {
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