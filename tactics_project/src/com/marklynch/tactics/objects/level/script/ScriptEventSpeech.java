package com.marklynch.tactics.objects.level.script;

public class ScriptEventSpeech extends ScriptEvent {

	public Speech dialog;

	public ScriptEventSpeech(int turn, int factionTurn, boolean blockUserInput,
			Speech dialog) {
		super(turn, factionTurn, blockUserInput);
		scriptType = ScriptEvent.SCRIPT_TYPE.DIALOG;
		this.dialog = dialog;
	}

	@Override
	public boolean checkIfCompleted() {
		return dialog.checkIfCompleted();
	}

	@Override
	public void click() {
		dialog.click();
	}

	@Override
	public void update(int delta) {

	}

	@Override
	public void draw() {
		dialog.draw();

	}

}
