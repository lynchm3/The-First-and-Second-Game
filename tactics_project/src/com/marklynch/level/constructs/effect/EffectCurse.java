package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.Color;

public class EffectCurse extends Effect {
	public EffectCurse() {

	}

	public EffectCurse(GameObject source, GameObject target, int totalTurns) {
		this.logString = " cursed by ";
		this.effectName = "Curse";
		this.source = source;
		this.target = target;
		this.totalTurns = totalTurns;
		this.turnsRemaining = totalTurns;
		this.imageTexture = getGlobalImage("effect_poison.png", false);
		highLevelStats.put(HIGH_LEVEL_STATS.POISON_DAMAGE, new Stat(HIGH_LEVEL_STATS.POISON_DAMAGE, 5));
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

	@Override
	public Color getColor() {
		return Color.MAGENTA;
	}

}
