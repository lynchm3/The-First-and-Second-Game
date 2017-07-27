package com.marklynch.level.constructs.power;

import java.util.ArrayList;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;

public abstract class Power {

	String name;
	GameObject source;
	GameObject target;
	Effect[] effects;

	public Power(String name, GameObject source, GameObject target, Effect[] effects) {
		super();
		this.name = name;
		this.source = source;
		this.target = target;
		this.effects = effects;
	}

	public abstract void cast(GameObject souce, Square target);

	public abstract ArrayList<Square> getAffectedSquares(Square target);

}
