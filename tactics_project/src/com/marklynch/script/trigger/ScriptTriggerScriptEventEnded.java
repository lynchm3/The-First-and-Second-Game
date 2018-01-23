package com.marklynch.script.trigger;

import com.marklynch.script.ScriptEvent;

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
	}

	@Override
	public ScriptTrigger makeCopy() {
		return new ScriptTriggerScriptEventEnded(scriptEvent);
	}

}
