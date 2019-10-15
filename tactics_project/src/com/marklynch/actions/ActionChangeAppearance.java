package com.marklynch.actions;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.objects.inanimateobjects.GameObject;

public class ActionChangeAppearance extends Action {

	public static final String ACTION_NAME = "Appearance";

	public ActionChangeAppearance(GameObject target) {
		super(ACTION_NAME, textureEllipse, null, target);
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

		if (targetGameObject.squareGameObjectIsOn != null) {
			targetGameObject.inventory.setMode(Inventory.INVENTORY_MODE.MODE_SELECT_MAP_MARKER);
			targetGameObject.inventory.open();
			targetGameObject.inventory.filter(Inventory.inventoryFilterBy, true);
			targetGameObject.inventory.sort(Inventory.inventorySortBy, true, false);
		} else {
			((GameObject) targetGameObject.inventoryThatHoldsThisObject.parent).imageTexture = targetGameObject.imageTexture;
			for (Inventory inventory : Level.openInventories) {
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
