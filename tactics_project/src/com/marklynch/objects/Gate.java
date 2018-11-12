package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Gate extends Door {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	float soundDampeningWhenClosed;
	boolean blocksLineOfSightWhenClosed;

	public Gate() {
		super();

		canBePickedUp = false;

		fitsInInventory = false;

		persistsWhenCantBeSeen = true;
		moveable = false;

		soundDampeningWhenClosed = soundDampening;
		blocksLineOfSightWhenClosed = blocksLineOfSight;
		type = "Gate";

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public Gate makeCopy(String name, Square square, boolean locked, boolean shouldBeClosed, boolean shouldBeLocked,
			Actor owner, Key... keys) {

		Gate door = new Gate();
		setInstances(door);

		super.setAttributesForCopy(door, square, locked, owner, keys);

		if (owner != null) {
			owner.doors.add(door);
		}
		door.soundDampeningWhenClosed = soundDampening;
		door.blocksLineOfSightWhenClosed = blocksLineOfSight;
		door.shouldBeClosed = shouldBeClosed;
		door.shouldBeLocked = shouldBeLocked;

		return door;
	}

}
