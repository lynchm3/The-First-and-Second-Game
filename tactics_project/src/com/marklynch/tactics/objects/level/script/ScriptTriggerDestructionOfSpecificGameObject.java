package com.marklynch.tactics.objects.level.script;

import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.unit.Actor;

public class ScriptTriggerDestructionOfSpecificGameObject extends ScriptTrigger {

	Actor actor;

	public ScriptTriggerDestructionOfSpecificGameObject(Level level, Actor actor) {
		super(level);
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
