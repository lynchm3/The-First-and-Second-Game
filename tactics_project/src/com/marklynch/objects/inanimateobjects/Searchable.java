package com.marklynch.objects.inanimateobjects;

import com.marklynch.utils.CopyOnWriteArrayList;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;

public class Searchable extends GameObject {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);

	public Effect[] effectsFromInteracting;

	public Searchable() {
		super();
		canBePickedUp = false;

		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = true;

		persistsWhenCantBeSeen = true;
		attackable = false;
		type = "Searchable";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public CopyOnWriteArrayList<GameObject> search() {
		return (CopyOnWriteArrayList<GameObject>) inventory.gameObjects;
	}

	@Override
	public Searchable makeCopy(Square square, Actor owner) {
		Searchable searchable = new Searchable();
		setInstances(searchable);
		super.setAttributesForCopy(searchable, square, owner);
		searchable.effectsFromInteracting = effectsFromInteracting;
		return searchable;
	}

	public void setAttributesForCopy(Searchable searchable, Square square, Actor owner) {
		super.setAttributesForCopy(searchable, square, owner);
		searchable.effectsFromInteracting = effectsFromInteracting;
	}

}
