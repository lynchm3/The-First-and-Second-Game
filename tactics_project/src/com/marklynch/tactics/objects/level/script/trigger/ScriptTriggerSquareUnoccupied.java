package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.level.Square;

public class ScriptTriggerSquareUnoccupied extends ScriptTrigger {

	Square square;

	public ScriptTriggerSquareUnoccupied(Level level, Square square) {
		super(level);
		this.square = square;
	}

	@Override
	public boolean checkTrigger() {
		if (square.gameObject == null) {
			return true;
		}
		return false;
	}
}
