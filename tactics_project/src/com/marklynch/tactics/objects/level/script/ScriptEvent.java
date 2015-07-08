package com.marklynch.tactics.objects.level.script;

public abstract class ScriptEvent {

	public int turn;
	public int factionTurn;
	public boolean blockUserInput;
	public SCRIPT_TYPE scriptType;

	public enum SCRIPT_TYPE {
		DIALOG,
		DIALOG_IN_FIELD,
		NEW_ACTORS,
		NEW_FACTION,
		OBJECTIVE_CHANGE,
		MOVE_CHARACTER
	};

	public ScriptEvent(int turn, int factionTurn, boolean blockUserInput) {
		super();
		this.turn = turn;
		this.factionTurn = factionTurn;
		this.blockUserInput = blockUserInput;
	}

	public abstract boolean checkIfCompleted();

	public abstract void click();

	public abstract void update(int delta);

	public abstract void draw();
}
