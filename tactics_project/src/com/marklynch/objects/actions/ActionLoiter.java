package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.objects.units.Actor;

public class ActionLoiter extends Action {

	public static final String ACTION_NAME = "Loiter here";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	Actor performer;
	Square target;

	public ActionLoiter(Actor loiterer, Square target) {
		super(ACTION_NAME);
		this.performer = loiterer;
		this.target = target;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
		legal = checkLegality();

	}

	@Override
	public void perform() {
		if (!enabled)
			return;
		loiter(performer, target);
		performer.actions.add(this);
	}

	public void loiter(Actor actor, Square squareToLoiterOn) {
		if (performer == Game.level.player)
			Game.level.endTurn();
	}

	@Override
	public boolean check() {
		return true;
	}

	@Override
	public boolean checkLegality() {
		return true;
	}
}
