package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.ContainerForLiquids;
import com.marklynch.objects.Liquid;
import com.marklynch.objects.Templates;
import com.marklynch.objects.WaterSource;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionFillSpecificContainer extends Action {

	public static final String ACTION_NAME = "Fill Container";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	WaterSource waterSource;
	ContainerForLiquids containerForLiquids;

	public ActionFillSpecificContainer(Actor performer, WaterSource waterSource,
			ContainerForLiquids containerForLiquids) {
		super(ACTION_NAME, "action_fill_container.png");
		this.performer = performer;
		this.waterSource = waterSource;
		this.containerForLiquids = containerForLiquids;
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

		Liquid water = Templates.WATER.makeCopy(null, performer, containerForLiquids.volume);
		containerForLiquids.inventory.add(water);
		if (performer.squareGameObjectIsOn.visibleToPlayer) {
			Game.level.logOnScreen(
					new ActivityLog(new Object[] { performer, " filled ", containerForLiquids, " with ", water }));
		}

		// if (Game.level.openInventories.size() > 0) {
		// Game.level.openInventories.clear();
		// } else {
		//
		// Game.level.player.inventory.open();
		// Game.level.player.inventory.filter(Inventory.INVENTORY_FILTER_BY.FILTER_BY_CONTAINER_FOR_LIQUIDS,
		// true);
		// Game.level.player.inventory.sort(Inventory.inventorySortBy);
		// Game.level.player.inventory.setMode(Inventory.INVENTORY_MODE.MODE_SELECT_CONTAINER_FOR_LIQUIDS_TO_FILL);
		// // Game.level.player.inventory.setActionOnSelect(new
		// // ActionFillEquippedContainer());
		// }
		// UserInputLevel.closeAllPopups();

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
		if (containerForLiquids.inventory.size() > 0) {
			actionName = ACTION_NAME_DISABLED;
			return false;
		}

		if (performer.straightLineDistanceTo(waterSource.squareGameObjectIsOn) > 1) {
			actionName = ACTION_NAME_DISABLED;
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