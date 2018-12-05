package com.marklynch.objects.inanimateobjects;

import com.marklynch.utils.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;

public class RemoteDoor extends Door {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);
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
		blocksLineOfSight = false;
		soundDampening = 1f;
		name = baseName + " (open)";
	}

	@Override
	public void close() {
		open = false;
		blocksLineOfSight = blocksLineOfSightWhenClosed;
		soundDampening = soundDampeningWhenClosed;
		name = baseName;
	}

	public RemoteDoor makeCopy(String name, Square square, boolean locked, Actor owner) {

		RemoteDoor door = new RemoteDoor();
		setInstances(door);

		super.setAttributesForCopy(door, square, locked, owner);
		door.soundDampeningWhenClosed = soundDampening;
		door.blocksLineOfSightWhenClosed = blocksLineOfSightWhenClosed;

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
