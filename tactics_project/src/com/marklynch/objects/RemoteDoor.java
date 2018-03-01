package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;

public class RemoteDoor extends Door {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	public float soundDampeningWhenClosed;
	public boolean blocksLineOfSightWhenClosed;

	public RemoteDoor() {
		super();
		soundDampeningWhenClosed = soundDampening;
		blocksLineOfSightWhenClosed = blocksLineOfSight;

		canBePickedUp = false;

		fitsInInventory = false;

		blocksLineOfSight = true;
		persistsWhenCantBeSeen = true;

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

		soundDampening = 1f;
	}

	@Override
	public void close() {
		open = false;
		blocksLineOfSight = blocksLineOfSightWhenClosed;
		soundDampening = soundDampeningWhenClosed;
	}

	public RemoteDoor makeCopy(String name, Square square, boolean locked, Key... keys) {

		RemoteDoor door = new RemoteDoor();
		setInstances(door);

		super.setAttributesForCopy(door, square, locked, null, keys);
		door.soundDampeningWhenClosed = soundDampening;
		door.blocksLineOfSightWhenClosed = blocksLineOfSight;

		return door;
	}

	@Override
	public boolean shouldBeClosed() {
		return false;
	}

	@Override
	public boolean shouldBeLocked() {
		return false;
	}
}
