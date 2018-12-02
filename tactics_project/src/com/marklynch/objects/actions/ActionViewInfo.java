package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.actors.Actor;
import com.marklynch.ui.PinWindow;

public class ActionViewInfo extends Action {

	public static final String ACTION_NAME = "Info";

	// Default for hostiles
	public ActionViewInfo(Actor performer, Object target) {
		super(ACTION_NAME, textureSearch, performer, target);
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

		Game.level.pinWindows.add(new PinWindow(targetGameObjectOrSquare));

	}

	@Override
	public boolean check() {
		for (PinWindow pinWindow : Game.level.pinWindows) {
			if (pinWindow.object == targetGameObjectOrSquare) {
				return false;
			}
		}
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
