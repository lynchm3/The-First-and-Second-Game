package com.marklynch.level.constructs.power;

import org.lwjgl.util.Point;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectHeal;
import com.marklynch.objects.GameObject;
import com.marklynch.utils.ResourceUtils;

public class PowerHealSelf extends Power {

	private static String NAME = "Heal Self";

	public PowerHealSelf(GameObject source, GameObject target) {
		super(NAME, ResourceUtils.getGlobalImage("action_heal.png"), source, target,
				new Effect[] { new EffectHeal(source, target, 1) }, 0, new Point[] { new Point(0, 0) }, 5, false,
				false);
	}
}
