package com.marklynch.tactics.objects.level.script;

import java.util.ArrayList;
import java.util.Vector;

import com.marklynch.tactics.objects.level.script.trigger.ScriptTrigger;

public class Script {

	public Vector<ScriptEvent> scriptEvents;
	public Vector<ScriptEvent> activeScriptEvents = new Vector<ScriptEvent>();
	public ArrayList<ScriptTrigger> scriptTriggers;

	public Script(Vector<ScriptEvent> scriptEvents) {
		super();
		this.scriptEvents = scriptEvents;
		this.scriptTriggers = new ArrayList<ScriptTrigger>();
		for (ScriptEvent scriptEvent : scriptEvents) {
			scriptTriggers.add(scriptEvent.scriptTrigger);
		}
	}

	public void activateScriptEvent() {

		// activeScriptEvent = null;
		for (ScriptEvent scriptEvent : this.scriptEvents) {
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
		for (ScriptTrigger scriptTrigger : scriptTriggers) {
			scriptTrigger.postLoad();
		}

	}
}
