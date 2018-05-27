package com.marklynch.level.constructs.power;

import org.lwjgl.util.Point;

import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.ResourceUtils;

public class PowerSpark extends Power {

	private static String NAME = "Spark";

	public PowerSpark(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("action_burn.png", false), source, new Effect[] {}, 5,
				new Point[] { new Point(0, 0) }, 10, true, true, Crime.TYPE.CRIME_ASSAULT);
		selectTarget = true;
	}

	@Override
	public Power makeCopy(GameObject source) {
		return new PowerSpark(source);
	}
}
