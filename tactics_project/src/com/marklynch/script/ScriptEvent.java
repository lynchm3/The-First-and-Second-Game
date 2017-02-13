package com.marklynch.script;

import java.util.UUID;

import com.marklynch.script.trigger.ScriptTrigger;

public abstract class ScriptEvent {

	public String name;

	public boolean blockUserInput;

	public ScriptTrigger scriptTrigger;

	public String guid = UUID.randomUUID().toString();

	public ScriptEvent() {
	}

	public ScriptEvent(boolean blockUserInput, ScriptTrigger scriptTrigger) {
		super();
		this.blockUserInput = blockUserInput;
		this.scriptTrigger = scriptTrigger;
	}

	public abstract boolean checkIfCompleted();

	public abstract void click();

	public abstract void update(int delta);

	public abstract void draw();

	public abstract void postLoad();

	@Override
	public String toString() {
		return "" + this.getClass() + " - " + name;
	}
}
