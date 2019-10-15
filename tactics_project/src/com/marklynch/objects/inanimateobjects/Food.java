package com.marklynch.objects.inanimateobjects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.CopyOnWriteArrayList;

public class Food extends GameObject {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);

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

}
