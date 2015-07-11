package com.marklynch.tactics.objects.level.script;

import com.marklynch.tactics.objects.level.Level;

public class ScriptTriggerTurnStart extends ScriptTrigger {

	int turn;
	int factionIndex;

	public ScriptTriggerTurnStart(Level level, int turn, int factionIndex) {
		super(level);
		this.turn = turn;
		this.factionIndex = factionIndex;
	}

	@Override
	public boolean checkTrigger() {
		if (level.turn == turn
				&& level.currentFactionMovingIndex == factionIndex) {
			return true;
		}

		return false;
	}
}
