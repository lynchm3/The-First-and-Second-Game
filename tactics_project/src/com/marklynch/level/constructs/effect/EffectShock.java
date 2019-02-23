package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.ui.ActivityLog;

public class EffectShock extends Effect {

	public EffectShock() {
	}

	public EffectShock(GameObject source, GameObject target, int totalTurns) {
		this.logString = " shocked by ";
		this.effectName = "Shock";
		this.source = source;
		this.target = target;
		this.totalTurns = totalTurns;
		this.turnsRemaining = totalTurns;
		this.imageTexture = getGlobalImage("spark.png", false);
		highLevelStats.put(HIGH_LEVEL_STATS.ELECTRICAL_DAMAGE, new Stat(HIGH_LEVEL_STATS.ELECTRICAL_DAMAGE, 5));
	}

	@Override
	public void activate() {
		float damage = target.changeHealth(this, null, this);
		if (Game.level.shouldLog(target))
			Game.level.logOnScreen(new ActivityLog(new Object[] { target, " lost " + damage + " HP to ", this }));

		turnsRemaining--;

	}

	@Override
	public EffectShock makeCopy(GameObject source, GameObject target) {
		return new EffectShock(source, target, totalTurns);
	}

}
