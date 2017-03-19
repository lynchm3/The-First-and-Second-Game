package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionAttack;
import com.marklynch.objects.actions.ActionLock;
import com.marklynch.objects.actions.ActionUnlock;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public class Door extends GameObject {

	public ArrayList<Key> keys;
	public boolean locked;

	public Door(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio, float heightRatio,
			float soundHandleX, float soundHandleY, float soundWhenHit, float soundWhenHitting, Color light,
			float lightHandleX, float lightHandlY, boolean stackable, float fireResistance, float iceResistance,
			float electricResistance, float poisonResistance, ArrayList<Key> keys, boolean locked) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio, heightRatio,
				soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, light, lightHandleX, lightHandlY, stackable,
				fireResistance, iceResistance, electricResistance, poisonResistance);
		this.keys = keys;
		this.locked = locked;

	}

	@Override
	public void draw1() {

		if (this.squareGameObjectIsOn.visibleToPlayer == false && persistsWhenCantBeSeen == false)
			return;

		if (!this.squareGameObjectIsOn.seenByPlayer)
			return;

		if (!this.squareGameObjectIsOn.inventory.contains(Actor.class)) {
			super.draw1();
		} else {
		}

	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		System.out.println("GAMEOBJECT.getAllActionsPerformedOnThisInWorld");
		ArrayList<Action> actions = new ArrayList<Action>();

		if (this.remainingHealth < 0)
			return actions;

		if (locked)
			actions.add(new ActionUnlock(performer, this));

		if (!locked)
			actions.add(new ActionLock(performer, this));

		actions.add(new ActionAttack(performer, this));

		return actions;

	}

	public Door makeCopy(Square square, ArrayList<Key> keys, boolean locked) {
		return new Door(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, blocksLineOfSight,
				persistsWhenCantBeSeen, widthRatio, heightRatio, soundHandleX, soundHandleY, soundWhenHit,
				soundWhenHitting, light, lightHandleX, lightHandlY, stackable, fireResistance, iceResistance,
				electricResistance, poisonResistance, keys, locked);
	}

}
