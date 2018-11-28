package com.marklynch.script.trigger;

import com.marklynch.Game;
import com.marklynch.objects.units.Actor;

public class ScriptTriggerActorSelected extends ScriptTrigger {

	public transient Actor actor;
	public final static String[] editableAttributes = { "name", "actor" };

	public ScriptTriggerActorSelected() {
		this.name = this.getClass().getSimpleName();
	}

	public ScriptTriggerActorSelected(Actor actor) {
		this.name = this.getClass().getSimpleName();
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
	}

	@Override
	public ScriptTrigger makeCopy() {
		return new ScriptTriggerActorSelected(actor);
	}
}
