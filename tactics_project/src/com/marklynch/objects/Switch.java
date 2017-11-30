package com.marklynch.objects;

public abstract class Switch extends GameObject {

	public Switch() {
		super();
		canBePickedUp = false;
		showInventory = false;
		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = false;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = true;
		attackable = true;
	}

	public abstract void use();

}
