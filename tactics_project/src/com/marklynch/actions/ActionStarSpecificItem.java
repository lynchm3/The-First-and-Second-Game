package com.marklynch.actions;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.Sound;
import com.marklynch.objects.inanimateobjects.GameObject;

public class ActionStarSpecificItem extends Action {

	public static final String ACTION_NAME = "Star";

	public ActionStarSpecificItem(GameObject target) {
		super(ACTION_NAME, textureStar, null, target);
		if (!check()) {
			enabled = false;
		} else {
			if (target.starred) {
				actionName = "Unstar " + target.name;
			} else {
				actionName = "Star " + target.name;
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

		targetGameObject.starred = !targetGameObject.starred;
		for (GameObject gameObjectInInventory : Level.player.inventory.gameObjects) {
			if (gameObjectInInventory.name.equals(targetGameObject.name)) {
				gameObjectInInventory.starred = targetGameObject.starred;
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
