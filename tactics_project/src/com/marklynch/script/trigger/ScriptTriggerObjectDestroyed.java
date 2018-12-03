package com.marklynch.script.trigger;

import com.marklynch.objects.inanimateobjects.GameObject;

public class ScriptTriggerObjectDestroyed extends ScriptTrigger {

	public transient GameObject gameObject;
	public final static String[] editableAttributes = { "name", "gameObject" };
	public String gameObjectGUID = null;

	public ScriptTriggerObjectDestroyed() {
		this.name = this.getClass().getSimpleName();
	}

	public ScriptTriggerObjectDestroyed(GameObject gameObject) {
		this.name = this.getClass().getSimpleName();
		this.gameObject = gameObject;

	}

	@Override
	public boolean checkTrigger() {
		if (gameObject.remainingHealth <= 0) {
			return true;
		}
		return false;
	}

	@Override
	public void postLoad() {
	}

	@Override
	public ScriptTrigger makeCopy() {
		return new ScriptTriggerObjectDestroyed(gameObject);
	}

}
