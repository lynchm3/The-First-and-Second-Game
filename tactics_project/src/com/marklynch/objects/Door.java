package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Door extends Openable {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	public float soundDampeningWhenClosed;
	public boolean blocksLineOfSightWhenClosed;
	protected boolean shouldBeClosed;
	protected boolean shouldBeLocked;

	public Door() {
		super();

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
	public void draw1() {

		// Don't draw if dead
		if (this.remainingHealth <= 0)
			return;

		if (!Game.fullVisiblity) {
			if (this.squareGameObjectIsOn.visibleToPlayer == false && persistsWhenCantBeSeen == false)
				return;

			if (!this.squareGameObjectIsOn.seenByPlayer)
				return;
		}

		if (open) {

		} else {
			super.draw1();
		}

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

		blocksLineOfSight = false;
		soundDampening = 1f;
	}

	@Override
	public void close() {
		open = false;
		blocksLineOfSight = blocksLineOfSightWhenClosed;
		soundDampening = soundDampeningWhenClosed;
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
