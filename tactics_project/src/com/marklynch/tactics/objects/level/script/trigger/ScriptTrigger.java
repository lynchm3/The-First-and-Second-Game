package com.marklynch.tactics.objects.level.script.trigger;


public abstract class ScriptTrigger {

	public boolean triggered = false;

	public abstract boolean checkTrigger();
}
