package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionTakeBite extends Action {

	public static final String ACTION_NAME = "Take bite";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor biter;
	GameObject target;

	// Default for hostiles
	public ActionTakeBite(Actor attacker, GameObject target) {
		super(ACTION_NAME);
		this.biter = attacker;
		this.target = target;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		if (biter.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { biter, " took a bite of ", target }));

		// Sound
		float loudness = 1;
		biter.sounds.add(new Sound(biter, biter, biter.squareGameObjectIsOn, loudness));

		if (biter.faction == Game.level.factions.get(0)) {
			Game.level.undoList.clear();
			Game.level.undoButton.enabled = false;
		}

		if (biter == Game.level.player)
			Game.level.endTurn();
	}

	@Override
	public boolean check() {
		if (!biter.visibleFrom(target.squareGameObjectIsOn))
			return false;
		if (biter.straightLineDistanceTo(target.squareGameObjectIsOn) > 1)
			return false;

		return true;
	}

}
