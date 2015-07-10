package com.marklynch.tactics.objects.level.script;

public class ScriptEventSpeech extends ScriptEvent {

	public Speech speech;

	public ScriptEventSpeech(int turn, int factionTurn, boolean blockUserInput,
			Speech dialog) {
		super(turn, factionTurn, blockUserInput);
		scriptType = ScriptEvent.SCRIPT_TYPE.DIALOG;
		this.speech = dialog;
	}

	@Override
	public boolean checkIfCompleted() {
		return speech.checkIfCompleted();
	}

	@Override
	public void click() {
		speech.click();
	}

	@Override
	public void update(int delta) {

	}

	@Override
	public void draw() {
		speech.draw();

	}

}
