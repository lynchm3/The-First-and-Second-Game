package com.marklynch.objects.inanimateobjects;

import com.marklynch.ai.utils.AILine;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.utils.CopyOnWriteArrayList;

public class PressurePlate extends Switch {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);

	public int targetWeight = 10;

	public PressurePlate() {
		super();
		canBePickedUp = false;
		fitsInInventory = false;
		persistsWhenCantBeSeen = true;
		attackable = false;
		isFloorObject = true;
		moveable = false;
		orderingOnGound = 30;
		type = "Pressure Plate";
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

		int weightOnPlate = 0;

		if (squareGameObjectIsOn == null)
			return;

		for (GameObject gameObject : squareGameObjectIsOn.inventory.gameObjects) {
			if (gameObject.isFloorObject == false) {
				weightOnPlate += gameObject.weight;
			}
		}

		if (pressed == false && weightOnPlate >= targetWeight) {
			pressed = true;
			use();
		} else if (pressed == true && weightOnPlate < targetWeight) {
			pressed = false;
			use();
		}

	}

	@Override
	public void use() {
		showPow();
		for (SwitchListener switchListener : switchListeners)
			switchListener.zwitch(this);
	}

	public PressurePlate makeCopy(Square square, Actor owner, SWITCH_TYPE switchType, int targetWeight,
			SwitchListener... switchListeners) {

		PressurePlate pressurePlate = new PressurePlate();
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
		pressurePlate.targetWeight = targetWeight;

		for (SwitchListener switchListener : switchListeners) {
			if (switchListener != null && switchListener instanceof GameObject)
				this.aiLine = new AILine(AILine.AILineType.AI_LINE_TYPE_SWITCH, pressurePlate,
						((GameObject) switchListener).squareGameObjectIsOn);
		}

		return pressurePlate;
	}

}
