package com.marklynch.level.constructs.power;

import org.lwjgl.util.Point;

import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectBurning;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.ResourceUtils;

public class PowerInferno extends Power {

	private static String NAME = "Inferno";

	public PowerInferno(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("action_burn.png"), source,
				new Effect[] { new EffectBurning(source, null, 3) }, 5,
				new Point[] { new Point(0, 0), new Point(0, 1), new Point(0, -1), new Point(-1, 0), new Point(1, 0) },
				10, true, true, Crime.CRIME_SEVERITY_ATTACK);
	}
}
