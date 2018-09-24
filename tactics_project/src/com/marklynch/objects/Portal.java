package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.primary.AnimationFall;
import com.marklynch.level.constructs.animation.primary.AnimationFallFromTheSky;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actions.ActionMove;
import com.marklynch.objects.units.Actor;

public class Portal extends GameObject implements UpdatesWhenSquareContentsChange {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	public Square connectedSquare = null;

	public Portal() {
		super();
		canBePickedUp = false;
		fitsInInventory = false;
		canShareSquare = true;
		canContainOtherObjects = false;
		persistsWhenCantBeSeen = true;
		attackable = false;
		isFloorObject = true;
	}

	// @Override
	// public void draw1() {
	// }
	//
	// @Override
	// public void draw2() {
	// }
	//
	// @Override
	// public void draw3() {
	// }

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public Portal makeCopy(Square square, Actor owner, Square connectedSquare) {
		Portal portal = new Portal();
		setInstances(portal);
		super.setAttributesForCopy(portal, square, owner);
		portal.connectedSquare = connectedSquare;
		return portal;
	}

	@Override
	public void update(int delta) {
		super.update(delta);
	}

	@Override
	public void squareContentsChanged() {
		System.out.println("updateVoid 1 - " + squareGameObjectIsOn.inventory);

		if (squareGameObjectIsOn == null)
			return;

		for (final GameObject gameObject : (ArrayList<GameObject>) squareGameObjectIsOn.inventory.gameObjects.clone()) {
			if (gameObject.isFloorObject == false) {
				gameObject.setPrimaryAnimation(new AnimationFall(gameObject, 1f, 0f, 400) {
					@Override
					public void runCompletionAlgorightm(boolean wait) {
						super.runCompletionAlgorightm(wait);

						// Square square = gameObject.lastSquare;
						// if (square == null)
						Square square = connectedSquare;

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
		System.out.println("updateVoid 2 - " + squareGameObjectIsOn.inventory);

	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
		return new ActionMove(performer, squareGameObjectIsOn, true);
	}

	@Override
	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {
		return new ActionMove(performer, squareGameObjectIsOn, true);
	}

	@Override
	public ArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		ArrayList<Action> actions = new ArrayList<Action>();
		actions.add(new ActionMove(performer, squareGameObjectIsOn, true));
		actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));
		return actions;
	}

}
