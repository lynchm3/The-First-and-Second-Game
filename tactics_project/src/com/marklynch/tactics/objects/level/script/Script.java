package com.marklynch.tactics.objects.level.script;

import java.util.Vector;

public class Script {

	public Vector<ScriptEvent> scriptEvents;
	public ScriptEvent activeScriptEvent;

	public Script(Vector<ScriptEvent> scriptEvents) {
		super();
		this.scriptEvents = scriptEvents;
	}

	public void activateScriptEvent(int turn, int activeFaction) {
		activeScriptEvent = null;
		for (ScriptEvent scriptEvent : this.scriptEvents) {
			if (scriptEvent.turn == turn
					&& scriptEvent.factionTurn == activeFaction) {
				activeScriptEvent = scriptEvent;
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
