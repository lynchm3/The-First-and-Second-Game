package com.marklynch.tactics.objects.level.script;

public class ScriptEventInlineSpeech extends ScriptEvent {

	public InlineSpeech inlineSpeech;

	public ScriptEventInlineSpeech(int turn, int factionTurn,
			boolean blockUserInput, InlineSpeech inlineSpeech) {
		super(turn, factionTurn, blockUserInput);
		scriptType = ScriptEvent.SCRIPT_TYPE.DIALOG;
		this.inlineSpeech = inlineSpeech;
	}

	@Override
	public boolean checkIfCompleted() {
		return inlineSpeech.checkIfCompleted();
	}

	@Override
	public void click() {
		inlineSpeech.click();
	}

	@Override
	public void update(int delta) {
		inlineSpeech.update(delta);
	}

	@Override
	public void draw() {
		inlineSpeech.draw();

	}

}
