package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.CustomizedTypeAdapterFactory;
import com.marklynch.Game;
import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.unit.Actor;

public class ScriptTriggerActorSelected extends ScriptTrigger {

	transient Actor actor;
	transient Level level;

	// for saving and loading
	String actorGUID = null;

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

	public static class ScriptTriggerActorSelectedAdapterFactory extends
			CustomizedTypeAdapterFactory<ScriptTriggerActorSelected> {
		public ScriptTriggerActorSelectedAdapterFactory() {
			super(ScriptTriggerActorSelected.class);
		}

		@Override
		protected void beforeWrite(ScriptTriggerActorSelected object) {
			object.actorGUID = object.actor.guid;
		}

		@Override
		protected ScriptTriggerActorSelected afterRead(
				ScriptTriggerActorSelected object) {
			object.actor = Game.editor.level
					.findActorFromGUID(object.actorGUID);
			object.level = Game.editor.level;
			return object;
		}
	}

}
