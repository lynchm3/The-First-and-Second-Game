package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.Game;
import com.marklynch.tactics.objects.GameObject;

public class ScriptTriggerHealthNotFull extends ScriptTrigger {

	public transient GameObject gameObject;
	public final static String[] editableAttributes = { "name", "gameObject" };
	public String gameObjectGUID = null;

	public ScriptTriggerHealthNotFull() {
		this.name = this.getClass().getSimpleName();
	}

	public ScriptTriggerHealthNotFull(GameObject gameObject) {
		this.name = this.getClass().getSimpleName();
		this.gameObject = gameObject;
		this.gameObjectGUID = gameObject.guid;

	}

	@Override
	public boolean checkTrigger() {
		if (gameObject.remainingHealth < gameObject.totalHealth) {
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
		return new ScriptTriggerHealthNotFull(gameObject);
	}

}
