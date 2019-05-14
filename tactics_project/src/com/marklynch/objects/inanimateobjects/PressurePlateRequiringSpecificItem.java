package com.marklynch.objects.inanimateobjects;

import com.marklynch.ai.utils.AILine;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.utils.ArrayList;

public class PressurePlateRequiringSpecificItem extends Switch {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public GameObject keyObject = null;

	public PressurePlateRequiringSpecificItem() {
		super();

		canBePickedUp = false;
		fitsInInventory = false;
		persistsWhenCantBeSeen = true;
		attackable = false;
		isFloorObject = true;
		moveable = false;
		orderingOnGound = 30;
		type = "Pressure Plate with hole";
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	// @Override
	// public void update(int delta) {
	// super.update(delta);
	// }

	@Override
	public void squareContentsChanged() {

		if (squareGameObjectIsOn == null)
			return;
		doTheThing(null);

	}

	public void doTheThing(final GameObject g) {

		if (squareGameObjectIsOn == null)
			return;

		boolean keyObjectOnPlate = false;
		for (GameObject gameObject : squareGameObjectIsOn.inventory.gameObjects) {
			if (gameObject == this.keyObject) {
				keyObjectOnPlate = true;
				break;
			}
		}

		if (pressed && !keyObjectOnPlate) {
			pressed = false;
			use();
		} else if (!pressed && keyObjectOnPlate) {
			pressed = true;
			use();
		}

	}

	@Override
	public void use() {
		for (SwitchListener switchListener : switchListeners)
			switchListener.zwitch(this);
	}

	public PressurePlateRequiringSpecificItem makeCopy(Square square, Actor owner, SWITCH_TYPE switchType,
			GameObject keyObject, SwitchListener... switchListeners) {

		PressurePlateRequiringSpecificItem pressurePlate = new PressurePlateRequiringSpecificItem();
		pressurePlate.switchListeners = switchListeners;
		for (SwitchListener switchListener : switchListeners) {

			if (switchListener instanceof GameObject) {
				GameObject switchListenerGameObject = (GameObject) switchListener;
				pressurePlate.linkedObjects.add(switchListenerGameObject);
				switchListenerGameObject.linkedObjects.add(pressurePlate);
			}
		}

		setInstances(pressurePlate);
		super.setAttributesForCopy(pressurePlate, square, owner);
		pressurePlate.actionName = actionName;
		pressurePlate.actionVerb = actionVerb;
		pressurePlate.switchType = switchType;
		pressurePlate.keyObject = keyObject;
		keyObject.linkedObjects.add(pressurePlate);
		pressurePlate.linkedObjects.add(keyObject);

		for (SwitchListener switchListener : switchListeners) {
			if (switchListener != null && switchListener instanceof GameObject)
				this.aiLine = new AILine(AILine.AILineType.AI_LINE_TYPE_SWITCH, pressurePlate,
						((GameObject) switchListener).squareGameObjectIsOn);
		}

		return pressurePlate;
	}

}
