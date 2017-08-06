package com.marklynch.objects.actions;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Inventory;

public class ActionChangeAppearance extends Action {

	public static final String ACTION_NAME = "Appearance";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	GameObject target;

	public ActionChangeAppearance(GameObject target) {
		super(ACTION_NAME, "action_select_object.png");
		this.target = target;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
		legal = checkLegality();
		sound = createSound();

	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		if (target.squareGameObjectIsOn != null) {
			target.inventory.open();
			target.inventory.filter(Inventory.inventoryFilterBy, true);
			target.inventory.sort(Inventory.inventorySortBy, true);
			target.inventory.setMode(Inventory.INVENTORY_MODE.MODE_SELECT_MAP_MARKER);
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
	public boolean checkLegality() {
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}
}