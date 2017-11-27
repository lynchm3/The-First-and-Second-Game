package com.marklynch.objects;

import com.marklynch.Game;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.ResourceUtils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class Storage extends Openable {

	Texture chestOpenTexture;
	Texture chestClosedTexture;
	String imagePathWhenOpen;

	public Storage(String name, int health, String imagePath, String imagePathWhenOpen, Square squareGameObjectIsOn,
			Inventory inventory,

			float widthRatio, float heightRatio, float drawOffsetX, float drawOffsetY, float soundWhenHit,
			float soundWhenHitting, float soundDampening, Color light, float lightHandleX, float lightHandlY,
			boolean stackable, float fireResistance, float waterResistance, float electricResistance,
			float poisonResistance, float slashResistance, float weight, int value, Actor owner, boolean locked, int i,
			Key... keys) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, widthRatio, heightRatio, drawOffsetX,
				drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
				stackable, fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance,
				weight, value, owner, locked, i, keys);
		if (locked)
			this.name = baseName + " (locked)";
		else if (this.inventory.size() == 0)
			this.name = baseName + " (empty)";

		chestClosedTexture = imageTexture;
		this.imagePathWhenOpen = imagePathWhenOpen;
		chestOpenTexture = ResourceUtils.getGlobalImage(imagePathWhenOpen);

		canBePickedUp = true;
		showInventory = false;
		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = true;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = false;
		attackable = true;

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
	}

	public Storage makeCopy(String name, Square square, boolean locked, Actor owner, Key... keys) {
		return new Storage(new String(name), (int) totalHealth, imageTexturePath, imagePathWhenOpen, square,
				new Inventory(), widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting,
				soundDampening, light, lightHandleX, lightHandlY, stackable, fireResistance, waterResistance,
				electricResistance, poisonResistance, slashResistance, weight, value, owner, locked, templateId, keys);
	}

}