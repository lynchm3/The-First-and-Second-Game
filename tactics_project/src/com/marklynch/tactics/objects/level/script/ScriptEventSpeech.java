package com.marklynch.tactics.objects.level.script;

import java.util.Vector;

public class ScriptEventSpeech extends ScriptEvent {

	public Vector<SpeechPart> speechParts;
	public int speechIndex = 0;

	public ScriptEventSpeech(int turn, int factionTurn, boolean blockUserInput,
			Vector<SpeechPart> speechParts) {
		super(turn, factionTurn, blockUserInput);
		scriptType = ScriptEvent.SCRIPT_TYPE.DIALOG;
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

}
