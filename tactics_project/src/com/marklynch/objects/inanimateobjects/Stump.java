package com.marklynch.objects.inanimateobjects;

import com.marklynch.utils.ArrayList;

import com.marklynch.actions.Action;
import com.marklynch.actions.ActionChopping;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;

public class Stump extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public Stump() {
		super();

		// BIG STUMP
		canBePickedUp = false;

		fitsInInventory = false;

		persistsWhenCantBeSeen = true;

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {
		return new ActionChopping(performer, this);
	}

	@Override
	public Stump makeCopy(Square square, Actor owner) {
		Stump stump = new Stump();
		setInstances(stump);
		super.setAttributesForCopy(stump, square, owner);
		return stump;
	}

}
