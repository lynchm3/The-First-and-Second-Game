package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.constructs.animation.primary.AnimationScale;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class VoidHole extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

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
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public VoidHole makeCopy(Square square, Actor owner) {
		VoidHole searchable = new VoidHole();
		setInstances(searchable);
		super.setAttributesForCopy(searchable, square, owner);
		return searchable;
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		if (squareGameObjectIsOn != null) {
			for (final GameObject gameObject : (ArrayList<GameObject>) squareGameObjectIsOn.inventory.gameObjects
					.clone()) {
				if (gameObject != this) {
					gameObject.setPrimaryAnimation(new AnimationScale(gameObject, 1f, 0f, 500) {
						@Override
						public void runCompletionAlgorightm() {
							super.runCompletionAlgorightm();
							squareGameObjectIsOn.inventory.remove(gameObject);
						}
					});

				}
			}
		}
	}

}
