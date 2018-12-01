package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.Texture;

public class Furnace extends Openable {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Texture chestOpenTexture;
	public Texture chestClosedTexture;

	public Furnace() {
		super();

		canBePickedUp = false;

		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = true;
		showInventoryInGroundDisplay = true;
		blocksLineOfSight = true;
		persistsWhenCantBeSeen = true;

	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public boolean isOpen() {
		return open;
	}

	@Override
	public void open() {
		open = true;
	}

	@Override
	public void close() {
		open = false;
	}

	@Override
	public void lock() {
		this.name = baseName + " (locked)";
		locked = true;
	}

	@Override
	public void unlock() {
		if (this.inventory.size() == 0)
			this.name = baseName + " (empty)";
		else
			this.name = baseName;
		locked = false;
	}

	@Override
	public void looted() {
	}

	@Override
	public void inventoryChanged() {
		if (locked)
			this.name = baseName + " (locked)";
		else if (this.inventory.size() == 0)
			this.name = baseName + " (empty)";
		else
			this.name = baseName;
	}

	public Furnace makeCopy(String name, Square square, boolean locked, Actor owner, Key... keys) {
		Furnace furnace = new Furnace();
		setInstances(furnace);
		super.setAttributesForCopy(furnace, square, locked, owner, keys);
		if (locked)
			this.name = baseName + " (locked)";
		else if (this.inventory.size() == 0)
			this.name = baseName + " (empty)";

		return furnace;
	}
}
