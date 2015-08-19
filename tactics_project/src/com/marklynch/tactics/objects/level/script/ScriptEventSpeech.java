package com.marklynch.tactics.objects.level.script;

import java.util.Vector;

import com.marklynch.tactics.objects.level.script.trigger.ScriptTrigger;

public class ScriptEventSpeech extends ScriptEvent {

	public Vector<SpeechPart> speechParts;
	public int speechIndex = 0;

	public ScriptEventSpeech(boolean blockUserInput,
			Vector<SpeechPart> speechParts, ScriptTrigger scriptTrigger) {
		super(blockUserInput, scriptTrigger);
		this.speechParts = speechParts;
	}

	@Override
	public boolean checkIfCompleted() {
		if (speechIndex >= speechParts.size())
			return true;
		return false;
	}

	@Override
	public void click() {
		speechIndex++;
	}

	@Override
	public void update(int delta) {

	}

	@Override
	public void draw() {
		if (speechIndex < speechParts.size()) {
			speechParts.get(speechIndex).draw();
		}

	}

	@Override
	public void postLoad() {
		super.postLoad();
		for (SpeechPart speechPart : speechParts)
			speechPart.postLoad();
	}
}
