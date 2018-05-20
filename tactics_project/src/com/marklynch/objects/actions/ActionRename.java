package com.marklynch.objects.actions;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.ui.popups.FullScreenTextBox;

public class ActionRename extends Action {

	public static final String ACTION_NAME = "Rename";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	GameObject target;

	public ActionRename(GameObject target) {
		super(ACTION_NAME, "action_write.png");
		this.target = target;
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

		FullScreenTextBox fullScreenTextBox = new FullScreenTextBox(target, FullScreenTextBox.ENTER_NEW_MARKER,
				FullScreenTextBox.TYPE.RENAME_MAP_MARKER);
		Level.openFullScreenTextBox(fullScreenTextBox);
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
