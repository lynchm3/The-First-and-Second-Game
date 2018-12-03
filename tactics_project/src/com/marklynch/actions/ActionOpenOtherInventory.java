package com.marklynch.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Openable;

public class ActionOpenOtherInventory extends Action {

	public static final String ACTION_NAME = "Loot";
	public static final String ACTION_NAME_2 = "Loot";

	ActionOpen actionOpen;

	public ActionOpenOtherInventory(Actor performer, GameObject target) {
		super(ACTION_NAME, textureEllipse, performer, target);
		if (target instanceof Openable)
			this.actionName = ACTION_NAME_2;
		// super.gameObjectPerformer = this.performer = performer;
		// this.target = gameObject;
		if (target instanceof Openable && ((Openable) target).isOpenable && !((Openable) target).isOpen()) {
			actionOpen = new ActionOpen(performer, (Openable) target);
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

		if (actionOpen != null) {
			actionOpen.perform();
		}

		if (Game.level.openInventories.size() > 0) {

			for (Inventory inventory : (ArrayList<Inventory>) Game.level.openInventories.clone()) {
				inventory.close();
			}
		} else {

			Game.level.player.inventory.setMode(Inventory.INVENTORY_MODE.MODE_LOOT);
			Game.level.player.inventory.otherInventory = target.inventory;
			// target.inventory.squaresX = 900;
			Game.level.player.inventory.open();
			Inventory.target = this.target;
			Game.level.player.inventory.filter(Inventory.inventoryFilterBy, true);
			Game.level.player.inventory.sort(Inventory.inventorySortBy, false, false);
		}
		Level.closeAllPopups();
	}

	@Override
	public boolean check() {

		if (actionOpen != null) {
			disabledReason = actionOpen.disabledReason;
			return actionOpen.check();
		}

		return true;
	}

	@Override
	public boolean checkRange() {

		if (actionOpen != null) {
			return actionOpen.checkRange();
		}

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
		if (actionOpen != null) {
			illegalReason = actionOpen.illegalReason;
			return actionOpen.legal;
		}
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}