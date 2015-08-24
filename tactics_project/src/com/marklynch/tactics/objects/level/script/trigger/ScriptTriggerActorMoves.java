package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.Game;
import com.marklynch.tactics.objects.unit.Actor;

public class ScriptTriggerActorMoves extends ScriptTrigger {

	Actor actor;
	public final static String[] editableAttributes = { "name", "actor" };
	public String actorGUID = null;

	public ScriptTriggerActorMoves() {
		name = "ScriptTriggerActorMoves";
	}

	public ScriptTriggerActorMoves(Actor actor) {
		name = "ScriptTriggerActorMoves";
		this.actor = actor;
		this.actorGUID = actor.guid;
	}

	@Override
	public boolean checkTrigger() {
		if (actor.distanceMovedThisTurn != 0)
			return true;
		return false;
	}

	@Override
	public void postLoad() {
		actor = Game.level.findActorFromGUID(actorGUID);
	}

	@Override
	public ScriptTrigger makeCopy() {
		return new ScriptTriggerActorMoves(actor);
	}

}
