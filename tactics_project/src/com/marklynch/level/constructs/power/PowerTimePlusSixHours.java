package com.marklynch.level.constructs.power;

import org.lwjgl.util.Point;

import com.marklynch.Game;
import com.marklynch.actions.Action;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.ResourceUtils;

public class PowerTimePlusSixHours extends Power {

	private static String NAME = "Time Plus Six Hours";

	public PowerTimePlusSixHours() {
		this(null);
	}

	public PowerTimePlusSixHours(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("power_time_plus_six_hours.png", false), source, new Effect[] {}, 0,
				null, new Point[] { new Point(0, 0) }, 0, false, false, Crime.TYPE.NONE);
	}

	@Override
	public void cast(GameObject source, GameObject targetGameObject, Square targetSquare, Action action) {
		// Game.level.changeTime(60 * 60 * 6);
		Game.level.changeTime(60 * 60 * 10);
	}

	@Override
	public Power makeCopy(GameObject source) {
		return new PowerTimePlusSixHours(source);
	}
}
