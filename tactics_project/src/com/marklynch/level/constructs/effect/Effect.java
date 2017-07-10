package com.marklynch.level.constructs.effect;

import static com.marklynch.utils.ResourceUtils.getGlobalImage;

import com.marklynch.objects.GameObject;

import mdesl.graphics.Texture;

public abstract class Effect {

	public String logString;
	public String effectName;
	public GameObject source;
	public GameObject target;
	public int totalTurns;
	public int turnsRemaining;
	public Texture image;

	public abstract void activate();

	public abstract Effect makeCopy(GameObject source, GameObject target);

	public abstract void draw2();

	public static void loadEffectImages() {
		getGlobalImage("effect_burn.png");
		getGlobalImage("effect_poison.png");
	}
}
