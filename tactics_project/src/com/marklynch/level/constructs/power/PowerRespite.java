package com.marklynch.level.constructs.power;

import org.lwjgl.util.Point;

import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.ResourceUtils;

public class PowerRespite extends Power {

	private static String NAME = "Respite";

	public PowerRespite(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("bed.png", false), source, new Effect[] {}, 0, null,
				new Point[] { new Point(0, 0) }, 0, false, false, Crime.TYPE.NONE);
		passive = true;
		activateAtStartOfTurn = true;
	}

	@Override
	public boolean check(GameObject source, Square targetSquare) {
		if (this.source.remainingHealth >= this.source.totalHealth) {
			return false;
		}

		if (source instanceof Actor && ((Actor) source).hasNearbyAttackers()) {
			return false;
		}

		return true;
	}

	@Override
	public void cast(GameObject source, GameObject targetGameObject, Square targetSquare, Action action) {
		if (this.source.remainingHealth < this.source.totalHealth) {
			this.source.remainingHealth += this.source.totalHealth / 20;
			if (this.source.remainingHealth > this.source.totalHealth) {
				this.source.remainingHealth = this.source.totalHealth;
			}
		}
	}

	@Override
	public Power makeCopy(GameObject source) {
		return new PowerRespite(source);
	}
}
