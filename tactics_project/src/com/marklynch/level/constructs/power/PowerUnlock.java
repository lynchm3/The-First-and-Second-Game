package com.marklynch.level.constructs.power;

import java.util.ArrayList;

import org.lwjgl.util.Point;

import com.marklynch.Game;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.Openable;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.ResourceUtils;

public class PowerUnlock extends Power {

	private static String NAME = "UNLOCK";

	public PowerUnlock(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("action_unlock.png"), source, new Effect[] {}, 1,
				new Point[] { new Point(0, 0) }, 5, false, true, Crime.TYPE.CRIME_THEFT);
	}

	@Override
	public void cast(Actor source, Square targetSquare) {
		ArrayList<GameObject> openables = targetSquare.inventory.getGameObjectsOfClass(Openable.class);
		for (GameObject openable : openables) {
			if (((Openable) openable).isLocked()) {
				((Openable) openable).unlock();
			}
		}
	}

	@Override
	public void log(Actor performer, Square targetSquare) {
		Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " used ", name }));
		ArrayList<GameObject> openables = targetSquare.inventory.getGameObjectsOfClass(Openable.class);
		for (GameObject openable : openables) {
			if (((Openable) openable).isLocked()) {
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " unlocked ", openable }));
			}
		}
	}

	@Override
	public boolean check(Actor source, Square targetSquare) {
		ArrayList<GameObject> openables = targetSquare.inventory.getGameObjectsOfClass(Openable.class);
		for (GameObject openable : openables) {
			if (((Openable) openable).isLocked()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean checkRange(Actor source, Square targetSquare) {
		return true;
	}

	// @Override
	// public ArrayList<Square> getAffectedSquares(Square target) {
	// // ArrayList<Square> squares = new ArrayList<Square>();
	// return Actor.getAllSquaresWithinDistance(0, target);
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
}
