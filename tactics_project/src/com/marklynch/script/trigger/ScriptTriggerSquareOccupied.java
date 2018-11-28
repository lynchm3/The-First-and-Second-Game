package com.marklynch.script.trigger;

import com.marklynch.level.squares.Square;

public class ScriptTriggerSquareOccupied extends ScriptTrigger {

	public transient Square square;
	public final static String[] editableAttributes = { "name", "square" };

	public ScriptTriggerSquareOccupied() {
		this.name = this.getClass().getSimpleName();
	}

	public ScriptTriggerSquareOccupied(Square square) {
		this.name = this.getClass().getSimpleName();
		this.square = square;
	}

	@Override
	public boolean checkTrigger() {

		if (square.inventory.size() != 0) {
			return true;
		}
		return false;
	}

	@Override
	public void postLoad() {
	}

	@Override
	public ScriptTrigger makeCopy() {
		return new ScriptTriggerSquareOccupied(square);
	}
}
