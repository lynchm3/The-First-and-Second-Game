package com.marklynch.objects;

import com.marklynch.Game;
import com.marklynch.ai.utils.AILine;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class PressurePlate extends Switch {

	boolean pressed = false;
	int targetWeight = 10;

	public PressurePlate() {
		super();
		attackable = false;
	}

	@Override
	public void draw2() {
		super.draw1();
		super.draw2();
		if (Game.showTriggerLines) {
			if (switchListener instanceof GameObject) {
				aiLine.target = ((GameObject) switchListener).squareGameObjectIsOn;
			}
			aiLine.draw2();
		}
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		if (squareGameObjectIsOn == null)
			return;
		int weightOnPlate = 0;
		for (GameObject gameObject : squareGameObjectIsOn.inventory.gameObjects) {
			if (gameObject != this) {
				weightOnPlate += gameObject.weight;
			}
		}

		if (pressed == false && weightOnPlate >= targetWeight) {
			use();
			pressed = true;
		} else if (pressed == true && weightOnPlate < targetWeight) {
			use();
			pressed = false;
		}
	}

	public PressurePlate makeCopy(Square square, Actor owner, SwitchListener switchListener, SWITCH_TYPE switchType,
			int targetWeight) {

		PressurePlate pressurePlate = new PressurePlate();
		super.setAttributesForCopy(pressurePlate, square, owner);
		pressurePlate.actionName = actionName;
		pressurePlate.actionVerb = actionVerb;
		pressurePlate.switchListener = switchListener;
		pressurePlate.switchType = switchType;
		pressurePlate.targetWeight = targetWeight;
		if (switchListener != null)
			this.aiLine = new AILine(AILine.AILineType.AI_LINE_TYPE_SWITCH, this,
					((GameObject) switchListener).squareGameObjectIsOn);

		return pressurePlate;
	}

}
