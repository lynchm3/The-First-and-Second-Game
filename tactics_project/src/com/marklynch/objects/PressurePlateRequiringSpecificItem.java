package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.ai.utils.AILine;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;

public class PressurePlateRequiringSpecificItem extends Switch implements UpdatesWhenSquareContentsChange {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

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
		setInstances(pressurePlate);
		super.setAttributesForCopy(pressurePlate, square, owner);
		pressurePlate.actionName = actionName;
		pressurePlate.actionVerb = actionVerb;
		pressurePlate.switchType = switchType;
		pressurePlate.keyObject = keyObject;

		for (SwitchListener switchListener : switchListeners) {
			if (switchListener != null && switchListener instanceof GameObject)
				this.aiLine = new AILine(AILine.AILineType.AI_LINE_TYPE_SWITCH, pressurePlate,
						((GameObject) switchListener).squareGameObjectIsOn);
		}

		return pressurePlate;
	}

}
