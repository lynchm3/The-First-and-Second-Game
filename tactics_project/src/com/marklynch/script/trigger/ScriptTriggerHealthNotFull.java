package com.marklynch.script.trigger;

import com.marklynch.objects.GameObject;

public class ScriptTriggerHealthNotFull extends ScriptTrigger {

	public transient GameObject gameObject;
	public final static String[] editableAttributes = { "name", "gameObject" };

	public ScriptTriggerHealthNotFull() {
		this.name = this.getClass().getSimpleName();
	}

	public ScriptTriggerHealthNotFull(GameObject gameObject) {
		this.name = this.getClass().getSimpleName();
		this.gameObject = gameObject;

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
	}

	@Override
	public ScriptTrigger makeCopy() {
		return new ScriptTriggerHealthNotFull(gameObject);
	}

}
