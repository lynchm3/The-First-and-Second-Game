package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Corpse extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	protected String baseName;

	public Corpse() {
		super();
		baseName = new String(name);
		if (this.inventory.size() == 0)
			this.name = baseName + " (empty)";

		fitsInInventory = false;

		canContainOtherObjects = true;

	}

	public Corpse makeCopy(String name, Square square, Actor owner, float weight) {
		Corpse meatChunk = new Corpse();
		instances.add(meatChunk);
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
