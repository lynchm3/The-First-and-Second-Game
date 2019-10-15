package com.marklynch.objects.inanimateobjects;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.utils.UpdatableGameObject;
import com.marklynch.utils.CopyOnWriteArrayList;

public class Searchable extends GameObject implements UpdatableGameObject {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(
			GameObject.class);

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
		return inventory.gameObjects;
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

	@Override
	public void update() {

		if (this.squareGameObjectIsOn == null)
			return;

		if (this.touchEffects == null || this.touchEffects.length == 0)
			return;

		System.out.println("Touch Effects - " + this.name);

		for (GameObject gameObject : this.squareGameObjectIsOn.inventory.gameObjects) {
			if (gameObject == this)
				continue;

			for (Effect effect : this.touchEffects) {
				gameObject.addEffect(effect.makeCopy(this, gameObject));
			}
		}
	}

}
