package com.marklynch.tactics.objects.level.script;

import java.util.Vector;

public class Script {

	public Vector<ScriptEvent> scriptEvents;
	public ScriptEvent activeScriptEvent;

	public Script(Vector<ScriptEvent> scriptEvents) {
		super();
		this.scriptEvents = scriptEvents;
	}

	public void activateScriptEvent() {
		// activeScriptEvent = null;
		for (ScriptEvent scriptEvent : this.scriptEvents) {
			if (scriptEvent.scriptTrigger.triggered == false
					&& scriptEvent.scriptTrigger.checkTrigger()) {
				this.activeScriptEvent = scriptEvent;
				scriptEvent.scriptTrigger.triggered = true;
			}
		}
	}

	public boolean checkIfCurrentScriptEventCompleted() {
		if (activeScriptEvent == null)
			return true;

		return activeScriptEvent.checkIfCompleted();
	}

	public void click() {
		if (activeScriptEvent != null)
			activeScriptEvent.click();
	}

	public void update(int delta) {
		activateScriptEvent();
		if (activeScriptEvent != null)
			activeScriptEvent.update(delta);
		if (checkIfCurrentScriptEventCompleted())
			activeScriptEvent = null;
	}

	public void draw() {
		if (activeScriptEvent != null)
			activeScriptEvent.draw();
	}
}
