package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionSkin;
import com.marklynch.objects.units.Actor;

public class Carcass extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	public String baseName;

	public Carcass() {
		super();

		fitsInInventory = false;

		canContainOtherObjects = true;
		showInventoryInGroundDisplay = true;

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public Carcass makeCopy(String name, Square square, Actor owner, float weight) {
		Carcass meatChunk = new Carcass();
		setInstances(meatChunk);
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
