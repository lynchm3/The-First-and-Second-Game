package com.marklynch.objects.inanimateobjects;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.utils.Consumable;
import com.marklynch.utils.ArrayList;

public class Food extends GameObject implements Consumable {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public float drawOffsetYInTree;

	public Food() {
		super();
		type = "Food";

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public Food makeCopy(Square square, Actor owner) {
		Food food = new Food();
		food.consumeEffects = consumeEffects;
		setInstances(food);
		super.setAttributesForCopy(food, square, owner);
		return food;
	}

	@Override
	public Effect[] getConsumeEffects() {
		return consumeEffects;
	}

}
