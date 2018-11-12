package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.ai.utils.AILine;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class PressurePlate extends Switch implements UpdatesWhenSquareContentsChange {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	int targetWeight = 10;

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

	@Override
	public void draw2() {
		super.draw2();
		if (Game.showTriggerLines) {
			for (SwitchListener switchListener : switchListeners) {
				if (switchListener instanceof GameObject) {
					aiLine.target = ((GameObject) switchListener).squareGameObjectIsOn;
				}
				aiLine.draw2();
			}
		}
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
		for (SwitchListener switchListener : switchListeners)
			switchListener.zwitch(this);
	}

	public PressurePlate makeCopy(Square square, Actor owner, SWITCH_TYPE switchType, int targetWeight,
			SwitchListener... switchListeners) {

		PressurePlate pressurePlate = new PressurePlate();
		pressurePlate.switchListeners = switchListeners;
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
