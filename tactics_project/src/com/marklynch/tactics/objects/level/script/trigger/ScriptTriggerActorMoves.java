package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.tactics.objects.unit.Actor;

public class ScriptTriggerActorMoves extends ScriptTrigger {

	Actor actor;

	public ScriptTriggerActorMoves(Actor actor) {
		this.actor = actor;
	}

	@Override
	public boolean checkTrigger() {
		if (actor.distanceMovedThisTurn != 0)
			return true;
		return false;
	}

}
