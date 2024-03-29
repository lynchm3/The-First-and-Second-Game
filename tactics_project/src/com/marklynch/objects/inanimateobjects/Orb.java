package com.marklynch.objects.inanimateobjects;

import com.marklynch.Game;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.CopyOnWriteArrayList;

public class Orb extends GameObject {

	public static final CopyOnWriteArrayList<GameObject> instances = new CopyOnWriteArrayList<GameObject>(GameObject.class);

	public Orb() {
		super();

		attackable = false;
		type = "Orb";

	}

	@Override
	public void setInstances(GameObject gameObject) {
		instances.add(gameObject);
		super.setInstances(gameObject);
	}

	@Override
	public String toString() {
		return "" + value + "XP";
	}

	public Orb makeCopy(Square square, Actor owner, int value) {
		Orb orb = new Orb();
		setInstances(orb);

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
			this.drawOffsetX = this.drawOffsetRatioX * Game.SQUARE_WIDTH;
			this.drawOffsetRatioY = (float) (Math.random() * drawOffsetYMax);
			this.drawOffsetY = this.drawOffsetRatioY * Game.SQUARE_HEIGHT;

		}
	}
}
