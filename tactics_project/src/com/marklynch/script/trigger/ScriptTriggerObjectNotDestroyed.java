package com.marklynch.script.trigger;

import com.marklynch.objects.units.Actor;

public class ScriptTriggerObjectNotDestroyed extends ScriptTrigger {

	public transient Actor actor;
	public final static String[] editableAttributes = { "name", "actor" };

	public ScriptTriggerObjectNotDestroyed() {
		this.name = this.getClass().getSimpleName();
	}

	public ScriptTriggerObjectNotDestroyed(Actor actor) {
		this.name = this.getClass().getSimpleName();
		this.actor = actor;

	}

	@Override
	public boolean checkTrigger() {
		if (actor.remainingHealth > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void postLoad() {
	}

	@Override
	public ScriptTrigger makeCopy() {
		return new ScriptTriggerObjectNotDestroyed(actor);
	}

}
