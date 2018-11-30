package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actors.Actor;
import com.marklynch.ui.ActivityLog;

public class EffectPoison extends Effect {

	public EffectPoison(GameObject source, GameObject target, int totalTurns) {
		this.logString = " poisoned by ";
		this.effectName = "Posion";
		this.source = source;
		this.target = target;
		this.totalTurns = totalTurns;
		this.turnsRemaining = totalTurns;
		this.imageTexture = getGlobalImage("effect_poison.png", false);
		highLevelStats.put(HIGH_LEVEL_STATS.POISON_DAMAGE, new Stat(5));
	}

	public EffectPoison(int totalTurns) {
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
	public EffectPoison makeCopy(GameObject source, GameObject target) {
		return new EffectPoison(source, target, totalTurns);
	}

}
