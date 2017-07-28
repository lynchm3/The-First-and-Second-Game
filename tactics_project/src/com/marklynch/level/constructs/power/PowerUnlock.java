package com.marklynch.level.constructs.power;

import java.util.ArrayList;

import org.lwjgl.util.Point;

import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Openable;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.ResourceUtils;

public class PowerUnlock extends Power {

	private static String NAME = "UNLOCK";

	public PowerUnlock(GameObject source, GameObject target) {
		super(NAME, ResourceUtils.getGlobalImage("action_unlock.png"), source, target, new Effect[] {},
				Integer.MAX_VALUE, new Point[] { new Point(0, 0) }, 1, false, true);
	}

	@Override
	public void cast(Actor source, Square targetSquare) {

		Openable openable = (Openable) targetSquare.inventory.getGameObjectOfClass(Openable.class);
		if (openable != null)
			openable.unlock();
	}

	@Override
	public ArrayList<Square> getAffectedSquares(Square target) {
		// ArrayList<Square> squares = new ArrayList<Square>();
		return Actor.getAllSquaresWithinDistance(0, target);

		// for (int i = -5; i <= 5; i++) {
		// for (int j = -5; j <= 5; j++) {
		// int x = i + target.xInGrid;
		// int y = j + target.yInGrid;
		// if (Square.inRange(x, y)) {
		// squares.add(Game.level.squares[x][y]);
		// }
		// }
		// }
		// return squares;
	}
}
