package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.Game;

public class ScriptTriggerTurnStart extends ScriptTrigger {

	public final static String[] editableAttributes = { "name", "turn", "factionIndex" };

	public int turn;
	public int factionIndex;

	public ScriptTriggerTurnStart() {
		this.name = this.getClass().getSimpleName();
	}

	public ScriptTriggerTurnStart(int turn, int factionIndex) {
		this.name = this.getClass().getSimpleName();
		this.turn = turn;
		this.factionIndex = factionIndex;
	}

	@Override
	public boolean checkTrigger() {
		if (Game.level.turn == turn && Game.level.currentFactionMovingIndex == factionIndex) {
			return true;
		}

		return false;
	}

	@Override
	public ScriptTrigger makeCopy() {
		return new ScriptTriggerTurnStart(turn, factionIndex);
	}

	@Override
	public void postLoad() {

	}
}
