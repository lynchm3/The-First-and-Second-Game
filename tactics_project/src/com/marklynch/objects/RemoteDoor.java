package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionClose;
import com.marklynch.objects.actions.ActionLock;
import com.marklynch.objects.actions.ActionOpen;
import com.marklynch.objects.actions.ActionUnlock;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class RemoteDoor extends Openable {
	float soundDampeningWhenClosed;
	boolean blocksLineOfSightWhenClosed;

	public RemoteDoor(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,

			float widthRatio, float heightRatio, float drawOffsetX, float drawOffsetY, float soundWhenHit,
			float soundWhenHitting, float soundDampening, Color light, float lightHandleX, float lightHandlY,
			boolean stackable, float fireResistance, float waterResistance, float electricResistance,
			float poisonResistance, float slashResistance, float weight, Actor owner, boolean locked, Key... keys) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, widthRatio, heightRatio, drawOffsetX,
				drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
				stackable, fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance,
				weight, owner, locked, keys);
		soundDampeningWhenClosed = soundDampening;
		blocksLineOfSightWhenClosed = blocksLineOfSight;

		canBePickedUp = false;
		showInventory = false;
		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = false;
		blocksLineOfSight = true;
		persistsWhenCantBeSeen = true;
		attackable = true;

	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();

		if (this.remainingHealth <= 0)
			return actions;

		// actions.add(new ActionAttack(performer, this));
		actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));

		ArrayList<Action> toRemove = new ArrayList<Action>();
		for (Action action : actions) {
			if (action instanceof ActionOpen) {
				toRemove.add(action);
			} else if (action instanceof ActionClose) {
				toRemove.add(action);
			} else if (action instanceof ActionLock) {
				toRemove.add(action);
			} else if (action instanceof ActionUnlock) {
				toRemove.add(action);
			}
		}
		actions.removeAll(toRemove);

		return actions;

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
		blocksLineOfSight = false;
		soundDampening = 1f;
	}

	@Override
	public void close() {
		open = false;
		blocksLineOfSight = blocksLineOfSightWhenClosed;
		soundDampening = soundDampeningWhenClosed;
	}

	public RemoteDoor makeCopy(String name, Square square, boolean locked, Actor owner, Key... keys) {
		return new RemoteDoor(new String(name), (int) totalHealth, imageTexturePath, square, new Inventory(),

				widthRatio, heightRatio, drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening,
				light, lightHandleX, lightHandlY, stackable, fireResistance, waterResistance, electricResistance,
				poisonResistance, slashResistance, weight, owner, locked, keys);
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
	// weight, owner,
	// locked, keys);
	// }

}
