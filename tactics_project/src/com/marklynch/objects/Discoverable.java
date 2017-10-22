package com.marklynch.objects;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.inventory.Inventory;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.ResourceUtils;

import mdesl.graphics.Color;
import mdesl.graphics.Texture;

public class Discoverable extends GameObject {

	public boolean discovered = false;
	public Texture preDiscoverTexture; // if null the object is invisible if not
										// discovered
	public Texture postDiscoverTexture;
	public int level;
	public String imagePathWhenPrediscovered;

	public Discoverable(String name, int health, int level, String imagePath, String imagePathWhenPrediscovered,
			Square squareGameObjectIsOn, Inventory inventory, float widthRatio, float heightRatio, float drawOffsetX,
			float drawOffsetY, float soundWhenHit, float soundWhenHitting, float soundDampening, Color light,
			float lightHandleX, float lightHandlY, boolean stackable, float fireResistance, float waterResistance,
			float electricResistance, float poisonResistance, float slashResistance, float weight, int value,
			Actor owner, int templateId) {
		super(name, health, imagePath, squareGameObjectIsOn, inventory, widthRatio, heightRatio, drawOffsetX,
				drawOffsetY, soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY,
				stackable, fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance,
				weight, value, owner, templateId);
		this.level = level;
		preDiscoverTexture = ResourceUtils.getGlobalImage(imagePathWhenPrediscovered);
		postDiscoverTexture = imageTexture;
		imageTexture = preDiscoverTexture;

		// Settings for BURROW
		canBePickedUp = false;
		showInventory = false;
		fitsInInventory = false;
		canShareSquare = true;
		canContainOtherObjects = true;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = true;
		attackable = false;

	}

	public void discovered() {
		discovered = true;
		imageTexture = postDiscoverTexture;
		Level.flashGameObject = true;
		Level.gameObjectToFlash = this;
		Level.flashGameObjectCounter = 0;
	}

	@Override
	public void draw1() {
		if (imageTexture == null)
			return;
		super.draw1();
	}

	@Override
	public void draw2() {
		if (imageTexture == null)
			return;
		super.draw2();
	}

	@Override
	public Discoverable makeCopy(Square square, Actor owner) {
		return new Discoverable(new String(name), (int) totalHealth, level, imageTexturePath,
				imagePathWhenPrediscovered, square, new Inventory(), widthRatio, heightRatio, drawOffsetX, drawOffsetY,
				soundWhenHit, soundWhenHitting, soundDampening, light, lightHandleX, lightHandlY, stackable,
				fireResistance, waterResistance, electricResistance, poisonResistance, slashResistance, weight, value,
				owner, templateId);
	}

	// need actionDiscover

	// need to add on to ActionMove the check for discovery, taking in to
	// account level of this and distance to this.

}
