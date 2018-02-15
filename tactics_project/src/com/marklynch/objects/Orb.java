package com.marklynch.objects;

import java.util.ArrayList;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.units.Actor;

public class Orb extends GameObject {

	public static final ArrayList<GameObject> instances = new ArrayList<GameObject>();

	public Orb() {
		super();

		attackable = false;

	}

	@Override
	public String toString() {
		return "" + value + "XP";
	}

	public Orb makeCopy(Square square, Actor owner, int value) {
		Orb orb = new Orb();
		instances.add(orb);

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
			this.drawOffsetRatioX = (float) (Math.random() * drawOffsetXMax);
			this.drawOffsetRatioY = (float) (Math.random() * drawOffsetYMax);
		}
	}
}
