package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.Game;
import com.marklynch.tactics.objects.unit.Actor;

public class ScriptTriggerActorSelected extends ScriptTrigger {

	public transient Actor actor;

	// for saving and loading
	public String actorGUID = null;

	public ScriptTriggerActorSelected() {
		this.name = this.getClass().getSimpleName();
	}

	public ScriptTriggerActorSelected(Actor actor) {
		this.actor = actor;
	}

	@Override
	public boolean checkTrigger() {
		if (Game.level.activeActor == actor)
			return true;
		return false;
	}

	@Override
	public void postLoad() {
		actor = Game.level.findActorFromGUID(actorGUID);
	}
}
