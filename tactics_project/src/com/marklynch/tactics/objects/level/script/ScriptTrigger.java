package com.marklynch.tactics.objects.level.script;

import com.marklynch.tactics.objects.level.Level;

public abstract class ScriptTrigger {

	Level level;
	boolean triggered = false;

	public ScriptTrigger(Level level) {
		super();
		this.level = level;
	}

	public abstract boolean checkTrigger();
}
