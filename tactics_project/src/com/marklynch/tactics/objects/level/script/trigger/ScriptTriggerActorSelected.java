package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.Game;
import com.marklynch.tactics.objects.unit.Actor;

public class ScriptTriggerActorSelected extends ScriptTrigger {

	public transient Actor actor;

	// for saving and loading
	public String actorGUID = null;

	public ScriptTriggerActorSelected(Actor actor) {
		this.actor = actor;
	}

	@Override
	public boolean checkTrigger() {

		// System.out.println("checkTrigger()");
		// System.out.println("checkTrigger() - Game.level.activeActor = "
		// + Game.level.activeActor);
		// System.out.println("checkTrigger() - actor = " + actor);
		// System.out.println("checkTrigger() -  triggered = " + triggered);

		System.out.println("checkTrigger() - actor.guid = " + actor.guid);
		if (Game.level.activeActor != null)
			System.out
					.println("checkTrigger() - Game.level.activeActor.guid = "
							+ Game.level.activeActor.guid);

		if (Game.level.activeActor == actor)
			return true;
		return false;
	}

	@Override
	public void postLoad() {
		actor = Game.level.findActorFromGUID(actorGUID);
	}
}
