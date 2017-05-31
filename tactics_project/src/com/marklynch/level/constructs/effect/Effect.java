package com.marklynch.level.constructs.effect;

import com.marklynch.objects.GameObject;

public abstract class Effect {

	public String logString;
	public String effectName;
	public GameObject source;
	public GameObject target;
	public int totalTurns;
	public int turnsRemaining;

	public abstract void activate();

	public abstract Effect makeCopy(GameObject source, GameObject target);
}
