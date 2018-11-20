package com.marklynch.objects;

import java.util.ArrayList;
import java.util.Arrays;

import com.marklynch.level.Level;
import com.marklynch.level.constructs.animation.Animation.OnCompletionListener;
import com.marklynch.level.constructs.animation.primary.AnimationShake;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.Utils;

public class Support extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public ArrayList<GameObject> supportedGameObjects;

	public Support() {
		super();
		canBePickedUp = false;
		fitsInInventory = false;
		canShareSquare = false;
		type = "Support";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public Support makeCopy(Square square, Actor owner, GameObject... supportedGameObjects) {

		return makeCopy(square, owner, new ArrayList<>(Arrays.asList(supportedGameObjects)));
	}

	public Support makeCopy(Square square, Actor owner, ArrayList<GameObject> supportedGameObjects) {

		Support support = new Support();
		setInstances(support);
		super.setAttributesForCopy(support, square, owner);
		support.supportedGameObjects = supportedGameObjects;
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

		ArrayList<GameObject> surroundingGameObjects = collapser.getSurroundingGameObjects();
		ArrayList<GameObject> surroundingObjectsToCollapse = Utils.intersection(surroundingGameObjects,
				supportedGameObjects);
		for (GameObject s : surroundingObjectsToCollapse) {

			final GameObject surroundingObjectToCollapse = s;

			if (surroundingObjectToCollapse.remainingHealth > 0) {
				OnCompletionListener onShakeCompletionListener = new OnCompletionListener() {

					@Override
					public void animationComplete(GameObject gameObject) {

						surroundingObjectToCollapse.changeHealthSafetyOff(-surroundingObjectToCollapse.remainingHealth,
								attacker, action);

						collapseSurroundingObjects(surroundingObjectToCollapse, attacker, action);
						Level.player.calculateVisibleSquares(Level.player.squareGameObjectIsOn);
					}
				};

				surroundingObjectToCollapse.setPrimaryAnimation(
						new AnimationShake(surroundingObjectToCollapse, onShakeCompletionListener, true, 500));
			}
		}

	}
}
