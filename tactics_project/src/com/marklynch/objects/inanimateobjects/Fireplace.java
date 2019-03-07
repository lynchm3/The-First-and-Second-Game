package com.marklynch.objects.inanimateobjects;

import com.marklynch.Game;
import com.marklynch.level.constructs.effect.EffectBurn;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.tools.FlammableLightSource;
import com.marklynch.objects.utils.UpdatableGameObject;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.ArrayList;

public class Fireplace extends FlammableLightSource implements UpdatableGameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public Fireplace() {
		super();
		type = "Fireplace";
		moveable = false;
		fitsInInventory = false;
		canContainOtherObjects = false;
		persistsWhenCantBeSeen = false;
		canBePickedUp = false;
		floatsInWater = false;
		canShareSquare = false;
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public Fireplace makeCopy(Square square, Actor owner) {
		Fireplace firePlace = new Fireplace();
		setInstances(firePlace);
		setAttributesForCopy(firePlace, square, owner);
		firePlace.imageTextureUnlit = this.imageTextureUnlit;
		firePlace.imageTextureLit = this.imageTextureLit;
		firePlace.setLighting(this.lit);
		return firePlace;
	}

	@Override
	public void update() {

		if (!lit)
			return;

		for (GameObject gameObject : this.squareGameObjectIsOn.inventory.gameObjects) {

			if (gameObject == this || gameObject.attackable == false)
				continue;

			EffectBurn effectBurning = new EffectBurn(this, gameObject, 3);

			if (!gameObject.hasActiveEffectOfType(EffectBurn.class)) {
				if (Game.level.shouldLog(gameObject)) {
					Game.level.logOnScreen(new ActivityLog(new Object[] { effectBurning, " spread to ", gameObject }));
				}
			}

			gameObject.removeWetEffect();
			gameObject.addEffect(effectBurning);
		}

	}

}
