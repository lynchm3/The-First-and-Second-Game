package com.marklynch.level.constructs.power;

import org.lwjgl.util.Point;

import com.marklynch.Game;
import com.marklynch.actions.Action;
import com.marklynch.level.Level;
import com.marklynch.level.constructs.Crime;
import com.marklynch.level.constructs.effect.Effect;
import com.marklynch.level.squares.Square;
import com.marklynch.objects.inanimateobjects.GameObject;
import com.marklynch.utils.Color;
import com.marklynch.utils.LineUtils;
import com.marklynch.utils.ResourceUtils;

public class PowerFindMatch extends Power {

	private static String NAME = "Find Match";
	private static GameObject targetToMatch = null;

	public PowerFindMatch() {
		this(null);
	}

	public PowerFindMatch(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("find_match.png", false), source, new Effect[] {}, Integer.MAX_VALUE,
				null, new Point[] { new Point(0, 0) }, 0, false, false, Crime.TYPE.NONE);
//		passive = true;
		endsTurn = false;
		draws = true;
		selectTarget = true;
		activateAtStartOfTurn = false;
		toggledOn = false;
	}

	@Override
	public boolean check(GameObject source, Square targetSquare) {
		return true;
	}

	@Override
	public void cast(GameObject source, GameObject targetGameObject, Square targetSquare, Action action) {

		Power playersFindMathchPower = Level.player.getPower(PowerFindMatch.class);
		if (playersFindMathchPower == null)
			return;

		if (playersFindMathchPower.target == null && targetGameObject != null) {
			playersFindMathchPower.target = targetToMatch = targetGameObject;
			playersFindMathchPower.toggledOn = true;
			playersFindMathchPower.selectTarget = false;
		} else {
			playersFindMathchPower.target = targetToMatch = null;
			playersFindMathchPower.toggledOn = false;
			playersFindMathchPower.selectTarget = true;
		}
	}

	@Override
	public void log(GameObject performer, Square target2) {
		// Game.level.logOnScreen(new ActivityLog(new Object[] { performer, " used ",
		// name }));
	}

	@Override
	public Power makeCopy(GameObject source) {
		return new PowerFindMatch(source);
	}

	@Override
	public void drawUI() {

		if (targetToMatch == null)
			return;

		Power playersFindMathchPower = Level.player.getPower(PowerFindMatch.class);
		if (playersFindMathchPower.toggledOn == false)
			return;

		Square square = targetToMatch.getWorldSquareGameObjectIsOn();
		if (square == null)
			return;
		float x1 = (Game.halfWindowWidth) + (Game.zoom
				* (square.xInGridPixels + Game.HALF_SQUARE_WIDTH - Game.halfWindowWidth + Game.getDragXWithOffset()));

		float y1 = (Game.halfWindowHeight) + (Game.zoom
				* (square.yInGridPixels + Game.HALF_SQUARE_HEIGHT - Game.halfWindowHeight + Game.getDragYWithOffset()));

		for (GameObject linkedGameObject : targetToMatch.linkedGameObjects) {
			Square linkedGameObjectSquare = linkedGameObject.getWorldSquareGameObjectIsOn();
			if (linkedGameObjectSquare == null)
				continue;

			float x2 = (Game.halfWindowWidth) + (Game.zoom * (linkedGameObjectSquare.xInGridPixels
					+ Game.HALF_SQUARE_WIDTH - Game.halfWindowWidth + Game.getDragXWithOffset()));

			float y2 = (Game.halfWindowHeight) + (Game.zoom * (linkedGameObjectSquare.yInGridPixels
					+ Game.HALF_SQUARE_HEIGHT - Game.halfWindowHeight + Game.getDragYWithOffset()));

			LineUtils.drawLine(Color.RED, x1, y1, x2, y2, 10);
		}
	}

}
