package com.marklynch.objects.actions;

import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.journal.Journal;
import com.marklynch.objects.MapMarker;

public class ActionUntrackMapMarker extends Action {

	public static final String ACTION_NAME = "Untrack Map Marker";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	MapMarker mapMarker;

	public ActionUntrackMapMarker(MapMarker mapMarker) {
		super(ACTION_NAME, "check_box_unchecked.png");
		this.mapMarker = mapMarker;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
		legal = checkLegality();
		sound = createSound();

	}

	@Override
	public void perform() {super.perform();

		if (!enabled)
			return;

		if (!checkRange())
			return;

		if (Journal.markersToTrack.contains(mapMarker)) {
			Journal.markersToTrack.remove(mapMarker);
			Journal.createButtonsForTrackedStuffInTopRight();
		}

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
