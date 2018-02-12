package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.AnimationWait;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class ActionWait extends Action {

	public static final String ACTION_NAME = "Wait";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";
	Actor performer;
	Square target;

	public ActionWait(Actor loiterer, Square target) {
		super(ACTION_NAME, "action_loiter.png");
		this.performer = loiterer;
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

		performer.actionsPerformedThisTurn.add(this);

		if (sound != null)
			sound.play();

		if (performer.squareGameObjectIsOn.onScreen() && performer.squareGameObjectIsOn.visibleToPlayer)
			performer.primaryAnimation = new AnimationWait();
		if (performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endTurn();

		trespassingCheck(this, performer, performer.squareGameObjectIsOn);

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
		if (target.restricted == true && !target.owners.contains(performer)) {
			return false;
		}
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}
}
