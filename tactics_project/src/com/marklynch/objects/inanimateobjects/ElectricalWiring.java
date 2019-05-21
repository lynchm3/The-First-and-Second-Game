package com.marklynch.objects.inanimateobjects;

import com.marklynch.Game;
import com.marklynch.level.constructs.effect.EffectShock;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.objects.utils.UpdatableGameObject;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.ArrayList;

public class ElectricalWiring extends GameObject implements UpdatableGameObject, SwitchListener {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);
	public boolean on = true;

	public ElectricalWiring() {
		super();
		type = "Electrical Wiring";
		moveable = false;
		fitsInInventory = false;
		canContainOtherObjects = false;
		persistsWhenCantBeSeen = false;
		canBePickedUp = false;
		floatsInWater = false;
		canShareSquare = true;
		isFloorObject = true;
		orderingOnGound = 101;
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public ElectricalWiring makeCopy(Square square, Actor owner) {
		ElectricalWiring electricalWiring = new ElectricalWiring();
		setInstances(electricalWiring);
		setAttributesForCopy(electricalWiring, square, owner);
//		electricalWiring.imageTextureUnlit = this.imageTextureUnlit;
//		electricalWiring.imageTextureLit = this.imageTextureLit;
//		electricalWiring.setLighting(this.lit);
		return electricalWiring;
	}

//	Power powerSpark = new PowerSpark(this);

	@Override
	public void update() {

		if (!on)
			return;

//		new ActionUsePower(this, null, this.squareGameObjectIsOn, powerSpark, true);

		for (GameObject gameObject : this.squareGameObjectIsOn.inventory.gameObjects) {

			if (gameObject == this)
				continue;

			if (gameObject.attackable == false && !(gameObject instanceof Liquid))
				continue;

			EffectShock effectShock = new EffectShock(this, gameObject, 3);
			gameObject.addEffect(effectShock);

//			if (!gameObject.hasActiveEffectOfType(EffectShock.class)) {
			if (Game.level.shouldLog(gameObject)) {
				Game.level.logOnScreen(new ActivityLog(new Object[] { effectShock, " spread to ", gameObject }));
			}
//			}
		}

	}

	@Override
	public void zwitch(Switch zwitch) {
		on = !on;
	}

}
