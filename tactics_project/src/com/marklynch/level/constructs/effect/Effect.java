package com.marklynch.level.constructs.effect;

import com.marklynch.objects.GameObject;

public abstract class Effect {

	public GameObject source;
	public GameObject target;
	public int totalTurns;
	public int turnsRemaining;

	public abstract void update();

	public abstract Effect makeCopy(GameObject source, GameObject target);
}
