package com.marklynch.objects.inanimateobjects;

import com.marklynch.utils.ArrayList;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.utils.Consumable;

public class Liquid extends GameObject implements Consumable {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);
	public float volume;
	public Effect[] touchEffects;
	public Effect[] consumeEffects;

	public Liquid() {
		super();
		canBePickedUp = false;

		fitsInInventory = false;

		attackable = false;
		type = "Liquid";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public Liquid makeCopy(Square square, Actor owner, float volume) {
		Liquid liquid = new Liquid();
		setInstances(liquid);
		super.setAttributesForCopy(liquid, square, owner);
		liquid.volume = volume;
		liquid.touchEffects = touchEffects;
		liquid.consumeEffects = consumeEffects;
		return liquid;
	}

	@Override
	public Effect[] getConsumeEffects() {
		return consumeEffects;
	}
}
