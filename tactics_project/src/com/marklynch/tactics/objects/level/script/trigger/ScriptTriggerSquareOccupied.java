package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.tactics.objects.level.Square;

public class ScriptTriggerSquareOccupied extends ScriptTrigger {

	Square square;

	public ScriptTriggerSquareOccupied() {
		this.name = this.getClass().getSimpleName();
	}

	public ScriptTriggerSquareOccupied(Square square) {
		this.square = square;
	}

	@Override
	public boolean checkTrigger() {
		if (square.gameObject != null) {
			return true;
		}
		return false;
	}
}
