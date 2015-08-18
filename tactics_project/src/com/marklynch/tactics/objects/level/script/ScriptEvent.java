package com.marklynch.tactics.objects.level.script;

import com.marklynch.tactics.objects.level.script.trigger.ScriptTrigger;

public class ScriptEvent {

	public boolean blockUserInput;

	public ScriptTrigger scriptTrigger;

	public ScriptEvent(boolean blockUserInput, ScriptTrigger scriptTrigger) {
		super();
		this.blockUserInput = blockUserInput;
		this.scriptTrigger = scriptTrigger;
	}

	public boolean checkIfCompleted() {
		return false;
	}

	public void click() {
	}

	public void update(int delta) {
	}

	public void draw() {
	}
}
