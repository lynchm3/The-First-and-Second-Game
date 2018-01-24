package com.marklynch.objects;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Orb extends GameObject {

	public Orb() {
		super();
		canBePickedUp = true;
		showInventory = false;
		fitsInInventory = true;
		canShareSquare = true;
		canContainOtherObjects = false;
		blocksLineOfSight = false;
		persistsWhenCantBeSeen = false;
		attackable = false;

	}

	@Override
	public String toString() {
		return "" + value + "XP";
	}

	public Orb makeCopy(Square square, Actor owner, int value) {
		Orb orb = new Orb();

		super.setAttributesForCopy(orb, square, owner);
		orb.value = value;
		// offsetX =

		return orb;

	}

	@Override
	public void randomisePosition() {
		if (widthRatio < 1f && heightRatio < 1f) {
			float drawOffsetXMax = 1 - width / Game.SQUARE_WIDTH;
			float drawOffsetYMax = 1 - height / Game.SQUARE_WIDTH;
			if (drawOffsetYMax < 0) {
				drawOffsetYMax = 0;
			}
			this.drawOffsetX = (float) (Math.random() * drawOffsetXMax);
			this.drawOffsetY = (float) (Math.random() * drawOffsetYMax);
		}
	}
}
