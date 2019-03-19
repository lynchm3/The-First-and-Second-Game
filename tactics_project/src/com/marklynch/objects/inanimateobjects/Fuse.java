package com.marklynch.objects.inanimateobjects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.actors.Actor.Direction;
import com.marklynch.objects.utils.SwitchListener;
import com.marklynch.utils.ArrayList;
import com.marklynch.utils.Texture;

public class Fuse extends Line implements SwitchListener {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>(GameObject.class);

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

	public Fuse() {
		super();

		canBePickedUp = false;
		fitsInInventory = false;
		persistsWhenCantBeSeen = true;
		attackable = false;
		isFloorObject = true;
		moveable = false;
		orderingOnGound = 20;
		type = "Fuse";

		super.imageTextureLeftRight = imageTextureLeftRightStatic;
		super.imageTextureUpDown = imageTextureUpDownStatic;
		super.imageTextureLeftUp = imageTextureLeftUpStatic;
		super.imageTextureRightUp = imageTextureRightUpStatic;
		super.imageTextureLeftDown = imageTextureLeftDownStatic;
		super.imageTextureRightDown = imageTextureRightDownStatic;
		super.imageTextureLeftBufferStop = imageTextureLeftBufferStopStatic;
		super.imageTextureRightBufferStop = imageTextureRightBufferStopStatic;
		super.imageTextureUpBufferStop = imageTextureUpBufferStopStatic;
		super.imageTextureDownBufferStop = imageTextureDownBufferStopStatic;
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

	public Fuse makeCopy(Square square, Actor owner, Direction direction1, Direction direction2) {
		Fuse fuse = new Fuse();
		setInstances(fuse);
		super.setAttributesForCopy(fuse, square, owner);
		fuse.direction1 = direction1;
		fuse.direction2 = direction2;

		fuse.updateImageTextures();

		fuse.updateNeighborLines();

		return fuse;
	}

	@Override
	public void zwitch(Switch zwitch) {
	}

}
