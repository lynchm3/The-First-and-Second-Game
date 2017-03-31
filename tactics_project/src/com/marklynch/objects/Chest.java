package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.ResourceUtils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class Chest extends Openable {

	Texture chestOpenTexture;
	Texture chestClosedTexture;

	public Chest(String name, int health, String imagePath, Square squareGameObjectIsOn, Inventory inventory,
			boolean showInventory, boolean canShareSquare, boolean fitsInInventory, boolean canContainOtherObjects,
			boolean blocksLineOfSight, boolean persistsWhenCantBeSeen, float widthRatio, float heightRatio,
			float soundHandleX, float soundHandleY, float soundWhenHit, float soundWhenHitting, Color light,
			float lightHandleX, float lightHandlY, boolean stackable, float fireResistance, float iceResistance,
			float electricResistance, float poisonResistance, Actor owner, ArrayList<Key> keys, boolean locked) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, showInventory, canShareSquare, fitsInInventory,
				canContainOtherObjects, blocksLineOfSight, persistsWhenCantBeSeen, widthRatio, heightRatio,
				soundHandleX, soundHandleY, soundWhenHit, soundWhenHitting, light, lightHandleX, lightHandlY, stackable,
				fireResistance, iceResistance, electricResistance, poisonResistance, owner, keys, locked);

	}

	@Override
	public void loadImages() {
		chestOpenTexture = ResourceUtils.getGlobalImage("chest_open.png");
		chestClosedTexture = ResourceUtils.getGlobalImage("chest.png");
	}

	@Override
	public void draw1() {

		if (this.squareGameObjectIsOn.visibleToPlayer == false && persistsWhenCantBeSeen == false)
			return;

		if (!this.squareGameObjectIsOn.seenByPlayer)
			return;

		if (open)
			imageTexture = chestOpenTexture;
		else
			imageTexture = chestClosedTexture;

		super.draw1();

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
	}

	@Override
	public void close() {
		open = false;
	}

	public Chest makeCopy(Square square, ArrayList<Key> keys, boolean locked, Actor owner) {
		return new Chest(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, blocksLineOfSight,
				persistsWhenCantBeSeen, widthRatio, heightRatio, soundHandleX, soundHandleY, soundWhenHit,
				soundWhenHitting, light, lightHandleX, lightHandlY, stackable, fireResistance, iceResistance,
				electricResistance, poisonResistance, owner, keys, locked);
	}

	@Override
	public Chest makeCopy(Square square, Actor owner) {
		return new Chest(new String(name), (int) totalHealth, imageTexturePath, square, inventory.makeCopy(),
				showInventory, canShareSquare, fitsInInventory, canContainOtherObjects, blocksLineOfSight,
				persistsWhenCantBeSeen, widthRatio, heightRatio, soundHandleX, soundHandleY, soundWhenHit,
				soundWhenHitting, light, lightHandleX, lightHandlY, stackable, fireResistance, iceResistance,
				electricResistance, poisonResistance, owner, keys, locked);
	}

}
