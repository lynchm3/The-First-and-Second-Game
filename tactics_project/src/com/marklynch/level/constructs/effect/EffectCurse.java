package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class EffectCurse extends Effect {

	public EffectCurse(GameObject source, GameObject target, int totalTurns) {
		this.logString = " cursed by ";
		this.effectName = "Curse";
		this.source = source;
		this.target = target;
		this.totalTurns = totalTurns;
		this.turnsRemaining = totalTurns;
		this.imageTexture = getGlobalImage("effect_poison.png", false);
		this.poisonDamage = 5;
	}

	public EffectCurse(int totalTurns) {
		this(null, null, totalTurns);
	}

	@Override
	public void activate() {
		if (target instanceof Actor) {
			// float damage = 10 - (10 * (target.getEffectivePoisonResistance()
			// / 100f));
			float damage = target.changeHealth(this, null, this);
			if (Game.level.shouldLog(target))
				Game.level.logOnScreen(new ActivityLog(new Object[] { target, " lost " + damage + " HP to ", this }));
			// target.attackedBy(this, null);
		}
		turnsRemaining--;
	}

	@Override
	public EffectCurse makeCopy(GameObject source, GameObject target) {
		return new EffectCurse(source, target, totalTurns);
	}

}
