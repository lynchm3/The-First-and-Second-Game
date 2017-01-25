package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.script.ScriptEvent;

public class ScriptTriggerScriptEventEnded extends ScriptTrigger {

	public transient ScriptEvent scriptEvent;
	public final static String[] editableAttributes = { "name", "scriptEvent" };
	public String scriptEventGUID;

	public ScriptTriggerScriptEventEnded() {
		this.name = this.getClass().getSimpleName();
	}

	public ScriptTriggerScriptEventEnded(ScriptEvent scriptEvent) {
		this.name = this.getClass().getSimpleName();
		this.scriptEvent = scriptEvent;
		this.scriptEventGUID = scriptEvent.guid;
	}

	@Override
	public boolean checkTrigger() {
		return scriptEvent.checkIfCompleted();
	}

	@Override
	public void postLoad() {
		scriptEvent = Game.level.script.findScriptEventFromGUID(scriptEventGUID);
	}

	@Override
	public ScriptTrigger makeCopy() {
		return new ScriptTriggerScriptEventEnded(scriptEvent);
	}

}
