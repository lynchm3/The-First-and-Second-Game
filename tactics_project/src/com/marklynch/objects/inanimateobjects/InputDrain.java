package com.marklynch.objects.inanimateobjects;

import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.constructs.animation.primary.AnimationScale;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.CopyOnWriteArrayList;

public class InputDrain extends GameObject implements OnCompletionListener {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);
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

		for (final GameObject gameObject : (CopyOnWriteArrayList<GameObject>) squareGameObjectIsOn.inventory.gameObjects) {

			if (gameObject == this || !(gameObject instanceof Liquid))
				continue;

			if (gameObject.primaryAnimation != null && gameObject.primaryAnimation.completed == false) {
				gameObject.primaryAnimation.onCompletionListener = this;
			} else {
				doTheThing((Liquid) gameObject);
			}
		}
	}

	public void doTheThing(final Liquid liquid) {
		liquid.setPrimaryAnimation(new AnimationScale(liquid, 1f, 0f, 500, new OnCompletionListener() {
			@Override
			public void animationComplete(GameObject gameObject) {
				InputDrain.this.squareGameObjectIsOn.inventory.remove(liquid);
				InputDrain.this.connectedSquare.liquidSpread(liquid);
			}
		}));

//		liquid.setPrimaryAnimation(new AnimationLiquidSpread(liquid, new OnCompletionListener() {
//			@Override
//			public void animationComplete(GameObject gameObject) {
//
//				liquid.setPrimaryAnimation(new AnimationScale(liquid, 1f, 0f, 2000, new OnCompletionListener() {
//					@Override
//					public void animationComplete(GameObject gameObject) {
//						InputDrain.this.squareGameObjectIsOn.inventory.remove(liquid);
//						InputDrain.this.connectedSquare.liquidSpread(liquid);
//					}
//				}));
//			}
//		}));
	}

	@Override
	public void animationComplete(GameObject gameObject) {
		doTheThing((Liquid) gameObject);
	}

}
