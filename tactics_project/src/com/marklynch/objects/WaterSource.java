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

	}

	public ArrayList<GameObject> search() {
		return (ArrayList<GameObject>) inventory.gameObjects.clone();
	}

	@Override
	public WaterSource makeCopy(Square square, Actor owner) {
		WaterSource waterSource = new WaterSource();
		instances.add(waterSource);
		super.setAttributesForCopy(waterSource, square, owner);
		waterSource.effectsFromInteracting = effectsFromInteracting;
		return waterSource;
	}

}
