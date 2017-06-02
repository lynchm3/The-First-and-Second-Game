package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.objects.HidingPlace;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionHide extends Action {

	public static final String ACTION_NAME = "Hide";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	HidingPlace object;

	public ActionMove actionMove;

	public ActionHide(Actor performer, HidingPlace object) {
		super(ACTION_NAME);
		this.performer = performer;
		this.object = object;
		if (!check()) {
			enabled = false;
			if (this.actionMove != null)
				actionName = this.actionMove.actionName;
		}

		if (actionMove != null)
			movement = true;

		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		if (actionMove != null)
			actionMove.perform();

		if (performer.hiding == false) {
			performer.hiding = true;
			if (performer.squareGameObjectIsOn.visibleToPlayer)
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " hid in ", object }));
		}

		for (Effect effect : object.effectsFromInteracting) {
			performer.addEffect(effect.makeCopy(object, performer));
		}

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		if (performer.squareGameObjectIsOn != object.squareGameObjectIsOn) {
			this.actionMove = new ActionMove(performer, object.squareGameObjectIsOn);
			return this.actionMove.enabled;
		}
		return true;
	}

	@Override
	public boolean checkLegality() {
		if (actionMove != null)
			return true;

		if (object.squareGameObjectIsOn.restricted == true && !object.squareGameObjectIsOn.owners.contains(performer)) {
			return false;
		}
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}