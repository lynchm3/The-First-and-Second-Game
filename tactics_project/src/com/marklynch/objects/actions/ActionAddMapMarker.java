package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.Templates;
import com.marklynch.ui.ActivityLog;

public class ActionAddMapMarker extends Action {

	public static final String ACTION_NAME = "Add Map Marker";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	Square target;

	public ActionAddMapMarker(Square target) {
		super(ACTION_NAME, "action_add_map_marker.png");
		this.target = target;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
		legal = checkLegality();
		sound = createSound();
		movement = true;

	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		Templates.MAP_MARKER.makeCopy(target, null);

		if (Game.level.shouldLog(Game.level.player))
			Game.level.logOnScreen(new ActivityLog(new Object[] { "Added map marker to ", target }));

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
