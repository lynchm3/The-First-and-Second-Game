package com.marklynch.script.trigger;

public abstract class ScriptTrigger {

	public boolean triggered = false;
	public String name = "";

	public ScriptTrigger() {
		name = "ScriptTrigger";
	}

	public abstract boolean checkTrigger();

	public abstract void postLoad();

	public abstract ScriptTrigger makeCopy();
}
