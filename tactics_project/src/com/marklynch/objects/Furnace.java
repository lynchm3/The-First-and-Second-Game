package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionLootAll;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class Furnace extends Openable {

	Texture chestOpenTexture;
	Texture chestClosedTexture;

	public Furnace(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio, float heightRatio,
			float soundHandleX, float soundHandleY, float soundWhenHit, float soundWhenHitting, float soundDampening,
			Color light, float lightHandleX, float lightHandlY, boolean stackable, float fireResistance,
			float iceResistance, float electricResistance, float poisonResistance, float weight, Actor owner,
			boolean locked, Key... keys) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio, heightRatio,
				soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX,
				lightHandlY, stackable, fireResistance, iceResistance, electricResistance, poisonResistance, weight,
				owner, locked, keys);
		if (locked)
			this.name = baseName + " (locked)";
		else if (this.inventory.size() == 0)
			this.name = baseName + " (empty)";
		drawOffsetX = -32;
		drawOffsetY = -64;

	}

	// @Override
	// public void loadImages() {
	// chestOpenTexture = ResourceUtils.getGlobalImage("chest_open.png");
	// chestClosedTexture = ResourceUtils.getGlobalImage("chest.png");
	// }

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();
		if (this.inventory.size() > 0 && (open || !locked || locked && performer.hasKeyForDoor(this))) {
			actions.add(new ActionLootAll(performer, this));
		}
		actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));
		return actions;
	}

	// @Override
	// public void draw1() {
	//
	// if (!Game.fullVisiblity) {
	// if (this.squareGameObjectIsOn.visibleToPlayer == false &&
	// persistsWhenCantBeSeen == false)
	// return;
	//
	// if (!this.squareGameObjectIsOn.seenByPlayer)
	// return;
	// }
	//
	// if (open)
	// imageTexture = chestOpenTexture;
	// else
	// imageTexture = chestClosedTexture;
	//
	// super.draw1();
	//
	// // if (!this.squareGameObjectIsOn.inventory.contains(Actor.class)) {
	// // super.draw1();
	// // } else {
	// // }
	//
	// }

	@Override
	public boolean isOpen() {
		return open;
	}

	@Override
	public void open() {
		open = true;
	}

	@Override
	public void close() {
		open = false;
	}

	@Override
	public void lock() {
		this.name = baseName + " (locked)";
		locked = true;
	}

	@Override
	public void unlock() {
		if (this.inventory.size() == 0)
			this.name = baseName + " (empty)";
		else
			this.name = baseName;
		locked = false;
	}

	@Override
	public void looted() {
	}

	@Override
	public void inventoryChanged() {
		if (locked)
			this.name = baseName + " (locked)";
		else if (this.inventory.size() == 0)
			this.name = baseName + " (empty)";
		else
			this.name = baseName;
	}

	public Furnace makeCopy(String name, Square square, boolean locked, Actor owner, Key... keys) {
		return new Furnace(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, blocksLineOfSight,
				persistsWhenCantBeSeen, widthRatio, heightRatio, soundHandleX, soundHandleY, soundWhenHit,
				soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY, stackable, fireResistance,
				iceResistance, electricResistance, poisonResistance, weight, owner, locked, keys);
	}

	@Override
	public Furnace makeCopy(Square square, Actor owner) {
		return new Furnace(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, blocksLineOfSight,
				persistsWhenCantBeSeen, widthRatio, heightRatio, soundHandleX, soundHandleY, soundWhenHit,
				soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY, stackable, fireResistance,
				iceResistance, electricResistance, poisonResistance, weight, owner, locked, keys);
	}

}
