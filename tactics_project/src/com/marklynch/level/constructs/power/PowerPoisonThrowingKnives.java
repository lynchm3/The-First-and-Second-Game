package com.marklynch.level.constructs.power;

import org.lwjgl.util.Point;

import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.animation.primary.AnimationPush;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectPoison;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.ResourceUtils;

public class PowerPoisonThrowingKnives extends Power {

	private static String NAME = "Poison";

	public PowerPoisonThrowingKnives(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("action_poison.png", false), source,
				new Effect[] { new EffectPoison(source, null, 3) }, 10,
				null,
				new Point[] { new Point(0, 0), new Point(0, 1), new Point(0, -1), new Point(-1, 0), new Point(1, 0) }, 1, true, true, Crime.TYPE.CRIME_ASSAULT);
		selectTarget = true;
	}

	@Override
	public Power makeCopy(GameObject source) {
		return new PowerPoisonThrowingKnives(source);
	}

	@Override
	public void cast(final Actor source, GameObject targetGameObject, Square targetSquare, final Action action) {
		source.setPrimaryAnimation(new AnimationPush(source, targetSquare, source.getPrimaryAnimation()));
		super.cast(source, targetGameObject, targetSquare, action);

	}
}
