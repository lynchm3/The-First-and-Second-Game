package com.marklynch.level.constructs.power;

import org.lwjgl.util.Point;

import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.animation.primary.AnimationPush;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.constructs.effect.EffectHeal;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.actors.Actor;
import com.marklynch.utils.ResourceUtils;

public class PowerHealRanged extends Power {

	private static String NAME = "Heal Self";

	public PowerHealRanged(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("action_heal.png", false), source,
				new Effect[] { new EffectHeal(source, null, 1) }, 5, null,
				new Point[] { new Point(0, 0), new Point(0, 1), new Point(0, -1), new Point(-1, 0), new Point(1, 0) },
				5, false, false, Crime.TYPE.CRIME_ASSAULT);
		selectTarget = true;
	}

	@Override
	public boolean check(GameObject source, Square targetSquare) {
		return true;
	}

	@Override
	public boolean checkRange(Actor source, Square targetSquare) {
		return true;
	}

	@Override
	public Power makeCopy(GameObject source) {
		return new PowerHealRanged(source);
	}

	@Override
	public void cast(final GameObject source, GameObject targetGameObject, Square targetSquare, final Action action) {
		source.setPrimaryAnimation(new AnimationPush(source, targetSquare, source.getPrimaryAnimation(), null));
		super.cast(source, targetGameObject, targetSquare, action);

	}
}
