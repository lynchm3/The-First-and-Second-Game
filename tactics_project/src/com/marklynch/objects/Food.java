package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Food extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

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

}
