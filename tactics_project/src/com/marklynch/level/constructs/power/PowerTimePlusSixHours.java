package com.marklynch.level.constructs.power;

import org.lwjgl.util.Point;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.ResourceUtils;

public class PowerTimePlusSixHours extends Power {

	private static String NAME = "Time Plus Six Hours";

	public PowerTimePlusSixHours(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("power_time_plus_six_hours.png", false), source, new Effect[] {}, 0,
				new Point[] { new Point(0, 0) }, 20, false, false, Crime.TYPE.NONE);
	}

	@Override
	public void cast(Actor source, Square targetSquare) {
		Game.level.changeTime(60 * 60 * 6);
	}

	@Override
	public Power makeCopy(GameObject source) {
		return new PowerTimePlusSixHours(source);
	}
}
