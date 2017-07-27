package com.marklynch.level.constructs.power;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;

public class PowerSelf extends Power {

	public PowerSelf(String name, GameObject source, GameObject target, Effect[] effects) {
		super(name, source, target, effects);
		// TODO Auto-generated constructor stub
	}

	public void cast(GameObject source) {
		this.cast(source, source.squareGameObjectIsOn);
	}

	@Override
	public void cast(GameObject souce, Square square) {
		// TODO Auto-generated method stub

	}

}
