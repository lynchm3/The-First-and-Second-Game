package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Food extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public float drawOffsetYInTree;

	public Food() {
		super();

	}

	@Override
	public Food makeCopy(Square square, Actor owner) {
		Food food = new Food();
		instances.add(food);
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
