package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.animation.primary.AnimationWait;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class ActionWait extends Action {

	public static final String ACTION_NAME = "Wait";
	Actor performer;
	Square targetSquare;

	public ActionWait(Actor performer, Square targetSquare) {
		super(ACTION_NAME, textureLoiter, performer, null, targetSquare);
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

		performer.actionsPerformedThisTurn.add(this);

		performer.walkPhase = 0;

		if (sound != null)
			sound.play();

		if (performer.squareGameObjectIsOn.onScreen() && performer.squareGameObjectIsOn.visibleToPlayer)
			performer.setPrimaryAnimation(new AnimationWait(performer, null));
		if (performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endPlayerTurn();

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
		if (targetSquare.restricted() == true && !targetSquare.owners.contains(performer)) {
			illegalReason = TRESPASSING;
			return false;
		}
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}
}
