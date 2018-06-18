package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class VoidHole extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Effect[] effectsFromInteracting;

	public VoidHole() {
		super();

		// DROP HOLE
		canBePickedUp = false;
		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = false;
		persistsWhenCantBeSeen = true;
		attackable = false;
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
	public VoidHole makeCopy(Square square, Actor owner) {
		VoidHole searchable = new VoidHole();
		setInstances(searchable);
		super.setAttributesForCopy(searchable, square, owner);
		searchable.effectsFromInteracting = effectsFromInteracting;
		return searchable;
	}

	public void setAttributesForCopy(VoidHole searchable, Square square, Actor owner) {
		super.setAttributesForCopy(searchable, square, owner);
		searchable.effectsFromInteracting = effectsFromInteracting;
	}

}
