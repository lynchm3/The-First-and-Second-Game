package com.marklynch.tactics.objects.level.script.trigger;

import com.marklynch.tactics.objects.level.Level;
import com.marklynch.tactics.objects.level.Square;
import com.marklynch.tactics.objects.unit.Actor;

public class ScriptTriggerSpecficActorOccupiesSquare extends ScriptTrigger {

	Actor actor;
	Square square;

	public ScriptTriggerSpecficActorOccupiesSquare(Level level, Actor actor,
			Square square) {
		super(level);
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
