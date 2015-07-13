package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.tactics.objects.level.Level;

public abstract class ScriptTrigger {

	public Level level;
	public boolean triggered = false;

	public ScriptTrigger(Level level) {
		super();
		this.level = level;
	}

	public abstract boolean checkTrigger();
}
