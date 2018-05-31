package com.marklynch.level.constructs.power;

import org.lwjgl.util.Point;

import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectWet;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.ResourceUtils;

public class PowerDouse extends Power {

	private static String NAME = "Douse";

	public PowerDouse(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("action_douse.png", false), source,
				new Effect[] { new EffectWet(source, null, 3) }, 5, new Point[] { new Point(0, 0) }, 10, true, true,
				Crime.TYPE.CRIME_DOUSE);
		selectTarget = true;
	}

	@Override
	public Power makeCopy(GameObject source) {
		return new PowerDouse(source);
	}

}
