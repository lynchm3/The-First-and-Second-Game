package com.marklynch.tactics.objects.level.script;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.script.trigger.ScriptTrigger;
import com.marklynch.tactics.objects.unit.Actor;
import com.marklynch.tactics.objects.unit.ai.routines.AIRoutine;

public class ScriptEventSetAI extends ScriptEvent {

	public final static String[] editableAttributes = { "name", "blockUserInput", "scriptTrigger", "actor", "ai" };

	public transient Actor actor;
	public AIRoutine aiRoutine;

	boolean completed = false;

	// for save + load
	public String actorGUID;

	public ScriptEventSetAI() {
		name = "ScriptEventSetAI";
	}

	public ScriptEventSetAI(boolean blockUserInput, ScriptTrigger scriptTrigger, Actor actor, AIRoutine aiRoutine) {
		super(blockUserInput, scriptTrigger);
		this.actor = actor;
		this.actorGUID = actor.guid;
		this.aiRoutine = aiRoutine;
		this.name = "ScriptEventSetAI";
	}

	@Override
	public void postLoad() {
		scriptTrigger.postLoad();
		actor = Game.level.findActorFromGUID(actorGUID);
		// aiRoutine.postLoad();
	}

	@Override
	public void update(int delta) {
		actor.aiRoutine = aiRoutine;
		completed = true;
	}

	@Override
	public boolean checkIfCompleted() {
		return completed;
	}

	@Override
	public void click() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}
}
