package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.objects.Sign;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionWrite extends Action {

	public static final String ACTION_NAME = "Pick Up";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor writer;
	Sign sign;
	Object[] text;

	public ActionWrite(Actor writer, Sign sign, Object[] text) {
		super(ACTION_NAME);
		this.writer = writer;
		this.sign = sign;
		this.text = text;
		if (!check()) {
			System.out.println("ActionWrite uh oh");
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		System.out.println("ActionWrite setting text");
		sign.setText(text);
		if (writer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { writer, " wrote on ", sign }));
	}

	@Override
	public boolean check() {
		if (writer.straightLineDistanceTo(sign.squareGameObjectIsOn) < 2) {
			return true;
		}
		return false;
	}

}