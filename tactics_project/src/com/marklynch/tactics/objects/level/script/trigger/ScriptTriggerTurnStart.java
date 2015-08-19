package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.Game;

public class ScriptTriggerTurnStart extends ScriptTrigger {

	int turn;
	int factionIndex;

	public ScriptTriggerTurnStart(int turn, int factionIndex) {
		this.turn = turn;
		this.factionIndex = factionIndex;
	}

	@Override
	public boolean checkTrigger() {
		if (Game.level.turn == turn
				&& Game.level.currentFactionMovingIndex == factionIndex) {
			return true;
		}

		return false;
	}
}
