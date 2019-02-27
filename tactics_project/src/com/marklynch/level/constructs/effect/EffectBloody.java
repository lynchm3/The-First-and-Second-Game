package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.objects.inanimateobjects.GameObject;

public class EffectBloody extends Effect {

	public EffectBloody() {
	}

	public EffectBloody(GameObject source, GameObject target, int totalTurns) {
		this.logString = " bloodied by ";
		this.effectName = "Bloody";
		this.source = source;
		this.target = target;
		this.totalTurns = totalTurns;
		this.turnsRemaining = totalTurns;
		this.imageTexture = getGlobalImage("effect_bloody.png", false);
	}

	public EffectBloody(int totalTurns) {
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
	public EffectBloody makeCopy(GameObject source, GameObject target) {
		return new EffectBloody(source, target, totalTurns);
	}

	public void onAdd() {
		target.removeBurningEffect();
	}

}
