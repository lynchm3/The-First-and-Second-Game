package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionClose;
import com.marklynch.objects.actions.ActionLock;
import com.marklynch.objects.actions.ActionOpen;
import com.marklynch.objects.actions.ActionPeek;
import com.marklynch.objects.actions.ActionStopPeeking;
import com.marklynch.objects.actions.ActionUnlock;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;

public abstract class Openable extends GameObject {

	protected boolean open = false;
	public Key[] keys;
	protected boolean locked;
	protected String baseName;

	public Openable(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio, float heightRatio,
			float drawOffsetX, float drawOffsetY, float soundWhenHit, float soundWhenHitting, float soundDampening,
			Color light, float lightHandleX, float lightHandlY, boolean stackable, float fireResistance,
			float waterResistance, float electricResistance, float poisonResistance, float weight, Actor owner,
			boolean locked, Key... keys) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, true, widthRatio, heightRatio,
				drawOffsetX, drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, waterResistance, electricResistance, poisonResistance, weight,
				owner);
		this.keys = keys;
		this.locked = locked;
		baseName = new String(name);
		if (locked)
			this.name = baseName + " (locked)";

	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();

		if (this.remainingHealth <= 0)
			return actions;

		if (!open) {
			actions.add(new ActionOpen(performer, this));
		}

		if (open)
			actions.add(new ActionClose(performer, this));

		if (locked)
			actions.add(new ActionUnlock(performer, this));

		if (!locked)
			actions.add(new ActionLock(performer, this));

		if (!open) {
			if (Game.level.player.peekingThrough == this)
				actions.add(new ActionStopPeeking(performer));
			else
				actions.add(new ActionPeek(performer, this));
		}

		// actions.add(new ActionAttack(performer, this));
		actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));

		return actions;

	}

	public boolean isOpen() {
		return open;
	}

	public abstract void open();

	public abstract void close();

	public void lock() {
		this.name = baseName + " (locked)";
		locked = true;
	}

	public void unlock() {
		this.name = baseName;
		locked = false;
	}

	public boolean isLocked() {
		return locked;
	}

}
