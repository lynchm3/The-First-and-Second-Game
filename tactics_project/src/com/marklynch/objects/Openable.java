package com.marklynch.objects;

import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public abstract class Openable extends GameObject {

	protected boolean open = false;
	public Key[] keys;
	public boolean locked;
	public String baseName;

	public Openable(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,

			float widthRatio, float heightRatio, float drawOffsetX, float drawOffsetY, float soundWhenHit,
			float soundWhenHitting, float soundDampening, Color light, float lightHandleX, float lightHandlY,
			boolean stackable, float fireResistance, float waterResistance, float electricResistance,
			float poisonResistance, float slashResistance, float weight, int value, Actor owner, boolean locked,
			int templateId, Key... keys) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, widthRatio, heightRatio, drawOffsetX,
				drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
				stackable, fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance,
				weight, value, owner, templateId);
		this.keys = keys;
		this.locked = locked;
		baseName = new String(name);
		if (locked)
			this.name = baseName + " (locked)";

	}

	public boolean isOpen() {
		return open;
	}

	public abstract void open();

	public abstract void close();

	public void lock() {
		this.name = baseName + " (locked)";
		locked = true;
	}

	public void unlock() {
		this.name = baseName;
		locked = false;
	}

	public boolean isLocked() {
		return locked;
	}

}
