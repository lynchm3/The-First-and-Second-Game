package com.marklynch.objects.inanimateobjects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.templates.Templates;
import com.marklynch.utils.ArrayList;

public class WaterSource extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);
	public Liquid liquid;

	public WaterSource() {
		super();

		canBePickedUp = false;

		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = true;

		persistsWhenCantBeSeen = true;
		type = "Water Source";
		liquid = Templates.WATER;

		orderingOnGound = 101;

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public ArrayList<GameObject> search() {
		return (ArrayList<GameObject>) inventory.gameObjects.clone();
	}

	@Override
	public WaterSource makeCopy(Square square, Actor owner) {
		WaterSource waterSource = new WaterSource();
		setInstances(waterSource);
		super.setAttributesForCopy(waterSource, square, owner);
		waterSource.liquid = liquid;
		return waterSource;
	}

}
