package com.marklynch.objects.inanimateobjects;

import com.marklynch.actions.Action;
import com.marklynch.actions.ActionHide;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.CopyOnWriteArrayList;

public class HidingPlace extends Searchable {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);

	public CopyOnWriteArrayList<GameObject> gameObjectsHiddenHere = new CopyOnWriteArrayList<GameObject>(GameObject.class);

	public HidingPlace() {
		super();
		canBePickedUp = false;

		fitsInInventory = false;

		canContainOtherObjects = true;

		this.orderingOnGound = 115;

		persistsWhenCantBeSeen = true;
		canShareSquare = true;
		attackable = true;
		type = "Hiding Place";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
		return new ActionHide(performer, this);
	}

	@Override
	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {
		return null;
	}

	@Override
	public CopyOnWriteArrayList<GameObject> search() {
		return (CopyOnWriteArrayList<GameObject>) inventory.gameObjects;
	}

	@Override
	public HidingPlace makeCopy(Square square, Actor owner) {

		HidingPlace hidingPlace = new HidingPlace();
		setInstances(hidingPlace);
		super.setAttributesForCopy(hidingPlace, square, owner);
		return hidingPlace;

	}

	@Override
	public boolean checkIfDestroyed(Object attacker, Action action) {
		boolean destroyed = super.checkIfDestroyed(attacker, action);
		if (destroyed) {

			if (gameObjectsHiddenHere.size() > 0) {
				// CopyOnWriteArrayList<Actor> actorsToRemove = (CopyOnWriteArrayList<Actor>)
				// gameObjectsHiddenHere;

				for (GameObject gameObjectHiddenHere : (CopyOnWriteArrayList<GameObject>) gameObjectsHiddenHere) {

					gameObjectHiddenHere.hiding = false;
					gameObjectHiddenHere.hidingPlace = null;
					this.gameObjectsHiddenHere.remove(gameObjectHiddenHere);
				}
				gameObjectsHiddenHere.clear();
			}
		}
		return destroyed;

	}

	@Override
	public void squareContentsChanged() {

		if (squareGameObjectIsOn == null)
			return;

		for (GameObject gameObjectHiddenHere : (CopyOnWriteArrayList<GameObject>) gameObjectsHiddenHere) {
			if (!squareGameObjectIsOn.inventory.contains(gameObjectHiddenHere)) {

				gameObjectHiddenHere.hiding = false;
				gameObjectHiddenHere.hidingPlace = null;
				this.gameObjectsHiddenHere.remove(gameObjectHiddenHere);
			}
		}

		for (GameObject gameObjectInInventory : squareGameObjectIsOn.inventory.gameObjects) {
			if (!this.gameObjectsHiddenHere.contains(gameObjectInInventory)) {

				if (gameObjectInInventory != this) {
					gameObjectsHiddenHere.add(gameObjectInInventory);
					gameObjectInInventory.hidingPlace = this;
					gameObjectInInventory.hiding = true;
				}

				// if (Game.level.shouldLog(performer))
				// Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " hid in ",
				// object }));
			}
		}
	}

}
