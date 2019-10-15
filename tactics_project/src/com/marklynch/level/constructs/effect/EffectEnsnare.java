package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.Color;

public class EffectEnsnare extends Effect {

	// Template id
	public int templateId;

	public EffectEnsnare() {
	}

	public EffectEnsnare(GameObject source, GameObject target, int totalTurns) {
		this.logString = " ensnared by ";
		this.effectName = "Ensnare";
		this.source = source;
		this.target = target;
		this.totalTurns = totalTurns;
		this.turnsRemaining = totalTurns;
		this.imageTexture = getGlobalImage("spider_web_white.png", false);
	}

	public EffectEnsnare(int totalTurns) {
		this(null, null, totalTurns);
	}

	@Override
	public void activate() {
		if (!(target instanceof Actor))
			return;
		turnsRemaining--;
	}

	@Override
	public EffectEnsnare makeCopy(GameObject source, GameObject target) {
		return new EffectEnsnare(source, target, totalTurns);
	}

	public final static Color color = new Color(0.7f, 0.7f, 0.7f);

	@Override
	public Color getColor() {
		return color;
	}
}
