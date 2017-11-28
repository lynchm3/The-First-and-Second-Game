package com.marklynch.objects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public abstract class Openable extends GameObject {

	protected boolean open = false;
	public Key[] keys;
	public boolean locked;
	public String baseName;

	public Openable() {
		super();

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

	public void setAttributesForCopy(Openable openable, Square square, boolean locked, Actor owner, Key... keys) {

		super.setAttributesForCopy(openable, square, owner);
		openable.keys = keys;
		openable.locked = locked;
		openable.baseName = baseName;
		if (locked)
			this.name = baseName + " (locked)";

	}

}
