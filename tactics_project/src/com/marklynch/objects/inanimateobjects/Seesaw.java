package com.marklynch.objects.inanimateobjects;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.templates.Templates;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Color;
import com.marklynch.utils.QuadUtils;
import com.marklynch.utils.Texture;
import com.marklynch.utils.TextureUtils;

public class Seesaw extends GameObject implements SwitchListener {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public Square square1;
	public Square square2;
	public SeesawPart pressurePlate1;
	public SeesawPart pressurePlate2;

	public static Texture gradientRightUp;
	public static Texture gradientLeftUp;

	public Seesaw() {
		super();
		canBePickedUp = false;
		fitsInInventory = false;
		isFloorObject = true;
		moveable = false;
		attackable = false;
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void draw2() {
		super.draw2();
		QuadUtils.drawQuad(Color.WHITE, square1.xInGridPixels + Game.SQUARE_WIDTH, square1.getCenterY() - 5,
				square2.xInGridPixels, square2.getCenterY() + 5);

		if (pressurePlate1.up == true && pressurePlate2.up == true) {

		} else {
			int actorPositionXInPixels = (int) (this.squareGameObjectIsOn.xInGridPixels + drawOffsetX);
			int actorPositionYInPixels = (int) (this.squareGameObjectIsOn.yInGridPixels + drawOffsetY);

			if (pressurePlate1.up) {
				TextureUtils.drawTexture(gradientLeftUp, 1f, actorPositionXInPixels - 256, actorPositionYInPixels,
						actorPositionXInPixels + 384, actorPositionYInPixels + 128);
			} else {
				TextureUtils.drawTexture(gradientRightUp, 1f, actorPositionXInPixels - 256, actorPositionYInPixels,
						actorPositionXInPixels + 384, actorPositionYInPixels + 128);
			}
		}
	}

	public Seesaw makeCopy(Square square, Actor owner, Square square1, Square square2, Square connectedSquare) {

		Seesaw seesaw = new Seesaw();
		setInstances(seesaw);

		super.setAttributesForCopy(seesaw, square, owner);

		seesaw.square1 = square1;
		seesaw.square2 = square2;
		seesaw.pressurePlate1 = Templates.SEESAW_PART.makeCopy(square1, null, Switch.SWITCH_TYPE.OPEN_CLOSE, 0,
				connectedSquare, seesaw);
		seesaw.pressurePlate1.up = true;
		seesaw.pressurePlate2 = Templates.SEESAW_PART.makeCopy(square2, null, Switch.SWITCH_TYPE.OPEN_CLOSE, 0,
				connectedSquare, seesaw);
		seesaw.pressurePlate1.up = true;

		return seesaw;
	}

	@Override
	public void zwitch(Switch zwitch) {
		if (pressurePlate1 == null || pressurePlate2 == null)
			return;

		if (pressurePlate1.weightOnPlate > pressurePlate2.weightOnPlate) {
			// pressurePlate1.
			if (pressurePlate1.up == false && pressurePlate2.up == true) {
				// already in position
			} else {
				pressurePlate1.up = false;
				pressurePlate2.up = true;
				moveSeesaw();
			}

		} else if (pressurePlate2.weightOnPlate > pressurePlate1.weightOnPlate) {
			if (pressurePlate1.up == true && pressurePlate2.up == false) {
				// already in position
			} else {
				pressurePlate1.up = true;
				pressurePlate2.up = false;
				moveSeesaw();
			}

		} else {
			if (pressurePlate1.up == true && pressurePlate2.up == true) {
				// already in position
			} else {
				pressurePlate1.up = true;
				pressurePlate2.up = true;
				moveSeesaw();
			}
		}
	}

	public void moveSeesaw() {
		if (pressurePlate1.weightOnPlate > pressurePlate2.weightOnPlate) {
			// pressurePlate1.
			if (pressurePlate1.up == false) {
				// already in position
			} else {
				pressurePlate1.up = false;
			}

			if (pressurePlate2.up == true) {
				// already in position
			} else {
				pressurePlate2.up = true;
			}

		} else if (pressurePlate2.weightOnPlate > pressurePlate1.weightOnPlate) {
			if (pressurePlate1.up == true) {
				// already in position
			} else {
				pressurePlate1.up = true;
			}

			if (pressurePlate2.up == false) {
				// already in position
			} else {
				pressurePlate2.up = false;

			}

		} else {
			if (pressurePlate1.up == true) {
				// already in position
			} else {
				pressurePlate1.up = true;
			}

			if (pressurePlate2.up == true) {
				// already in position
			} else {
				pressurePlate2.up = true;
			}
		}
	}

}
