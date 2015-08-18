package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.tactics.objects.level.Level;

public class ScriptTriggerTurnStart extends ScriptTrigger {

	int turn;
	int factionIndex;
	transient Level level;

	public ScriptTriggerTurnStart(Level level, int turn, int factionIndex) {
		this.turn = turn;
		this.factionIndex = factionIndex;
		this.level = level;
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
