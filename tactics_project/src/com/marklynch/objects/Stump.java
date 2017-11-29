package com.marklynch.objects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionChop;
import com.marklynch.objects.units.Actor;

public class Stump extends GameObject {

	public Stump() {
		super();

		// BIG STUMP
		canBePickedUp = false;
		showInventory = false;
		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = false;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = true;
		attackable = true;
	}

	@Override
	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {
		return new ActionChop(performer, this);
	}

	@Override
	public Stump makeCopy(Square square, Actor owner) {
		Stump stump = new Stump();
		super.setAttributesForCopy(stump, square, owner);
		return stump;
	}

}
