package com.marklynch.script.trigger;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;

public class ScriptTriggerSquareOccupied extends ScriptTrigger {

	public transient Square square;
	public final static String[] editableAttributes = { "name", "square" };
	public String squareGUID = null;

	public ScriptTriggerSquareOccupied() {
		this.name = this.getClass().getSimpleName();
	}

	public ScriptTriggerSquareOccupied(Square square) {
		this.name = this.getClass().getSimpleName();
		this.square = square;
		this.squareGUID = square.guid;
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
		square = Game.level.findSquareFromGUID(squareGUID);
	}

	@Override
	public ScriptTrigger makeCopy() {
		return new ScriptTriggerSquareOccupied(square);
	}
}
