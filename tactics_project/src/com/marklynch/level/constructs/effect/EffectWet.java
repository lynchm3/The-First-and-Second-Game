package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.objects.GameObject;
import com.marklynch.objects.tools.FlammableLightSource;

public class EffectWet extends Effect {

	public EffectWet(GameObject source, GameObject target, int totalTurns) {
		this.logString = " wet by ";
		this.effectName = "Wet";
		this.source = source;
		this.target = target;
		this.totalTurns = totalTurns;
		this.turnsRemaining = totalTurns;
		this.imageTexture = getGlobalImage("effect_wet.png", false);
	}

	public EffectWet(int totalTurns) {
		this(null, null, totalTurns);
	}

	@Override
	public void activate() {
		if (target instanceof FlammableLightSource) {
			((FlammableLightSource) target).setLighting(false);
		}
		turnsRemaining--;
	}

	@Override
	public EffectWet makeCopy(GameObject source, GameObject target) {
		return new EffectWet(source, target, totalTurns);
	}

}
