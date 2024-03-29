package com.marklynch.level.constructs.power;

import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.util.Point;

import com.marklynch.Game;
import com.marklynch.actions.Action;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.animation.primary.AnimationPush;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.actors.Actor;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.objects.inanimateobjects.Openable;
import com.marklynch.ui.ActivityLog;
import com.marklynch.utils.ResourceUtils;

public class PowerUnlock extends Power {

	private static String NAME = "UNLOCK";

	public PowerUnlock() {
		this(null);
	}

	public PowerUnlock(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("action_unlock.png", false), source, new Effect[] {}, 1, null,
				new Point[] { new Point(0, 0) }, 3, false, true, Crime.TYPE.CRIME_THEFT);
		selectTarget = true;
		illegalReason = Action.TRESPASSING;
	}

	@Override
	public void cast(GameObject source, GameObject targetGameObject, Square targetSquare, Action action) {
		source.setPrimaryAnimation(new AnimationPush(source, targetSquare, source.getPrimaryAnimation(), null));
		CopyOnWriteArrayList<GameObject> openables = targetSquare.inventory.getGameObjectsOfClass(Openable.class);
		for (GameObject openable : openables) {
			if (((Openable) openable).isLocked()) {
				((Openable) openable).unlock();
			}
		}
	}

	@Override
	public void log(GameObject performer, Square targetSquare) {
		Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " used ", name }));
		CopyOnWriteArrayList<GameObject> openables = targetSquare.inventory.getGameObjectsOfClass(Openable.class);
		for (GameObject openable : openables) {
			if (((Openable) openable).isLocked()) {
				Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " unlocked ", openable }));
			}
		}
	}

	@Override
	public boolean check(GameObject source, Square targetSquare) {
		CopyOnWriteArrayList<GameObject> openables = targetSquare.inventory.getGameObjectsOfClass(Openable.class);
		for (GameObject openable : openables) {
			if (((Openable) openable).isLocked()) {
				return true;
			}
		}
		disabledReason = Action.NOT_LOCKED;
		return false;
	}

	@Override
	public boolean checkRange(Actor source, Square targetSquare) {
		return true;
	}

	@Override
	public Power makeCopy(GameObject source) {
		return new PowerUnlock(source);
	}

	// @Override
	// public CopyOnWriteArrayList<Square> getAffectedSquares(Square target) {
	// // CopyOnWriteArrayList<Square> squares = new CopyOnWriteArrayList<Square>();
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
