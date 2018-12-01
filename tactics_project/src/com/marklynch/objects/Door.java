package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;

public class Door extends Openable {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	public float soundDampeningWhenClosed;
	public boolean blocksLineOfSightWhenClosed;
	public boolean shouldBeClosed;
	public boolean shouldBeLocked;

	public Door() {
		super();

		canBePickedUp = false;

		fitsInInventory = false;

		blocksLineOfSight = true;
		persistsWhenCantBeSeen = true;
		moveable = false;
		type = "Door";

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
	public boolean draw1() {

		if (open) {
			return shouldDraw();
		} else {
			return super.draw1();
		}

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

	public Door makeCopy(String name, Square square, boolean locked, boolean shouldBeClosed, boolean shouldBeLocked,
			Actor owner, Key... keys) {

		Door door = new Door();
		setInstances(door);

		super.setAttributesForCopy(door, square, locked, owner, keys);

		if (owner != null) {
			owner.doors.add(door);
		}
		door.soundDampeningWhenClosed = soundDampeningWhenClosed;
		door.blocksLineOfSightWhenClosed = blocksLineOfSight;
		door.shouldBeClosed = shouldBeClosed;
		door.shouldBeLocked = shouldBeLocked;
		if (!door.open) {
			blocksLineOfSight = blocksLineOfSightWhenClosed;
			soundDampening = soundDampeningWhenClosed;
		} else {

			blocksLineOfSight = false;
			soundDampening = 1f;

		}

		return door;
	}

	public boolean shouldBeClosed() {
		return shouldBeClosed;
	}

	public boolean shouldBeLocked() {
		return shouldBeLocked;
	}

}
