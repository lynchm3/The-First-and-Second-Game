package com.marklynch.level.constructs.power;

import org.lwjgl.util.Point;

import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectBleed;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.ResourceUtils;

public class PowerBleed extends Power {

	private static String NAME = "bleed";

	public PowerBleed(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("effect_bleed.png", false), source,
				new Effect[] { new EffectBleed(source, null, 3) }, 5, new Point[] { new Point(0, 0) }, 10, true,
				true, Crime.TYPE.CRIME_ASSAULT);
	}
}
