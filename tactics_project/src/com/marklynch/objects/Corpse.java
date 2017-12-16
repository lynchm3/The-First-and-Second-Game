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

	public Corpse makeCopy(String name, Square square, Actor owner, float weight) {
		Corpse meatChunk = new Corpse();
		super.setAttributesForCopy(meatChunk, square, owner);
		meatChunk.weight = weight;
		meatChunk.name = name;
		meatChunk.baseName = new String(meatChunk.name);
		if (meatChunk.inventory.size() == 0)
			meatChunk.name = this.baseName + " (empty)";
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
