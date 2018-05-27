package com.marklynch.level.constructs.power;

import org.lwjgl.util.Point;

import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.ResourceUtils;

public class PowerRespite extends Power {

	private static String NAME = "Respite";

	public PowerRespite(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("bed.png", false), source, new Effect[] {}, 0,
				new Point[] { new Point(0, 0) }, 20, false, false, Crime.TYPE.NONE);
		passive = true;
	}

	@Override
	public boolean check(Actor source, Square targetSquare) {
		if (this.source.remainingHealth >= this.source.totalHealth) {
			return false;
		}

		if (source.hasNearbyAttackers()) {
			return false;
		}

		return true;
	}

	@Override
	public void cast(Actor source, Square targetSquare) {
		if (this.source.remainingHealth < this.source.totalHealth) {
			this.source.remainingHealth = this.source.totalHealth;
		}

	}

	@Override
	public Power makeCopy(GameObject source) {
		return null;
	}
}
