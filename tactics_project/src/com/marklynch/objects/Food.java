package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Food extends GameObject implements Consumable {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public float drawOffsetYInTree;
	public Effect[] consumeEffects;

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
	public void draw1() {

	}

	@Override
	public void draw2() {
		super.draw1();
		super.draw2();
	}

	@Override
	public Effect[] getConsumeEffects() {
		return consumeEffects;
	}

}
