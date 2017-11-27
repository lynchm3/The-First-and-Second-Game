package com.marklynch.objects;

import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Texture;

public class BigGameObject extends GameObject {

	Texture chestOpenTexture;
	Texture chestClosedTexture;

	public BigGameObject() {
		super();
		canBePickedUp = false;
		showInventory = false;
		fitsInInventory = false;
		canShareSquare = false;
		canContainOtherObjects = false;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = false;
		attackable = true;
	}

	@Override
	public BigGameObject makeCopy(Square square, Actor owner) {
		BigGameObject bigGameObject = new BigGameObject();
		super.setAttributesForCopy(bigGameObject, square, owner);
		return bigGameObject;
	}

}
