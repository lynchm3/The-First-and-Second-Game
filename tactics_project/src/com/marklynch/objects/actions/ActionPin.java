package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.Window;

public class ActionPin extends Action {

	public static final String ACTION_NAME = "Pin";

	public Actor performer;
	public GameObject target;

	// Default for hostiles
	public ActionPin(Actor reader, GameObject target) {
		super(ACTION_NAME, "action_search.png");
		this.performer = reader;
		this.target = target;
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		Game.level.popupPinneds.add(new Window(target));

		performer.actionsPerformedThisTurn.add(this);
	}

	@Override
	public boolean check() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean checkLegality() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}
