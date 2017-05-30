package com.marklynch.level.constructs.effect;

import com.marklynch.objects.GameObject;

public class Effect {

	GameObject source;
	GameObject target;
	int totalTurns;
	int turnsRemaining;

	public Effect(GameObject source, GameObject target, int totalTurns, int turnsRemaining) {
		super();
		this.source = source;
		this.target = target;
		this.totalTurns = totalTurns;
		this.turnsRemaining = turnsRemaining;
	}

	public void update() {

	}

}
