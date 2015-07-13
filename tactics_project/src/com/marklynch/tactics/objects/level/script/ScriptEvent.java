package com.marklynch.tactics.objects.level.script;

import com.marklynch.tactics.objects.level.script.trigger.ScriptTrigger;

public abstract class ScriptEvent {

	public boolean blockUserInput;

	public ScriptTrigger scriptTrigger;

	public ScriptEvent(boolean blockUserInput, ScriptTrigger scriptTrigger) {
		super();
		this.blockUserInput = blockUserInput;
		this.scriptTrigger = scriptTrigger;
	}

	public abstract boolean checkIfCompleted();

	public abstract void click();

	public abstract void update(int delta);

	public abstract void draw();
}
