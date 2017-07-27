package com.marklynch.level.constructs.power;

import org.lwjgl.util.Point;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.ResourceUtils;

public class PowerSuperPeek extends PowerRanged {

	private static String NAME = "SUPER PEEK";

	public PowerSuperPeek(GameObject source, GameObject target) {
		super(NAME, ResourceUtils.getGlobalImage("action_stop_hiding.png"), source, target, new Effect[] {},
				Integer.MAX_VALUE, new Point[] { new Point(0, 0) }, 10, true, true);
	}

	@Override
	public void cast(Actor source, Square targetSquare) {
		source.calculateVisibleSquares(targetSquare);
		source.peekSquare = targetSquare;
	}
}
