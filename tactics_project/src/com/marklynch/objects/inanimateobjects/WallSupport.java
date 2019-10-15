package com.marklynch.objects.inanimateobjects;

import java.util.Arrays;

import com.marklynch.actions.Action;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.constructs.animation.primary.AnimationShake;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.CopyOnWriteArrayList;
import com.marklynch.utils.Utils;

public class WallSupport extends GameObject {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(
			GameObject.class);

	public CopyOnWriteArrayList<GameObject> supportedGameObjects;

	public WallSupport() {
		super();
		canBePickedUp = false;
		fitsInInventory = false;
		canShareSquare = false;
		moveable = false;
		type = "Support";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public WallSupport makeCopy(Square square, Actor owner, GameObject... supportedGameObjects) {
		return makeCopy(square, owner,
				new java.util.concurrent.CopyOnWriteArrayList<>(Arrays.asList(supportedGameObjects)));
	}

	public WallSupport makeCopy(Square square, Actor owner,
			java.util.concurrent.CopyOnWriteArrayList<GameObject> supportedGameObjects) {
		WallSupport support = new WallSupport();
		setInstances(support);
		super.setAttributesForCopy(support, square, owner);
		support.supportedGameObjects = new CopyOnWriteArrayList<GameObject>(GameObject.class);
		support.supportedGameObjects.addAll(supportedGameObjects);
		support.gameObjectsToHighlight.addAll(supportedGameObjects);
		return support;
	}

	@Override
	public boolean checkIfDestroyed(Object attacker, Action action) {
		boolean destroyed = super.checkIfDestroyed(attacker, action);

		if (destroyed && supportedGameObjects != null) {

			collapseSurroundingObjects(this, attacker, action);
		}
		return destroyed;
	}

	public void collapseSurroundingObjects(GameObject collapser, final Object attacker, final Action action) {

		CopyOnWriteArrayList<GameObject> surroundingGameObjects = collapser.getSurroundingGameObjects();
		CopyOnWriteArrayList<GameObject> surroundingObjectsToCollapse = new CopyOnWriteArrayList<GameObject>(
				GameObject.class);
		surroundingObjectsToCollapse.addAll(Utils.intersection(surroundingGameObjects, supportedGameObjects));
		for (GameObject s : surroundingObjectsToCollapse) {

			final GameObject surroundingObjectToCollapse = s;

			if (surroundingObjectToCollapse.remainingHealth > 0) {
				OnCompletionListener onShakeCompletionListener = new OnCompletionListener() {

					@Override
					public void animationComplete(GameObject gameObject) {

//						surroundingObjectToCollapse.showPow(GameObject.dustCloudTexture, 200, 200);

						surroundingObjectToCollapse.changeHealthSafetyOff(-surroundingObjectToCollapse.remainingHealth,
								attacker, action);

						collapseSurroundingObjects(surroundingObjectToCollapse, attacker, action);
						Level.player.calculateVisibleAndCastableSquares(Level.player.squareGameObjectIsOn);
					}
				};

				surroundingObjectToCollapse.setPrimaryAnimation(
						new AnimationShake(surroundingObjectToCollapse, onShakeCompletionListener, true, 500));
			}
		}

	}
}
