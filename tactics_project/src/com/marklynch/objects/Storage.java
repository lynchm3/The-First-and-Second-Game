package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.Texture;

public class Storage extends Openable {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Texture chestOpenTexture;
	public Texture chestClosedTexture;
	public String imagePathWhenOpen;
	public ArrayList<Actor> ownersOfContents = new ArrayList<Actor>();

	public Storage() {
		super();

		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = true;
		showInventoryInGroundDisplay = true;
		type = "Storage";

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	// @Override
	// public void loadImages() {
	// chestOpenTexture = ResourceUtils.getGlobalImage("chest_open.png");
	// chestClosedTexture = ResourceUtils.getGlobalImage("chest.png");
	// }

	@Override
	public void draw1() {

		if (!Game.fullVisiblity) {
			if (this.squareGameObjectIsOn.visibleToPlayer == false && persistsWhenCantBeSeen == false)
				return;

			if (!this.squareGameObjectIsOn.seenByPlayer)
				return;
		}

		if (open)
			imageTexture = chestOpenTexture;
		else
			imageTexture = chestClosedTexture;

		super.draw1();

		// if (!this.squareGameObjectIsOn.inventory.contains(Actor.class)) {
		// super.draw1();
		// } else {
		// }

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

		ownersOfContents.clear();
		for (GameObject gameObject : inventory.gameObjects) {
			if (gameObject.owner != null && !ownersOfContents.contains(gameObject.owner)) {
				ownersOfContents.add(gameObject.owner);
			}
		}
	}

	public Storage makeCopy(String name, Square square, boolean locked, Actor owner, Key... keys) {

		Storage storage = new Storage();
		setInstances(storage);
		super.setAttributesForCopy(storage, square, locked, owner, keys);

		storage.baseName = baseName;
		if (locked)
			this.name = baseName + " (locked)";
		else if (this.inventory.size() == 0)
			this.name = baseName + " (empty)";

		storage.chestOpenTexture = chestOpenTexture;
		storage.chestClosedTexture = chestClosedTexture;
		// this.imagePathWhenOpen = imagePathWhenOpen;

		return storage;

	}

}
