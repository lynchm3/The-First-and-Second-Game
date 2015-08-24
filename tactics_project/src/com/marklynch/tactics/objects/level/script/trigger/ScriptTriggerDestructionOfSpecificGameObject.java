package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.Game;
import com.marklynch.tactics.objects.unit.Actor;

public class ScriptTriggerDestructionOfSpecificGameObject extends ScriptTrigger {

	public transient Actor actor;
	public final static String[] editableAttributes = { "name", "actor" };
	public String actorGUID = null;

	public ScriptTriggerDestructionOfSpecificGameObject() {
		this.name = this.getClass().getSimpleName();
	}

	public ScriptTriggerDestructionOfSpecificGameObject(Actor actor) {
		this.name = this.getClass().getSimpleName();
		this.actor = actor;
		this.actorGUID = actor.guid;

	}

	@Override
	public boolean checkTrigger() {
		if (actor.remainingHealth <= 0) {
			return true;
		}
		return false;
	}

	@Override
	public void postLoad() {
		actor = Game.level.findActorFromGUID(actorGUID);
	}

	@Override
	public ScriptTrigger makeCopy() {
		return new ScriptTriggerDestructionOfSpecificGameObject(actor);
	}

}
