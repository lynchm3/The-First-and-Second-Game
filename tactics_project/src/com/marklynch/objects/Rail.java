package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Actor.Direction;
import com.marklynch.utils.Texture;

public class Rail extends GameObject implements SwitchListener {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();
	Direction direction1;
	Direction direction2;
	public static Texture imageTextureLeftRight;
	public static Texture imageTextureUpDown;
	// boolean up, down, left, right;
	public static Texture imageTextureLeftUp;
	public static Texture imageTextureRightUp;
	public static Texture imageTextureLeftDown;
	public static Texture imageTextureRightDown;

	public Rail() {
		super();
		canBePickedUp = false;
		fitsInInventory = false;
		isFloorObject = true;
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

		rail.updateImageTexture();

		return rail;
	}

	public Direction getOppositeDirection(Direction direction) {
		if (direction1 == direction) {
			return direction2;
		} else if (direction2 == direction) {
			return direction1;
		}
		return null;
	}

	@Override
	public void zwitch(Switch zwitch) {
		// rotate 90 degrees
		// if(direction)
		direction1 = rotate90Degrees(direction1);
		direction2 = rotate90Degrees(direction2);
		updateImageTexture();

	}

	public Direction rotate90Degrees(Direction direction) {
		if (direction == Direction.RIGHT) {
			return Direction.DOWN;
		} else if (direction == Direction.DOWN) {
			return Direction.LEFT;
		} else if (direction == Direction.LEFT) {
			return Direction.UP;
		} else if (direction == Direction.UP) {
			return Direction.RIGHT;
		}
		return Direction.DOWN;

	}

	public void updateImageTexture() {
		if ((direction1 == Direction.LEFT || direction1 == Direction.RIGHT)
				&& (direction2 == Direction.LEFT || direction2 == Direction.RIGHT)) {
			// left right
			imageTexture = imageTextureLeftRight;
		} else if ((direction1 == Direction.UP || direction1 == Direction.DOWN)
				&& (direction2 == Direction.UP || direction2 == Direction.DOWN)) {
			// up down
			imageTexture = imageTextureUpDown;
		} else if ((direction1 == Direction.UP || direction1 == Direction.LEFT)
				&& (direction2 == Direction.UP || direction2 == Direction.LEFT)) {
			// up left
			imageTexture = imageTextureLeftUp;
		} else if ((direction1 == Direction.DOWN || direction1 == Direction.RIGHT)
				&& (direction2 == Direction.DOWN || direction2 == Direction.RIGHT)) {
			// down right
			imageTexture = imageTextureRightDown;
		} else if ((direction1 == Direction.UP || direction1 == Direction.RIGHT)
				&& (direction2 == Direction.UP || direction2 == Direction.RIGHT)) {
			// up right
			imageTexture = imageTextureRightUp;
		} else if ((direction1 == Direction.DOWN || direction1 == Direction.LEFT)
				&& (direction2 == Direction.DOWN || direction2 == Direction.LEFT)) {
			// down left
			imageTexture = imageTextureLeftDown;
		} else {
			imageTexture = imageTextureLeftRight;
		}

	}

}
