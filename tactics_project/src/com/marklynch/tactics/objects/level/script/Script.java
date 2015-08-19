package com.marklynch.tactics.objects.level.script;

import java.util.Vector;

public class Script {

	public Vector<ScriptEvent> scriptEvents;
	public Vector<ScriptEvent> activeScriptEvents = new Vector<ScriptEvent>();

	public Script(Vector<ScriptEvent> scriptEvents) {
		super();
		this.scriptEvents = scriptEvents;
	}

	public void activateScriptEvent() {

		System.out.println("activateScriptEvent()");

		// activeScriptEvent = null;
		for (ScriptEvent scriptEvent : this.scriptEvents) {
			System.out.println("scriptEvent.scriptTrigger.triggered = "
					+ scriptEvent.scriptTrigger.triggered);
			if (scriptEvent.scriptTrigger != null
					&& scriptEvent.scriptTrigger.triggered == false
					&& scriptEvent.scriptTrigger.checkTrigger()) {
				this.activeScriptEvents.addElement(scriptEvent);
				scriptEvent.scriptTrigger.triggered = true;
			}
		}
	}

	public void click() {
		for (ScriptEvent activeScriptEvent : activeScriptEvents) {
			activeScriptEvent.click();
		}
	}

	public void update(int delta) {
		activateScriptEvent();

		Vector<ScriptEvent> completedScriptEvents = new Vector<ScriptEvent>();

		for (ScriptEvent activeScriptEvent : activeScriptEvents) {
			activeScriptEvent.update(delta);
			if (activeScriptEvent.checkIfCompleted()) {
				completedScriptEvents.add(activeScriptEvent);
			}
		}

		for (ScriptEvent completedScriptEvent : completedScriptEvents) {
			activeScriptEvents.remove(completedScriptEvent);
		}
	}

	public void draw() {
		for (ScriptEvent activeScriptEvent : activeScriptEvents) {
			activeScriptEvent.draw();
		}
	}

	public boolean checkIfBlocking() {
		for (ScriptEvent activeScriptEvent : activeScriptEvents) {
			if (activeScriptEvent.blockUserInput)
				return true;
		}
		return false;
	}

	public void postLoad() {
		for (ScriptEvent scriptEvent : scriptEvents) {
			scriptEvent.postLoad();
		}

	}
}
