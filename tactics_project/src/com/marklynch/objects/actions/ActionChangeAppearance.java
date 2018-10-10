package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.objects.GameObject;

public class ActionChangeAppearance extends Action {

	public static final String ACTION_NAME = "Appearance";
	GameObject target;

	public ActionChangeAppearance(GameObject target) {
		super(ACTION_NAME, "action_select_object.png");
		this.target = target;
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

		if (target.squareGameObjectIsOn != null) {
			target.inventory.setMode(Inventory.INVENTORY_MODE.MODE_SELECT_MAP_MARKER);
			target.inventory.open();
			target.inventory.filter(Inventory.inventoryFilterBy, true);
			target.inventory.sort(Inventory.inventorySortBy, true, false);
		} else {
			((GameObject) target.inventoryThatHoldsThisObject.parent).imageTexture = target.imageTexture;
			for (Inventory inventory : (ArrayList<Inventory>) Game.level.openInventories.clone()) {
				inventory.close();
			}
		}

		// Game.level.popupTextBoxes.add(new PopupTextBox(target));
		// Templates.MAP_MARKER.makeCopy(target, null);
		//
		// if (Game.level.shouldLog(Game.level.player))
		// Game.level.logOnScreen(new ActivityLog(new Object[] { "Added map
		// marker to ", target }));

	}

	@Override
	public boolean check() {
		return true;
	}

	@Override
	public boolean checkRange() {
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
