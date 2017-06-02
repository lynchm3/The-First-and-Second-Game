package com.marklynch.level.constructs.effect;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.ui.ActivityLog;

public class EffectPoison extends Effect {

	public EffectPoison(GameObject source, GameObject target, int totalTurns) {
		this.logString = " poisoned by ";
		this.effectName = "Posion";
		this.source = source;
		this.target = target;
		this.totalTurns = totalTurns;
		this.turnsRemaining = totalTurns;
	}

	public EffectPoison(int totalTurns) {
		this(null, null, totalTurns);
	}

	@Override
	public void activate() {
		float damage = 10;
		target.remainingHealth -= damage;
		Game.level.logOnScreen(new ActivityLog(new Object[] { target, " lost " + damage + " HP to ", this }));
		target.attacked(this);
		turnsRemaining--;
	}

	@Override
	public EffectPoison makeCopy(GameObject source, GameObject target) {
		return new EffectPoison(source, target, totalTurns);
	}

}
