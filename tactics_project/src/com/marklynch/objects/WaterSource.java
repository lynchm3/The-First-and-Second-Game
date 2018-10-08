package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class WaterSource extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Effect[] effectsFromInteracting;

	public WaterSource() {
		super();

		// WELL
		// These are basically settings for a WELL at the moment...
		canBePickedUp = false;

		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = true;

		persistsWhenCantBeSeen = true;
		type = "Water Source";

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
		waterSource.effectsFromInteracting = effectsFromInteracting;
		return waterSource;
	}

}
