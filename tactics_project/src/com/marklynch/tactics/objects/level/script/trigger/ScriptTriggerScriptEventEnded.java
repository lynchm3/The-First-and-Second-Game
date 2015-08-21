package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.tactics.objects.level.script.ScriptEvent;

public class ScriptTriggerScriptEventEnded extends ScriptTrigger {

	ScriptEvent scriptEvent;

	public ScriptTriggerScriptEventEnded() {
		this.name = this.getClass().getSimpleName();
	}

	public ScriptTriggerScriptEventEnded(ScriptEvent scriptEvent) {
		this.scriptEvent = scriptEvent;
	}

	@Override
	public boolean checkTrigger() {
		return scriptEvent.checkIfCompleted();
	}

}
