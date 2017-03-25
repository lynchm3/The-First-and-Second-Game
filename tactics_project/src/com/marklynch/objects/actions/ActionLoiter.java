package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.Square;
import com.marklynch.objects.units.Actor;

public class ActionLoiter extends Action {

	public static final String ACTION_NAME = "Loiter here";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	Actor loiterer;
	Square target;

	public ActionLoiter(Actor loiterer, Square target) {
		super(ACTION_NAME);
		this.loiterer = loiterer;
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
		loiter(loiterer, target);
	}

	public void loiter(Actor actor, Square squareToLoiterOn) {

		boolean illegal = false;
		if (illegal)
			actor.performingIllegalAction = true;

		if (illegal)
			actor.performingIllegalAction = true;

		if (loiterer == Game.level.player)
			Game.level.endTurn();
	}

	private void move(Actor actor, Square square) {
		actor.squareGameObjectIsOn.inventory.remove(actor);
		actor.distanceMovedThisTurn += 1;
		actor.squareGameObjectIsOn = square;
		square.inventory.add(actor);
		// Actor.highlightSelectedCharactersSquares();
	}

	@Override
	public boolean check() {
		return true;
	}
}
