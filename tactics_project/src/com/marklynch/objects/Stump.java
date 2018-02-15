package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionChop;
import com.marklynch.objects.units.Actor;

public class Stump extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Stump() {
		super();

		// BIG STUMP
		canBePickedUp = false;

		fitsInInventory = false;

		persistsWhenCantBeSeen = true;

	}

	@Override
	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {
		return new ActionChop(performer, this);
	}

	@Override
	public Stump makeCopy(Square square, Actor owner) {
		Stump stump = new Stump();
		instances.add(stump);
		super.setAttributesForCopy(stump, square, owner);
		return stump;
	}

}
