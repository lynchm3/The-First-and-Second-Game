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

	public Carcass makeCopy(String name, Square square, Actor owner, float weight) {
		Carcass meatChunk = new Carcass();
		super.setAttributesForCopy(meatChunk, square, owner);
		meatChunk.weight = weight;
		meatChunk.name = name;
		meatChunk.baseName = new String(meatChunk.name);
		if (meatChunk.inventory.size() == 0)
			meatChunk.name = this.baseName + " (empty)";
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
