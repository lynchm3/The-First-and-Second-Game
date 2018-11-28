package com.marklynch.script.trigger;

import com.marklynch.objects.units.Actor;

public class ScriptTriggerActorMoves extends ScriptTrigger {

	Actor actor;
	public final static String[] editableAttributes = { "name", "actor" };

	public ScriptTriggerActorMoves() {
		name = "ScriptTriggerActorMoves";
	}

	public ScriptTriggerActorMoves(Actor actor) {
		name = "ScriptTriggerActorMoves";
		this.actor = actor;
	}

	@Override
	public boolean checkTrigger() {
		if (actor.distanceMovedThisTurn != 0)
			return true;
		return false;
	}

	@Override
	public void postLoad() {
	}

	@Override
	public ScriptTrigger makeCopy() {
		return new ScriptTriggerActorMoves(actor);
	}

}
