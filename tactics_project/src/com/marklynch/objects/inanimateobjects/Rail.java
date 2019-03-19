package com.marklynch.objects.inanimateobjects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Actor.Direction;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Texture;

public class Rail extends Line implements SwitchListener {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

	public boolean turnsClockwiseFirst = true;

	public static Texture imageTextureLeftRightStatic;
	public static Texture imageTextureUpDownStatic;
	public static Texture imageTextureLeftUpStatic;
	public static Texture imageTextureRightUpStatic;
	public static Texture imageTextureLeftDownStatic;
	public static Texture imageTextureRightDownStatic;
	public static Texture imageTextureLeftBufferStopStatic;
	public static Texture imageTextureRightBufferStopStatic;
	public static Texture imageTextureUpBufferStopStatic;
	public static Texture imageTextureDownBufferStopStatic;

	public Rail() {
		super();

		canBePickedUp = false;
		fitsInInventory = false;
		persistsWhenCantBeSeen = true;
		attackable = false;
		isFloorObject = true;
		moveable = false;
		orderingOnGound = 20;

		type = "Rail";

		super.imageTextureLeftRight = imageTextureLeftRightStatic;
		super.imageTextureUpDown = imageTextureUpDownStatic;
		super.imageTextureLeftUp = imageTextureLeftUpStatic;
		super.imageTextureRightUp = imageTextureRightUpStatic;
		super.imageTextureLeftDown = imageTextureLeftDownStatic;
		super.imageTextureRightDown = imageTextureRightDownStatic;
		super.imageTextureLeftEnd = imageTextureLeftBufferStopStatic;
		super.imageTextureRightEnd = imageTextureRightBufferStopStatic;
		super.imageTextureUpEnd = imageTextureUpBufferStopStatic;
		super.imageTextureDownEnd = imageTextureDownBufferStopStatic;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	public Rail makeCopy(Square square, Actor owner, Direction direction1, Direction direction2) {
		Rail rail = new Rail();
		setInstances(rail);
		super.setAttributesForCopy(rail, square, owner);
		rail.direction1 = direction1;
		rail.direction2 = direction2;

		rail.updateImageTextures();

		rail.updateNeighborLines();

		return rail;
	}

	@Override
	public void zwitch(Switch zwitch) {
		// rotate 90 degrees
		// if(direction)
		if (turnsClockwiseFirst) {
			if (zwitch.pressed) {
				direction1 = rotate90Degrees(direction1);
				direction2 = rotate90Degrees(direction2);
			} else {

				direction1 = rotateMinus90Degrees(direction1);
				direction2 = rotateMinus90Degrees(direction2);
			}
		} else {

			if (zwitch.pressed) {
				direction1 = rotateMinus90Degrees(direction1);
				direction2 = rotateMinus90Degrees(direction2);
			} else {

				direction1 = rotate90Degrees(direction1);
				direction2 = rotate90Degrees(direction2);
			}
		}

		this.showPow();
		updateImageTextures();
		updateNeighborLines();

	}

}
