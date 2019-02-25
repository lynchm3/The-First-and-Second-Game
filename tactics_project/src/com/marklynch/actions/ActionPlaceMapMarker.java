package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.MapMarker;
import com.marklynch.objects.templates.Templates;
import com.marklynch.ui.ActivityLog;

public class ActionPlaceMapMarker extends Action {

	public static final String ACTION_NAME = "Place Map Marker";

	public ActionPlaceMapMarker(Square target) {
		super(ACTION_NAME, textureAddMapMarker, null, target);
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

		MapMarker mapMarker = Templates.MAP_MARKER_RED.makeCopy(targetSquare, null);
		mapMarker.inventory.add(Templates.MAP_MARKER_RED.makeCopy(null, null));
		mapMarker.inventory.add(Templates.MAP_MARKER_GREEN.makeCopy(null, null));
		mapMarker.inventory.add(Templates.MAP_MARKER_BLUE.makeCopy(null, null));
		mapMarker.inventory.add(Templates.MAP_MARKER_SKULL.makeCopy(null, null));
		mapMarker.inventory.add(Templates.MAP_MARKER_TREASURE.makeCopy(null, null));
		mapMarker.inventory.add(Templates.MAP_MARKER_PORTAL.makeCopy(null, null));

		if (Game.level.shouldLog(Game.level.player))
			Game.level.logOnScreen(new ActivityLog(new Object[] { "Added map marker to ", targetGameObject }));

		Level.markerList.add(mapMarker);

		mapMarker.inventory.setMode(Inventory.INVENTORY_MODE.MODE_SELECT_MAP_MARKER);
		mapMarker.inventory.open();
		mapMarker.inventory.filter(Inventory.inventoryFilterBy, true);
		mapMarker.inventory.sort(Inventory.inventorySortBy, true, false);

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
