package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Inventory;
import com.marklynch.objects.Templates;
import com.marklynch.ui.ActivityLog;

public class ActionPlaceMapMarker extends Action {

	public static final String ACTION_NAME = "Place Map Marker";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	Square target;

	public ActionPlaceMapMarker(Square target) {
		super(ACTION_NAME, "action_add_map_marker.png");
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

		GameObject mapMarker = Templates.MAP_MARKER_RED.makeCopy(target, null);
		mapMarker.inventory.add(Templates.MAP_MARKER_RED.makeCopy(null, null));
		mapMarker.inventory.add(Templates.MAP_MARKER_GREEN.makeCopy(null, null));
		mapMarker.inventory.add(Templates.MAP_MARKER_BLUE.makeCopy(null, null));
		mapMarker.inventory.add(Templates.MAP_MARKER_SKULL.makeCopy(null, null));
		mapMarker.inventory.add(Templates.MAP_MARKER_TREASURE.makeCopy(null, null));

		if (Game.level.shouldLog(Game.level.player))
			Game.level.logOnScreen(new ActivityLog(new Object[] { "Added map marker to ", target }));

		mapMarker.inventory.open();
		mapMarker.inventory.filter(Inventory.inventoryFilterBy, true);
		mapMarker.inventory.sort(Inventory.inventorySortBy, true);
		mapMarker.inventory.setMode(Inventory.INVENTORY_MODE.MODE_SELECT_MAP_MARKER);

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
