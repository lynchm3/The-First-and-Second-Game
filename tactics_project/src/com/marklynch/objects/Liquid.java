package com.marklynch.objects;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Liquid extends GameObject {
	public float volume;
	public Effect[] touchEffects;
	public Effect[] drinkEffects;

	public Liquid() {
		super();
		canBePickedUp = false;
		showInventoryInGroundDisplay = false;
		fitsInInventory = false;
		canShareSquare = true;
		canContainOtherObjects = false;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = false;
		attackable = false;
	}

	public Liquid makeCopy(Square square, Actor owner, float volume) {
		Liquid liquid = new Liquid();
		super.setAttributesForCopy(liquid, square, owner);
		liquid.volume = volume;
		liquid.touchEffects = touchEffects;
		liquid.drinkEffects = drinkEffects;
		return liquid;
	}
}
