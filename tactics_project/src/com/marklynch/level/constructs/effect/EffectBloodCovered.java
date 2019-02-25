package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.Game;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.tools.FlammableLightSource;
import com.marklynch.ui.ActivityLog;

public class EffectBloodCovered extends Effect {

	public EffectBloodCovered() {
	}

	public EffectBloodCovered(GameObject source, GameObject target, int totalTurns) {
		this.logString = " wet by ";
		this.effectName = "Wet";
		this.source = source;
		this.target = target;
		this.totalTurns = totalTurns;
		this.turnsRemaining = totalTurns;
		this.imageTexture = getGlobalImage("effect_wet.png", false);
	}

	public EffectBloodCovered(int totalTurns) {
		this(null, null, totalTurns);
	}

	@Override
	public void activate() {
		if (target instanceof FlammableLightSource) {
			((FlammableLightSource) target).setLighting(false);

			if (Game.level.shouldLog(this)) {
				Game.level.logOnScreen(new ActivityLog(new Object[] { target, " was doused" }));
			}
		}
		turnsRemaining--;
	}

	@Override
	public EffectBloodCovered makeCopy(GameObject source, GameObject target) {
		return new EffectBloodCovered(source, target, totalTurns);
	}

	public void onAdd() {
		target.removeBurningEffect();
	}

}
