package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.ui.ActivityLog;

public class ActionRemoveMapMarker extends Action {

	public static final String ACTION_NAME = "Remove Map Marker";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	GameObject target;

	public ActionRemoveMapMarker(GameObject gameObject) {
		super(ACTION_NAME, "x.png");
		this.target = gameObject;
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

		Game.level.inanimateObjectsOnGround.remove(target);
		target.squareGameObjectIsOn.inventory.remove(target);

		if (Game.level.shouldLog(Game.level.player))
			Game.level.logOnScreen(new ActivityLog(new Object[] { "Removed map marker" }));

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
