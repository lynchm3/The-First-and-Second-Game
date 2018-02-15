package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;

public class RemoteDoor extends Openable {

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
	public void draw1() {

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

	// @Override
	// public Door makeCopy(Square square, Actor owner) {
	// return new Door(new String(baseName), (int) totalHealth,
	// imageTexturePath, square, new Inventory(),
	//
	//
	// widthRatio, heightRatio, drawOffsetX,
	// drawOffsetY, soundWhenHit,
	// soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
	// stackable, fireResistance,
	// waterResistance, electricResistance, poisonResistance, slashResistance,
	// weight, value, owner,
	// locked, keys);
	// }

}
