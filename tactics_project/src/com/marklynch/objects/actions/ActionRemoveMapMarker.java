package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.MapMarker;
import com.marklynch.ui.ActivityLog;

public class ActionRemoveMapMarker extends Action {

	public static final String ACTION_NAME = "Remove Map Marker";
	MapMarker mapMarker;

	public ActionRemoveMapMarker(MapMarker mapMarker) {
		super(ACTION_NAME, "x.png");
		this.mapMarker = mapMarker;
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

		Game.level.inanimateObjectsOnGround.remove(mapMarker);
		mapMarker.squareGameObjectIsOn.inventory.remove(mapMarker);

		if (Game.level.shouldLog(Game.level.player))
			Game.level.logOnScreen(new ActivityLog(new Object[] { "Removed map marker" }));

		Level.markerList.remove(mapMarker);

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
