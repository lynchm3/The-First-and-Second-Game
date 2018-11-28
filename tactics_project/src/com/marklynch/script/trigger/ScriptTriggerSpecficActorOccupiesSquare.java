package com.marklynch.script.trigger;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class ScriptTriggerSpecficActorOccupiesSquare extends ScriptTrigger {

	public transient Actor actor;
	public transient Square square;
	public final static String[] editableAttributes = { "name", "actor", "square" };

	public ScriptTriggerSpecficActorOccupiesSquare() {
		this.name = this.getClass().getSimpleName();
	}

	public ScriptTriggerSpecficActorOccupiesSquare(Actor actor, Square square) {
		this.name = this.getClass().getSimpleName();
		this.actor = actor;
		this.square = square;
	}

	@Override
	public boolean checkTrigger() {
		if (square.inventory.contains(actor))
			return true;
		return false;
	}

	@Override
	public void postLoad() {
	}

	@Override
	public ScriptTrigger makeCopy() {
		return new ScriptTriggerSpecficActorOccupiesSquare(actor, square);
	}

}
