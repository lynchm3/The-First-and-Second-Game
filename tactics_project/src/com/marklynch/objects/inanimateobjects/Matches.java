package com.marklynch.objects.inanimateobjects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.tools.Tool;

public class Matches extends Tool {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Matches() {
		super();

		canBePickedUp = true;
		fitsInInventory = true;
		canShareSquare = true;
		canContainOtherObjects = false;
		persistsWhenCantBeSeen = false;
		type = "Matches";

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public ArrayList<GameObject> search() {
		return (ArrayList<GameObject>) inventory.gameObjects.clone();
	}

	@Override
	public Matches makeCopy(Square square, Actor owner) {
		Matches matches = new Matches();
		setInstances(matches);
		super.setAttributesForCopy(matches, square, owner);
		return matches;
	}
}
