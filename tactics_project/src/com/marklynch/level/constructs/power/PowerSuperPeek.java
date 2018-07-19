package com.marklynch.level.constructs.power;

import org.lwjgl.util.Point;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.animation.primary.AnimationPush;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.actions.Action;
import com.marklynch.objects.units.Actor;
import com.marklynch.utils.ResourceUtils;

public class PowerSuperPeek extends Power {

	private static String NAME = "SUPER PEEK";

	public PowerSuperPeek(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("action_stop_hiding.png", false), source, new Effect[] {},
				Integer.MAX_VALUE, null, new Point[] { new Point(0, 0) }, 0, false, false, Crime.TYPE.NONE);
		selectTarget = true;
	}

	@Override
	public void cast(Actor source, GameObject targetGameObject, Square targetSquare, Action action) {
		source.setPrimaryAnimation(new AnimationPush(source, targetSquare, source.getPrimaryAnimation()));
		if (source == Game.level.player)
			Game.level.player.calculateVisibleSquares(targetSquare);
		source.peekSquare = targetSquare;
	}

	// @Override
	// public ArrayList<Square> getAffectedSquares(Square target) {
	// // ArrayList<Square> squares = new ArrayList<Square>();
	// return Actor.getAllSquaresWithinDistance(0, 10, target);
	//
	// // for (int i = -5; i <= 5; i++) {
	// // for (int j = -5; j <= 5; j++) {
	// // int x = i + target.xInGrid;
	// // int y = j + target.yInGrid;
	// // if (Square.inRange(x, y)) {
	// // squares.add(Game.level.squares[x][y]);
	// // }
	// // }
	// // }
	// // return squares;
	// }

	@Override
	public Power makeCopy(GameObject source) {
		return new PowerSuperPeek(source);
	}
}
