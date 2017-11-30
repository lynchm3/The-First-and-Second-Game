package com.marklynch.objects;

import com.marklynch.level.Level;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Texture;

public class Discoverable extends GameObject {

	public boolean discovered = false;
	public Texture preDiscoverTexture; // if null the object is invisible if not
										// discovered
	public Texture postDiscoverTexture;
	public int level;
	public String imagePathWhenPrediscovered;

	public Discoverable() {
		super();

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

	public Discoverable makeCopy(Square square, Actor owner, int level) {
		Discoverable discoverable = new Discoverable();
		super.setAttributesForCopy(discoverable, square, owner);
		discoverable.level = level;
		return discoverable;
	}

	// need actionDiscover

	// need to add on to ActionMove the check for discovery, taking in to
	// account level of this and distance to this.

}
