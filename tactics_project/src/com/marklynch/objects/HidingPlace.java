package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionHide;
import com.marklynch.objects.actors.Actor;

public class HidingPlace extends Searchable implements UpdatesWhenSquareContentsChange {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public ArrayList<GameObject> gameObjectsHiddenHere = new ArrayList<GameObject>();

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
	public ArrayList<GameObject> search() {
		return (ArrayList<GameObject>) inventory.gameObjects.clone();
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
				// ArrayList<Actor> actorsToRemove = (ArrayList<Actor>)
				// gameObjectsHiddenHere.clone();

				for (GameObject gameObjectHiddenHere : (ArrayList<GameObject>) gameObjectsHiddenHere.clone()) {

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

		for (GameObject gameObjectHiddenHere : (ArrayList<GameObject>) gameObjectsHiddenHere.clone()) {
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
