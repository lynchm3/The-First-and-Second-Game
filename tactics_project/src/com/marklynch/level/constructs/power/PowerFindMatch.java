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
	private static GameObject target = null;

	public PowerFindMatch() {
		this(null);
	}

	public PowerFindMatch(GameObject source) {
		super(NAME, ResourceUtils.getGlobalImage("find_match.png", false), source, new Effect[] {}, Integer.MAX_VALUE,
				null, new Point[] { new Point(0, 0) }, 0, false, false, Crime.TYPE.NONE);
//		passive = true;
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
		// sumthing like this, altho omne will happen after u press on target, other
		// when u press on obj
		// maybe i change the state between 2 click and one click

		System.out.println("PowerFindMatch.cast() a");
		System.out.println("target = " + target);
		System.out.println("targetGameObject = " + targetGameObject);

		if (target == null && targetGameObject != null) {
			System.out.println("PowerFindMatch.cast() b");
			Power playersFindMathchPower = Level.player.getPower(PowerFindMatch.class);
			playersFindMathchPower.target = targetGameObject;
			playersFindMathchPower.toggledOn = true;
			playersFindMathchPower.selectTarget = false;
		} else {
			Power playersFindMathchPower = Level.player.getPower(PowerFindMatch.class);
			playersFindMathchPower.target = null;
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

		if (target == null)
			return;

		if (toggledOn == false)
			return;

		float x1 = (Game.halfWindowWidth) + (Game.zoom * (target.squareGameObjectIsOn.xInGridPixels
				+ Game.HALF_SQUARE_WIDTH - Game.halfWindowWidth + Game.getDragXWithOffset()));

		float y1 = (Game.halfWindowHeight) + (Game.zoom * (target.squareGameObjectIsOn.yInGridPixels
				+ Game.HALF_SQUARE_HEIGHT - Game.halfWindowHeight + Game.getDragYWithOffset()));

		for (GameObject linkedGameObject : target.linkedGameObjects) {
			float x2 = (Game.halfWindowWidth) + (Game.zoom * (linkedGameObject.squareGameObjectIsOn.xInGridPixels
					+ Game.HALF_SQUARE_WIDTH - Game.halfWindowWidth + Game.getDragXWithOffset()));

			float y2 = (Game.halfWindowHeight) + (Game.zoom * (linkedGameObject.squareGameObjectIsOn.yInGridPixels
					+ Game.HALF_SQUARE_HEIGHT - Game.halfWindowHeight + Game.getDragYWithOffset()));

			LineUtils.drawLine(Color.RED, x1, y1, x2, y2, 10);
		}

		// cast on an object? to see what it has a connection to.
		// key-door

//		ehhhhhh
		// dunno how this wull work...
		// object ur holding to other shit...
		// do keys have a list of objs that they open?
		// i did not think this thruu
	}

}
