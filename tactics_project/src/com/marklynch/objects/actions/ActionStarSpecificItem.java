package com.marklynch.objects.actions;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.GameObject;

public class ActionStarSpecificItem extends Action {

	public static final String ACTION_NAME = "Star";

	public ActionStarSpecificItem(GameObject object) {
		super(ACTION_NAME, textureStar, null, object, null);
		this.target = object;
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
		super.perform();

		if (!enabled)
			return;

		if (!checkRange())
			return;

		target.starred = !target.starred;
		for (GameObject gameObjectInInventory : Level.player.inventory.gameObjects) {
			if (gameObjectInInventory.name.equals(target.name)) {
				gameObjectInInventory.starred = target.starred;
			}
		}
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
		return null;
	}
}
