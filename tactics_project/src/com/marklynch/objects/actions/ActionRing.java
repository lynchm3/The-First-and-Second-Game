package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionRing extends Action {

	public static final String ACTION_NAME = "Ring Bell";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	GameObject object;

	public ActionRing(Actor ringer, GameObject object) {
		super(ACTION_NAME);
		this.performer = ringer;
		this.object = object;
		if (!check()) {
			enabled = false;
			actionName = ACTION_NAME_DISABLED;
		}
		legal = checkLegality();
		sound = createSound();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		if (performer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] {

					performer, " rang ", object }));

		if (performer.faction == Game.level.factions.get(0)) {
			Game.level.undoList.clear();
			Game.level.undoButton.enabled = false;
		}

		if (performer == Game.level.player && Game.level.activeActor == Game.level.player)
			Game.level.endTurn();
		performer.actions.add(this);
	}

	@Override
	public boolean check() {
		return true;
	}

	@Override
	public boolean checkLegality() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Sound createSound() {

		// Sound
		return new Sound(performer, object, performer.squareGameObjectIsOn, object.soundWhenHitting, legal,
				this.getClass());
	}

}
