package com.marklynch.objects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Corpse extends GameObject {
	protected String baseName;

	public Corpse() {
		super();
		baseName = new String(name);
		if (this.inventory.size() == 0)
			this.name = baseName + " (empty)";

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
	public Corpse makeCopy(Square square, Actor owner) {
		Corpse meatChunk = new Corpse();
		super.setAttributesForCopy(meatChunk, square, owner);
		meatChunk.baseName = new String(meatChunk.name);
		if (this.inventory.size() == 0)
			this.name = meatChunk.baseName + " (empty)";
		return meatChunk;
	}

	@Override
	public void inventoryChanged() {
		if (this.inventory.size() == 0)
			this.name = baseName + " (empty)";
		else
			this.name = baseName;
	}

}
