package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.level.constructs.Stat;
import com.marklynch.level.constructs.Stat.HIGH_LEVEL_STATS;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Liquid;
import com.marklynch.objects.templates.Templates;
import com.marklynch.ui.ActivityLog;

public class EffectBleed extends Effect {

	// Template id
	public int templateId;

	public EffectBleed() {
	}

	public EffectBleed(GameObject source, GameObject target, int totalTurns) {
		this.logString = " cut by ";
		this.effectName = "Bleed";
		this.source = source;
		this.target = target;
		this.totalTurns = totalTurns;
		this.turnsRemaining = totalTurns;
		this.imageTexture = getGlobalImage("effect_bleed.png", false);

		highLevelStats.put(HIGH_LEVEL_STATS.BLEED_DAMAGE, new Stat(HIGH_LEVEL_STATS.BLEED_DAMAGE, 5));
	}

	public EffectBleed(int totalTurns) {
		this(null, null, totalTurns);
	}

	@Override
	public void activate() {

		if (!(target instanceof Actor))
			return;
		float damage = target.changeHealth(this, null, this);
		if (Game.level.shouldLog(target))
			Game.level.logOnScreen(new ActivityLog(new Object[] { target, " lost " + damage + " HP to ", this }));
		Liquid blood = target.squareGameObjectIsOn.liquidSpread(Templates.BLOOD);
		turnsRemaining--;

	}

	@Override
	public EffectBleed makeCopy(GameObject source, GameObject target) {
		return new EffectBleed(source, target, totalTurns);
	}
}
