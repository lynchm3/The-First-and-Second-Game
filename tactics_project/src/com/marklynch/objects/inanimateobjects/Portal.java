package com.marklynch.objects.inanimateobjects;

import com.marklynch.actions.Action;
import com.marklynch.actions.ActionMove;
import com.marklynch.actions.ActionTeleport;
import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.CopyOnWriteArrayList;

public class Portal extends GameObject implements OnCompletionListener {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);
	public Square connectedSquare = null;

	public Portal() {
		super();
		canBePickedUp = false;
		fitsInInventory = false;
		canShareSquare = true;
		canContainOtherObjects = false;
		persistsWhenCantBeSeen = true;
		attackable = false;
		moveable = false;
		type = "Portal";
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
		portal.linkedObjects.add(connectedSquare);
		return portal;
	}

	@Override
	public void squareContentsChanged() {

		if (squareGameObjectIsOn == null)
			return;

		for (final GameObject gameObject : (CopyOnWriteArrayList<GameObject>) squareGameObjectIsOn.inventory.gameObjects) {

			if (gameObject == this || gameObject.isFloorObject)
				continue;

			if (gameObject.primaryAnimation != null && gameObject.primaryAnimation.completed == false) {
				gameObject.primaryAnimation.onCompletionListener = this;
			} else {
				doTheThing(gameObject);
			}
		}

	}

	public void doTheThing(final GameObject gameObject) {

		if (squareGameObjectIsOn == null || gameObject == null || connectedSquare == null)
			return;
		new ActionTeleport(Portal.this, gameObject, connectedSquare, true, true, false).perform();

	}

	@Override
	public Action getDefaultActionPerformedOnThisInWorld(Actor performer) {
		return new ActionMove(performer, squareGameObjectIsOn, true, true);
	}

	@Override
	public Action getSecondaryActionPerformedOnThisInWorld(Actor performer) {
		return new ActionMove(performer, squareGameObjectIsOn, true, true);
	}

	@Override
	public CopyOnWriteArrayList<Action> getAllActionsPerformedOnThisInWorld(Actor performer) {
		CopyOnWriteArrayList<Action> actions = new CopyOnWriteArrayList<Action>(Action.class);
		actions.add(new ActionMove(performer, squareGameObjectIsOn, true, true));
		actions.addAll(super.getAllActionsPerformedOnThisInWorld(performer));
		return actions;
	}

	@Override
	public void animationComplete(GameObject gameObject) {
		doTheThing(gameObject);
	}

}
