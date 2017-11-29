package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Searchable extends GameObject {

	public Effect[] effectsFromInteracting;

	public Searchable() {
		super();

		// DROP HOLE
		canBePickedUp = false;
		showInventory = false;
		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = true;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = true;
		attackable = false;
	}

	public ArrayList<GameObject> search() {
		return (ArrayList<GameObject>) inventory.gameObjects.clone();
	}

	@Override
	public Searchable makeCopy(Square square, Actor owner) {
		Searchable searchable = new Searchable();
		super.setAttributesForCopy(searchable, square, owner);
		searchable.effectsFromInteracting = effectsFromInteracting;
		return searchable;
	}

	public void setAttributesForCopy(Searchable searchable, Square square, Actor owner) {
		super.setAttributesForCopy(searchable, square, owner);
		searchable.effectsFromInteracting = effectsFromInteracting;
	}

}
