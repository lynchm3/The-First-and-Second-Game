package com.marklynch.level.constructs.power;

import java.util.ArrayList;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;

import mdesl.graphics.Texture;

public abstract class PowerTouch extends Power {

	public PowerTouch(String name, Texture image, GameObject source, GameObject target, Effect[] effects, int loudness,
			boolean hostile, boolean potentiallyCriminal) {
		super(name, image, source, target, effects, loudness, hostile, potentiallyCriminal);
		// TODO Auto-generated constructor stub
	}

	@Override
	public abstract void cast(Actor souce, Square square);

	@Override
	public abstract ArrayList<Square> getAffectedSquares(Square target);

	@Override
	public boolean hasRange(int weaponDistanceTo) {

		if (weaponDistanceTo <= 1)
			return true;

		return false;
	}

}
