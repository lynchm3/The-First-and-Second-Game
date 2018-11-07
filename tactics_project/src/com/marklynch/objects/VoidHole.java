package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.constructs.animation.primary.AnimationFall;
import com.marklynch.level.constructs.animation.primary.AnimationFallFromTheSky;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class VoidHole extends GameObject implements UpdatesWhenSquareContentsChange, OnCompletionListener {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	public Square connectedSquare = null;

	public VoidHole() {
		super();
		canBePickedUp = false;
		fitsInInventory = false;
		canShareSquare = true;
		canContainOtherObjects = false;
		persistsWhenCantBeSeen = true;
		attackable = false;
		isFloorObject = true;
		orderingOnGound = 10;
		type = "Pit";
	}

	@Override
	public boolean draw1() {
		return true;
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
		VoidHole voidHole = new VoidHole();
		setInstances(voidHole);
		super.setAttributesForCopy(voidHole, square, owner);
		voidHole.connectedSquare = connectedSquare;
		return voidHole;
	}

	@Override
	public void update(int delta) {
		super.update(delta);
	}

	@Override
	public void squareContentsChanged() {

		if (squareGameObjectIsOn == null)
			return;

		for (final GameObject gameObject : (ArrayList<GameObject>) squareGameObjectIsOn.inventory.gameObjects.clone()) {

			if (gameObject == Level.player)
				System.out.println("in voidhole.squareContentsChanged");

			if (gameObject == this || gameObject.isFloorObject)
				continue;

			if (gameObject.primaryAnimation != null && gameObject.primaryAnimation.completed == false) {
				gameObject.primaryAnimation.onCompletionListener = this;

				if (gameObject == Level.player)
					System.out.println("void hole is now completion listener for " + gameObject.primaryAnimation);
			} else {

				if (gameObject == Level.player)
					System.out.println("calling voidhole.dothething");
				doTheThing(gameObject);
			}
		}
	}

	public void doTheThing(final GameObject gameObject) {

		if (gameObject == Level.player)
			System.out.println("voidhole.dothething");

		if (gameObject.isFloorObject == false && gameObject.squareGameObjectIsOn == this.squareGameObjectIsOn) {
			gameObject.setPrimaryAnimation(new AnimationFall(gameObject, 1f, 0f, 400, new OnCompletionListener() {
				@Override
				public void animationComplete(GameObject gameObject) {

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

					if (gameObject == Level.player) {
						Level.pausePlayer();
					}

					gameObject.setPrimaryAnimation(new AnimationFallFromTheSky(gameObject, 200, null));

				}
			}));

		}
	}

	@Override
	public void animationComplete(GameObject gameObject) {
		doTheThing(gameObject);
	}

}
