package com.marklynch.level.constructs.power;

import java.util.ArrayList;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;

public abstract class PowerTouch extends Power {

	public PowerTouch(String name, GameObject source, GameObject target, Effect[] effects) {
		super(name, source, target, effects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public abstract void cast(GameObject souce, Square square);

	@Override
	public abstract ArrayList<Square> getAffectedSquares(Square target);

}
