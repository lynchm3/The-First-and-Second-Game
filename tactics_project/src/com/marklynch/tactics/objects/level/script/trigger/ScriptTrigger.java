package com.marklynch.tactics.objects.level.script.trigger;

public class ScriptTrigger {

	public boolean triggered = false;
	public String name = "";

	public ScriptTrigger() {
		name = "ScriptTrigger";
	}

	public boolean checkTrigger() {
		return false;
	}

	public void postLoad() {

	}

	public ScriptTrigger makeCopy() {
		return null;
	}
}
