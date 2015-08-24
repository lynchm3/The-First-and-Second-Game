package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;

public class ScriptTriggerHealthLessThan extends ScriptTrigger {

	public transient GameObject gameObject;
	public int healthLimit;
	public final static String[] editableAttributes = { "name", "gameObject",
			"healthLimit" };
	public String gameObjectGUID = null;

	public ScriptTriggerHealthLessThan() {
		this.name = this.getClass().getSimpleName();
	}

	public ScriptTriggerHealthLessThan(GameObject gameObject, int healthLimit) {
		this.name = this.getClass().getSimpleName();
		this.gameObject = gameObject;
		this.gameObjectGUID = gameObject.guid;
		this.healthLimit = healthLimit;
	}

	@Override
	public boolean checkTrigger() {
		if (gameObject.remainingHealth < healthLimit) {
			return true;
		}
		return false;
	}

	@Override
	public void postLoad() {
		gameObject = Game.level.findObjectFromGUID(gameObjectGUID);
	}

	@Override
	public ScriptTrigger makeCopy() {
		return new ScriptTriggerHealthLessThan(gameObject, healthLimit);
	}

}
