package com.marklynch.objects.actions;

import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;

public class ActionStarSpecificItem extends Action {

	public static final String ACTION_NAME = "Star";
	GameObject object;

	public ActionStarSpecificItem(GameObject object) {
		super(ACTION_NAME, "star.png");
		this.object = object;
		if (!check()) {
			enabled = false;
		} else {
			if (object.starred) {
				actionName = "Unstar " + object.name;
			} else {
				actionName = "Star " + object.name;
			}
		}
		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		object.starred = !object.starred;
	}

	@Override
	public boolean check() {
		return true;
	}

	@Override
	public boolean checkLegality() {
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}
}
