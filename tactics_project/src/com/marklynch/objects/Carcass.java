package com.marklynch.objects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionSkin;
import com.marklynch.objects.units.Actor;

public class Carcass extends GameObject {
	protected String baseName;

	public Carcass() {
		super();

		canBePickedUp = true;
		showInventory = false;
		fitsInInventory = false;
		canShareSquare = true;
		canContainOtherObjects = true;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = false;
		attackable = true;
	}

	@Override
	public Carcass makeCopy(Square square, Actor owner) {
		Carcass meatChunk = new Carcass();
		super.setAttributesForCopy(meatChunk, square, owner);
		meatChunk.baseName = new String(meatChunk.name);
		if (this.inventory.size() == 0)
			this.name = meatChunk.baseName + " (empty)";
		return meatChunk;
	}

	@Override
	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {
		return new ActionSkin(performer, this);
	}

	@Override
	public void inventoryChanged() {
		if (this.inventory.size() == 0)
			this.name = baseName + " (empty)";
		else
			this.name = baseName;
	}

}
