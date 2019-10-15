package com.marklynch.script;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.marklynch.script.ScriptEventSpeech.SpeechPart;
import com.marklynch.script.trigger.ScriptTrigger;

public class Script {

	public CopyOnWriteArrayList<ScriptEvent> scriptEvents = new CopyOnWriteArrayList<ScriptEvent>();
	public CopyOnWriteArrayList<ScriptEvent> activeScriptEvents = new CopyOnWriteArrayList<ScriptEvent>();
	public CopyOnWriteArrayList<ScriptTrigger> scriptTriggers = new CopyOnWriteArrayList<ScriptTrigger>();
	public CopyOnWriteArrayList<SpeechPart> speechParts = new CopyOnWriteArrayList<SpeechPart>();

	public Script() {
		super();

		scriptEvents = new CopyOnWriteArrayList<ScriptEvent>();
		activeScriptEvents = new CopyOnWriteArrayList<ScriptEvent>();
		scriptTriggers = new CopyOnWriteArrayList<ScriptTrigger>();
		speechParts = new CopyOnWriteArrayList<SpeechPart>();

		// for (ScriptEvent scriptEvent : this.scriptEvents) {
		// scriptTriggers.add(scriptEvent.scriptTrigger.makeCopy());
		// if (scriptEvent instanceof ScriptEventSpeech) {
		// System.out
		// .println("scriptEvent instanceof ScriptEventSpeech");
		// System.out
		// .println("((ScriptEventSpeech) scriptEvent).speechParts.size() = "
		// + ((ScriptEventSpeech) scriptEvent).speechParts
		// .size());
		//
		// for (SpeechPart speechPart : ((ScriptEventSpeech)
		// scriptEvent).speechParts) {
		// this.speechParts.add(speechPart.makeCopy());
		// }
		// }
		// if (scriptEvent instanceof ScriptEventInlineSpeech) {
		// for (SpeechPart speechPart : ((ScriptEventInlineSpeech)
		// scriptEvent).speechParts) {
		// this.speechParts.add(speechPart.makeCopy());
		// }
		// }
		// }
	}

	public void activateScriptEvent() {

		// activeScriptEvent = null;
		for (ScriptEvent scriptEvent : this.scriptEvents) {
			if (scriptEvent.scriptTrigger != null && scriptEvent.scriptTrigger.triggered == false
					&& scriptEvent.scriptTrigger.checkTrigger()) {
				this.activeScriptEvents.add(scriptEvent);
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

		CopyOnWriteArrayList<ScriptEvent> completedScriptEvents = new CopyOnWriteArrayList<ScriptEvent>();

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

	public ScriptEvent findScriptEventFromGUID(String guid) {
		for (ScriptEvent scriptEvent : scriptEvents) {
			if (scriptEvent.guid.equals(guid)) {
				return scriptEvent;
			}
		}
		return null;
	}
}
