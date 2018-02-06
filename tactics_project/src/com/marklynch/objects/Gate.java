package com.marklynch.objects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Gate extends Door {
	float soundDampeningWhenClosed;
	boolean blocksLineOfSightWhenClosed;

	public Gate() {
		super();

		canBePickedUp = false;

		fitsInInventory = false;



		persistsWhenCantBeSeen = true;

		soundDampeningWhenClosed = soundDampening;
		blocksLineOfSightWhenClosed = blocksLineOfSight;

	}

	@Override
	public Gate makeCopy(String name, Square square, boolean locked, boolean shouldBeClosed, boolean shouldBeLocked,
			Actor owner, Key... keys) {

		Gate door = new Gate();

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
