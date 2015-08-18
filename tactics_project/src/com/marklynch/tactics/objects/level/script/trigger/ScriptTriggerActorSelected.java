package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.unit.Actor;

public class ScriptTriggerActorSelected extends ScriptTrigger {

	public transient Actor actor;
	public transient Level level;

	// for saving and loading
	public String actorGUID = null;

	public ScriptTriggerActorSelected(Level level, Actor actor) {
		this.actor = actor;
		this.level = level;
	}

	@Override
	public boolean checkTrigger() {

		System.out.println("checkTrigger()");
		System.out.println("checkTrigger() - level.activeActor = "
				+ level.activeActor);
		System.out.println("checkTrigger() - actor = " + actor);
		System.out.println("checkTrigger() -  triggered = " + triggered);

		if (level.activeActor == actor)
			return true;
		return false;
	}
}
