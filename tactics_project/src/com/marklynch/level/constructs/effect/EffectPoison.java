package com.marklynch.level.constructs.effect;

import com.marklynch.objects.GameObject;

public class EffectPoison extends Effect {

	public EffectPoison(GameObject source, GameObject target, int totalTurns) {
		this.source = source;
		this.target = target;
		this.totalTurns = totalTurns;
		this.turnsRemaining = totalTurns;
	}

	public EffectPoison(int totalTurns) {
		this(null, null, totalTurns);
	}

	@Override
	public void update() {
		target.remainingHealth--;
		turnsRemaining--;
	}

	@Override
	public EffectPoison makeCopy(GameObject source, GameObject target) {
		return new EffectPoison(source, target, totalTurns);
	}

}
