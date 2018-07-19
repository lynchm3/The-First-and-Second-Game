package com.marklynch.level.constructs.power;

import org.lwjgl.util.Point;

import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.ResourceUtils;

public class PowerSpiritBag extends Power {

	private static String NAME = "Spirit Bag";

	public PowerSpiritBag(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("bag.png", false), source, new Effect[] {}, 0,
				null, new Point[] { new Point(0, 0) }, 0, false, false, Crime.TYPE.NONE);
		passive = true;
		activateAtStartOfTurn = false;
	}

	@Override
	public boolean check(Actor source, Square targetSquare) {
		return true;
	}

	@Override
	public void cast(Actor source, GameObject targetGameObject, Square targetSquare, Action action) {

	}

	@Override
	public void log(Actor performer, Square target2) {
		// Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " used ",
		// name }));
	}

	@Override
	public Power makeCopy(GameObject source) {
		return new PowerSpiritBag(source);
	}
}
