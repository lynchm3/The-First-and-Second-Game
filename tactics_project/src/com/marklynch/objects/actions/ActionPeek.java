package com.marklynch.objects.actions;

import com.marklynch.Game;
import com.marklynch.level.constructs.Sound;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.GameObject;
import com.marklynch.objects.units.Actor;
import com.marklynch.ui.ActivityLog;

public class ActionPeek extends Action {

	public static final String ACTION_NAME = "Peek";
	public static final String ACTION_NAME_DISABLED = ACTION_NAME + " (can't reach)";

	Actor performer;
	GameObject object;

	public ActionPeek(Actor performer, GameObject object) {
		super(ACTION_NAME, "action_stop_hiding.png");
		this.performer = performer;
		this.object = object;

		legal = checkLegality();
		sound = createSound();
	}

	@Override
	public void perform() {

		if (!enabled)
			return;

		// object.actorsHidingHere.add(performer);
		if (performer.squareGameObjectIsOn.visibleToPlayer)
			Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " peeked through ", object }));

		if (performer == Game.level.player) {
			System.out.println("ActionPeek a");

			performer.peekingThrough = object;
			Game.level.player.peekingThrough = object;

			System.out.println("ActionPeek nulling peekSquare");
			Square peekSquare = null;
			if (object.squareGameObjectIsOn.xInGrid < performer.squareGameObjectIsOn.xInGrid) {
				peekSquare = Game.level.squares[performer.squareGameObjectIsOn.xInGrid
						- 2][performer.squareGameObjectIsOn.yInGrid];
				System.out.println("ActionPeek b");
			} else if (object.squareGameObjectIsOn.xInGrid > performer.squareGameObjectIsOn.xInGrid) {
				peekSquare = Game.level.squares[performer.squareGameObjectIsOn.xInGrid
						+ 2][performer.squareGameObjectIsOn.yInGrid];
				System.out.println("ActionPeek c");
			} else if (object.squareGameObjectIsOn.yInGrid < performer.squareGameObjectIsOn.yInGrid) {
				peekSquare = Game.level.squares[performer.squareGameObjectIsOn.xInGrid][performer.squareGameObjectIsOn.yInGrid
						- 2];
				System.out.println("ActionPeek d");
			} else if (object.squareGameObjectIsOn.yInGrid > performer.squareGameObjectIsOn.yInGrid) {
				peekSquare = Game.level.squares[performer.squareGameObjectIsOn.xInGrid][performer.squareGameObjectIsOn.yInGrid
						+ 2];
				System.out.println("ActionPeek e");
			}
			System.out.println("ActionPeek f peekSquare = " + peekSquare);
			performer.peekSquare = peekSquare;
			Game.level.player.peekSquare = peekSquare;
			System.out.println("ActionPeek g Game.level.player.peekSquare= " + Game.level.player.peekSquare);
			System.out.println("ActionPeek h player = " + Game.level.player);
			if (peekSquare != null)
				performer.calculateVisibleSquares(peekSquare);
		}

		performer.actionsPerformedThisTurn.add(this);
		if (sound != null)
			sound.play();
	}

	@Override
	public boolean check() {
		if (performer.straightLineDistanceTo(object.squareGameObjectIsOn) > 1) {
			return false;
		}
		return true;
	}

	@Override
	public boolean checkLegality() {
		if (object.squareGameObjectIsOn.restricted == true && !object.squareGameObjectIsOn.owners.contains(performer)) {
			return false;
		}
		return true;
	}

	@Override
	public Sound createSound() {
		return null;
	}

}