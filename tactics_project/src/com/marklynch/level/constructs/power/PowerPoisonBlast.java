package com.marklynch.level.constructs.power;

import org.lwjgl.util.Point;

import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectPoison;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.ResourceUtils;

public class PowerPoisonBlast extends Power {

	private static String NAME = "Poison";

	public PowerPoisonBlast(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("action_poison.png"), source,
				new Effect[] { new EffectPoison(source, null, 3) }, 10,
				new Point[] { new Point(0, 0), new Point(0, 1), new Point(0, -1), new Point(-1, 0), new Point(1, 0) },
				10, true, true, Crime.CRIME_SEVERITY_ATTACK);
	}
}