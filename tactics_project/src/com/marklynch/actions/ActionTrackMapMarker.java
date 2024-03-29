package com.marklynch.actions;

import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.journal.Journal;
import com.marklynch.objects.inanimateobjects.MapMarker;

public class ActionTrackMapMarker extends Action {

	public static final String ACTION_NAME = "Track Map Marker";
	MapMarker mapMarker;

	public ActionTrackMapMarker(MapMarker mapMarker) {
		super(ACTION_NAME, textureCheckboxChecked, null, null);
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

		if (!Journal.markersToTrack.contains(mapMarker)) {
			Journal.markersToTrack.add(mapMarker);
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
