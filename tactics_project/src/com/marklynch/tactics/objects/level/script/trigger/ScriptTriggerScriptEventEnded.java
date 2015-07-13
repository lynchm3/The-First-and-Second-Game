package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.level.script.ScriptEvent;

public class ScriptTriggerScriptEventEnded extends ScriptTrigger {

	ScriptEvent scriptEvent;

	public ScriptTriggerScriptEventEnded(Level level, ScriptEvent scriptEvent) {
		super(level);
		this.scriptEvent = scriptEvent;
	}

	@Override
	public boolean checkTrigger() {
		return scriptEvent.checkIfCompleted();
	}

}
