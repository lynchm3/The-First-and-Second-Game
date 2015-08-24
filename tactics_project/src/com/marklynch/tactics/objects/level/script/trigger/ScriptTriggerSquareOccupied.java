package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.Game;
import com.marklynch.tactics.objects.level.Square;

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
		if (square.gameObject != null) {
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
