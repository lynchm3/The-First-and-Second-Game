package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.objects.inanimateobjects.GameObject;

public class EffectOily extends Effect {

	public EffectOily() {
	}

	public EffectOily(GameObject source, GameObject target, int totalTurns) {
		this.logString = " made oily by ";
		this.effectName = "Oily";
		this.source = source;
		this.target = target;
		this.totalTurns = totalTurns;
		this.turnsRemaining = totalTurns;
		this.imageTexture = getGlobalImage("effect_oily.png", false);
	}

	public EffectOily(int totalTurns) {
		this(null, null, totalTurns);
	}

	@Override
	public void activate() {
//		if (target instanceof FlammableLightSource) {
//			((FlammableLightSource) target).setLighting(false);
//
//			if (Game.level.shouldLog(this)) {
//				Game.level.logOnScreen(new ActivityLog(new Object[] { target, " was doused" }));
//			}
//		}
		turnsRemaining--;
	}

	@Override
	public EffectOily makeCopy(GameObject source, GameObject target) {
		return new EffectOily(source, target, totalTurns);
	}

	public void onAdd() {
		target.removeBurningEffect();
	}

}
