package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.MapMarker;
import com.marklynch.ui.ActivityLog;

public class ActionRemoveMapMarker extends Action {

	public static final String ACTION_NAME = "Remove Map Marker";
	GameObject target;

	public ActionRemoveMapMarker(MapMarker target) {
		super(ACTION_NAME, textureX, null, target);
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

		Game.level.inanimateObjectsOnGround.remove(target);
		target.squareGameObjectIsOn.inventory.remove(target);

		if (Game.level.shouldLog(Game.level.player))
			Game.level.logOnScreen(new ActivityLog(new Object[] { "Removed map marker" }));

		Level.markerList.remove(target);

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
