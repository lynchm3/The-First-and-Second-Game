package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;
import com.marklynch.objects.units.Actor.Direction;
import com.marklynch.utils.Texture;

public class Rail extends GameObject {

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
		if ((direction1 == Direction.LEFT || direction1 == Direction.RIGHT)
				&& (direction2 == Direction.LEFT || direction2 == Direction.RIGHT)) {
			// left right
			rail.imageTexture = imageTextureLeftRight;
		} else if ((direction1 == Direction.UP || direction1 == Direction.DOWN)
				&& (direction2 == Direction.UP || direction2 == Direction.DOWN)) {
			// up down
			rail.imageTexture = imageTextureUpDown;
		} else if ((direction1 == Direction.UP || direction1 == Direction.LEFT)
				&& (direction2 == Direction.UP || direction2 == Direction.LEFT)) {
			// up left
			rail.imageTexture = imageTextureLeftUp;
		} else if ((direction1 == Direction.DOWN || direction1 == Direction.RIGHT)
				&& (direction2 == Direction.DOWN || direction2 == Direction.RIGHT)) {
			// down right
			rail.imageTexture = imageTextureRightDown;
		} else if ((direction1 == Direction.UP || direction1 == Direction.RIGHT)
				&& (direction2 == Direction.UP || direction2 == Direction.RIGHT)) {
			// up right
			rail.imageTexture = imageTextureRightUp;
		} else if ((direction1 == Direction.DOWN || direction1 == Direction.LEFT)
				&& (direction2 == Direction.DOWN || direction2 == Direction.LEFT)) {
			// down left
			rail.imageTexture = imageTextureLeftDown;
		} else {
			rail.imageTexture = imageTextureLeftRight;

		}

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

}
