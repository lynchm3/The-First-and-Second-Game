package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.ai.utils.AILine;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.Texture;

public class Seesaw extends GameObject implements SwitchListener {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Square square1;
	public Square square2;
	public Seesaw.SeesawPart pressurePlate1;
	public Seesaw.SeesawPart pressurePlate2;

	public static Texture imageTextureLeftRight;
	public static Texture imageTextureUpDown;
	// boolean up, down, left, right;
	public static Texture imageTextureLeftUp;
	public static Texture imageTextureRightUp;
	public static Texture imageTextureLeftDown;
	public static Texture imageTextureRightDown;
	public boolean turnsClockwiseFirst = true;

	public Seesaw() {
		super();
		canBePickedUp = false;
		fitsInInventory = false;
		isFloorObject = true;
		attackable = false;
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public void draw1() {
		super.draw1();
		super.draw2();
		QuadUtils.drawQuad(Color.RED, square1.xInGridPixels, square1.yInGridPixels, square2.xInGridPixels,
				square2.yInGridPixels + 10);
	}

	public Seesaw makeCopy(Square square, Actor owner, Square square1, Square square2) {
		System.out.println("Seesaw.makeCopy()");

		Seesaw seesaw = new Seesaw();
		setInstances(seesaw);

		super.setAttributesForCopy(seesaw, square, owner);

		seesaw.square1 = square1;
		seesaw.square2 = square2;
		System.out.println("calling pressurePlate1.makeCopy()");
		seesaw.pressurePlate1 = Templates.SEESAW_PART.makeCopy(square1, null, Switch.SWITCH_TYPE.OPEN_CLOSE, 0, seesaw);
		System.out.println("calling pressurePlate2.makeCopy()");
		seesaw.pressurePlate2 = Templates.SEESAW_PART.makeCopy(square2, null, Switch.SWITCH_TYPE.OPEN_CLOSE, 0, seesaw);

		return seesaw;
	}

	@Override
	public void zwitch(Switch zwitch) {
		System.out.println("Seesaw.zwitch()");
		if (pressurePlate1 == null || pressurePlate2 == null)
			return;

		if (pressurePlate1.weightOnPlate > pressurePlate2.weightOnPlate) {
			// pressurePlate1.

		} else if (pressurePlate1.weightOnPlate > pressurePlate2.weightOnPlate) {

		} else {
			// equal weight
		}
	}

	public static class SeesawPart extends PressurePlate {

		public int weightOnPlate = 0;

		@Override
		public void updateWeight() {
			if (squareGameObjectIsOn == null)
				return;
			weightOnPlate = 0;
			for (GameObject gameObject : squareGameObjectIsOn.inventory.gameObjects) {
				if (gameObject.isFloorObject == false) {
					weightOnPlate += gameObject.weight;
				}
			}
			use();

		}

		@Override
		public SeesawPart makeCopy(Square square, Actor owner, SWITCH_TYPE switchType, int targetWeight,
				SwitchListener... switchListeners) {
			System.out.println("in SeesawPart.makeCopy()");

			SeesawPart seesawPart = new SeesawPart();
			seesawPart.switchListeners = switchListeners;
			setInstances(seesawPart);
			super.setAttributesForCopy(seesawPart, square, owner);
			seesawPart.actionName = actionName;
			seesawPart.actionVerb = actionVerb;
			System.out.println("SeesawPart.switchListeners = " + seesawPart.switchListeners);
			seesawPart.switchType = switchType;
			seesawPart.targetWeight = targetWeight;

			for (SwitchListener switchListener : switchListeners) {
				if (switchListener != null && switchListener instanceof GameObject)
					this.aiLine = new AILine(AILine.AILineType.AI_LINE_TYPE_SWITCH, seesawPart,
							((GameObject) switchListener).squareGameObjectIsOn);
			}

			return seesawPart;
		}
	}

}
