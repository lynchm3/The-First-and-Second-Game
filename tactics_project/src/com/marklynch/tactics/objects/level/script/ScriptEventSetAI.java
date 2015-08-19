package com.marklynch.tactics.objects.level.script;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.script.trigger.ScriptTrigger;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.tactics.objects.unit.ai.AI;

public class ScriptEventSetAI extends ScriptEvent {

	public transient Actor actor;
	public AI ai;

	boolean completed = false;

	// for save + load
	public String actorGUID;

	public ScriptEventSetAI(boolean blockUserInput,
			ScriptTrigger scriptTrigger, Actor actor, AI ai) {
		super(blockUserInput, scriptTrigger);
		this.actor = actor;
		this.actorGUID = actor.guid;
		this.ai = ai;
	}

	@Override
	public void postLoad() {
		super.postLoad();
		actor = Game.level.findActorFromGUID(actorGUID);
		ai.postLoad();
	}

	@Override
	public void update(int delta) {
		actor.ai = ai;
		completed = true;
	}
}
