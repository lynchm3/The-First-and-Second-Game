package com.marklynch.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.ui.ActivityLog;

public class ActionRing extends Action {

	public static final String ACTION_NAME = "Ring Bell";

	public ActionRing(Actor ringer, GameObject target) {
		super(ACTION_NAME, textureRing, ringer, target);
		if (!check()) {
			enabled = false;
		}
		legal = checkLegality();
		sound = createSound();
		sound = createSound();
	}

	@Override
	public void perform() {
		super.perform();

		if (!enabled)
			return;

		if (!checkRange())
			return;

		if (Game.level.shouldLog(performer))
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " rang ", targetGameObject }));

		if (performer.faction == Game.level.factions.player) {
			Game.level.undoList.clear();
		}

		trespassingCheck(this, performer, performer.squareGameObjectIsOn);

		if (performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endPlayerTurn();
		if (sound != null)
			sound.play();
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
		return true;
	}

	@Override
	public Sound createSound() {

		// Sound
		return new Sound(performer, targetGameObject, performer.squareGameObjectIsOn, targetGameObject.soundWhenHitting, legal,
				this.getClass());
	}

}
