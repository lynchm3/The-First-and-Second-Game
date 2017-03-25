package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionRing extends Action {

	public static final String ACTION_NAME = "Ring Bell";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor ringer;
	GameObject object;

	// Default for hostiles
	public ActionRing(Actor ringer, GameObject object) {
		super(ACTION_NAME);
		this.ringer = ringer;
		this.object = object;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		boolean illegal = false;
		if (illegal)
			ringer.performingIllegalAction = true;

		// Sound
		ringer.sounds.add(new Sound(ringer, object, ringer.squareGameObjectIsOn, object.soundWhenHitting, illegal,
				this.getClass()));

		if (ringer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] {

					ringer, " rang ", object }));

		if (ringer.faction == Game.level.factions.get(0)) {
			Game.level.undoList.clear();
			Game.level.undoButton.enabled = false;
		}

		if (ringer == Game.level.player)
			Game.level.endTurn();
	}

	@Override
	public boolean check() {
		return true;
	}

}
