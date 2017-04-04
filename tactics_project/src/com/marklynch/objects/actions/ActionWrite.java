package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.Readable;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionWrite extends Action {

	public static final String ACTION_NAME = "Pick Up";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	Readable sign;
	Object[] text;

	public ActionWrite(Actor writer, Readable sign, Object[] text) {
		super(ACTION_NAME);
		this.performer = writer;
		this.sign = sign;
		this.text = text;
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

		sign.setText(text);
		if (performer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " wrote on ", sign }));
		performer.actions.add(this);if (sound != null)sound.play();
	}

	@Override
	public boolean check() {
		if (performer.straightLineDistanceTo(sign.squareGameObjectIsOn) < 2) {
			return true;
		}
		return false;
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