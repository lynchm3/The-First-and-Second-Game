package com.marklynch.objects;

import com.marklynch.Game;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class Door extends Openable {
	float soundDampeningWhenClosed;
	boolean blocksLineOfSightWhenClosed;
	private boolean shouldBeClosed;
	private boolean shouldBeLocked;

	public Door(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,

			float widthRatio, float heightRatio, float drawOffsetX, float drawOffsetY, float soundWhenHit,
			float soundWhenHitting, float soundDampening, Color light, float lightHandleX, float lightHandlY,
			boolean stackable, float fireResistance, float waterResistance, float electricResistance,
			float poisonResistance, float slashResistance, float weight, int value, Actor owner, boolean locked,
			boolean shouldBeClosed, boolean shouldBeLocked, int templateId, Key... keys) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, widthRatio, heightRatio, drawOffsetX,
				drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
				stackable, fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance,
				weight, value, owner, locked, templateId);

		canBePickedUp = false;
		showInventory = false;
		fitsInInventory = false;
		canShareSquare = true;
		canContainOtherObjects = false;
		blocksLineOfSight = true;
		persistsWhenCantBeSeen = true;
		attackable = true;
		soundDampeningWhenClosed = soundDampening;
		blocksLineOfSightWhenClosed = blocksLineOfSight;
		this.shouldBeClosed = shouldBeClosed;
		this.shouldBeLocked = shouldBeLocked;

		if (owner != null) {
			owner.doors.add(this);
		}
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
		return new Door(new String(name), (int) totalHealth, imageTexturePath, square, new Inventory(),

				widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening,
				light, lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, slashResistance, weight, value, owner, locked, shouldBeClosed, shouldBeLocked,
				templateId, keys);
	}

	public boolean shouldBeClosed() {
		return shouldBeClosed;
	}

	public boolean shouldBeLocked() {
		return shouldBeLocked;
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
