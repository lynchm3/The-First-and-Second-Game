package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.primary.AnimationFall;
import com.marklynch.level.constructs.animation.primary.AnimationFallFromTheSky;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class VoidHole extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	public Square connectedSquare = null;

	public VoidHole() {
		super();

		// DROP HOLE
		canBePickedUp = false;
		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = false;
		persistsWhenCantBeSeen = true;
		attackable = false;
		isFloorObject = true;
	}

	@Override
	public void draw1() {
	}

	@Override
	public void draw2() {
	}

	@Override
	public void draw3() {
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public VoidHole makeCopy(Square square, Actor owner, Square connectedSquare) {
		VoidHole searchable = new VoidHole();
		setInstances(searchable);
		super.setAttributesForCopy(searchable, square, owner);
		searchable.connectedSquare = connectedSquare;
		return searchable;
	}

	@Override
	public void update(int delta) {
		super.update(delta);
	}

	public void updateVoid() {
		if (squareGameObjectIsOn == null)
			return;

		for (final GameObject gameObject : (ArrayList<GameObject>) squareGameObjectIsOn.inventory.gameObjects.clone()) {
			if (gameObject.isFloorObject == false) {
				gameObject.setPrimaryAnimation(new AnimationFall(gameObject, 1f, 0f, 400) {
					@Override
					public void runCompletionAlgorightm(boolean wait) {
						super.runCompletionAlgorightm(wait);

						Square square = gameObject.lastSquare;
						if (square == null)
							square = connectedSquare;

						square.inventory.add(gameObject);

						if (gameObject == Level.player) {
							// Game.ca
							Game.level.centerToSquare = true;
							Game.level.squareToCenterTo = square;
						} else {
							Level.gameObjectsToFlash.add(gameObject);
							Level.flashGameObjectCounters.put(gameObject, 0);
						}

						gameObject.setPrimaryAnimation(new AnimationFallFromTheSky(gameObject, 200));
					}
				});

			}
		}

	}

}
