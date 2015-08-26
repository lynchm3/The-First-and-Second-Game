package com.marklynch.tactics.objects.level.script;

import java.util.ArrayList;
import java.util.Vector;

import com.marklynch.tactics.objects.level.script.ScriptEventSpeech.SpeechPart;
import com.marklynch.tactics.objects.level.script.trigger.ScriptTrigger;

public class Script {

	public ArrayList<ScriptEvent> scriptEvents;
	public Vector<ScriptEvent> activeScriptEvents = new Vector<ScriptEvent>();
	public ArrayList<ScriptTrigger> scriptTriggers;
	public ArrayList<SpeechPart> speechParts = new ArrayList<SpeechPart>();

	public Script(ArrayList<ScriptEvent> scriptEvents) {
		super();
		this.scriptEvents = scriptEvents;
		this.scriptTriggers = new ArrayList<ScriptTrigger>();
		System.out.println("this.scriptEvents.size() = "
				+ this.scriptEvents.size());

		// for (ScriptEvent scriptEvent : this.scriptEvents) {
		// scriptTriggers.add(scriptEvent.scriptTrigger.makeCopy());
		// if (scriptEvent instanceof ScriptEventSpeech) {
		// System.out.println("scriptEvent instanceof ScriptEventSpeech");
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

	public ScriptEvent findScriptEventFromGUID(String guid) {
		for (ScriptEvent scriptEvent : scriptEvents) {
			if (scriptEvent.guid.equals(guid)) {
				return scriptEvent;
			}
		}
		return null;
	}
}
