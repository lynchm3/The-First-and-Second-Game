package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.unit.Actor;

public class ScriptTriggerSpecficActorOccupiesSquare extends ScriptTrigger {

	Actor actor;
	Square square;

	public ScriptTriggerSpecficActorOccupiesSquare() {
		this.name = this.getClass().getSimpleName();
	}

	public ScriptTriggerSpecficActorOccupiesSquare(Actor actor, Square square) {
		this.actor = actor;
		this.square = square;
	}

	@Override
	public boolean checkTrigger() {
		if (square.gameObject == actor)
			return true;
		return false;
	}

}
