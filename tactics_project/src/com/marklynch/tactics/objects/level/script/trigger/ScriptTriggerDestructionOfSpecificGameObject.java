package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.tactics.objects.unit.Actor;

public class ScriptTriggerDestructionOfSpecificGameObject extends ScriptTrigger {

	Actor actor;

	public ScriptTriggerDestructionOfSpecificGameObject(Actor actor) {
		this.actor = actor;

	}

	@Override
	public boolean checkTrigger() {
		if (actor.remainingHealth <= 0) {
			return true;
		}
		return false;
	}

}
