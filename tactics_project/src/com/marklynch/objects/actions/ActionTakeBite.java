package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionTakeBite extends Action {

	public static final String ACTION_NAME = "Take bite";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	GameObject target;

	// Default for hostiles
	public ActionTakeBite(Actor attacker, GameObject target) {
		super(ACTION_NAME);
		this.performer = attacker;
		this.target = target;
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

		if (performer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " took a bite of ", target }));

		if (performer.faction == Game.level.factions.get(0)) {
			Game.level.undoList.clear();
			Game.level.undoButton.enabled = false;
		}

		if (performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endTurn();

		performer.actionsPerformedThisTurn.add(this);if (sound != null)sound.play();
	}

	@Override
	public boolean check() {
		if (!performer.visibleFrom(target.squareGameObjectIsOn))
			return false;
		if (performer.straightLineDistanceTo(target.squareGameObjectIsOn) > 1)
			return false;
		return true;
	}

	@Override
	public boolean checkLegality() {
		if (target.owner != null && target.owner != performer)
			return false;
		return true;
	}

	@Override
	public Sound createSound() {
		return new Sound(performer, target, target.squareGameObjectIsOn, 1, legal, this.getClass());
	}

}
