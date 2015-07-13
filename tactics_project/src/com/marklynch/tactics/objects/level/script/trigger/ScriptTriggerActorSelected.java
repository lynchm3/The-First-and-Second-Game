package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.unit.Actor;

public class ScriptTriggerActorSelected extends ScriptTrigger {

	Actor actor;
	Level level;

	public ScriptTriggerActorSelected(Level level, Actor actor) {
		this.actor = actor;
		this.level = level;
	}

	@Override
	public boolean checkTrigger() {
		if (level.activeActor == actor)
			return true;
		return false;
	}

}
