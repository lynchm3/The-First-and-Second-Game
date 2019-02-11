package com.marklynch.objects.inanimateobjects;

import com.marklynch.level.Level;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.ArrayList;

public class Discoverable extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public boolean discovered = false;
	public int level;
	public String imagePathWhenPrediscovered;

	public Discoverable() {
		super();

		canBePickedUp = false;

		fitsInInventory = false;

		canContainOtherObjects = true;

		persistsWhenCantBeSeen = true;
		attackable = false;

		type = "Discoverable";

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public void discovered() {
		discovered = true;

		if (squareGameObjectIsOn != null) {
			Level.gameObjectsToFlash.add(this);
			Level.flashGameObjectCounters.put(this, 0);
		}
	}

	@Override
	public boolean draw1() {
		if (!discovered)
			return false;
		return super.draw1();
	}

	@Override
	public void draw2() {
		if (!discovered)
			return;
		super.draw2();
	}

	public Discoverable makeCopy(Square square, Actor owner, int level) {
		Discoverable discoverable = new Discoverable();
		setInstances(discoverable);
		super.setAttributesForCopy(discoverable, square, owner);
		discoverable.level = level;
		return discoverable;
	}

	// need actionDiscover

	// need to add on to ActionMove the check for discovery, taking in to
	// account level of this and distance to this.

}
