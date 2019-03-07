package com.marklynch.objects.inanimateobjects;

import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.ArrayList;

public class InputDrain extends GameObject implements OnCompletionListener {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);
	public Square connectedSquare = null;

	public InputDrain() {
		super();
		canBePickedUp = false;
		fitsInInventory = false;
		canShareSquare = true;
		canContainOtherObjects = false;
		persistsWhenCantBeSeen = true;
		attackable = false;
		isFloorObject = true;
		moveable = false;
		orderingOnGound = 10;
		type = "Drain";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public InputDrain makeCopy(Square square, Actor owner, Square connectedSquare) {
		InputDrain drain = new InputDrain();
		setInstances(drain);
		super.setAttributesForCopy(drain, square, owner);
		drain.connectedSquare = connectedSquare;
		return drain;
	}

	@Override
	public void squareContentsChanged() {

		if (squareGameObjectIsOn == null)
			return;

		for (final GameObject gameObject : (ArrayList<GameObject>) squareGameObjectIsOn.inventory.gameObjects.clone()) {

			if (gameObject == this || gameObject.isFloorObject || !(gameObject instanceof Liquid))
				continue;

//			if (gameObject.primaryAnimation != null && gameObject.primaryAnimation.completed == false) {
//				gameObject.primaryAnimation.onCompletionListener = this;
//			} else {
			doTheThing((Liquid) gameObject);
//			}
		}
	}

	public void doTheThing(final Liquid gameObject) {
		this.squareGameObjectIsOn.inventory.remove(gameObject);
		this.connectedSquare.liquidSpread(gameObject);
	}

	@Override
	public void animationComplete(GameObject gameObject) {
		doTheThing((Liquid) gameObject);
	}

}
