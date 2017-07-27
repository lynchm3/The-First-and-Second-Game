package com.marklynch.level.constructs.power;

import java.util.ArrayList;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;

import mdesl.graphics.Texture;

public abstract class PowerSelf extends Power {

	public PowerSelf(String name, Texture image, GameObject source, GameObject target, Effect[] effects, int loudness) {
		super(name, image, source, target, effects, loudness);
		// TODO Auto-generated constructor stub
	}

	@Override
	public abstract void cast(GameObject souce, Square square);

	@Override
	public abstract ArrayList<Square> getAffectedSquares(Square target);

	@Override
	public boolean hasRange(int weaponDistanceTo) {

		if (weaponDistanceTo == 0)
			return true;

		return false;

		// if (getEffectiveMinRange() == 1 && weaponDistanceTo == 0)
		// return true;
		//
		// if (weaponDistanceTo >= getEffectiveMinRange() && weaponDistanceTo <=
		// getEffectiveMaxRange()) {
		// return true;
		// }
		// return false;
	}
}
