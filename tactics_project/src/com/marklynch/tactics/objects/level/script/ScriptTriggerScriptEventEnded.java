package com.marklynch.tactics.objects.level.script;

import com.marklynch.tactics.objects.level.Level;

public class ScriptTriggerScriptEventEnded extends ScriptTrigger {

	ScriptEvent scriptEvent;

	public ScriptTriggerScriptEventEnded(Level level, ScriptEvent scriptEvent) {
		super(level);
		this.scriptEvent = scriptEvent;
	}

	@Override
	public boolean checkTrigger() {
		if (scriptEvent.checkIfCompleted())
			return true;
		return false;
	}

}
